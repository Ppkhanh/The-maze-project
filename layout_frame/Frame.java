package layout_frame;



import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mazegen.MazePanel;
import Spiel2.container;
import Spiel3.Auswahlbereichh;
import Spiel3.Snippet;
import game_4_main.Game_4_Main;





public class Frame extends JFrame {
	public static int WIDTH ;
	public static int HEIGHT ;
	public static JPanel panel;
	public static CardLayout cl;
	public static JPanel cont;
	public Spielbereich spielbereich ;


	public Frame() {
		
		panel = new JPanel();
		cont = new JPanel();
		setTitle("TEAM19-TEST");
		setMinimumSize(new Dimension(600, 600));
		//setMaximumSize(new Dimension(1000, 800));
	
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		spielbereich  = new Spielbereich();
		
		Auswahlbereich auswahlbereich = new Auswahlbereich();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.8;
		gbc.weighty = 1;
		panel.add(spielbereich, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		panel.add(auswahlbereich, gbc);
		
		cl = new CardLayout();
		cont.setLayout(cl);
		cont.add(panel, "card1");
		
		Auswahlbereich.spielanleitung_b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == Auswahlbereich.spielanleitung_b){
					Auswahlbereich.spielanleitung();
					spielbereich.getPanel().requestFocusInWindow();	
				}
			}
		});
		
		
		Spiel1 spiel_1 = new Spiel1();
		cont.add(spiel_1, "spiel1");
		game_1.Auswahlbereich1.zurück.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spielbereich.remove(spielbereich.getPanel());
				spielbereich.setP(new MazePanel(Color.WHITE));
				spielbereich.add(spielbereich.getPanel());
				cl.show(cont, "card1");	
				spielbereich.getPanel().requestFocusInWindow();	
			
			}

		});
		
		
		
		auswahlbereich.spiel_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cont, "spiel1");
			

			}
		});
		
		container spiel_2 = new container();
//		Spiel2 spiel_2 = new Spiel2();
		cont.add(spiel_2, "spiel2");
		spiel_2.getSett().z_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				spielbereich.remove(spielbereich.getPanel());
				spielbereich.setP(new MazePanel(Color.WHITE));
				spielbereich.add(spielbereich.getPanel());
				cl.show(cont, "card1");	
				spielbereich.getPanel().requestFocusInWindow();	
			

			}
			
		});
		
		auswahlbereich.spiel_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cont, "spiel2");	
			
			
			}
			
		});
		
		Snippet spiel_3 = new Snippet();
		cont.add(spiel_3, "spiel3");
		Auswahlbereichh.zurück.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				spielbereich.remove(spielbereich.getPanel());
				spielbereich.setP(new MazePanel(Color.WHITE));
				spielbereich.add(spielbereich.getPanel());
				cl.show(cont, "card1");	
				spielbereich.getPanel().requestFocusInWindow();	
			

			

			}
			
		});
		
		auswahlbereich.spiel_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cont, "spiel3");	
				
			
			}
			
		});
		
		Game_4_Main spiel_4 = new Game_4_Main();
		cont.add(spiel_4, "spiel4");
		spiel_4.auswahlbereich.b_zurueck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				spielbereich.remove(spielbereich.getPanel());
				spielbereich.setP(new MazePanel(Color.WHITE));
				spielbereich.add(spielbereich.getPanel());
				cl.show(cont, "card1");	
				spielbereich.getPanel().requestFocusInWindow();	
			
	
			
			}
			
		});
		
		auswahlbereich.spiel_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cont, "spiel4");
		
				
			}
			
		});
		
		add(cont);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		Scanner();
//	
	
			
		}
	
public void Scanner() {
	Scanner Spielauswahl1 = new Scanner(System.in);
	int eingabe;

	boolean eingabeBeendet = false;
	String ungueltigeEingabe = "Ungueltige Eingabe! Moegliche Eingaben sind 1 fuer Spiel 1, 2 fuer Spiel 2, 3 fuer Spiel 3 und 4 fuer Spiel 4.";
	
		
			try {
				System.out.println("Willkommen, jetzt kannst du eine Spielnummer eingeben");
				Spielauswahl1 = new Scanner(System.in);
				eingabe = Spielauswahl1.nextInt();

				if (eingabe == 1) {
					
					cl.show(cont, "spiel1");
					System.out.println("Spiel 1 wird gestartet");
				} else if (eingabe == 2) {
					
					cl.show(cont, "spiel2");
					System.out.println("Spiel 2 wird gestartet");
				} else if (eingabe == 3) {
					
					cl.show(cont, "spiel3");
					System.out.println("Spiel 3 wird gestartet");
				} else if (eingabe == 4) {
					
					cl.show(cont, "spiel4");
					System.out.println("Spiel 4 wird gestartet");
				} else {
					
				}
			} catch (InputMismatchException e) {
				
			}
		}


	public static int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}
	public static int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

}
