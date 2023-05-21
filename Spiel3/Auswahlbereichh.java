package Spiel3;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;





public  class Auswahlbereichh extends JPanel implements KeyListener{
	
	private final int WIDTH = Frame.WIDTH/5;
	private final int HEIGHT = 800; 
	private Image img;
	private Icon icon;
	public static JButton spielanleitung_b;
    public JButton Start;
    public JButton beenden;
    public static JButton zurück;
    public static JButton pausieren;
    public static JButton LösAb;
	public static JButton zurueck_start_b;
	public static JButton highscore;
	private static boolean verloren;
	public static boolean isVerloren() {
		return verloren;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		Auswahlbereichh.level = level;
	}

	public static void setVerloren(boolean verloren) {
		Auswahlbereichh.verloren = verloren;
	}

	int delay;
	private static Spielpanel p;
	static String[] choices = { "Training", "Mittel", "Schwer", };
	private static final JComboBox<String> cb = new JComboBox<String>(choices);
	public static int click = 0;
	public static JLabel optSuminAuswahl;
	public static int level;
	public static JLabel Levelauswahl;
	public static JLabel punkte;
	public static int punkt;



	
	
	

	public static Timer getMedium() {
		return medium;
	}

	public void setMedium(Timer medium) {
		this.medium = medium;
	}

	public static Timer getSchwer() {
		return schwer;
	}

	public void setSchwer(Timer schwer) {
		this.schwer = schwer;
	}

	public static JComboBox<String> getCb() {
		return cb ;
	}

	public static Spielpanel getP() {
		return p;
	}

	public static void setP(Spielpanel p) {
		Auswahlbereichh.p = p;
	}
	
//	Timer für das Training
	public final static ClockListener clock = new ClockListener();
	public static Timer training = new Timer (1000, clock);
	public final static ClockListenerMedium clockMedium = new ClockListenerMedium();
	public static Timer medium = new Timer(1000, clockMedium);
	public final static ClockListenerSchwer clockSchwer = new ClockListenerSchwer();
	public static Timer schwer = new Timer(1000, clockSchwer);


	
	private static int hours;
	private static int minutes;
	private static int seconds;
   

	private static String hour;
	private static String minute;
	private static String second;
    private static int med_start = 60;
	private static int hard_start = 30;
    private static JTextField tf;
   public  GridBagConstraints gbc;
   
	
	public void setTf(JTextField tf) {
		this.tf = tf;
	}

	public Auswahlbereichh(Spielpanel p ) {
		this.p = p;
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.red);
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		
		
		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // added code
		    verloren = false;
		    add(panel);

		    JLabel lbl = new JLabel("    Wähle den Schwierigkeitsgrad   ");
		    lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		    //lbl.setVisible(true); // Not needed

		    panel.add(lbl);

//		    String[] choices = { "Training", "Mittel", "Schwer", };
//
//		    cb = new JComboBox<String>(choices);
		    cb.setFocusable(false);

		    cb.setMaximumSize(cb.getPreferredSize()); // added code
		    cb.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
		    //cb.setVisible(true); // Not needed
		    panel.add(cb);

		    JButton btn = new JButton("OK");
		    btn.setAlignmentX(Component.CENTER_ALIGNMENT); // added code
		    panel.add(btn);
		    
		    btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					
		
				}
			});

		
//		highscore = new JButton("Highscore");
//		hihscore.setMinimumSize(new Dimension(60,20));
//		highscore.setBackground(Color.blue);

		
		highscore = new JButton("Highscoreboard ");
//		highscorezurück.setMinimumSize(new Dimension(60,20));
		highscore.setBackground(Color.blue);

		
//		String EM_Schriftzug = "<html>"
//				+ "<p style=\"text-align: center; color: white; font-family: 'Courier New'; \"> <strong>Entwicklermodus</strong></p>\r\n"
//				+ "<p style=\"text-align: justify; color: white; font-family: 'Courier New';\">Spiele direkt starten</p>"
//				+ "</html>";
		
		
		JLabel EM_b = new JLabel("");
		
		Start = new JButton("Start");
		Start.setBackground(Color.blue);
		Start.setOpaque(true);
//		Start.setMinimumSize(new Dimension(60,20));
		
		

	
		
		beenden = new JButton("Beenden");
		beenden.setBackground(Color.blue);
		beenden.setOpaque(true);
