package Spiel3;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


import java.text.DecimalFormat;
import java.text.NumberFormat;





class Vect2 {
    final double x, y;

    Vect2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Vect2 scale(double k) {
        return new Vect2(x * k, y * k);
    }

    Vect2 plus(Vect2 that) {
        return new Vect2(x + that.x, y + that.y);
    }

    Vect2 minus(Vect2 that) {
        return this.plus(that.scale(-1));
    }
}

public class Spielpanel extends JPanel implements MouseListener, KeyListener, ActionListener{
	
	private static int WIDTH;

	public int ROWS = 20;
	public int COLS = ROWS;
	public static int block_size;
	private static Block[][] blocks;
	
	Block randpoint;
	private ArrayList<Block> stations = new ArrayList<Block>();
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	Graph graph;
	
	//Move picture pipe drag and drop
	private Icon icon;
	private Image image;
	Point image_corner;
	Point previousPoint;
	
	Timer t = new Timer(5, this);
	double x=0, y=0, velx=0, vely = 0;
	private int sum;
	double optimalSum;
	ArrayList<Integer[]> orderEdges = new ArrayList<>();
	public static int clicked;
	
	public static boolean pause = true;
	
	public int drück = 0;
	DragListener dragListener;
	boolean erreicht;
	boolean verloren = Auswahlbereichh.isVerloren();

	public static boolean isPause() {
		return pause;
	}

	public static void setPause(boolean pause) {
		Spielpanel.pause = pause;
	}

	public Spielpanel(Color c) {
		WIDTH = 800;

		block_size = 40;
		
	graph = new Graph(4, false, true);
//	System.out.print("Anfang");
//	System.out.println(graph.getIncludedInMST(0));
	 clicked = 0; 
	orderEdges = new ArrayList<Integer[]>();
	sum =0;
	setOptimalSum(0);
	setBackground(c);
	setLayout(new BorderLayout());
	createBlocks();

	 previousPoint= new Point(0,0);
erreicht = false;

	
	for (int i = 0; i < graph.getNumOfNodes() ; i++) {
		randpoint = randomPoint(blocks);
		stations.add(randpoint);
		randpoint.setIndex(i);
		graph.setIncludedInMST(i, false);
	}
	for( int i = 0; i < graph.getNumOfNodes() ; i++) {
		Block src = stations.get(i);
		
	}
//	System.out.println(stations.size());
	addMouseListener(this);
	
	image_corner = new Point(0,0);
	solve();
	ClickListener clickListener = new ClickListener();
	this.addMouseListener(clickListener);
	
	DragListener dragListener = new DragListener();
	this.addMouseMotionListener(dragListener);
	
	 
		URL urll = this.getClass().getResource("/resources/rohr2.png");

			try {
				image = ImageIO.read(urll);
				image= image.getScaledInstance(80, 9, Image.SCALE_SMOOTH);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			icon = new ImageIcon(image);
			
			
			
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					WIDTH = getSize().width;
					block_size = Math.min(getSize().width / 20, getSize().height / 20);
					repaint();
				}
			});

		t.start();
		addKeyListener(this);
		setFocusable(true);
		setVisible(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

}
	
	public Spielpanel(Color c, int numberNodes) {
		WIDTH = 800;

		block_size = 40;
		
	graph = new Graph(numberNodes, false, true);
//	System.out.print("Anfang");
//	System.out.println(graph.getIncludedInMST(0));
	 clicked = 0; 
	orderEdges = new ArrayList<Integer[]>();
	sum =0;
	setOptimalSum(0);
	setBackground(c);
	setLayout(new BorderLayout());
	createBlocks();
	
	for (int i = 0; i < graph.getNumOfNodes() ; i++) {
		randpoint = randomPoint(blocks);
		stations.add(randpoint);
		randpoint.setIndex(i);
		graph.setIncludedInMST(i, false);
	}
	for( int i = 0; i < graph.getNumOfNodes() ; i++) {
		Block src = stations.get(i);
		
	}
//int i = 0;
////	for (int i = 0; i < graph.getNumOfNodes() ; i++) {
//	while(i < graph.getNumOfNodes()) {
//		randpoint = randomPoint(blocks);
//		randpoint.updateCoords();
//		if( i == 0) {
//		stations.add(randpoint);
//		randpoint.setIndex(i);
//		graph.setIncludedInMST(i, false);
//		i += 1;
//		}
//		else {
//			boolean at = true;
//		for(Block lauf : stations) {
//			System.out.print(randpoint.getX1() + " "+ lauf.getX1() + "halooooo");
//		if(Math.abs(randpoint.getX1() - lauf.getX1()) < 30) {
//			at = false;
//		  }}
//		if(at) {
//			stations.add(randpoint);
//			randpoint.setIndex(i);
//			graph.setIncludedInMST(i, false);
//			i += 1;
//		}
//	}
//}
//	for( int j = 0; j < graph.getNumOfNodes() ; j++) {
//		Block src = stations.get(j);
//		
//	}
//	System.out.println(stations.size());
	addMouseListener(this);
	
	image_corner = new Point(0,0);
	solve();
	ClickListener clickListener = new ClickListener();
	this.addMouseListener(clickListener);
	
	dragListener = new DragListener();
	this.addMouseMotionListener(dragListener);
	
	

	
		URL urll = this.getClass().getResource("/resources/rohr2.png");

			try {
				image = ImageIO.read(urll);
				image= image.getScaledInstance(80, 9, Image.SCALE_SMOOTH);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			icon = new ImageIcon(image);
			
			
			
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					WIDTH = getSize().width;
					block_size = Math.min(getSize().width / 20, getSize().height / 20);
					repaint();
				}
			});
