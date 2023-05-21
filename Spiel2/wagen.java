package Spiel2;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class wagen {
    private Image wagen;
    private int w, h, pointX, pointY;
    private int rP , cP;	
    public boolean reached = false;
	public Integer getcP() {
        return cP;
    }
    public void setcP(int cP) {
        this.cP = cP;
    }
    public int getrP() {
        return rP;
    }
    public void setrP(int rP) {
        this.rP = rP;
    }
    //Needed for checking for permitted movement
    public Block current;
	public Block[][] c;
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public int getPointY() {
        return pointY;
    }
    public void setPointY(int pointY) {
        this.pointY = pointY;
    }
    public int getPointX() {
        return pointX;
    }
    public void setPointX(int pointX) {
        this.pointX = pointX;
    }
    public wagen(int w , int h) {
        rP=9;
        cP=2;
        this.w = w;
        this.h = h;
        c = lower.blocks;
        current = c[rP][cP];
        try {
            wagen = ImageIO.read(getClass().getClassLoader().getResource("resources/wagen.png"));
            wagen = wagen.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void move(int x){
	    cP = cP + x;
	    setCurrent(c[rP][cP]);
        setPointX(current.getX1());
        setPointY(current.getY1());
    }
    public Block getCurrent() {
		return current;
	}

	public void setCurrent(Block current) {
		this.current = current;
	}
    public Image getImg(){
        return wagen;
    }
}
