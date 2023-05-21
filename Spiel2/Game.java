package Spiel2;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class Game {
    private Graph graph;
    private String[] names = { "Antoine", "Basile",
            "Cuco", "Deluxe", "Elise", "Feliz", "Gabriel", "Hector", "Ivette", "Jax" };

    private Image image, antoine, basile, cuco, deluxe, elise, feliz, gabriel, hector, ivette, jax, shade1, shade2,
            shade3, shade4, shade5, plus, minus;
    private int w, h;
    private HashMap<Vertex, ArrayList<Vertex>> conFish;
    private Level level;
    private int max ;
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public enum Level {
        TRAINING, MEDIUM, HARD;

        public int Conflict() {
            Random rand = new Random();
            int i = rand.nextInt(11);
            int[] i1 = new int[] {1,2,3,8,9,10};
            switch (this) {
                case TRAINING:
                    i = rand.nextInt(5);
                    break;
                case MEDIUM:
                        i = rand.nextInt(i1.length);
                        i = i1[i];
                    break;
                case HARD:
                    i = rand.nextInt(15 - 10 + 1) + 10;
                    break;
            }
            return i;
        }

        public int Fish_No() {
            switch (this) {
                case TRAINING:
                    return 4;
                case MEDIUM:
                    return 6;
                case HARD:
                    return 8;
                default:
                    return 0;
            }
        }
        public int tolerance(){
            switch (this) {
                case TRAINING:
                    return 1;
                case MEDIUM:
                    return 1;
                case HARD:
                    return 0;
                default:
                    return 0;
            }
        }
     
    }

    public Game(Level l) {
    	max = 0;
        this.level = l;
        graph = new Graph();
        graph.createGraph(names, l.Fish_No(), l.Conflict());
        conFish = graph.getAdjVertices();
        w = 50 / 4 * 3;
        h = 50 / 2;
        solve();
        try {
            antoine = ImageIO.read(getClass().getClassLoader().getResource("resources/antoine.png"));
            antoine = antoine.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            basile = ImageIO.read(getClass().getClassLoader().getResource("resources/basile.png"));
            basile = basile.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            cuco = ImageIO.read(getClass().getClassLoader().getResource("resources/cuco.png"));
            cuco = cuco.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            deluxe = ImageIO.read(getClass().getClassLoader().getResource("resources/deluxe.png"));
            deluxe = deluxe.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            elise = ImageIO.read(getClass().getClassLoader().getResource("resources/elise.png"));
            elise = elise.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            feliz = ImageIO.read(getClass().getClassLoader().getResource("resources/feliz.png"));
            feliz = feliz.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            gabriel = ImageIO.read(getClass().getClassLoader().getResource("resources/gabriel.png"));
            gabriel = gabriel.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            hector = ImageIO.read(getClass().getClassLoader().getResource("resources/hector.png"));
            hector = hector.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            ivette = ImageIO.read(getClass().getClassLoader().getResource("resources/ivette.png"));
            ivette = ivette.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            jax = ImageIO.read(getClass().getClassLoader().getResource("resources/jax.png"));
            jax = jax.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            shade1 = ImageIO.read(getClass().getClassLoader().getResource("resources/shade1.png"));
            shade1 = shade1.getScaledInstance(w, h, 1);

            shade2 = ImageIO.read(getClass().getClassLoader().getResource("resources/shade2.png"));
            shade2 = shade2.getScaledInstance(w, h, 1);

            shade3 = ImageIO.read(getClass().getClassLoader().getResource("resources/shade3.png"));
            shade3 = shade3.getScaledInstance(w, h, 1);

            shade4 = ImageIO.read(getClass().getClassLoader().getResource("resources/shade4.png"));
            shade4 = shade4.getScaledInstance(w, h,1);

            shade5 = ImageIO.read(getClass().getClassLoader().getResource("resources/shade5.png"));
            shade5 = shade5.getScaledInstance(w, h, 1);
            plus = ImageIO.read(getClass().getClassLoader().getResource("resources/plus.png"));
            plus = plus.getScaledInstance(w, h, 1);
            minus = ImageIO.read(getClass().getClassLoader().getResource("resources/minus.png"));
            minus = minus.getScaledInstance(w, h, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void solve(){
    graph.GraphColor(0, level.Fish_No());
    for(Vertex f: graph.getA()){
        if(f.getColor()>max){
            setMax(f.getColor());
        }
    }
   // System.out.println("max tank"+max);
}
public int Max_tank(Level l){
    switch (l) {
        case TRAINING:
            return max+1;
        case MEDIUM:
            return max;
        case HARD:
            return max;
        default:
            return 0;
    }
}
    public Image getImg(String l) {
        // DIFFERENT IMAGES FOR DIFFERENT fish
        switch (l) {
            case "Antoine":
                image = antoine;
                break;
            case "Basile":
                image = basile;
                break;
            case "Cuco":
                image = cuco;
                break;
            case "Deluxe":
                image = deluxe;
                break;
            case "Elise":
                image = elise;
                break;
            case "Feliz":
                image = feliz;
                break;
            case "Gabriel":
                image = gabriel;
                break;
            case "Hector":
                image = hector;
                break;
            case "Ivette":
                image = ivette;
                break;
            case "Jax":
                image = jax;
                break;
            case "shade1":
                image = shade1;
                break;
            case "shade2":
                image = shade2;
                break;
            case "shade3":
                image = shade3;
                break;
            case "shade4":
                image = shade4;
                break;
            case "shade5":
                image = shade5;
                break;
            case "plus":
                image = plus;
                break;
            case "minus":
                image = minus;
                break;
        }
        return image;
    }

    public Graph getGraph() {
        return graph;

    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public HashMap<Vertex, ArrayList<Vertex>> getConFish() {
        return conFish;
    }

    public void setConFish(HashMap<Vertex, ArrayList<Vertex>> conFish) {
        this.conFish = conFish;
    }

    public String getEnemies(Vertex v) {
        String s = "";
        String[] enemies_list = new String[conFish.get(v).size()];
        for (int i = 0; i < enemies_list.length; i++) {
            enemies_list[i] = conFish.get(v).get(i).getLabel();
            s += enemies_list[i] + ", ";
        }
        return s;
    }

}