//	
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setVisible(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

}
	
	public int getSUm() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

// Draw a circle but no on a JPanel
@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    // Rohr
	icon.paintIcon(this, g, (int)image_corner.getX(), (int)image_corner.getY()+ 5);

//Lüftungssystem
   g2d.fill(new Ellipse2D.Double(x,y + 20,20,20));
	
    //System.out.println(randpoint.getX1() +" "+ randpoint.getY1());
    for( Block b: stations) {
    	
    	g2d.drawOval(b.getJ()*block_size-10, b.getI()*block_size-10, 20, 20);

    	
    }
    //Das malt die die erste Station grün an jm zu erkennen welches die Startposition ist  hier wird der erste Knonten genommen (nur die nächsten zwei zeilen)
    g2d.setColor(Color.blue);
    g2d.drawOval(stations.get(0).getJ()*block_size-10, stations.get(0).getI()*block_size-10, 20, 20);
	g2d.fillOval(stations.get(0).getJ()*block_size-10, stations.get(0).getI()*block_size-10, 20, 20);  
	graph.setNodeIncluded(0, true);

        Block block;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				block = blocks[i][j];
				if (block != null) {
					block.draw(g);
				}
			}
		}
		drawEdges(g);


    }

public void drawEdges(Graphics g) {
	for (int[] pair: graph.getEdges()) {
		int src_index = pair[0];
		int destination_index = pair[1];
		Block src = stations.get(src_index);
		Block des = stations.get(destination_index);
		if( pair[2] == 0) {
			g.setColor(Color.black);

		}else {
			g.setColor(Color.pink);

		}
		double weight = graph.getMatrix()[src.getIndex()][des.getIndex()];
		g.drawLine(src.getX1(), src.getY1(), des.getX1(), des.getY1());
		g.setColor(Color.red);
		g.drawString(weight+ " ", (src.getX1()+des.getX1())/2 , (src.getY1()+des.getY1())/2 );
//		System.out.println(src.getX1() + " " + src.getY1() + " " + des.getX1() + " " + des.getY1() + " cool"  );
		for(int i = 1; i < graph.getNumOfNodes(); i++){
			 int Src_index = (int)graph.getParents(i);
				int Destination_index = i;
				Block Src = stations.get(Src_index);
				Block Des = stations.get(Destination_index);
	       // System.out.print("edge: (" + (int)graph.getParents(i) + ", " + i + "), weight: " + graph.getEdges(i));
	        
//	        g.setColor(Color.pink);
//			g.drawLine(Src.getX1(), Src.getY1(), Des.getX1(), Des.getY1());
		
	  }

	}
	 
	}
