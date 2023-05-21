package Spiel2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
public class Graph{
	
private HashMap<Vertex, ArrayList<Vertex>> adjVertices;
private ArrayList<Vertex> a;
private String message;

public String getMessage() {
    return message;
}
public void setMessage(String message) {
    this.message = message;
}
public Graph() {
    adjVertices = new HashMap<Vertex, ArrayList<Vertex>>();
    a = new ArrayList<Vertex>();
    message = new String();
}
void addVertex(String label) {
    Vertex v = new Vertex(label);
    a.add(v);
    adjVertices.put(v, new ArrayList<Vertex>());
}

void removeVertex(String label) {
    Vertex v = new Vertex(label);
    adjVertices.values().stream().forEach(e -> e.remove(v));
    adjVertices.remove(new Vertex(label));
}
void addEdge(Vertex v1, Vertex v2) {
    adjVertices.get(v1).add(v2);
    adjVertices.get(v2).add(v1);
}

void removeEdge(String label1, String label2) {
    Vertex v1 = new Vertex(label1);
    Vertex v2 = new Vertex(label2);
    List<Vertex> eV1 = adjVertices.get(v1);
    List<Vertex> eV2 = adjVertices.get(v2);
    if (eV1 != null)
        eV1.remove(v2);
    if (eV2 != null)
        eV2.remove(v1);
}
//check if fish name has alr been taken by one the vertexes
boolean VertexExisted(Vertex v){
    boolean b = false;
    for(int n = 0; n<a.size();n++){
        if(v.getLabel() == a.get(n).getLabel()){
            b = true;
        } 
    }
    return b;
}




public void createGraph(String[] names, int fish_no, int conflict) {
     // pick random names from names array
    int h = new Random().nextInt(names.length);
    Vertex v = new Vertex(names[h]);
    int fish_no_n = fish_no;
    while(fish_no_n > 0 ){
       while(VertexExisted(v)==true){
           h = new Random().nextInt(names.length);
           v = new Vertex(names[h]);
            } 
        addVertex(names[h]);
        
        //System.out.println(names[h]);
        fish_no_n--;
    }
    //System.out.println("conflict is " + conflict);
    int k = new Random().nextInt(fish_no); //pick random vertex from list of Vertexes
    while(conflict>0){
    k = new Random().nextInt(fish_no); 
    int j = new Random().nextInt(fish_no); //pick random conflictVertex
    //as long as conflictVertex is the same as the current vertex or conflictVertex alr existed => pick new conflictVertex
    while(a.get(j)== a.get(k) || adjVertices.get(a.get(k)).contains(a.get(j))){
        j = new Random().nextInt(fish_no);
        //System.out.println("j "+j);
    }
        // System.out.println(j);
        addEdge(a.get(k), a.get(j));
        conflict--;
        // System.out.println(a.get(k).getLabel() + " conflicts with " + a.get(j).getLabel());
        
    }
}
// check if it's safe to color v with color c(1,2,3)
private boolean isSafe(Vertex ver, int c){
    for( Vertex v: a){
        if( c == v.getColor() && adjVertices.get(ver).contains(v)){
            return false;
        }
    }
    return true;
}
public void GraphColor(int i, int colors){
    if(i >= a.size()){
        return;
      }
    Vertex v = a.get(i);
    for(int c = 1; c <= colors; c++){
        if (isSafe(v, c)){
        v.setColor(c);
         //System.out.println("color of " + v.getLabel() + " is " + v.getColor());
         message +=v.getLabel() + " is in Aquarium " + v.getColor() +". ";
        break;
        }
    }
    GraphColor(i+1, colors);
  
}


public void setA(ArrayList<Vertex> a) {
    this.a = a;
}
public ArrayList<Vertex> getA() {
    return a;
}
public HashMap<Vertex, ArrayList<Vertex>> getAdjVertices() {
    return adjVertices;
}
public void setAdjVertices(HashMap<Vertex, ArrayList<Vertex>> adjVertices) {
    this.adjVertices = adjVertices;
}

}