package Spiel3;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class Block {
	private Spielpanel panel;
	
	
	
	
	
	//2D array variables:
	private int i = 0;
	private int j = 0;
	
	private int index = -1;

	
	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}

	//Width and height of Block
	private int h = 0;
	
	//4 vertex coordinates
	private int x1 = 0;
	public int getX1() {
		return x1;
	}


	public void setX1(int x1) {
		this.x1 = x1;
	}

	private int y1 = 0;
	public int getY1() {
		return y1;
	}


	public void setY1(int y1) {
		this.y1 = y1;
	}

	private int x2 = 0;
	private int y2 = 0;
	private int x3 = 0;
	private int y3 = 0;
	private int x4 = 0;
	private int y4 = 0;
	
	private boolean[] walls = new boolean[4];

	//If Block has been accessed
	private boolean visited = false;
	
//	// if Block has been visited
//	private static boolean alreadyVisited;
	
//	public static boolean isAlreadyVisited() {
//		return alreadyVisited;
//	}
//	
//	public static void setAlreadyVisited(boolean AlreadyVisited) {
//		alreadyVisited = AlreadyVisited;
//	}
	
	
	//constructor of the Block
		public Block(int i, int j, Spielpanel panel){
			this.i = i;
			this.j = j;
			this.panel = panel;
			
			
			
			//Calculate the coordinates of the 4 vertices
			init();
		}
	
	
	//Default: the upper, lower, left and right walls are displayed
	private void init() {
		walls[0] = true;
		walls[1] = true;
		walls[2] = true;
		walls[3] = true;
	}

	public void updateCoords() {
		int h = Spielpanel.block_size;
			//i for row and j for column
		//Upper left coordinate
		this.x1 = j*h;		
		this.y1 = i*h;
		//Upper right coordinate
		this.x2 = (j+1)*h;
		this.y2 = i*h;
		//Lower right coordinate
		this.x3 = (j+1)*h;
		this.y3 = (i+1)*h;
		//Lower left coordinate
		this.x4 = j*h;
		this.y4 = (i+1)*h;
	}
	
	//Method of drawing indicator
	public void draw(Graphics g) {
		updateCoords();
//		drawBlock(g);
	}
	
	//Draw maze block
	private void drawBlock(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//Judge the upper, right, lower and left walls. If true, there will be walls, otherwise there will be no walls
		boolean top = walls[0];
		boolean right = walls[1];
		boolean bottom = walls[2];
		boolean left = walls[3];
		
		g2.setStroke(new BasicStroke(4));
		if(top) {//Draw upper wall
			g2.drawLine(x1, y1, x2, y2);
		}
		if(right) {//Draw right wall
			g2.drawLine(x2, y2, x3, y3);	
		}
		if(bottom) {//Draw the lower wall
			g2.drawLine(x3, y3, x4, y4);	
		}
		if(left) {//Draw left wall
			g2.drawLine(x4, y4, x1, y1);	
		}
	}
	
	//Find out whether the current Block has an unreachable neighbor cell
//	public List<Block> findNeighbors(int position) {
//		List<Block> res = new ArrayList<Block>();
//		Block top    = this.getNeighbor(0,false, position);
//		Block right  = this.getNeighbor(1,false, position);
//		Block bottom = this.getNeighbor(2,false, position);
//		Block left   = this.getNeighbor(3,false, position);
//		
//		if(top != null){
//			res.add(top);
//		}
//		if(right != null){
//			res.add(right);
//		}
//		if(bottom != null){
//			res.add(bottom);
//		}
//		if(left != null){
//			res.add(left);
//		}
//		return res;
//	}
	
	//Get neighbors according to direction
