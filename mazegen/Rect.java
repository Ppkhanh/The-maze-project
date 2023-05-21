package mazegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

//Player Figure - Rectangle
public class Rect extends JComponent  {
	private int i=0; //Subscript i of two-dimensional array
	private int j=0; //Subscript j of two-dimensional array
	
	private int x=0; //x coordinate
	private int y=0; //y coordinate
	
	private int h=0; //Width and height
	private boolean playerTouched;
	
	//Font, PLAIN 0/BOLD 1/ITALIC 2, FontSize
	private Font myFont = new Font("Comic Sans MS", 1, MazePanel.block_size/5);
	private Font myFontTOUCHED = new Font("Comic Sans MS", 1, MazePanel.block_size/3);
	
	//Constructor
	public Rect(int i,int j,int h){
		this.i=i;
		this.j=j;
		this.h=h;
		
		this.playerTouched = false;
	}
	
	
	//initialization
	private void init() {
		this.x = j*h+2;
		this.y = i*h+2;
	}
	
	
	//draw the actual 
	void draw(Graphics g, Color c, String s){
		//Calculate x and y coordinates
		init();

		g.setColor(c);
		g.fillRect(x, y, h-5, h-5);
		
		//Text on Rectangles
		g.setColor(Color.BLACK);
		g.setFont (myFont);
		
		if(this.playerTouched == false) {
			g.drawString(s, x, y + MazePanel.block_size/2);
		}
		else {	//if player touched game:
			g.setColor(Color.RED);
			g.setFont(myFontTOUCHED);
			g.drawString(s, x , y - MazePanel.block_size/4);
		}
	}

	
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	
	public boolean isPlayerTouched() {
		return playerTouched;
	}

	public void setPlayerTouched(boolean playerTouched) {
		this.playerTouched = playerTouched;
	}




}