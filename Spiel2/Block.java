package Spiel2;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Block {
	JPanel panel;
	// Shortest path variables:
	private int distance;
	private boolean visitedDIJK = false;
	private boolean path = false;
	private Block parent;
	// 2D array variables:
	private int i = 0;
	private int j = 0;

	// Width and height of Block
	private int h = 0;

	// 4 vertex coordinates
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

	// If Block has been accessed
	private boolean visited = false;

	// if Block has been visited
	private static boolean alreadyVisited;

	public static boolean isAlreadyVisited() {
		return alreadyVisited;
	}

	public static void setAlreadyVisited(boolean AlreadyVisited) {
		alreadyVisited = AlreadyVisited;
	}

	// constructor of the Block
	public Block(int i, int j, JPanel panel) {
		this.i = i;
		this.j = j;
		this.panel = panel;
		h = lower.block_size;
		x1 = this.j * h;
		y1 = this.i * h;
		// DIJSK
		this.distance = Integer.MAX_VALUE;

		// Calculate the coordinates of the 4 vertices
		init();
	}

	// Default: the upper, lower, left and right walls are displayed
	private void init() {
		walls[0] = true;
		walls[1] = true;
		walls[2] = true;
		walls[3] = true;
	}

	private void updateCoords() {
		h = lower.block_size;
		// i for row and j for column
		// Upper left coordinate
		this.x1 = j * h;
		this.y1 = i * h;
		// Upper right coordinate
		this.x2 = (j + 1) * h;
		this.y2 = i * h;
		// Lower right coordinate
		this.x3 = (j + 1) * h;
		this.y3 = (i + 1) * h;
		// Lower left coordinate
		this.x4 = j * h;
		this.y4 = (i + 1) * h;
	}

	// Method of drawing indicator
	public void draw(Graphics g) {
		updateCoords();
		drawBlock(g);
	}

	// Draw maze block
	private void drawBlock(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Judge the upper, right, lower and left walls. If true, there will be walls,
		// otherwise there will be no walls
		boolean top = walls[0];
		boolean right = walls[1];
		boolean bottom = walls[2];
		boolean left = walls[3];

		g2.setStroke(new BasicStroke(4));
		if (top) {// Draw upper wall
			g2.drawLine(x1, y1, x2, y2);
		}
		if (right) {// Draw right wall
			g2.drawLine(x2, y2, x3, y3);
		}
		if (bottom) {// Draw the lower wall
			g2.drawLine(x3, y3, x4, y4);
		}
		if (left) {// Draw left wall
			g2.drawLine(x4, y4, x1, y1);
		}
	}

	// Find out whether the current Block has an unreachable neighbor cell

	// Shortest path:

	// GETTERS AND SETTERS
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

	public boolean[] getWalls() {
		return this.walls;
	}

	public boolean[] setWalls(boolean[] b) {
		return this.walls = b;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	// NEW
	public boolean isVisitedDIJK() {
		return visitedDIJK;
	}

	public void setVisitedDIJK(boolean visited) {
		this.visitedDIJK = visited;
	}

	public boolean isPath() {
		return path;
	}

	public void setPath(boolean path) {
		this.path = path;
	}

	public Block getParent() {
		return parent;
	}

	public void setParent(Block parent) {
		this.parent = parent;
	}

}