//		beenden.setMinimumSize(new Dimension(60,20));
		
		beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Snippet.spielbereich.remove(Spielbereich.getPanel());
				Snippet.spielbereich.setP(new Spielpanel(Color.CYAN));
				Snippet.spielbereich.add(Spielbereich.getPanel());
				Spielbereich.getPanel().setSum(0);
				setP(Spielbereich.getPanel());
				Snippet.cl.show(Snippet.cont, "card1");
//				System.out.println("click");
				Auswahlbereichh.setLevel(1);
				Auswahlbereichh.Levelauswahl.setText(" Level :" + Auswahlbereichh.getLevel());
				String mode = cb.getSelectedItem().toString();
				if(mode == "Training") {
					training.stop();
					minutes = 00;
					seconds = 00;
					getTf().setText(String.valueOf("00" + ":" + "00" + ":" + "00"));
					punkt = 0;
					punkte.setText(" Deine Punkte sind: " + punkt);

					
				}
				if(mode == "Mittel") {
					medium.stop();
					med_start = 60;
					seconds = 00;
					minutes = 00;
					getTf().setText(String.valueOf("00" + ":" + "00" + ":" + "00"));
					punkt = 0;
					punkte.setText(" Deine Punkte sind: " + punkt);


				}
				if(mode == "Schwer") {
					schwer.stop();
					hard_start = 30;
					minutes = 00;
					seconds = 00;
					getTf().setText(String.valueOf("00" + ":" + "00" + ":" + "00"));
					punkt = 0;
					punkte.setText(" Deine Punkte sind: " + punkt);

				}
				 highscorebeenden();

			}
		});
		
		
		optSuminAuswahl = new JLabel("Kostenschranke: ");
		Levelauswahl = new JLabel("Level : 1");
		level = 1;
		punkte = new JLabel("Deine Punkte sind : ");
		punkt = 0;

		
		zurück = new JButton("Zurück");
		zurück.setBackground(Color.blue);
		zurück.setOpaque(true);
//		zurück.setMinimumSize(new Dimension(60,20));
		
		pausieren = new JButton("Pausieren");
		pausieren.setBackground(Color.blue);
		pausieren.setOpaque(true);
//		pausieren.setMinimumSize(new Dimension(60,20));
		
		LösAb = new JButton("Lösung abgeben");
		LösAb.setBackground(Color.blue);
		LösAb.setOpaque(true);
//		LösAb.setMinimumSize(new Dimension(60,20));
		
		LösAb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lösung();
				String mode = cb.getSelectedItem().toString();
				if(mode == "Training") {
					training.stop();
				}
				if(mode == "Mittel") {
					medium.stop();				
				}
				if(mode == "Schwer") {
					schwer.stop();
				}
				
				
	
			}
		});
		pausieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mode = cb.getSelectedItem().toString();
				if(mode == "Training" && click == 0 ) {
					training.stop();
					Spielpanel.setPause(false);
					click = 1;
					
			       
				}else if(mode == "Training" && click == 1) {
					training.start();
					Spielpanel.setPause(true);
					click = 0;
					
					
				}
				if(mode == "Mittel" && click == 0) {
					medium.stop();	
					Spielpanel.setPause(false);

					click = 1;
				}else if (mode == "Mittel" && click == 1) {
					medium.start();
					Spielpanel.setPause(true);

					click = 0;
				}
				if(mode == "Schwer" && click == 0) {
					schwer.stop();
					Spielpanel.setPause(false);

					click = 1 ;
				}else if (mode == "Schwer" && click == 1) {
					schwer.start();
					Spielpanel.setPause(true);

					click = 0;
				}
					
			} 
		});
		
		

		
		

		URL url = this.getClass().getResource("/resources/game_guide.png");
		try {
			img = ImageIO.read(url);
			img= img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			icon = new ImageIcon(img);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		spielanleitung_b  = new JButton(icon);
		spielanleitung_b.setSize(40, 40);
		spielanleitung_b.setBorderPainted(false);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 20;
		gbc.insets = new Insets(0,0,150,0); 
		add(spielanleitung_b, gbc);
		gbc.insets = new Insets(0,0,0,0); 
		gbc.gridy = 1;
		add(optSuminAuswahl,gbc);
		optSuminAuswahl.setForeground(Color.WHITE);
		gbc.gridy = 2;
		add(punkte,gbc);
		punkte.setForeground(Color.WHITE);
		gbc.gridy = 3;
		add(Levelauswahl, gbc);
		Levelauswahl.setForeground(Color.WHITE);
		
		spielanleitung_b.setFocusable(false);
		gbc.weighty = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0,0,10,0); 
