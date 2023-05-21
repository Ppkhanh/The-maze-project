package layout_frame;

import mazegen.MazePanel;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;

import mazegen.Block;

import java.awt.Image;
import java.io.IOException;

public class Player {


	private static int player_size = MazePanel.block_size/3*2; //width and height of player
    
    private int x ; //coordinates of player
    private int y;
	
    
    public Block[][] blocks_player;
    private int rP , cP = rP;	
    
	//Needed for checking for permitted movement
    public Block current;
	public Block[][] c;
    
	//Player image
	private Image down;
	private Image up;
	private Image left;
	private Image right;	
	private Image sad_down;
	private Image sad_up;
	private Image sad_left;
	private Image sad_right;
	private Image image;
	
	private int move_counter;
	private boolean costume_change;
	
    public Image getImg(String direction) {
			//DIFFERENT IMAGES FOR DIFFERENT MOVEMENt
    	if(isCostume_change() == false) {
			switch(direction) {
			case "up":
				image =getUp();
				break;
			case "down":
				image = getDown();
				break;
			case "right":
				image = getRight();
				break;
			case "left":
				image = getLeft();
				break;
				}
		}
		else{
			switch(direction) {
			case "up":
				image = getSad_up();
				break;
			case "down":
				image = getSad_down();
				break;
			case "right":
				image = getSad_right();
				break;
			case "left":
				image = getSad_left();
				break;
			}
		}
    	return image;
	}

	public void setImg(Image img) {
		this.image = img;
	}
	
	public Player() {
		rP = 9;
		cP = rP;
		c = MazePanel.getBlocks();
		current = c[9][9];
		setX(current.getX1()); 
		setY(current.getY1());

    try {
    	down = ImageIO.read(getClass().getClassLoader().getResource("resources/DOWN_FIG.png"));
    	down = down.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
    	up = ImageIO.read(getClass().getClassLoader().getResource("resources/UP_FIG.png"));
		up = up.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
		left = ImageIO.read(getClass().getClassLoader().getResource("resources/LEFT_FIG.png"));
		left = left.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
		right = ImageIO.read(getClass().getClassLoader().getResource("resources/RIGHT_FIG.png"));
		right = right.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
		sad_down = ImageIO.read(getClass().getClassLoader().getResource("resources/down_sad.png"));
		sad_down = sad_down.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
		sad_up = ImageIO.read(getClass().getClassLoader().getResource("resources/up_sad.png"));
		sad_up = sad_up.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
		sad_left = ImageIO.read(getClass().getClassLoader().getResource("resources/left_sad.png"));
		sad_left = sad_left.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
		
		sad_right = ImageIO.read(getClass().getClassLoader().getResource("resources/right_sad.png"));
		sad_right = sad_right.getScaledInstance(player_size, player_size, Image.SCALE_DEFAULT);
    	} 
    catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void move(int x, int y){
		rP = rP + y;
	    cP = cP + x;
	    setCurrent(c[rP][cP]);
	    this.x = current.getX1();
	    this.y = current.getY1();
	    mazegen.Block.setAlreadyVisited(true);;
	    mazegen.MazePanel.AlreadyVisited();
	}

    public Image getDown() {
		return down;
	}
	public Image getUp() {
		return up;
	}
	public Image getLeft() {
		return left;
	}
	public Image getRight() {
		return right;
	}
	public Image getSad_down() {
		return sad_down;
	}

	public void setSad_down(Image sad_down) {
		this.sad_down = sad_down;
	}

	public Image getSad_up() {
		return sad_up;
	}

	public void setSad_up(Image sad_up) {
		this.sad_up = sad_up;
	}

	public Image getSad_left() {
		return sad_left;
	}

	public void setSad_left(Image sad_left) {
		this.sad_left = sad_left;
	}

	public Image getSad_right() {
		return sad_right;
	}

	public void setSad_right(Image sad_right) {
		this.sad_right = sad_right;
	}
	
	public Block getCurrent() {
		return current;
	}

	public void setCurrent(Block current) {
		this.current = current;
	}
	
	public int getX(){
	    return x;
	}
	public int getY(){
	    return y;
	}
	public void setX(int x){
	    this.x = x;
	}
	public void setY(int y){
	    this.y = y;
	}

	public int getMove_counter() {
		return move_counter;
	}

	public void setMove_counter(int move_counter) {
		this.move_counter = move_counter;
	}
	public int getPlayer_size() {
		return Player.player_size;
	}

	public void setPlayer_size(int player_size) {
		Player.player_size = player_size;
	}

	public int getrP() {
		return rP;
	}
	
	public void setrP(int rP) {
		this.rP = rP;
	}

	public int getcP() {
		return cP;
	}
	
	public void setcP(int cP) {
		this.cP = cP;
	}
	
	public boolean isCostume_change() {
		return costume_change;
	}

	public void setCostume_change(boolean costume_change) {
		this.costume_change = costume_change;
	}

	
}

