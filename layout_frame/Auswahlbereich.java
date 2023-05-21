package layout_frame;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import mazegen.MazePanel;
import mazegen.Path;


public class Auswahlbereich extends JPanel{
	private final int WIDTH = Frame.WIDTH/5;
	private final int HEIGHT = 800; 
	private Image img;
	private Icon icon;
	public static JButton spielanleitung_b;
    public JButton spiel_1;
    public JButton spiel_2;
    public JButton spiel_3;
    public JButton spiel_4;
	public static JButton zurueck_start_b;
	public static JButton eins_zureuck_b;
	
	private List<int[]> moves = MazePanel.moves;
	
	public Auswahlbereich() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		zurueck_start_b = new JButton("Zurueck zum Start");
		zurueck_start_b.setMinimumSize(new Dimension(60,20));
		
		eins_zureuck_b = new JButton("Eins zurueck");
		eins_zureuck_b.setMinimumSize(new Dimension(60,20));
		
		String EM_Schriftzug = "<html>"
				+ "<p style=\"text-align: center; color: white; font-family: 'Courier New'; \"> <strong>Entwicklermodus</strong></p>\r\n"
				+ "<p style=\"text-align: justify; color: white; font-family: 'Courier New';\">Spiele direkt starten</p>"
				+ "</html>";
		
		
		JLabel EM_b = new JLabel("");
		
		spiel_1 = new JButton("Spiel 1");
		spiel_1.setBackground(Color.blue);
		spiel_1.setOpaque(true);
		spiel_1.setMinimumSize(new Dimension(60,20));
		
		spiel_2 = new JButton("Spiel 2");
		spiel_2.setBackground(Color.green);
		spiel_2.setOpaque(true);
		spiel_2.setMinimumSize(new Dimension(60,20));
		
		spiel_3 = new JButton("Spiel 3");
		spiel_3.setBackground(Color.pink);
		spiel_3.setOpaque(true);
		spiel_3.setMinimumSize(new Dimension(60,20));
		
		spiel_4 = new JButton("Spiel 4");
		spiel_4.setBackground(Color.yellow);
		spiel_4.setOpaque(true);
		spiel_4.setMinimumSize(new Dimension(60,20));
		

		URL url = this.getClass().getResource("/resources/game_guide.png");
		try {
			img = ImageIO.read(url);
			img= img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			icon = new ImageIcon(img);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		spielanleitung_b  = new JButton(icon);
		spielanleitung_b.setSize(80, 80);
		spielanleitung_b.setBorderPainted(false);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 20;
		gbc.insets = new Insets(0,0,20,0); 
		add(spielanleitung_b, gbc);
		
		spielanleitung_b.setFocusable(false);
		gbc.weighty = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0,0,10,0); 
		add(zurueck_start_b, gbc);
		
		zurueck_start_b.setFocusable(false);
		gbc.weighty = 0;
		gbc.gridy = 2;
		add(eins_zureuck_b, gbc);
		
		eins_zureuck_b.setFocusable(false);
		gbc.weighty = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0,0,20,0); 

		EM_b.setText(EM_Schriftzug);
		add(EM_b, gbc);
		EM_b.setFocusable(false);

		gbc.weighty = 0;
		gbc.gridy = 4;
		add(spiel_1, gbc);
		spiel_1.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 5;
		add(spiel_2, gbc);
		spiel_2.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 6;
		add(spiel_3, gbc);
		spiel_3.setFocusable(false);
		
		gbc.weighty = 0;
		gbc.gridy = 7;
		add(spiel_4, gbc);
		spiel_4.setFocusable(false);
		
		moves.add(new int[]{MazePanel.getP().getrP(),MazePanel.getP().getcP()});

		
		 zurueck_start_b.addActionListener(new ActionListener(){
		 	public void actionPerformed(ActionEvent e){
		 		// calculate the move needed to get back to start position
		 		// Spielbereich.WIDTH/2 - MazePanel.getP().getPlayer_size() is start row block
		 		// MazePanel.getP().getX() is current row position of player
		 		int og_x = MazePanel.getBlocks()[9][9].getX1();
		 		int og_r = 9;
		 		int x = (og_x - MazePanel.getP().getX())/40;
		 		int y = (og_x - MazePanel.getP().getY())/40;
		 		MazePanel.getP().move(x,y);
		 		moves.clear();
		 		moves.add(new int[]{og_r, og_r});
		 		MazePanel.footsteps.clear();
				MazePanel.blockPath.clear();
				MazePanel.getVisitedBlocks().clear();
		// 		//Set move-counter back to 0
		 		MazePanel.getP().setMove_counter(0);
		 		repaint();
		 		}
		 	 });

		zurueck_start_b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton button = ((JButton)e.getSource());
					button.setEnabled(false);
		
				}
			});
		
		eins_zureuck_b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(moves.size()>=1){
				int x1 = moves.get(moves.size()-1)[0];
				int y1 = moves.get(moves.size()-1)[1];
				int x2 = moves.get(moves.size()-2)[0];
				int y2 = moves.get(moves.size()-2)[1];
				MazePanel.getP().move(y2-y1,x2-x1);
				moves.clear();
				moves.add(new int[]{x2,y2});
				}
				// Decrease move-counter by 1!
				 MazePanel.getP().setMove_counter(MazePanel.getP().getMove_counter()-1);
				 MazePanel.getP().setCostume_change(false);
				 repaint();
				 
				}
		 	 });
		
		// deactivate button after click and only activate when key is pressed
		eins_zureuck_b.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JButton button = ((JButton)e.getSource());
		        button.setEnabled(false);
	
		    }
		});
		}


		public static void spielanleitung(){
			Spielanleitung anleitung = new Spielanleitung();
			anleitung.setVisible(true);
			anleitung.setResizable(false);
		}
		
		
			
}
	
		

	
		