public void solve() {
	
	for(int i = 1; i < graph.getParents().length; i++){
			setOptimalSum(getOptimalSum() + graph.getEdges(i));
	}
	
//	System.out.println("optimal "+ optimalSum);
}
String userWord = "";
JTextField userInput;

public void Lösung() {
	JFrame lösung = new JFrame();
	JLabel win = new JLabel();
	JLabel optsum = new JLabel();
	JButton next = new JButton("Weiter");
	JButton jButton = new JButton("antippen");

	lösung.setLayout(new FlowLayout());
	boolean allStations = false;
	int nodes_clicked = graph.getNumberNodeIncluded();
	String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
      if(mode == "Training") {
	setOptimalSum((optimalSum*2)-1);
	Auswahlbereichh.punkt += graph.getIncludedEdges().size()  ;

    }
     if(mode == "Mittel") {
	setOptimalSum(optimalSum*1.5);
	Auswahlbereichh.punkt += graph.getIncludedEdges().size()   ;


}
if(mode == "Schwer") {
	setOptimalSum(optimalSum*1.2);
	Auswahlbereichh.punkt += graph.getIncludedEdges().size() * 3  ;


}
	
	if (nodes_clicked == graph.getNumOfNodes()) {
//		System.out.println("nodes "+nodes_clicked);
		allStations = true;
	}
	
	
	if (!allStations || getSUm() > (int) getOptimalSum()) {
		verloren = true;
		win.setText("Du hast VERLOREN." + " Deine Summe: " +sum);
//		optsum.setText( "Die Kostenschranke liegt bei : " + (int)( getOptimalSum()+1));
		lösung.add(win);
		lösung.add(optsum);
		Auswahlbereichh.setLevel(1);
		Auswahlbereichh.Levelauswahl.setText(" Level :" + Auswahlbereichh.getLevel());
		Auswahlbereichh.setPunkt(0);
		Auswahlbereichh.punkte.setText(" Deine Punkte sind: " + Auswahlbereichh.getPunkt());
		optsum.setText("<html><body>1. Jonas 415 Punkte.<br>2. Tom 325 Punkte.<br>3. Lishan 300 Punkte.<br><br>Es wird Überprüft, ob dein Score größer als die der drei besten Spieler sind.</body></html>");
		userInput = new JTextField("", 30);
		jButton.addActionListener( (e) -> {
			submitAction();
		});
		
		userInput.setToolTipText("Gib deinen Namen ein");
		lösung.add(optsum);
		lösung.add(userInput);
		lösung.add(jButton);
		
		Auswahlbereichh.training.stop();
		Auswahlbereichh.medium.stop();
		Auswahlbereichh.schwer.stop();



	} else {
		verloren = false;
		win.setText("Du hast GEWONNEN." + " " +  "Deine Summe : " + sum  );
//		optsum.setText( "Die Kostenschranke liegt bei : " + (int)( getOptimalSum()+1));
		lösung.add(win);
		lösung.add(optsum);
		Auswahlbereichh.setLevel(Auswahlbereichh.getLevel() +1);
		Auswahlbereichh.Levelauswahl.setText(" Level :" + Auswahlbereichh.getLevel());
//		Auswahlbereich.setPunkt(Auswahlbereich.getPunkt()+4);
		Auswahlbereichh.punkte.setText(" Deine Punkte sind: " + Auswahlbereichh.getPunkt());
	}
	lösung.add(next, BorderLayout.SOUTH);
	next.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Snippet.cont.add(Snippet.spielbereich, "spiel1");
			Snippet.cl.show(Snippet.cont, "spiel1");
//			Spielbereich.getPanel().solve();
		
			
			
//			System.out.println("Wir sind in den Snippets");
//			System.out.println(Auswahlbereichh.getCb().getSelectedItem().toString());
			String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
			int numberNodes = 4;
			
			if (mode == "Mittel") {
				numberNodes = 8;
			}
			if (mode == "Schwer") {
				numberNodes = 12;
			}
		
			Snippet.spielbereich.remove(Spielbereich.getPanel());
			Snippet.spielbereich.setP(new Spielpanel(Color.CYAN, numberNodes));
			Snippet.spielbereich.add(Spielbereich.getPanel());
			Spielbereich.getPanel().setSum(0);
			Spielbereich.getPanel().requestFocusInWindow();
			if (mode == "Training") {
				Auswahlbereichh.getTraining().stop();
				Auswahlbereichh.getMedium().stop();
				Auswahlbereichh.getSchwer().stop();
				Auswahlbereichh.setMinutes(00) ;
				Auswahlbereichh.setSeconds(00) ;
				Auswahlbereichh.getTf().setText(String.valueOf("00" + ":" + "00" + ":" + "00"));
				Auswahlbereichh.training = new Timer (1000, Auswahlbereichh.clock);
				Auswahlbereichh.training.start();
				Auswahlbereichh.setP(Spielbereich.getPanel());	
				double d =  Auswahlbereichh.getP().getOptimalSum()* 2;
				Auswahlbereichh.optSuminAuswahl.setText(" Kostenschranke: "+  d );
				
			}
			if (mode == "Mittel") {
				Auswahlbereichh.getTraining().stop();
				Auswahlbereichh.getMedium().stop();
				Auswahlbereichh.getSchwer().stop();
				Auswahlbereichh.setMed_start(60);
				Auswahlbereichh.setMinutes(00) ;
				Auswahlbereichh.setSeconds(00) ;
				Auswahlbereichh.getTf().setText(String.valueOf("00" + ":" + "00" + ":" + "00"));
				Auswahlbereichh.medium = new Timer (1000, Auswahlbereichh.clockMedium);
				Auswahlbereichh.medium.start();
				Auswahlbereichh.setP(Spielbereich.getPanel());		
				double d =  Auswahlbereichh.getP().getOptimalSum()* 1.5;
				Auswahlbereichh.optSuminAuswahl.setText("Kostenschranke: "+ (int) (d+1) );
			}
			if (mode == "Schwer") {
				Auswahlbereichh.getTraining().stop();
				Auswahlbereichh.getMedium().stop();
				Auswahlbereichh.getSchwer().stop();
				Auswahlbereichh.setHard_start(30);
				Auswahlbereichh.setMinutes(00) ;
				Auswahlbereichh.setSeconds(00) ;
				Auswahlbereichh.getTf().setText(String.valueOf("00" + ":" + "00" + ":" + "00"));
				Auswahlbereichh.schwer = new Timer (1000, Auswahlbereichh.clockSchwer);
				Auswahlbereichh.schwer.start();
				Auswahlbereichh.setP(Spielbereich.getPanel());		
				double d = Auswahlbereichh.getP().getOptimalSum()* 1.2;
				Auswahlbereichh.optSuminAuswahl.setText("Kostenschranke: "+ (int) (d+1) );
			}
			

			
			Auswahlbereichh.optSuminAuswahl.setForeground(Color.WHITE);
			Auswahlbereichh.optSuminAuswahl.setVisible(true);
			lösung.dispose();	
			}
		
	});

	lösung.setPreferredSize(new Dimension(500, 300));
	lösung.setLocationRelativeTo(null);
	lösung.pack();
	lösung.setVisible(true);
}

