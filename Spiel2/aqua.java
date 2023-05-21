package Spiel2;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;


public class aqua {
    private Image aqua;
    private int w, h, pointX, pointY, index;
    private int fishInTank;

    public int getFishInTank() {
        return fishInTank;
    }

    public void setFishInTank(int fishInTank) {
        this.fishInTank = fishInTank;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int rP, cP;
    public boolean added = false;

    public int getcP() {
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

    // Needed for checking for permitted movement
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

    public aqua(int w, int h, int rP, int cP, int index) {
        setrP(rP);
        setcP(cP);
        setW(w);
        setH(h);
        setIndex(index);
        c = lower.blocks;
        setCurrent(c[rP][cP]);
        this.fishInTank = 0;
        try {
            aqua = ImageIO.read(getClass().getClassLoader().getResource("resources/aqua.png"));
            aqua = aqua.getScaledInstance(w, h, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void move(int x) {
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

    public Image getImg() {
        return aqua;
    }

    public Image setImg(Image img) {
        return aqua = img;
    }
}
