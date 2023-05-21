package mazegen;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Path {
    private Image footstep;
    private int size;
    
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    
    private int x,y;
    
    public Path(){
        size = MazePanel.block_size - 20;
        try {
            footstep = ImageIO.read(getClass().getClassLoader().getResource("resources/foot-print.png"));
            footstep = footstep.getScaledInstance(size, size, Image.SCALE_DEFAULT);
            } 
        catch (IOException e) {
                e.printStackTrace();
            }
        }
   
    public void drawFootstep(Graphics g) {
            g.drawImage(footstep, x+10, y+10, size, size, null);
     }
        
     public int getX() {
      	return x;
     }
      
     public int getY() {
       	return y;
     }
        
     public void setX(int X) {
        	x = X ;
     }
     
     public void setY(int Y) {
        	y = Y;
     }
     
}