private void submitAction() {
	userWord = userInput.getText();
//	System.out.println("Name");
	JFrame high = new JFrame();
	JLabel bester = new JLabel();
	bester.setText("<html><body><br><br>LEIDER hast du es nicht unter den drei besten geschafft... </body></html>");		
	high.add(bester);
	high.setTitle("HIGHSCORE");

	high.add(userInput);
	high.setLayout(new FlowLayout());
	high.dispose();	

	high.setPreferredSize(new Dimension(500, 200));
	high.setLocationRelativeTo(null);
	high.pack();
	high.setVisible(true);
}




// This is for the distance between the clicked mouse and the line to click
static Vect2 getIntersection(Vect2 A, Vect2 B, Vect2 P) {
    Vect2 abDir = B.minus(A);
    Vect2 perpDir = new Vect2(-abDir.y, abDir.x);
    Vect2 apDir = P.minus(A);
    double s = (perpDir.y * apDir.x - perpDir.x * apDir.y)
             / (abDir.x * perpDir.y - abDir.y * perpDir.x);
    return A.plus(abDir.scale(s));
}


public void mouseClicked(MouseEvent e)
{
	if(pause == true) {
	for (int[] pair: graph.getEdges()) {
		int src_index = pair[0];
		int destination_index = pair[1];
		Block src = stations.get(src_index);
		Block des = stations.get(destination_index);
		int min1 =   Math.min(src.getX1(), des.getX1());
		int min2 =   Math.min(src.getY1(), des.getY1());
		int max1 =   Math.max(src.getX1(), des.getX1());
		int max2 =   Math.max(src.getY1(), des.getY1());
		int mousex = e.getX();
		int mousey = e.getY();


		if(mousex <= max1 && mousex >= min1  && mousey <= max2  && mousey >= min2  ) {
			
			
			Vect2 Inter = getIntersection(new Vect2(src.getX1(), src.getY1()) , new Vect2(des.getX1(), des.getY1()) ,new Vect2(e.getX(), e.getY()) );
			Vect2 Punkt = new Vect2(e.getX(), e.getY());
			double a = Inter.x - Punkt.x;
			double b = Inter.y - Punkt.y;
			// a^2 + b^2 = c^2
			double c = Math.sqrt(a*a + b*b);
			if(c <= 8) {
				int weight = 0;
				//zwei If Abfragen
				if(pair[2] == 0 ) {
					if (graph.getNodeIncluded(pair[0]) || graph.getNodeIncluded(pair[1])) {
						pair[2] = 1;
						
						graph.setNodeIncluded(pair[0], true);
						graph.setNodeIncluded(pair[1], true);
						clicked += 1;
//						System.out.println("clicked "+clicked);
						weight = (int) graph.getMatrix()[src.getIndex()][des.getIndex()];
						sum += weight;
//						System.out.println("weight " + weight);s
						graph.addEdge(pair);
					}

				}else {
					if(graph.getLatestEdge() == pair) {
						boolean isSrcIncluded = false;
						boolean isDescIncluded = false;
						for (int[] edge : graph.getEdges()) {
							if (edge[2] == 1 && edge[0] != pair[0]){
								if (pair[0] == edge[0] || pair[0] == edge[1]) {
									isSrcIncluded = true;
								}
								if (pair[1] == edge[0] || pair[1] == edge[1]) {
									isDescIncluded = true;
								}
							}	
						}
						if (!isSrcIncluded) {
							graph.setNodeIncluded(pair[0], false);
						}
						if (!isDescIncluded) {
							graph.setNodeIncluded(pair[1], false);
						}
						pair[2] = 0;
						clicked -= 1;
//						System.out.println("clicked "+clicked);
						weight = (int) graph.getMatrix()[src.getIndex()][des.getIndex()];
						sum -= weight;
//						System.out.println("weight " + weight);
						graph.removeEdge();
					}
					
				}
			}	
			
		}}
		
	}
repaint();
}
private void createBlocks() {
	blocks = new Block[ROWS][COLS];
	Block block;
	for (int i = 0; i < ROWS; i++) {
		for (int j = 0; j < COLS; j++) {
			block = new Block(i, j, this);
			// set walls for most outer rows and columns so that there're no walls
			
			blocks[i][j] = block;
		}
	}
}


