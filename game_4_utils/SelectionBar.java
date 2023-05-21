package game_4_utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import game_4_objects.Store;

public class SelectionBar extends JComponent {

	private int x, y, width, height;
	
	public static Store store;
	
	public SelectionBar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		store = new Store(10, 5, height); 
	}
	
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(234, 226, 213, 123));
		g2.fillRect(x, y, width, height);
		
		store.draw(g2);
	}
	
}
