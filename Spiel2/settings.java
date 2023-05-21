package Spiel2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import Spiel2.Game.Level;
import Spiel2.lower.MyLabel;
import layout_frame.Spielanleitung;

public class settings extends JPanel {
	private static JButton Train, Med, Hard, Start, End, Submit, spielanleitung_b;
	private JToggleButton Pause;

	public JToggleButton getPause() {
		return Pause;
	}

	public void setPause(JToggleButton pause) {
		Pause = pause;
	}

	private static container cont;
	private ArrayList<JButton> levels = new ArrayList<JButton>();
	private final ClockListener clock = new ClockListener();
	private static Timer timer;
	private final JTextField tf;
	public JButton z_button, resetScore;
	public GridBagConstraints gbc;
	private Image img;

	private JLabel actualMode, maxTank, actualLvl;

	public JLabel getActualLvl() {
		return actualLvl;
	}

	public void setActualLvl(JLabel actualLvl) {
		this.actualLvl = actualLvl;
	}

	public int actualLevel;

	public JLabel getMaxTank() {
		return maxTank;
	}

	public void setMaxTank(JLabel maxTank) {
		this.maxTank = maxTank;
	}

	private String Mode_text;
	private String max_tank;

	public String getMax_tank() {
		return max_tank;
	}

	public void setMax_tank(String max_tank) {
		this.max_tank = max_tank;
	}

	private int hours, minutes, seconds;
	private String hour, minute, second;
	private int med_start = 90, hard_start = 120;
	public JTextArea highScore;
	private JButton button;
	private static int points;
	private JTextField aktScore;
	public JLabel timeOut = new JLabel("Time Out!");
	public JTextField getAktScore() {
		return aktScore;
	}

	public void setAktScore(JTextField aktScore) {
		this.aktScore = aktScore;
	}

	public String line1 = new String(), line2 = new String(), line3 = new String();
	private File file = new File("score_spiel2.txt");;

	public static int getPoints() {
		return points;
	}

	public static void setPoints(int points) {
		settings.points = points;
	}

