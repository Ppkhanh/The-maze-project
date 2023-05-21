package Spiel2;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Spiel2.Game.Level;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class arrow_button extends JButton {
    private int rP, cP;

    public Block location;
    private String s;
    public Block[][] c;
    private lower p;
    // int click_count;
    // private int block_size = lower.block_size;
    private ArrayList<aqua> Aquas;

    private Level level;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public arrow_button(Image img, Block[][] c, String s, Level l, lower p, int size) {
        img = img.getScaledInstance(size-10, size-10, Image.SCALE_FAST);
        ImageIcon icon = new ImageIcon(img);
        setIcon(icon);
        this.c = c;
        init(s);
        setBounds(location.getX1(), location.getY1(), size, size);
        this.s = s;
        this.level = l;
        // this.click_count = lower.getClick_count();
        this.Aquas = lower.Aquas;
        this.p = p;
        addTank(s);
        
        // System.out.println(Aquas.size());
        setFocusable(false);
    }

    private void init(String s) {
        switch (s) {
            case "more":
                setrP(5);
                setcP(0);
                setLoc(c[rP][cP]);
                // this.click_count = 0;
                break;
            case "less":
                setrP(6);
                setcP(0);
                setLoc(c[rP][cP]);
                // this.click_count = 3;
                break;
        }

    }

    private void addTank(String s) {
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 if(! p.pause) {
            		 
            		 if (s == "more") {
            			 
            			 if (lower.getClick_count() < p.fishList.size()-1) {
            				
//            				 if( lower.getClick_count() < p.fishList.size() ){
//            			 
//            				 }
            				 lower.setClick_count(lower.getClick_count()+1);
            				 aqua a = Aquas.get(lower.getClick_count());
            				 a.added = true;
            				 p.repaint();
            				 p.revalidate();
            				 //System.out.println(lower.getClick_count());
            			 }
            		 } else {
            			 if (lower.getClick_count() > 0) {
            				 aqua a = Aquas.get(lower.getClick_count());
            				 //System.out.println(a.getFishInTank() + "fishes");
            				if(a.getFishInTank()==0) {
            					a.added = false;
            					p.repaint();
            					p.revalidate();
            					// System.out.println("pressed");
            					lower.setClick_count(lower.getClick_count()-1);
            					//System.out.println(lower.getClick_count());
            				}
            				 
            			 }
            		 }
            	 }

            }

        });

    }

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

    public Block getLoc() {
        return location;
    }

    public void setLoc(Block location) {
        this.location = location;
    }

}

