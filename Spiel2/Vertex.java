package Spiel2;

public class Vertex{
    private String label;
    private int color = 0;
    public Vertex(){
        
    }
    public String getShade(String name) {
        String s = new String();
        if (name == "Antoine" || name == "Cuco") {
            s = "shade1";
        }
        else if (name == "Basile" || name == "Deluxe") {
            s = "shade2";
        }
        else if (name == "Elise" || name == "Feliz") {
            s = "shade3";
        }
        else if (name == "Gabriel" || name == "Hector") {
            s = "shade4";
        }
        else if (name == "Ivette" || name == "Jax") {
            s = "shade5";
        }
        return s;

    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    
}
