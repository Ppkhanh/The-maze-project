package game_4_ui;

import static game_4_algo.GamePlay.COLS;
import static game_4_algo.GamePlay.ROWS;
import static game_4_algo.GamePlay.bar_size;
import static game_4_algo.GamePlay.grid;
import static game_4_algo.GamePlay.pauseClicked;
import static game_4_algo.GamePlay.tile_size;
import static game_4_main.Auswahlbereich.freezeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Pause extends JComponent {

	private CustomFont pixAntiqua = new CustomFont(48);
	
	private int offset = bar_size;
	
	
	public Pause() {
		
	}
	
	public void draw(Graphics g) {
		((Graphics2D) g).setFont(pixAntiqua.getCustomFont());
		
		int x2 = grid[COLS/2-2][ROWS/2].x*tile_size + offset/3;
		int y2 = grid[COLS/2-2][ROWS/2].y*tile_size + offset + 10;
		
		if(pauseClicked && !freezeGame) {
			g.setColor(Color.RED);
			g.drawString("Pause", x2, y2);
		}
		
	}
	
}