//		add(zurueck_start_b, gbc);
		
//		zurueck_start_b.setFocusable(false);
		gbc.weighty = 0;
		gbc.gridy = 5;
		add(highscore, gbc);
		
		highscore.setFocusable(false);
		gbc.weighty = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(0,0,20,0); 

//		EM_b.setText(EM_Schriftzug);
//		add(EM_b, gbc);
//		EM_b.setFocusable(false);

		gbc.weighty = 0;
		gbc.gridy = 7;
		add(Start, gbc);
		Start.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 8;
		add(beenden, gbc);
		beenden.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 9;
		add(zurück, gbc);
		zurück.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 10;
		add(pausieren, gbc);
		pausieren.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 11;
		add(LösAb, gbc);
		LösAb.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 12;
		
		tf = new JTextField(5);
		tf.setMinimumSize(new Dimension(60,20));
		add(getTf(), gbc);
		
		highscore.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					highscoreframe();					
		
				}
			});
//		
//	
//		
		// deactivate button after click and only activate when key is pressed
		spielanleitung_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mode = cb.getSelectedItem().toString();
				if(mode == "Training" && click == 0 ) {
					training.stop();
					Spielpanel.setPause(false);
					click = 1;
					
			       
				}else if(mode == "Training" && click == 1) {
					training.start();
					Spielpanel.setPause(true);
					click = 0;
					
					
				}
				if(mode == "Mittel" && click == 0) {
					medium.stop();	
					Spielpanel.setPause(false);

					click = 1;
				}else if (mode == "Mittel" && click == 1) {
					medium.start();
					Spielpanel.setPause(true);

					click = 0;
				}
				if(mode == "Schwer" && click == 0) {
					schwer.stop();
					Spielpanel.setPause(false);

					click = 1 ;
				}else if (mode == "Schwer" && click == 1) {
					schwer.start();
					Spielpanel.setPause(true);

					click = 0;
				}
					
			} 
		});
