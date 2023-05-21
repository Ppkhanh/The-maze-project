package Spiel3;
import java.awt.CardLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;







public class Snippet extends JPanel implements MouseListener, KeyListener, ActionListener {
	
	public static int WIDTH ;
	public static int HEIGHT ;
	
	public static JPanel panel;
	public static CardLayout cl;
	public static JPanel cont;
	public static Spielbereich spielbereich;
	public static Auswahlbereichh auswahlbereich;
	
	




	public Snippet() {
			panel = new JPanel();
			cont = new JPanel();
		
			setMinimumSize(new Dimension(600, 600));
			setPreferredSize(new Dimension(1000, 800));
//			setResizable(true);
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			spielbereich  = new Spielbereich();
			Auswahlbereichh auswahlbereich = new Auswahlbereichh(Spielbereich.getPanel());

			
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.8;
			gbc.weighty = 1;
			add(cont, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.weightx = 0.2;
			add(auswahlbereich, gbc);
			
			cl = new CardLayout();
			cont.setLayout(cl);
			cont.add(panel, "card1");
			
			Auswahlbereichh.spielanleitung_b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == Auswahlbereichh.spielanleitung_b){
						Auswahlbereichh.spielanleitung();
						Spielbereich.getPanel().requestFocusInWindow();	
					}
				}
			});
		
			cont.add(spielbereich, "spiel1");
			auswahlbereich.Start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cont.add(spielbereich, "spiel1");
					cl.show(cont, "spiel1");
//					Spielbereich.getPanel().solve();
//					System.out.println("Wir sind in den Snippets");
//					System.out.println(auswahlbereich.getCb().getSelectedItem().toString());
					String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
					int numberNodes = 4;
					
					if (mode == "Mittel") {
						numberNodes = 8;
					}
					if (mode == "Schwer") {
						numberNodes = 12;
					}
				
					spielbereich.remove(Spielbereich.getPanel());
					spielbereich.setP(new Spielpanel(Color.CYAN, numberNodes));
					spielbereich.add(Spielbereich.getPanel());
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
						double d =  Auswahlbereichh.getP().getOptimalSum()* 1.2;
						Auswahlbereichh.optSuminAuswahl.setText("Kostenschranke: "+ (int) (d+1) );
					}
					
					gbc.gridy = 1;
					
					Auswahlbereichh.optSuminAuswahl.setForeground(Color.WHITE);
					Auswahlbereichh.optSuminAuswahl.setVisible(true);
					
					Auswahlbereichh.pausieren.setEnabled(true);
					Auswahlbereichh.LösAb.setEnabled(true);


				}
				
			});
			
			Auswahlbereichh.pausieren.setEnabled(false);
			Auswahlbereichh.LösAb.setEnabled(false);


//			pack();
//			setLocationRelativeTo(null);
			setVisible(true);
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


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_P ) {
//           		System.out.println("hallo");
			String mode = auswahlbereich.getCb().getSelectedItem().toString();
			if(mode == "Training" && auswahlbereich.getClick() == 0 ) {
				auswahlbereich.getTraining().stop();
				Spielpanel.setPause(false);
				auswahlbereich.setClick(1) ;
				
		       
			}else if(mode == "Training" && auswahlbereich.getClick() == 1) {
				auswahlbereich.getTraining().start();
				Spielpanel.setPause(true);
				auswahlbereich.setClick(0);
				
			}
		}			
		}
	
}