	public settings(container cont) {
		settings.cont = cont;
		setMinimumSize(new Dimension(150, 600));
		setVisible(true);
		setLayout(new GridBagLayout());

		gbc = new GridBagConstraints();

		timer = new Timer(1000, clock);
		tf = new JTextField(5);
		tf.setMinimumSize(new Dimension(80, 30));
		points = 0;
		aktScore = new JTextField("Score: " + Integer.toString(cont.getLower().getHighScore()));
		aktScore.setEditable(false);
		aktScore.setMinimumSize(new Dimension(80, 30));

		Train = new JButton("Training");
		Med = new JButton("Medium");
		Hard = new JButton("Hard");
		Start = new JButton("Start");
		Pause = new JToggleButton("Pause");
		End = new JButton("End Game");
		Submit = new JButton("Submit");

		Mode_text = new String();
		max_tank = new String();
		maxTank = new JLabel();
		actualLevel = 0;
		actualLvl = new JLabel();

		levels.add(Train);
		levels.add(Med);
		levels.add(Hard);

		gbc.gridy = 0;
		gbc.ipadx = 0;
		URL url = this.getClass().getResource("/resources/game_guide.png");
		try {
			img = ImageIO.read(url);
			img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		} catch (IOException e) {
			e.printStackTrace();
		}

		spielanleitung_b = new JButton(new ImageIcon(img));
		spielanleitung_b.setSize(50, 50);
		spielanleitung_b.setBorderPainted(false);
		spielanleitung_b.setFocusable(false);
		add(spielanleitung_b, gbc);
		spielanleitung_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame j = new JFrame();
				// j.setPreferredSize(new Dimension(400,300));
				j.setVisible(false);

				String s = "  Es gibt insgesamt 10 Fischarten, die sich nicht miteinander vertragen,\n"
						+ "und diese Konflikte der Fischarten" + "sind in einer Tabelle dargestellt.\n"
						+ "Spieler muss die Fische, die sich daunten befinden so mit dem Mouse auf Aquarien aufteilen,\n"
						+ "dass es in keinem Aquarium zu Konflikten zwischen zwei Fischen kommt.\n"
						+ "Zudem gibt es eine Schranke, die vorgibt, wie viele Aquarien dabei maximal verwendet werden dürfen.\n"
						+ "Man kann seine Lösung abgeben, indem man entweder den Wagen zu der Fahnenstange führt, \n"
						+ "oder das Button 'Submit' drückt. \n"
						+ "Man erhält so viele Punkte, wie es Fische in Aquarien gibt.";

				JOptionPane.showMessageDialog(j, s, "Spielanleitung", JOptionPane.PLAIN_MESSAGE);
//                a.setText(s);
//                j.add(a);
				j.setLocationRelativeTo(null);
				j.pack();

			}
		});

		JPopupMenu popupMenu = new JPopupMenu("level");
		popupMenu.setFocusable(false);
		popupMenu.add(Train);
		popupMenu.add(Med);
		popupMenu.add(Hard);
		button = new JButton("MODE");
		// this button shows popupmenu when clicked on
		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		gbc.gridy = 5;
		gbc.ipadx = 50;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(Start, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 6;
		gbc.ipady = 20;

		add(button, gbc);

		gbc.ipadx = 0;
		gbc.ipady = 0;
		button.setFocusable(false);
		getTrain_b().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualLevel = 0;
				cont.getSplitPane().remove(cont.getUpper());
				cont.getSplitPane().remove(cont.getUpper().getScroll_table());
				cont.remove(cont.getLower());
				cont.setUpper(new upper(Level.TRAINING));
				// System.out.println("upper level" + upper.getL());
				cont.getSplitPane().setTopComponent(cont.getUpper().getScroll_table());
				cont.setLower(new lower(cont));
				cont.getSplitPane().setBottomComponent(cont.getLower());
				cont.getSplitPane().setDividerLocation(200);
				// cont.getLower().requestFocusInWindow();
				cont.getUpper().setVisible(false);
				cont.getUpper().getScroll_table().setVisible(false);
				cont.getLower().setVisible(false);
				Start.setEnabled(true);
				cont.revalidate();
			}
		});

		getMed_b().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualLevel = 0;
				cont.getSplitPane().remove(cont.getUpper());
				cont.getSplitPane().remove(cont.getUpper().getScroll_table());
				cont.remove(cont.getLower());
				cont.setUpper(new upper(Level.MEDIUM));
				cont.getSplitPane().setTopComponent(cont.getUpper().getScroll_table());
				cont.setLower(new lower(cont));
				cont.getSplitPane().setBottomComponent(cont.getLower());
				cont.getSplitPane().setDividerLocation(200);
				// cont.getLower().requestFocusInWindow();
				cont.getUpper().setVisible(false);
				cont.getUpper().getScroll_table().setVisible(false);
				cont.getLower().setVisible(false);
				Start.setEnabled(true);
				cont.revalidate();
			}
		});

		getHard_b().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualLevel = 0;
				cont.getSplitPane().remove(cont.getUpper());
				cont.getSplitPane().remove(cont.getUpper().getScroll_table());
				cont.remove(cont.getLower());
				cont.setUpper(new upper(Level.HARD));
				cont.getSplitPane().setTopComponent(cont.getUpper().getScroll_table());
				cont.setLower(new lower(cont));
				cont.getSplitPane().setBottomComponent(cont.getLower());
				cont.getSplitPane().setDividerLocation(200);
				// cont.getLower().requestFocusInWindow();
				cont.getUpper().setVisible(false);
				cont.getUpper().getScroll_table().setVisible(false);
				cont.getLower().setVisible(false);
				Start.setEnabled(true);
				cont.revalidate();
			}
		});

		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(timeOut);
				z_button.setEnabled(false);
				line1 = new String();
				line2 = new String();
				line3 = new String();

				cont.getUpper().getScroll_table().setVisible(true);
				cont.getUpper().setVisible(true);
				cont.getLower().setVisible(true);
				Mode_text = "Your Mode: " + upper.getL();
				// System.out.println(Mode_text);
				actualMode.setForeground(Color.white);
				actualMode.setText(Mode_text);
				gbc.gridy = 1;
				actualMode.setVisible(true);
				add(actualMode, gbc);
				actualLevel += 1;

				gbc.insets = new Insets(0, 0, 0, 0);
				actualLvl.setText("Level: " + Integer.toString(actualLevel));
				actualLvl.setVisible(true);
				gbc.gridy = 2;
				add(actualLvl, gbc);
				actualLvl.setForeground(Color.white);
				gbc.insets = new Insets(0, 0, 20, 0);
				gbc.gridy = 3;
				max_tank = Integer.toString(upper.getNewGame().Max_tank(upper.getL()));
				maxTank.setText("Max.Aquarium: " + max_tank);
				maxTank.setForeground(Color.white);
				maxTank.setVisible(true);
				add(maxTank, gbc);
				gbc.insets = new Insets(0, 0, 0, 0);
				gbc.gridy = 4;
				aktScore.setVisible(true);
				add(aktScore, gbc);

				cont.getSplitPane().setDividerLocation(200);
				cont.getLeftPane().add(cont.getSplitPane());
				cont.getLeftPane().setFocusable(false);
				cont.getLower().requestFocusInWindow();
				cont.revalidate();

				timer = new Timer(1000, clock);
				if (upper.getL() == Level.TRAINING) {
					minutes = 0;
					seconds = 0;
				}
				timer.start();

				try {

					BufferedReader reader = new BufferedReader(new FileReader(file));
					String line = reader.readLine();
//            		
//            		{
					for (int i = 0; i < 6; i++) {
						if (i < 2) {
							line1 += line + "   ";
						} else if (i < 4) {
							line2 += line + "   ";
						} else {
							line3 += line + "   ";
						}

						line = reader.readLine();
					}

					reader.close();
				} catch (NumberFormatException | IOException e1) {

				}
				highScore.setText("Highscores: " + "\n" + line1 + "\n" + line2 + "\n" + line3);

				button.setEnabled(false);
				cont.revalidate();
				Start.setEnabled(false);
				Pause.setEnabled(true);
			}
		});

		Pause.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (Pause.isSelected()) {
					timer.stop();

					Pause.setText("Continue");
					tf.setForeground(Color.red);
					cont.getLower().pause = true;
				} else {
					timer.start();
					tf.setForeground(Color.BLACK);
					Pause.setText("Pause");
					cont.getLower().pause = false;

				}
			}
		});
		Submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cont.getLower().keylistener.SolutionValid()) {
					cont.getLower().solveGame();
					cont.getLower().Solution();
					cont.getLower().timer = new Timer(50, new ActionListener() {
						int change = 10;

						@Override
						public void actionPerformed(ActionEvent e) {
							if (!cont.getLower().lost)
								for (MyLabel f : cont.getLower().fishList) {
									int x = f.getX() + change;
									change += 1;
									// f.setBounds(f.getX(), f.getY()-100, (int)f.getSize().getWidth(),(int)
									// f.getSize().getHeight());
									f.setLocation(x, f.getY());
									revalidate();
									repaint();

								}
						}
					});
					cont.getLower().timer.start();
					timer.stop();
				}
			}
		});

		End.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGames();
				z_button.setEnabled(true);
			}
		});

		resetScore = new JButton("Reset Scores");
		resetScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = "score_spiel2.txt";
				StringBuilder highscoreString = new StringBuilder();
				try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
					String name = "-";

					String line = Integer.toString(0);
					for (int i = 0; i < 3; i++) {
						highscoreString.append(name + "   ");
						highscoreString.append(line + "\n");
						bufferedWriter.write("-" + "\n" + 0 + "\n");
					}
					bufferedWriter.close();
					;
				} catch (IOException e1) {
					// Exception handling
				}