//		}
		
	

		
		
	}
	
	public static int getMed_start() {
		return med_start;
	}

	public static void setMed_start(int med_start) {
		Auswahlbereichh.med_start = med_start;
	}

	public static int getHard_start() {
		return hard_start;
	}

	public static void setHard_start(int hard_start) {
		Auswahlbereichh.hard_start = hard_start;
	}
	String userWord = "";
	JTextField userInput;
	
	public void highscorebeenden() {
		JFrame high = new JFrame();
		JLabel bester = new JLabel();
		bester.setText("<html><body>1. Jonas 415 Punkte.<br>2. Tom 325 Punkte.<br>3. Lishan 300 Punkte.<br><br>Es wird Überprüft, ob dein Score größer als die der drei besten Spieler sind.</body></html>");
		JButton jButton = new JButton("Click");
		userInput = new JTextField("", 30);
		jButton.addActionListener( (e) -> {
			submitAction();
		});
		high.setTitle("HIGHSCORE");
		userInput.setToolTipText("Gib deinen Namen ein");
		high.add(bester);
		high.add(userInput);
		high.add(jButton);

		high.setLayout(new FlowLayout());
		high.dispose();	
		high.setPreferredSize(new Dimension(500, 200));
		high.setLocationRelativeTo(null);
		high.pack();
		high.setVisible(true);
	}
	public void highscoreframe() {
		JFrame high = new JFrame();
		JLabel bester = new JLabel();
		bester.setText("<html><body>1. Jonas 415 Punkte.<br>2. Tom 325 Punkte.<br>3. Lishan 300 Punkte. </body></html>");
		high.add(bester);
		
		high.setLayout(new FlowLayout());
		high.dispose();	

		high.setPreferredSize(new Dimension(300, 200));
		high.setLocationRelativeTo(null);
		high.pack();
		high.setVisible(true);

	}
	private void submitAction() {
		userWord = userInput.getText();
//		System.out.println("Name");
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

	public void Lösung() {
		JFrame lösung = new JFrame();
		JButton next = new JButton("Weiter");
		JLabel win = new JLabel();
		JLabel optsum = new JLabel();
		JButton jButton = new JButton("antippen");
		lösung.setLayout(new FlowLayout());
		boolean allStations = false;
		int nodes_clicked = p.graph.getNumberNodeIncluded();
		
		String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
		if(mode == "Training") {
			getP().setOptimalSum((getP().getOptimalSum()*2)-1);
			punkt += p.graph.getIncludedEdges().size()  ;

		}
		if(mode == "Mittel") {
			getP().setOptimalSum(getP().getOptimalSum()*1.5);
			punkt += p.graph.getIncludedEdges().size() * 2 ;

		}
		if(mode == "Schwer") {
			getP().setOptimalSum(getP().getOptimalSum()*1.2);
			punkt += p.graph.getIncludedEdges().size()*3;
		}
		
		if (nodes_clicked == p.graph.getNumOfNodes()) {
//			System.out.println("nodes "+nodes_clicked);
			allStations = true;
		}
		
//		System.out.println("Das ist der test");
//		System.out.println(p.getSUm());
//		System.out.println(nodes_clicked);
//		System.out.println(p.graph.getNumOfNodes());

		if (!allStations || p.getSUm() > (int) p.getOptimalSum()) {
			verloren = true;
			win.setText("Du hast VERLOREN."  + " "+  " Deine Summe ist: " +Spielbereich.getPanel().getSUm());
			optsum.setText( "-->Die Kostenschranke liegt bei : " +(int) (getP().getOptimalSum()+1));
			lösung.add(optsum);
			lösung.add(win);
			level = 1;
			Levelauswahl.setText(" Level :" + level);
			punkt = 0;
			punkte.setText(" Deine Punkte sind: " + punkt);
			optsum.setText("<html><body>1. Jonas 415 Punkte.<br>2. Tom 325 Punkte.<br>3. Lishan 300 Punkte.<br><br>Es wird Überprüft, ob dein Score größer als die der drei besten Spieler sind.</body></html>");
			userInput = new JTextField("", 30);
			jButton.addActionListener( (e) -> {
				submitAction();
			});
			
			userInput.setToolTipText("Gib deinen Namen ein");
			lösung.add(optsum);
			lösung.add(userInput);
			lösung.add(jButton);
			

			

		} else {
			verloren = false;
			win.setText("Du hast GEWONNEN. " + " Deine Summe ist: " +Spielbereich.getPanel().getSUm());
			optsum.setText( "-->Die Kostenschranke liegt bei : " + (int) (getP().getOptimalSum()+1));
			lösung.add(win);
			lösung.add(optsum, BorderLayout.SOUTH);
			level += 1;
			Levelauswahl.setText(" Level :" + level);
//			punkt += p.graph.getIncludedEdges().size();
			punkte.setText(" Deine Punkte sind: " + punkt);
			
		}
		lösung.add(next, BorderLayout.SOUTH);
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Snippet.cont.add(Snippet.spielbereich, "spiel1");
				Snippet.cl.show(Snippet.cont, "spiel1");
//				Spielbereich.getPanel().solve();
			
				
				
//				System.out.println("Wir sind in den Snippets");
//				System.out.println(auswahlbereich.getCb().getSelectedItem().toString());
				String mode = getCb().getSelectedItem().toString();
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
					setP(Spielbereich.getPanel());	
					double d =  getP().getOptimalSum()* 2;
					optSuminAuswahl.setText(" Kostenschranke: "+  (int) (d) );
					
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
					setP(Spielbereich.getPanel());		
					double d =  getP().getOptimalSum()* 1.5;
					optSuminAuswahl.setText("Kostenschranke: "+ (int) (d+1) );
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
					setP(Spielbereich.getPanel());		
					double d =  getP().getOptimalSum()* 1.2;
					optSuminAuswahl.setText("Kostenschranke: "+ (int) (d+1) );
				}
				
				gbc.gridy = 1;
				
				optSuminAuswahl.setForeground(Color.WHITE);
				optSuminAuswahl.setVisible(true);
				lösung.dispose();		}
			
		});
		
			

		lösung.setPreferredSize(new Dimension(500, 300));
		lösung.setLocationRelativeTo(null);
		lösung.pack();
		lösung.setVisible(true);
	}
	
	public static JLabel getPunkte() {
		return punkte;
	}

	public static void setPunkte(JLabel punkte) {
		Auswahlbereichh.punkte = punkte;
	}

	public static int getPunkt() {
		return punkt;
	}

	public static void setPunkt(int punkt) {
		Auswahlbereichh.punkt = punkt;
	}

	public static Timer getTraining() {
		return training;
	}

	public static int getHours() {
		return hours;
	}

	public static void setHours(int hours) {
		Auswahlbereichh.hours = hours;
	}

	public static int getMinutes() {
		return minutes;
	}

	public static void setMinutes(int minutes) {
		Auswahlbereichh.minutes = minutes;
	}

	public static int getSeconds() {
		return seconds;
	}

	public static void setSeconds(int seconds) {
		Auswahlbereichh.seconds = seconds;
	}

	public void setTraining(Timer training) {
		this.training = training;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	private static class ClockListenerSchwer implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
	        NumberFormat formatter = new DecimalFormat("00");

	            minutes = hard_start / 60;
	            seconds = hard_start % 60;
	            hard_start--;
	            minute = formatter.format(minutes);
	            second = formatter.format(seconds);
	            tf.setText(String.valueOf("00" + ":" + minute + ":" + second));
	            if (hard_start < 15) {
	                tf.setForeground(Color.red);
	            }
	            if (hard_start < 0) {
	                schwer.stop();
	                verloren();
	
	            }
	        
	        hour = formatter.format(hours);
	        minute = formatter.format(minutes);
	        second = formatter.format(seconds);
	        getTf().setText(String.valueOf(hour + ":" + minute + ":" + second));
			
		}}
	
	
	private static class ClockListenerMedium implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        NumberFormat formatter = new DecimalFormat("00");
	        
	            minutes = med_start / 60;
	            seconds = med_start % 60;
	            med_start--;
	            minute = formatter.format(minutes);
	            second = formatter.format(seconds);
	            tf.setText(String.valueOf("00" + ":" + minute + ":" + second));
	            if (med_start < 15) {
	                tf.setForeground(Color.red);
	            }
	            if (med_start < 0) {
	            	medium.stop();
	            	
	
	            }
	            if(med_start ==0){
	            	verloren();	           
}
	            // System.out.println(med_start);
	         
	            hour = formatter.format(hours);
		        minute = formatter.format(minutes);
		        second = formatter.format(seconds);
		        getTf().setText(String.valueOf(hour + ":" + minute + ":" + second));

		}}
	
	private static class ClockListener implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent e) {
//	         minutes = 0;
//	         seconds = 0;
	        NumberFormat formatter = new DecimalFormat("00");
	           
	        if (seconds == 60) {
	                seconds = 00;
	                minutes++;
	            }

	            if (minutes == 60) {
	                minutes = 00;
	                hours++;
	            }
	            seconds++;
	        
	        hour = formatter.format(hours);
	        minute = formatter.format(minutes);
	        second = formatter.format(seconds);
	        getTf().setText(String.valueOf(hour + ":" + minute + ":" + second));

	    }}
	
	public static void spielanleitung(){
		Spielanleitung anleitung = new Spielanleitung();
		anleitung.setVisible(true);
		anleitung.setResizable(false);
		
		
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P ) {
//           		System.out.println("hallo");
			String mode = cb.getSelectedItem().toString();
			if(mode == "Training" && click == 0 ) {
				training.stop();
				Spielpanel.setPause(false);
				click = 1;
				
		       
			}else if(mode == "Training" && click == 1) {
				training.start();
				Spielpanel.setPause(true);
				click = 0;
				
			}
		}	}
	public static void verloren() {
		JFrame verloren = new JFrame();
		JLabel win = new JLabel();
		win.setText("Du hast verloren");
		verloren.add(win);
		verloren.setPreferredSize(new Dimension(300, 300));
		verloren.setLocationRelativeTo(null);
		verloren.pack();
		verloren.setVisible(true);
		
	}


	public static JTextField getTf() {
		return tf;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


			
}
			


	
		

	
		



