package game_4_utils;

import static game_4_algo.GamePlay.bar_size;
import static game_4_algo.GamePlay.mst;
import static game_4_algo.GamePlay.tile_size;
import static game_4_algo.SafetyDistanceGen.allVillies;
import static game_4_algo.SafetyDistanceGen.calculateNearestVillageWithCastle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import game_4_algo.GamePlay;
import game_4_ui.Village;

public class Intersection extends Rectangle{
	
//	
//	public static boolean clicked;
//	public int x, y;
//	
//	private int offset = bar_size;
//	
//	private boolean village; //if we pick this, chosen = true
//	private boolean center;	//if this got made center
//	
//	public double distance;
//	
//	public boolean protectedbyC;
//	public boolean closestProtection;
//	
//	private Village villageImg = new Village();
//	
//	private Timer timer = new Timer();
//	
//	private int size;
//	
//	public Intersection(int x, int y) {
//		this.x = x;
//		this.y = y;
//		
//		this.village = false;
//		this.distance = Integer.MIN_VALUE;
//		
//		this.protectedbyC = false;
//		clicked = false;
//		this.closestProtection = false;
//		
//	}
//	
//	public void draw(Graphics g) {
//		Graphics2D g2 = (Graphics2D) g;
//		
//		int x2 = x*tile_size + offset/3;
//		int y2 = y*tile_size + offset + 10;
//		
//		int sizeOffset = size/2;
//		
//		int posX2 = x2 - sizeOffset;
//		int posY2 = y2 - sizeOffset;
//		
//		
//		Rectangle rect = new Rectangle(posX2, posY2, size, size);
//		setBounds(rect);
//		
//		g2.setStroke(new BasicStroke(8));
//		
//		if(village) {
//			g2.setColor(new Color(234, 226, 213, 123));
//			g2.fillRect(posX2 , posY2,  size, size);
//			
//			g2.drawImage(villageImg.getSprite(), posX2, posY2, size , size , null);
//		}
//		
//		//PAINTING IF MOUSE INSIDE 
//		if(contains(mst) && village) {
//			g2.setColor(new Color(51, 139, 101));
//			g2.fillRect(posX2 , posY2, size, size);
//			
//			g2.drawImage(villageImg.getSprite(), posX2, posY2, size, size, null);
//		}
//		
//		if(protectedbyC && (center || village)) { //maybe
//			g2.setColor(new Color(51, 129, 101));
//			g2.fillRect(posX2 , posY2,  size, size);
//			
//			g2.drawImage(villageImg.getSprite(), posX2, posY2, size, size, null);
//		}
//		
//		if(closestProtection) {
//			g2.setColor(new Color(71, 255, 176));
//			g2.fillRect(posX2, posY2, size, size);
//						
//			g2.drawImage(villageImg.getSprite(), posX2, posY2, size, size, null);	
//			
//			TimerTask task = new TimerTask() {
//				@Override
//				public void run() {
//					closestProtection = false;
//				}
//			};
//			timer.schedule(task, 3000);
//		}
//		
//	
//	}
//	
//	public static void click(int button) {
//		if(button == 1) {
//			for(Intersection v : allVillies) {
//				if(v.contains(mst)) {
//					clicked = true;
//					calculateNearestVillageWithCastle(allVillies, v);
//				}
//			}
//			
//		}
//	}
//	
//	
//	
//	@Override
//	public double getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	@Override
//	public double getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public boolean isVillage() {
//		return village;
//	}
//
//	public void setVillage(boolean chosen) {
//		this.village = chosen;
//	}
//
//	public boolean isCenter() {
//		return center;
//	}
//
//	public void setCenter(boolean center) {
//		this.center = center;
//	}
//
//	public int getVillageSize() {
//		return size;
//	}
//
//	public void setVillageSize(int size) {
//		this.size = size;
//	}

	
}