//        		System.out.println(highscoreString.toString());
				highScore.setText("Highscores \n" + highscoreString.toString());
			}
		});

		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 8;
		add(Pause, gbc);
		gbc.gridy = 9;
		add(Submit, gbc);
		gbc.gridy = 10;
		add(End, gbc);
		highScore = new JTextArea(4, 10);
		highScore.setMinimumSize(new Dimension(100, 80));
		highScore.setPreferredSize(new Dimension(100, 80));

		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
//    		while (line != null) // read the score file line by line
//    		{
			for (int i = 0; i < 6; i++) {
				if (i < 2) {
					line1 += line + "   ";
				} else if (i < 4) {
					line2 += line + "   ";
				} else {
					line3 += line + "   ";
				}

				line = reader.readLine();
			}

			// }
			reader.close();
		} catch (NumberFormatException | IOException e1) {

		}
		highScore.setText("Highscores: " + "\n" + line1 + "\n" + line2 + "\n" + line3);

//  
//        try {
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

		gbc.insets = new Insets(20, 0, 10, 0);
		gbc.gridy = 11;
		highScore.setEditable(false);
		add(highScore, gbc);

		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 12;
		add(resetScore, gbc);

		gbc.gridy = 13;
		z_button = new JButton("Zurück");
		z_button.setBackground(Color.GREEN);
		z_button.setForeground(Color.GREEN);
		z_button.setOpaque(true);
		add(z_button, gbc);

		Train.setFocusable(false);
		Med.setFocusable(false);
		Hard.setFocusable(false);
		Pause.setFocusable(false);
		End.setFocusable(false);
		Submit.setFocusable(false);
		resetScore.setFocusable(false);
		z_button.setFocusable(false);

		Countup();
		actualMode = new JLabel(Mode_text);
		setBackground(new Color(105, 161, 151));
		setVisible(true);
	}

	private void Countup() {
		timer.setInitialDelay(0);
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setEditable(false);
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridy = 7;
		add(tf, gbc);
	}

	public void resetTimer(Level l) {
		switch (l) {
		case TRAINING:
			minutes = 0;
			seconds = 0;
		case MEDIUM:
			med_start = 90;
		case HARD:
			hard_start = 120;
		}
		timer.start();
	}

	public void resetGames() {
		Level l_tem = upper.getL();
		cont.getSplitPane().remove(cont.getUpper());
		cont.getSplitPane().remove(cont.getUpper().getScroll_table());
		cont.remove(cont.getLower());
		cont.setUpper(new upper(l_tem));
		cont.getSplitPane().setTopComponent(cont.getUpper().getScroll_table());
		cont.setLower(new lower(cont));
		cont.getSplitPane().setBottomComponent(cont.getLower());
		cont.getSplitPane().setDividerLocation(200);
		cont.getUpper().setVisible(false);
		cont.getUpper().getScroll_table().setVisible(false);
		cont.getLower().setVisible(false);
		button.setEnabled(true);
		Pause.setEnabled(false);
		timer.stop();
//        timer = new Timer(1000, clock);
		NumberFormat formatter = new DecimalFormat("00");
		hour = formatter.format(00);
		minute = formatter.format(00);
		second = formatter.format(00);
		med_start = 90;
		hard_start = 120;
		actualLevel = 0;
		tf.setForeground(Color.black);
		tf.setText(String.valueOf(hour + ":" + minute + ":" + second));
		Pause.setSelected(false);
		cont.getLeftPane().revalidate();
		Start.setEnabled(true);
		actualMode.setVisible(false);
		actualLvl.setVisible(false);
		maxTank.setVisible(false);
		revalidate();
	}

	private class ClockListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			File file = new File("score_spiel2.txt");
			// minutes = 0;
			// seconds = 0;
			NumberFormat formatter = new DecimalFormat("00");
			if (upper.getL() == Level.TRAINING) {
				if (seconds == 60) {
					seconds = 00;
					minutes++;
				}

				if (minutes == 60) {
					minutes = 00;
					hours++;
				}

				seconds++;
			} else if (upper.getL() == Level.MEDIUM) {
				minutes = med_start / 60;
				seconds = med_start % 60;
				med_start--;
				minute = formatter.format(minutes);
				second = formatter.format(seconds);
				tf.setText(String.valueOf("00" + ":" + minute + ":" + second));
				if (med_start < 15) {
					tf.setForeground(Color.red);
				}
				if (med_start < 1) {
					System.out.println(cont.getLower().findMin(file));
					if (cont.getLower().getHighScore() >= cont.getLower().findMin(file)) {
						cont.getLower().lost = true;
						cont.getLower().Username();
					}
					gbc.gridy = 1;
					timeOut.setForeground(Color.red);
					add(timeOut, gbc);

					resetGames();
				}
				// System.out.println(med_start);
			} else {
				minutes = hard_start / 60;
				seconds = hard_start % 60;
				hard_start--;
				minute = formatter.format(minutes);
				second = formatter.format(seconds);
				tf.setText(String.valueOf("00" + ":" + minute + ":" + second));
				if (hard_start < 15) {
					tf.setForeground(Color.red);
				}
				if (hard_start < 1) {
					System.out.println(cont.getLower().findMin(file));
					if (cont.getLower().getHighScore() >= cont.getLower().findMin(file)) {
						cont.getLower().lost = true;
						cont.getLower().Username();
					}
					gbc.gridy = 1;
					timeOut.setForeground(Color.red);
					add(timeOut,gbc);

					resetGames();
				}
			}
			hour = formatter.format(hours);
			minute = formatter.format(minutes);
			second = formatter.format(seconds);
			tf.setText(String.valueOf(hour + ":" + minute + ":" + second));

		}
	}

	public static JButton getHard_b() {
		return settings.Hard;
	}

	public void setHard_b(JButton hard) {
		Hard = hard;
	}

	public static JButton getMed_b() {
		return settings.Med;
	}

	public void setMed_b(JButton med) {
		Med = med;
	}

	public static JButton getTrain_b() {
		return settings.Train;
	}

	public void setTrain_b(JButton train) {
		Train = train;
	}

	public static container getCont() {
		return settings.cont;
	}

	public void setCont(container cont) {
		settings.cont = cont;
	}

	public static Timer getTimer() {
		return settings.timer;
	}

	public void setTimer(Timer timer) {
		settings.timer = timer;
	}

	public JLabel getactualMode() {
		return actualMode;
	}

	public void setactualMode(JLabel actualMode) {
		this.actualMode = actualMode;
	}

	public String getMode_text() {
		return Mode_text;
	}

	public void setMode_text(String Mode_text) {
		Mode_text = Mode_text;
	}

}
