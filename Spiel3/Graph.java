package Spiel3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import java.io.*;
import java.util.*;

public class Graph {
	//Anzahl an Knoten
	private int numOfNodes;
    public void setNumOfNodes(int numOfNodes) {
		this.numOfNodes = numOfNodes;
	}

	private boolean directed;
    private boolean weighted;
    private double[][] matrix;
    // Knoten
    private double[] edges;
    //informationen von jedem Knoten
    private double[] parents;
    public double[] getParents() {
		return parents;
	}
	public void setParents(double[] parents) {
		this.parents = parents;
	}

	// teilt uns mit was besucht ist
    private boolean[] includedInMST;
    private boolean[] nodeIncluded;
    
    private Stack<int[]> includedEdges; 
    
    private boolean[][] isSetMatrix;
    public ArrayList<int[]> Edges ;
    
    public ArrayList<int[]> getEdges() {
		return Edges;
	}
	public void setEdges(ArrayList<int[]> edges) {
		Edges = edges;
	}
	public Graph(int numOfNodes, boolean directed, boolean weighted) {
    	this.directed = directed;
        this.weighted = weighted;
        this.numOfNodes = numOfNodes;
        
//        Edges = new ArrayList<>();
        
        matrix = new double[numOfNodes][numOfNodes];
        isSetMatrix = new boolean[numOfNodes][numOfNodes];
        
        edges = new double[numOfNodes];
        parents = new double[numOfNodes];
        includedInMST = new boolean[numOfNodes];
        nodeIncluded = new boolean[numOfNodes];
        setIncludedEdges(new Stack<int[]>());
        for(int i = 0; i < numOfNodes; i++){
            edges[i] = Double.POSITIVE_INFINITY;
            parents[i] = -1;
            includedInMST[i] = false;
            nodeIncluded[i] = false;
        }
		String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
        if(mode == "Training"  ) {
            Graphrandom(numOfNodes, 21, numOfNodes/3);

        }
        
		if(mode == "Mittel" ) {
	        Graphrandom(numOfNodes, 21, numOfNodes/4);

		}
		if(mode == "Schwer" ) {
	        Graphrandom(numOfNodes, 21, numOfNodes/8);

		}
//        Graphrandom(numOfNodes, 21, numOfNodes/4);
     Prim.PrimAlgo(this);

        
    }
    public void addEdge(int source, int destination, float weight) {

        float valueToAdd = weight;
       if (valueToAdd ==0) {
          valueToAdd = 1;

             }
        if (!weighted) {
            valueToAdd = 1;
        }

        matrix[source][destination] = valueToAdd;
        isSetMatrix[source][destination] = true;

        if (!directed) {
            matrix[destination][source] = valueToAdd;
            isSetMatrix[destination][source] = true;
        }
    }
    
//    public void printMatrix() {
//        for (int i = 0; i < numOfNodes; i++) {
//            for (int j = 0; j < numOfNodes; j++) {
//                // We only want to print the values of those positions that have been marked as set
//                if (isSetMatrix[i][j])
//                    System.out.format("%8s", String.valueOf(matrix[i][j]));
//                else System.out.format("%8s", "/  ");
//            }
//            System.out.println();
//        }
//    }
//    public void addEdge(int source, int destination) {
//
//        int valueToAdd = 1;
//
//        if (weighted) {
//            valueToAdd = 0;
//        }
//
//        matrix[source][destination] = valueToAdd;
//        isSetMatrix[source][destination] = true;
//
//        if (!directed) {
//            matrix[destination][source] = valueToAdd;
//            isSetMatrix[destination][source] = true;
//        }
//    }
    public void printEdges() {
        for (int i = 0; i < numOfNodes; i++) {
//            System.out.print("Node " + i + " is connected to: ");
            for (int j = 0; j < numOfNodes; j++) {
                if (isSetMatrix[i][j]) {
//                    System.out.print(j + " ");
                }
            }
//            System.out.println();
        }
    }
    
    
    private boolean edgeExisted(int[] i, ArrayList<int[]> a){
        boolean b = false;
        for (int[] arr : a) {
        if ((arr[0] == i[0] && arr[1] == i[1] ) || (arr[0] == i[1] && arr[1] == i[0])) {
            b=true ;
        } 
        }
        return b ;
    }

    public void Graphrandom (int x, int z, int max_edge) {
    	 Edges = new  ArrayList<int[]>();
    	//for(int h = 0; h < max_edge; h++){
    	 
    	 for(int i = 1; i < x ; i++) {
    		 int[] new_e = new int[] {i,i-1,0};
    		 
    		 Edges.add(new_e);
    		 int k =  new Random().nextInt(z);
    		 addEdge(i,i-1,k);
    		 
    	 }
    	 while(max_edge>0) {
    		 
    		 int i = new Random().nextInt(x);
    		 int j = new Random().nextInt(x);
    		 int[] new_e = new int[] {i,j,0};
    		 int[] new_e1 = new int[] {j,i,0};
    		 while (edgeExisted(new_e1,Edges) || edgeExisted(new_e,Edges) || j==i ) {
//    			 System.out.println(i + " " + j);
    			 i = new Random().nextInt(x);
    			 j = new Random().nextInt(x);	
    			 new_e1 = new int[] {j,i,0};
    			 new_e = new int[] {i,j,0};
    		 }
//    		 System.out.print(edgeExisted(new_e,Edges));  
    		 
    		 
    		 Edges.add(new_e);
//    		 System.out.print(Arrays.toString(Edges.get(Edges.size()-1)) );  
    		 int k =  new Random().nextInt(z-1)+1;
    		 addEdge(i,j,k);
//    		 System.out.println(i + " " + j + " "+ k);
    		 max_edge--;
    	 }
    	//}
    
//    	 System.out.println(i +" "+j); 	
    }
    public boolean hasEdge(int source, int destination) {
        return isSetMatrix[source][destination];
    }
    
    public int getNumOfNodes() {
        return numOfNodes;
    }

    public boolean getNodeIncluded(int i) {
		return this.nodeIncluded[i];
	}
    
    public int getNumberNodeIncluded() {
    	int numberNodeIncluded = 0;
    	for (boolean value: nodeIncluded) {
    		if(value) {
    			numberNodeIncluded += 1;
    		}
    	}
    	return numberNodeIncluded;
    }
	public void setNodeIncluded(int i, boolean value) {
		this.nodeIncluded[i] = value;
	}
	public double getEdges(int i) {
    	return edges[i];
    }
	
	public double[] getAllEdges() {
		return this.edges;
	}

    public void setEdges(double edge, int node) {
    	this.edges[node] = edge;
    }

    public boolean getIncludedInMST(int i) {
    	return includedInMST[i];
    }

    public void setIncludedInMST(int node, boolean value) {
    	this.includedInMST[node] = value;
    }
    
    public void addEdge(int[] edge) {
    	this.getIncludedEdges().push(edge);
    }
    
    public void removeEdge() {
    	this.getIncludedEdges().pop();
    }
    
    public int[] getLatestEdge() {
    	return this.getIncludedEdges().peek();
    }

    public double[][] getMatrix() {
    	return matrix;
    }

    public void setParents(double parent, int node) {
    	this.parents[node] = parent;
    }

    public double getParents(int i) { 
       return parents[i]; 
    }
	public Stack<int[]> getIncludedEdges() {
		return includedEdges;
	}
	public void setIncludedEdges(Stack<int[]> includedEdges) {
		this.includedEdges = includedEdges;
	}

}