//	public Block getNeighbor(int type, boolean lose_visited, int position) {
//		Block neighbor;
//		int neighbor_i = 0, neighbor_j = 0;
//		
//		switch(type) {
//			case 0:
//				neighbor_i = this.i-1;
//				neighbor_j = this.j;
//				break;
//			case 1:
//				neighbor_i = this.i;
//				neighbor_j = this.j+1;
//				break;
//			case 2:
//				neighbor_i = this.i+1;
//				neighbor_j = this.j;
//				break;
//			case 3:
//				neighbor_i = this.i;
//				neighbor_j = this.j-1;
//				break;
//		}
//		
//		Block[][] blocks = panel.getBlocks();
//		ArrayList<Integer> boundaries = getBoundaries(position);
//		
//		//It's beyond the boundary, therefore no neighbor
//		if(neighbor_i < boundaries.get(0) || neighbor_j < boundaries.get(1) || neighbor_i >= boundaries.get(2) || neighbor_j >= boundaries.get(3)) {
//			neighbor = null;
//		}
//		//The dimensions are permitted, neighbor is found
//		else {
//			neighbor = blocks[neighbor_i][neighbor_j];
//			//Judge whether it is accessed. If it is accessed, null is returned
//			if(neighbor.visited && !lose_visited) {//lose_visited equals true to ignore access
//				neighbor = null;
//			}
//		}
//		return neighbor;
//	}

//	private ArrayList<Integer> getBoundaries(int position) {
//		ArrayList<Integer> boundaries = new ArrayList<Integer>();
//		// ROW_boundary, COL_boundary, panel_ROW_boundary, panel_COL_boundary
//		switch(position) {
//		case 1: //OBEN LINKS
//			boundaries.add(1);	
//			boundaries.add(1);	
//			boundaries.add(panel.ROWS/2);	
//			boundaries.add(panel.COLS/2);	
//			break;
//			
//		case 2: //OBEN RECHTS
//			boundaries.add(1);
//			boundaries.add(panel.COLS/2);
//			boundaries.add(panel.ROWS/2);
//			boundaries.add(panel.COLS-1);
//			break;
//		
//		case 3: //UNTEN LINKS
//			boundaries.add(panel.ROWS/2);
//			boundaries.add(1);
//			boundaries.add(panel.ROWS-1);
//			boundaries.add(panel.COLS/2);
//			break;
//			
//		case 4: //UNTEN RECHTS 
//			boundaries.add(panel.ROWS/2);
//			boundaries.add(panel.COLS/2);
//			boundaries.add(panel.ROWS-1);
//			boundaries.add(panel.COLS-1);
//			break;
//		}
//		return boundaries;
//	}

	//Shortest path:
//	public List<Block> getValidMoveNeighborsTEST() {
//		List<Block> neighbours = new ArrayList<Block>(4);
//			
//		Block top = getValidNeighborTEST(0);
//		Block right = getValidNeighborTEST(1);
//		Block bottom = getValidNeighborTEST(2);
//		Block left = getValidNeighborTEST(3);
//			
//		if (top != null) {
//			if (!this.getWalls()[0]) {
//				neighbours.add(top);	//if theres no wall from current one
//			}
//		}			
//		if (right != null) {
//			if (!this.getWalls()[1]) {
//				neighbours.add(right);
//			}
//		}	
//		if (bottom != null) {
//			if (!this.getWalls()[2]) {
//				neighbours.add(bottom);
//			}
//		}
//		if (left != null) {
//			if (!this.getWalls()[3]) {
//				neighbours.add(left);
//			}
//		}
//		return neighbours;
//	}
//	
//	public Block getValidNeighborTEST(int type) {
//		Block neighbor;
//		int neighbor_i = 0, neighbor_j = 0;
//		
//		switch(type) {
//			case 0:
//				neighbor_i = this.i-1;
//				neighbor_j = this.j;
//				break;
//			case 1:
//				neighbor_i = this.i;
//				neighbor_j = this.j+1;
//				break;
//			case 2:
//				neighbor_i = this.i+1;
//				neighbor_j = this.j;
//				break;
//			case 3:
//				neighbor_i = this.i;
//				neighbor_j = this.j-1;
//				break;
//		}
//		
//		Block[][] blocks = panel.getBlocks();
//		
//		if(neighbor_i < 1 || neighbor_j < 1 || neighbor_i >= panel.ROWS-1 || neighbor_j >= panel.COLS-1) {//since we now are running the algo over all "grids" (1,2,3,4)
//			neighbor = null;
//		}
//		else {
//			neighbor = blocks[neighbor_i][neighbor_j];
//		}
//		return neighbor;
//	}
	
	//GETTERS AND SETTERS
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

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		public boolean[] getWalls(){
			return this.walls;
		}

		public boolean[] setWalls(boolean[] b){
			return this.walls = b;
		}

		
	
}