//public void compareTwoArrayLists() {
//	 if(graph.getEdges().equals(randpoint.getIndex()))
//         System.out.println("The two ArrayList are equal.");
//     else
//         System.out.println("The two ArrayList are not equal.");
//	
//}


public Block randomPoint(Block[][] b) {

	int i = new Random().nextInt(ROWS - 3 ) + 3;
	int j = new Random().nextInt(COLS - 3) + 3;
//	 System.out.println(i +" "+j);
	return b[i][j];
	
}



private class ClickListener extends MouseAdapter{
	public void mousePressed(MouseEvent evt) {
		previousPoint = evt.getPoint();
	}
	
}

private class DragListener extends MouseMotionAdapter{
	public boolean inStation = false;
		public void mouseDragged(MouseEvent evt) {
			if(pause == true) {

			Point currentPoint = evt.getPoint();
			
			image_corner.translate(
					              (int)(currentPoint.getX() - previousPoint.getX()), 
					              (int)(currentPoint.getY() - previousPoint.getY()) );
			
			previousPoint = currentPoint;
		
			
			for(Block b: stations) {
				if(previousPoint.getX() > b.getX1() - block_size 
						&& previousPoint.getX() < b.getX1() + block_size &&
						previousPoint.getY() > b.getY1() - block_size &&
						previousPoint.getY() < b.getY1() + block_size) {
					inStation = true;
					break;
					
				}
			
				
			}
			
			repaint();
		}}
		
		
		
public boolean instationFeld() {
	return inStation;
}

}



