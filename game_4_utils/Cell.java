package game_4_utils;

import static game_4_algo.GamePlay.COLS;
import static game_4_algo.GamePlay.ROWS;
import static game_4_algo.GamePlay.bar_size;
import static game_4_algo.GamePlay.fillCell;
import static game_4_algo.GamePlay.fillRemove;
import static game_4_algo.GamePlay.getThrone;
import static game_4_algo.GamePlay.mst;
import static game_4_algo.GamePlay.tile_size;
import static game_4_algo.SafetyDistanceGen.allVillies;
import static game_4_algo.SafetyDistanceGen.calculateNearestVillageWithCastle;
import static game_4_algo.SafetyDistanceGen.removeCastleProtection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import game_4_algo.GamePlay;
import game_4_objects.Store;
import game_4_ui.Village;

public class Cell extends Rectangle {
	public int x, y;
	private int offset = bar_size;

	private Timer timer = new Timer();
	
	private Village village = new Village();
	
	public int oldPosX;
	public int oldPosY;
	
	public boolean hasCastle;
	public static boolean clicked;
	public boolean hasVillage;
	private boolean center;	
	public double distance;
	public boolean protectedbyC;
	public boolean closestProtection;
	
	public static boolean conditionsRepaint;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		
		oldPosX = Integer.MIN_VALUE;
		oldPosY = Integer.MIN_VALUE;
		
		this.distance = Integer.MIN_VALUE;
		this.hasCastle = false;
		this.hasVillage = false;
		clicked = false;
	}
	
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		int x2 = x*tile_size + offset/3;
		int y2 = y*tile_size + offset + 10;
		
		//each cell is a rectangle that has bounds given; later necessary for drag/drop!
		Rectangle rect = new Rectangle(x2, y2, tile_size, tile_size);
		setBounds(rect);
		
		g.setColor(Color.BLACK);
		g.drawRect(x2, y2, tile_size, tile_size);
	
		if(!hasCastle && hasVillage) {//if a cell contains a village, then draw it:
			g2.setColor(new Color(255, 105, 125, 130)); //51, 129, 101, 100
			g2.fillRect(x2, y2, tile_size, tile_size);
			g.drawImage(village.getSprite(), x2, y2, tile_size, tile_size, null);
		}
		if(hasCastle && hasVillage) { 
			g.drawImage(village.getSprite2(), x2, y2, tile_size, tile_size, null);
			
		}
		if(contains(mst) && hasVillage && !hasCastle) {
			g2.setColor(new Color(88, 252, 146));
			g2.fillRect(x2, y2, tile_size, tile_size);
			
			g2.drawImage(village.getSprite(), x2, y2, tile_size, tile_size, null);
		}
		if(protectedbyC && hasVillage && !hasCastle) { 
			g2.setColor(new Color(0, 235, 2, 150));
			g2.fillRect(x2, y2, tile_size, tile_size);
			
			g2.drawImage(village.getSprite(), x2, y2, tile_size, tile_size, null);
		}
		if(closestProtection && !hasCastle & !Store.holdsItem) {
			g2.setColor(new Color(188, 255, 183));
			g2.fillRect(x2, y2, tile_size, tile_size);
			
			g2.drawImage(village.getSprite(), x2, y2, tile_size, tile_size, null);
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					closestProtection = false;
				}
			};
			timer.schedule(task, 3000);
		}
		if(contains(getThrone()) && hasCastle && hasVillage) {
			g2.setColor(new Color(255, 254, 204, 150));
			g2.fillRect(x2, y2, tile_size, tile_size);
			
		}
	}
	
	public static void click(int mouseButton) {
		
		if(mouseButton == 1) {
			//We click on the item and can place castles:
			if(Store.holdsItem && GamePlay.castleNum > 0) {
				
				for(int x = 0; x < GamePlay.COLS; x++) {	
					for(int y = 0; y < GamePlay.ROWS; y++) {
						
						if(GamePlay.grid[x][y].getBounds().contains(mst)) {
							if(!fillCell.contains(GamePlay.grid[x][y]) 
								&& GamePlay.grid[x][y].hasVillage && GamePlay.grid[x][y].hasCastle == false) {
								
								fillCell.add(GamePlay.grid[x][y]);
								Store.holdsItem = false;
								GamePlay.castleNum -= 1;
								
							}
						}
						if(mouseOutOfBounds()) { // && !oldCell.contains(GamePlay.grid[x][y])
							Store.holdsItem = false;
						}
//						else if(mouseOutOfBounds() && oldCell.contains(GamePlay.grid[x][y])) {
//							fillCell.add(GamePlay.grid[x][y]);
//							castleNum -= 1;
//						}
					}
				}
			}	
		else {
			//If we pick up the castle AFTER placing it:
			for(int x = 0; x < GamePlay.COLS; x++) {	
				for(int y = 0; y < GamePlay.ROWS; y++) {
					
					if(GamePlay.grid[x][y].getBounds().contains(mst)) {//if we can pick up the castle:
						if(GamePlay.grid[x][y].hasCastle == true && Store.holdsItem == false) { 
							fillCell.remove(GamePlay.grid[x][y]); 
							removeCastleProtection(GamePlay.grid[x][y]);
							fillRemove.add(GamePlay.grid[x][y]);
							Store.holdsItem = true;
							GamePlay.castleNum += 1;
							}
						}
					}
				}
			}
		if(!Store.holdsItem) {
			for(Cell v : allVillies) {
				if(v.contains(mst) ) {
					clicked = true;
					calculateNearestVillageWithCastle(allVillies, v);
				}
			}
		}
	  }
	}
	
	/**Checks if mouse is out of bounds when item is held
	 * 
	 * @return true if out of bounds
	 */
	public static boolean mouseOutOfBounds() {
		for(int x = 0; x < GamePlay.COLS; x++) {	
			for(int y = 0; y < GamePlay.ROWS; y++) {
				if(!Store.c_button.contains(mst)) {
					if (mst.x < GamePlay.grid[0][y].getBounds().x || mst.x > GamePlay.grid[COLS-1][y].getBounds().getMaxX()
					 || mst.y < GamePlay.grid[x][0].getBounds().y || mst.y > GamePlay.grid[x][ROWS-1].getBounds().getMaxY()){ 
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public boolean isHasVillage() {
		return hasVillage;
	}

	public void setHasVillage(boolean hasVillage) {
		this.hasVillage = hasVillage;
	}
	
	public boolean isCenter() {
		return center;
	}
	
	public void setCenter(boolean center) {
		this.center = center;
	}
	
}
