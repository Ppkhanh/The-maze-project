package Spiel2;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import Spiel2.Game.Level;


public class upper extends JPanel {

    private static ArrayList<Vertex> fish;
    private JScrollPane scroll_table;
    private static Level l;
  
    private static ArrayList<MyLabel> fishList;
    // ArrayList<Boolean> moved = new ArrayList<Boolean>();
    public upper(Level l) {
        setL(l);
        setBackground(new Color(3, 25, 64));
        computeGame();
        setFocusable(false);
        // setVisible(true);
        scroll_table = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // scroll_table.setFocusable(false);
        
        scroll_table.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_table.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       
    }

    public void computeGame() {
        newGame = new Game(l);
        fish = newGame.getGraph().getA();
        JLabel name = new JLabel();
        JLabel enemies = new JLabel();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        fishList = new ArrayList<MyLabel>();
  //create table of information by gridbaglayout
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < fish.size() + 1; j++) {
                gbc.gridx = i;
                gbc.gridy = j;
                gbc.insets = new Insets(5, 30, 5, 30);
                MyLabel f = new MyLabel(newGame.getImg(fish.get(0).getLabel()));
                if (j > 0) {
                    f = new MyLabel(newGame.getImg(fish.get(j-1).getLabel()));
                    fishList.add(f);
                    name = new JLabel(fish.get(j - 1).getLabel());
                    enemies = new JLabel(newGame.getEnemies(fish.get(j - 1)));
                    name.setFont(new Font("Courier New", Font.PLAIN, 14));
                    name.setForeground(Color.white);
                    enemies.setForeground(Color.white);

                }
                if (i == 0) {
                    if (j == 0) {
                        JLabel title = new JLabel("Fish Icon");
                        add(title, gbc);
                        title.setFont(new Font("Courier New", Font.BOLD, 16));
                        title.setForeground(new Color(187, 212, 242));
                    } else {
                        add(f, gbc);
                    }
                }
                if (i == 1) {
                    if (j == 0) {
                        JLabel title = new JLabel("Labels");
                        add(title, gbc);
                        title.setFont(new Font("Courier New", Font.BOLD, 16));
                        title.setForeground(new Color(187, 212, 242));
                    } else {
                        add(name, gbc);
                    }
                }
                if (i == 2) {
                    if (j == 0) {
                        JLabel title = new JLabel("Conflicted Fishes");
                        add(title, gbc);
                        title.setFont(new Font("Courier New", Font.BOLD, 16));
                        title.setForeground(new Color(187, 212, 242));
                    } else {
                        add(enemies, gbc);
                    }
                }
            }
        }
        //create popupmenu of enemy list for every fish
        for (int i =0; i< fish.size(); i++){
            JPopupMenu conFish = new JPopupMenu();
            JLabel e_new = new JLabel(newGame.getEnemies(fish.get(i)));
            conFish.add(e_new);
            // fishList.get(i).setTransferHandler(new TransferHandler("text"));
            fishList.get(i).addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    conFish.show(e.getComponent(), e.getX(), e.getY());
          
                }

            });
  

    }
}
public class MyLabel extends JLabel implements Cloneable {
    private Image image;
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private boolean not_moved = true;
    private boolean reached  = false;
    public boolean hasReached() {
        return reached;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    private String s;
    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public boolean isMoved() {
        return not_moved;
    }

    public void setMoved(boolean moved) {
        this.not_moved = moved;
    }

    public MyLabel(Image image) {
        this.image = image;
        ImageIcon icon = new ImageIcon(image);
        setIcon(icon);
    }

}

    public static ArrayList<Vertex> getFish() {
        return upper.fish;
    }

    public void setFish(ArrayList<Vertex> fish) {
        upper.fish = fish;
    }

    private static Game newGame;

    public static Game getNewGame() {
        return upper.newGame;
    }

    public void setNewGame(Game newGame) {
        upper.newGame = newGame;
    }
    public JScrollPane getScroll_table() {
        return scroll_table;
    }

    public void setScroll_table(JScrollPane scroll_table) {
        this.scroll_table = scroll_table;
    }

    public static ArrayList<MyLabel> getFishList() {
        return upper.fishList;
    }

    public void setFishList(ArrayList<MyLabel> fishList) {
        upper.fishList = fishList;
    }
    public static Level getL() {
        return upper.l;
    }

    public void setL(Level l) {
        upper.l = l;
    }


}