public void actionPerformed(ActionEvent e) {
	
	if(x < 0) {
		velx = 0;
		x = 0;
	}if(x > 700) {
		velx = 0;
		x = 700;
	}if(y < 0) {
		vely = 0;
		y = 0;
	}if(y > 700) {
		velx = 0;
//		x = 800;
		y=700;
	}
	
	x += velx;
	y += vely;
	repaint();
}

public void up() {
	vely  =-2.5;
	velx  =0;
}

public void down() {
	vely  = 2.5;
	velx  = 0;
}

public void left() {
	velx  = - 2.5;
	vely  = 0;
}
public void right() {
	velx  = 2.4;
	vely  = 0;
}
public void keyPressed(KeyEvent e) {
	if(pause == true) {

	int code = e.getKeyCode();
	if(code == KeyEvent.VK_UP || e.getKeyCode() == e.VK_W) {
		up();
	}
	if(code == KeyEvent.VK_DOWN || e.getKeyCode() == e.VK_S) {
		down();
	}if(code == KeyEvent.VK_RIGHT || e.getKeyCode() == e.VK_D) {
		right();
	}if(code == KeyEvent.VK_LEFT|| e.getKeyCode() == e.VK_A) {
		left();
	}
//	System.out.println(x+","+y);
erreicht = false;
	for(Block b: stations) {
//		System.out.println(b.getX1()+","+b.getY1());
		if((int) x  > b.getX1() - 50 
				&& (int) x < b.getX1() + 50 &&
				(int) y  > b.getY1() - 30 &&
				(int) y  < b.getY1() + 5) {
			erreicht = true;
			break;
			
		}
	
	}
//	System.out.println(erreicht + " " + dragListener.instationFeld() );
	if(erreicht && dragListener.instationFeld()) {
	Lösung();
	}
	
		}
		if (e.getKeyCode() == KeyEvent.VK_P ) {  
			
//			System.out.println("hallo");
			String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
			
		if(mode == "Training" && drück == 0 ) {
			Auswahlbereichh.getTraining().stop();
			pause = false;
			drück = 1 ;
			
	       
		}else if(mode == "Training" && drück == 1) {
			Auswahlbereichh.getTraining().start();
			pause = true;
			drück = 0;
			
		}
		if(mode == "Mittel" && drück == 0) {
			Auswahlbereichh.getMedium().stop();	
			pause = false;
			drück = 1;
		}else if (mode == "Mittel" && drück == 1) {
			Auswahlbereichh.getMedium().start();
			pause = false;
			drück = 0;
		}
		if(mode == "Schwer" && drück == 0) {
			Auswahlbereichh.getSchwer().stop();
			pause = false;

			drück = 1 ;
		}else if (mode == "Schwer" && drück == 1) {
			Auswahlbereichh.getSchwer().start();
			pause = true;

			drück = 0;
		}
//
	}
		
		
	}
	

public void keyTyped(KeyEvent e) {}



	public static Block[][] getBlocks() {
		return Spielpanel.blocks;
	}

	public void setBlocks(Block[][] blocks) {
		Spielpanel.blocks = blocks;
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		velx = 0;
		vely = 0;
		
	}

	public double getOptimalSum() {
		return optimalSum;
	}

	public void setOptimalSum(double d) {
		this.optimalSum = d;
	}


}
