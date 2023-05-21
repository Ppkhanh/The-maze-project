package Spiel2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Spiel2.Game.Level;

public class lower extends JPanel implements ActionListener {
	private wagen w;

	public wagen getW() {
		return w;
	}

	public void setW(wagen w) {
		this.w = w;
	}

	public static int block_size;
	private int Width, Height;
	static Block[][] blocks;
	private ArrayList<Integer> last_position;
	private int mistake;
	private int conflicts;
	private container c;
	public boolean resized, lost, exceeded;
	private int points, highScore, min;
	private JTextField name;
	public boolean pause;

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

//    public BufferedReader reader;
	public String line;

	public int getWIdth() {
		return Width;
	}

	public void setWIdth(int width) {
		Width = width;
	}

	private int change;
	private ArrayList<Vertex> fish;
	public ArrayList<MyLabel> fishList;
	public Game newGame;
	public static ArrayList<aqua> Aquas;
	private int y; // y position of fish
	private ArrayList<Integer> x = new ArrayList<Integer>(); // x position of fish
	private JLabel text = new JLabel();
	private Image plus, minus;
	arrow_button plus_b;
	arrow_button minus_b;
	private Level level;
	private static int click_count;
	private int[] distance;
	boolean solution;
	public static JButton exit;
	public Kl keylistener;
	public Timer timer;
	private String nameofMin = new String();
	private Image goal = null;
	private Image ocean = null;

	public static int getClick_count() {
		return lower.click_count;
	}

	public static void setClick_count(int click_count) {
		lower.click_count = click_count;
	}

	private ArrayList<Integer> Cordy_aqua = new ArrayList<Integer>();

	public lower(container c) {
		this.c = c;
		setBackground(Color.white);
		fish = upper.getFish();
		block_size = 50;
		newGame = upper.getNewGame();
		level = newGame.getLevel();
		click_count = -1;
		last_position = new ArrayList<Integer>();
		distance = new int[10];
		for (int i = 0; i < 10; i++) {
			distance[i] = i * 10;
		}
		solution = true;
		lost = false;
		exceeded = false;
		pause = false;

		computeGame();

		points = 0;
		highScore = 0;
		exit = new JButton("Continue");
		timer = new Timer(50, null);
		min = Short.MAX_VALUE;
		// System.out.println(fish.size());
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Width = getSize().width;
				Height = getSize().height;
				Update(Math.min(Width, Height));
				resized = true;
				repaint();
			}
		});

		keylistener = new Kl();
		// timer = new Timer(10, null);
		change = 10;
		addKeyListener(keylistener);
		setFocusable(true);
		requestFocus();
		requestFocusInWindow();
		// setVisible(true);

	}

	public void computeGame() {
		createBlocks();
		w = new wagen(block_size * 6, block_size);
		initAqua();
		setLayout(null);
		fishList = new ArrayList<MyLabel>();
		for (Vertex f : newGame.getGraph().getA()) {
			MyLabel fish = new MyLabel(newGame.getImg(f.getLabel()), f.getShade(f.getLabel()));
			// System.out.println(f.getLabel());
			fishList.add(fish);
		}
		// add fish
		initFish(x, block_size);
		for (int i = 0; i < fish.size(); i++) {

			// draw fish horizontally on panel
			MyLabel f = fishList.get(i);
			f.setBounds(x.get(i), y, block_size / 4 * 3, block_size / 2);

			MyLabel clone = f.myClone();
			clone.setBounds(x.get(i), y, block_size / 4 * 3, block_size / 2);
			add(f);
			add((clone));
			text.setForeground(Color.white);
			text.setText("Click and drag the fish");
			text.setBounds(x.get(0), block_size, Short.MAX_VALUE, 16);
			add(text);

			JPopupMenu conFish = new JPopupMenu();
			JLabel e_new = new JLabel(newGame.getEnemies(fish.get(i)));
			conFish.add(e_new);

			// System.out.println(f.getS());
			if (newGame.getEnemies(fish.get(i)) != null) {

				f.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						conFish.show(e.getComponent(), e.getX(), e.getY());
						conFish.setFocusable(false);
						requestFocusInWindow();
					}

				});

			}
			Mausi m = new Mausi((JComponent) this);
			f.addMouseListener(m);
			f.addMouseMotionListener(m);
			f.setFocusable(false);
		}

		plus = newGame.getImg("plus");
		minus = newGame.getImg("minus");
		plus_b = new arrow_button(plus, blocks, "more", level, this, block_size);
		minus_b = new arrow_button(minus, blocks, "less", level, this, block_size);
		// plus_b.setBounds(plus_b.getLoc().getX1(), plus_b.getLoc().getY1(),
		// block_size-5, block_size-5);
		// minus_b.setBounds(minus_b.getLoc().getX1(), minus_b.getLoc().getY1(),
		// block_size-5, block_size-5);
		add(plus_b);
		add(minus_b);

	}

	private void initAqua() {
		int l = 7;
		int index = 0;
		Aquas = new ArrayList<aqua>();
		while (l > 0) {
			aqua Aqua = new aqua(block_size * 3, block_size * 2, l, 2, index);
			Aquas.add(Aqua);
			Cordy_aqua.add(l);
			l -= 2;
			index += 1;
		}
		l = 7;
		while (l > 0) {
			aqua Aqua = new aqua(block_size * 3, block_size * 2, l, 5, index);
			Aquas.add(Aqua);
			Cordy_aqua.add(l);
			l -= 2;
			index += 1;
		}
		// System.out.println("no of tanks " + Aquas.size());
	}

	private void initFish(ArrayList<Integer> a, int block_size) {
		a.clear();
		for (int i = 0; i < fish.size(); i++) {
			int k = (i + 1) * block_size - 5;
			// int k = blocks[1][i].getX1();
			a.add(k);
		}
		y = 0;

	}

	private void createBlocks() {
		blocks = new Block[10][14];
		Block block;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 14; j++) {
				block = new Block(i, j, this);
				blocks[i][j] = block;
			}
		}

	}

	public void solveGame() {
		newGame.solve();
		int count = 0;
		for (aqua a : Aquas) {
			if (a.added) {
				count += 1;
			}
		}
		for (MyLabel f : fishList) {
			int tank1 = f.getTank();
			int i_fish_check = fishList.indexOf(f);
			Vertex v = newGame.getGraph().getA().get(i_fish_check);
			for (Vertex enemy : newGame.getGraph().getAdjVertices().get(v)) {
				int i_enemy = newGame.getGraph().getA().indexOf(enemy);
				MyLabel enemyFish = fishList.get(i_enemy);
				if (tank1 == enemyFish.getTank()) {
					conflicts += 1;
				}
			}
		}
		conflicts /= 2;
		mistake = count - newGame.getMax();
		if (mistake > level.tolerance()) {
			exceeded = true;
			lost = true;

			// System.out.println(mistake);
		} else {
			if (mistake > 0) {
				exceeded = false;
			}
			if (conflicts > 0) {
				lost = true;

				// System.out.println("conflicts " + conflicts);
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!resized) {
			g.setColor(Color.black);
			g.drawString("LOADING...", 300, 100);
		}
		if (resized) {
			Block block;
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 14; j++) {
					block = blocks[i][j];
					block.setWalls(new boolean[] { false, false, false, false });
					if (j == 2) {
						block.setWalls(new boolean[] { false, false, false, true });
					} else if (j == 9) {
						block.setWalls(new boolean[] { false, true, false, false });
					}
					g.setColor(Color.white);
					block.draw(g);
				}
			}
			drawBackgr(g);
			drawWagen(g);
			addAqua(g);
			drawGoal(g);

			g.setFont(new Font("Courier New", Font.BOLD, 20));
			if (pause) {

				g.drawString("Game is paused!", getBounds().width / 4, getBounds().height / 2);
			}
		}

	}

	private void addAqua(Graphics g) {
		for (int i = 0; i < Aquas.size(); i++) {
			aqua a = Aquas.get(i);
			if (a.added) {
				g.drawImage(a.getImg(), a.current.getX1(), a.current.getY1(), this);
			}
		}
		revalidate();
	}

	private void drawWagen(Graphics g) {
		g.drawImage(w.getImg(), w.current.getX1(), w.current.getY1(), this);
	}

	private void drawGoal(Graphics g) {
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {

				try {
					goal = ImageIO.read(getClass().getClassLoader().getResource("resources/goal.png"));
					goal = goal.getScaledInstance(block_size * 2, block_size * 6, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub

			//}
		//});
		g.drawImage(goal, blocks[4][9].getX1(), blocks[4][9].getY1(), this);

	}

	private void drawBackgr(Graphics g) {
		//SwingUtilities.invokeLater(new Runnable() {

			//@Override
			//public void run() {

				try {
					ocean = ImageIO.read(getClass().getClassLoader().getResource("resources/ocean3.jpeg"));
					ocean = ocean.getScaledInstance(getBounds().width, getBounds().height, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}
//		});
		g.drawImage(ocean, 0, 0, this);

	}

	private void Update(int size) {
		block_size = size / 10;
		boolean b = true;
		if (getW().reached == false) {
			b = false;
		}
		w = new wagen(block_size * 6, block_size);
		w.reached = b;
		ArrayList<Integer> fishinTanks = new ArrayList<Integer>();
		ArrayList<Boolean> tank_added = new ArrayList<Boolean>();
		for (aqua a : Aquas) {
			fishinTanks.add(a.getFishInTank());
			tank_added.add(a.added);
		}
		Aquas.clear();
		for (int i = 0; i < Cordy_aqua.size(); i++) {
			// aqua a = Aquas.get(i);
			if (i < 4) {
				aqua a = new aqua(block_size * 3, block_size * 2, Cordy_aqua.get(i), 2, i);
				a.setFishInTank(fishinTanks.get(i));
				a.added = tank_added.get(i);
				Aquas.add(a);
			} else {
				aqua a = new aqua(block_size * 3, block_size * 2, Cordy_aqua.get(i), 5, i);
				a.setFishInTank(fishinTanks.get(i));
				a.added = tank_added.get(i);
				Aquas.add(a);
			}

		}
		if (last_position.size() > 0) {
			w.setcP(last_position.get(last_position.size() - 1));
			w.setCurrent(blocks[w.getrP()][w.getcP()]);
			w.setPointX(w.current.getX1());
			w.setPointY(w.current.getY1());
		}

		plus_b.setBounds(plus_b.getLoc().getX1(), plus_b.getLoc().getY1(), block_size, block_size);
		minus_b.setBounds(minus_b.getLoc().getX1(), minus_b.getLoc().getY1(), block_size, block_size);
		int i = 0;
		for (MyLabel f : fishList) {
			if (f.hasReached()) {
				aqua a = Aquas.get(f.getTank());
				// System.out.println("fish is in tank " + f.getTank());
				f.setLocation(a.getCurrent().getX1() + block_size / 2 + i, a.getCurrent().getY1() + block_size / 2);
				i += block_size / 5;

			}
		}
	}

	public void Solution() {
		JFrame sol = new JFrame("Solution");
		sol.setPreferredSize(new Dimension(300, 300));
		sol.setMinimumSize(new Dimension(300, 300));
		sol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sol.setLocationRelativeTo(c);
		sol.setLayout(new BorderLayout());
		JButton exit = new JButton("Continue");
		sol.add(exit, BorderLayout.NORTH);
		JLabel mess = new JLabel();
		String message = new String();

		if (lost) {
			points = 0;
//            highScore = points;

			if (exceeded) {
				message = "<html>"
						+ "<p style=\"text-align: center; font-family: 'Courier New'; \"> <strong>YOU LOST! :(\n</strong></p>\r\n"
						+ "<p style=\"text-align: center; font-family: 'Courier New';\">You used " + mistake
						+ " extra Aquariums.</p>" + " <p style=\"text-align: center;\"> SOLUTION: \n </p>"
						+ "<p style=\"text-align: center;\">" + newGame.getGraph().getMessage() + "</p>" + "</html>";
			} else {
				message = "<html>"
						+ "<p style=\"text-align: center; font-family: 'Courier New'; \"> <strong>YOU LOST! :(\n</strong></p>\r\n"
						+ "<p style=\"text-align: center; font-family: 'Courier New';\">" + conflicts
						+ " conflict(s) found in one of your Aquariums.</p>"
						+ " <p style=\"text-align: center;\"> SOLUTION: \n </p>" + "<p style=\"text-align: center;\">"
						+ newGame.getGraph().getMessage() + "</p>" + "</html>";
			}
		} else {
			points = fishList.size();
//            highScore = points;

			//System.out.println("points " + points);
			message = "<html>"
					+ "<p style=\"text-align: center; font-family: 'Courier New'; \"> <strong>YOU WON! :)\n</strong></p>\r\n"
					+ "<p style=\"text-align: center; font-family: 'Courier New'; \"> Let's move on by pressing Continue \n</p>\r\n"
					+ "</html>";
		}
		settings.setPoints(settings.getPoints() + points);
		highScore = settings.getPoints();

		if (exit.getActionListeners().length < 1) {
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.getSett().getAktScore().setText("Score: " + Integer.toString(getHighScore()));
					File file = new File("score_spiel2.txt");
					//System.out.println(findMin(file));
					if (lost && highScore >= findMin(file)) {

						Username();

//						System.out.println("lost?" + lost);
					} else {
						timer.stop();
						Level l_tem = upper.getL();
						//System.out.println("l_tem " + l_tem);
						c.getSplitPane().remove(c.getUpper());
						c.getSplitPane().remove(c.getUpper().getScroll_table());
						c.remove(c.getLower());
						c.setUpper(new upper(l_tem));

						c.getSett().resetTimer(l_tem);
						// System.out.println("next level: " + l_tem);
						c.getSett().gbc.gridy = 1;

						c.getSett().add(c.getSett().getactualMode(), c.getSett().gbc);
						// c.getSett().setMode_text("Your Level: " + upper.getNewGame().Label());
						c.getSett().getactualMode().setText("Your Mode: " + upper.getL());
						// System.out.println("upper level:" + upper.getL());
						c.getSett().getactualMode().setVisible(true);

						c.getSett().actualLevel += 1;

						c.getSett().getActualLvl().setText("Level: " + Integer.toString(c.getSett().actualLevel));
						c.getSett().getActualLvl().setVisible(true);
						c.getSett().gbc.gridy = 2;
						c.getSett().add(c.getSett().getActualLvl(), c.getSett().gbc);

						c.getSett().gbc.gridy = 3;
						c.getSett().getMaxTank().setVisible(true);
						c.getSett().add(c.getSett().getMaxTank(), c.getSett().gbc);
						if (l_tem == Level.MEDIUM || l_tem == Level.HARD) {
							c.getSett().getMaxTank().setText("Max.Aquarium: " + upper.getNewGame().Max_tank(upper.getL()));
						}
						c.getSplitPane().setTopComponent(c.getUpper().getScroll_table());
						c.setLower(new lower(c));
						c.getSplitPane().setBottomComponent(c.getLower());
						c.getSplitPane().setDividerLocation(200);

						revalidate();
					}
					sol.dispose();

				}
			});
		}
		mess.setText(message);
		sol.add(mess, BorderLayout.CENTER);

		sol.pack();
		sol.setVisible(true);
	}

	public void Username() {
		JFrame user = new JFrame("New Highscore!");
		user.setLayout(new GridLayout());

		user.setPreferredSize(new Dimension(300, 300));
		user.setMinimumSize(new Dimension(300, 300));
		user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		user.setLocationRelativeTo(c);
		user.setLayout(new GridLayout(5, 0));

		JLabel score = new JLabel("Your score: " + Integer.toString(settings.getPoints()));
		user.add(score);
		JLabel enter = new JLabel("Enter your Name: ");
		user.add(enter);
		name = new JTextField();
		user.add(name);

		JLabel mess = new JLabel();
		String message = new String();
		if (!lost) {
			message = "<html>" + "<p style=\"text-align: center;\"> Let's move onto next Level </p>\r\n" + "</html>";
		} else {
			message = "<html>" + "<p style=\"text-align: center;\"> Let's try again </p>\r\n" + "</html>";
		}

		mess.setText(message);
		user.add(mess);
		user.add(exit);
		if (exit.getActionListeners().length < 1) {
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.getSett().remove(c.getSett().timeOut);
					Level l_tem = upper.getL();
					System.out.println("l_tem " + l_tem);
					c.getSplitPane().remove(c.getUpper());
					c.getSplitPane().remove(c.getUpper().getScroll_table());
					c.remove(c.getLower());
					c.setUpper(new upper(l_tem));

					c.getSett().resetTimer(l_tem);
					// System.out.println("next level: " + l_tem);
					c.getSett().gbc.gridy = 1;

					c.getSett().add(c.getSett().getactualMode(), c.getSett().gbc);
					// c.getSett().setMode_text("Your Level: " + upper.getNewGame().Label());
					c.getSett().getactualMode().setText("Your Mode: " + upper.getL());
					// System.out.println("upper level:" + upper.getL());
					c.getSett().getactualMode().setVisible(true);

					if (!lost) {
						c.getSett().actualLevel += 1;
					} else {
						String line1 = new String();
						String line2 = new String();
						String line3 = new String();
						File file = new File("score_spiel2.txt");
						saveScore(file);
						try {
							BufferedReader reader = new BufferedReader(new FileReader(file));
							String line = reader.readLine();
//		            		
//		            		{
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
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						c.getSett().highScore.setText("Highscores: " + "\n" + line1 + "\n" + line2 + "\n" + line3);
					}
					c.getSett().getActualLvl().setText("Level: " + Integer.toString(c.getSett().actualLevel));
					c.getSett().getActualLvl().setVisible(true);
					c.getSett().gbc.gridy = 2;
					c.getSett().add(c.getSett().getActualLvl(), c.getSett().gbc);

					c.getSett().gbc.gridy = 3;
					c.getSett().getMaxTank().setVisible(true);
					c.getSett().add(c.getSett().getMaxTank(), c.getSett().gbc);
					if (l_tem == Level.MEDIUM || l_tem == Level.HARD) {
						c.getSett().getMaxTank().setText("Max.Aquarium: " + upper.getNewGame().getMax());
					}

					System.out.println(line);
					c.getSplitPane().setTopComponent(c.getUpper().getScroll_table());
					c.setLower(new lower(c));
					c.getSplitPane().setBottomComponent(c.getLower());
					c.getSplitPane().setDividerLocation(200);

					revalidate();
					user.dispose();

				}
			});
		}
		timer.stop();
		user.pack();
		user.setVisible(true);
	}

	public int findMin(File file) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> scores = new ArrayList<Integer>();

		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			line = reader.readLine();
			while (line != null) // read the score file line by line
			{
				if (line.startsWith("-")) {
					names.add(line);
				}

				try {
					int score = Integer.parseInt(line.trim()); // parse each line as an int
					scores.add(score);
					if (score < min) // and keep track of the largest
					{
						min = score;

					}
//               

				} catch (NumberFormatException e1) {

				}
				line = reader.readLine();
			}
			reader.close();
			int index = scores.indexOf(min);
			nameofMin = names.get(index);

			//System.out.println("min " + min + "of" + nameofMin);

		} catch (IOException ex) {
			//System.err.println("ERROR reading scores from file");
		}
		return min;
	}

	public void saveScore(File file) {
		// store points into file

//		File file = new File("score_spiel2.txt");
		File tempFile = new File(file.getAbsolutePath() + ".tmp");
//        File tempFile = new File(file.getAbsolutePath());

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			StringBuilder s = new StringBuilder();
			int count = 0;
			BufferedWriter output = new BufferedWriter(new FileWriter(tempFile));
			while ((line = reader.readLine()) != null) {
				if ((line.contains(nameofMin) || line.contains(Integer.toString(min))) && count < 2) {
					count += 1;
					//System.out.println("count " + count);
					//System.out.println(line.contains(nameofMin) + ", " + line.contains(Integer.toString(min)) + "?");
					continue;

				}
				s.append(line + "\n");
//					output.append(line.trim() + "\n");
				//System.out.println(line);
//					output.newLine();
//					output.flush();

			}
			reader.close();
			output.append(s.toString());
			output.close();

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(tempFile));
			String line = null;
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			StringBuilder s = new StringBuilder();
			while ((line = reader.readLine()) != null) {

				s.append(line + "\n");
				//System.out.println("new line" + line);
			}

			s.append("- " + name.getText() + "\n");
			s.append(Integer.toString(settings.getPoints()));
			output.write(s.toString().trim());
			output.close();
			reader.close();

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public class MyLabel extends JLabel {
		private Image image;

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
		}

		private boolean moveable = true;
		private boolean reached = false;
		private boolean[] inTank = new boolean[8];
		private int Tank;

		public int getTank() {
			return Tank;
		}

		public void setTank(int tank) {
			Tank = tank;
		}

		public boolean hasReached() {
			return reached;
		}

		public void setReached(boolean reached) {
			this.reached = reached;
		}

		private String s;

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}

		public boolean isMoveable() {
			return moveable;
		}

		public void setMoveable(boolean moved) {
			this.moveable = moved;
		}

		public MyLabel(Image image, String s) {
			this.image = image;
			this.s = s;
			ImageIcon icon = new ImageIcon(image);
			setIcon(icon);
			this.Tank = -1;
		}

		public MyLabel myClone() {
			MyLabel other = new MyLabel(newGame.getImg(s), this.getS());
			other.setBorder(this.getBorder());
			other.setSize(this.getSize());
			return other;
		}

		public int inTank() {
			int tank = -1;
			if (getLocation().getY() > blocks[7][2].getY1() && getLocation().getY() < blocks[9][2].getY1()
					&& getLocation().getX() > blocks[7][2].getX1() && getLocation().getX() < blocks[7][5].getX1()) {
				tank = 0;
			} else if (getLocation().getY() > blocks[5][2].getY1() && getLocation().getY() < blocks[7][2].getY1()
					&& getLocation().getX() > blocks[5][2].getX1() && getLocation().getX() < blocks[5][5].getX1()) {
				tank = 1;
			} else if (getLocation().getY() > blocks[3][2].getY1() && getLocation().getY() < blocks[5][2].getY1()
					&& getLocation().getX() > blocks[3][2].getX1() && getLocation().getX() < blocks[3][5].getX1()) {
				tank = 2;
			} else if (getLocation().getY() > blocks[1][2].getY1() && getLocation().getY() < blocks[3][2].getY1()
					&& getLocation().getX() > blocks[1][2].getX1() && getLocation().getX() < blocks[1][5].getX1()) {
				tank = 3;
			} else if (getLocation().getY() > blocks[7][5].getY1() && getLocation().getY() < blocks[9][5].getY1()
					&& getLocation().getX() > blocks[7][5].getX1() && getLocation().getX() < blocks[7][8].getX1()) {
				tank = 4;
			} else if (getLocation().getY() > blocks[5][5].getY1() && getLocation().getY() < blocks[7][5].getY1()
					&& getLocation().getX() > blocks[5][5].getX1() && getLocation().getX() < blocks[5][8].getX1()) {
				tank = 5;
			} else if (getLocation().getY() > blocks[3][5].getY1() && getLocation().getY() < blocks[5][5].getY1()
					&& getLocation().getX() > blocks[3][5].getX1() && getLocation().getX() < blocks[3][8].getX1()) {
				tank = 6;
			} else if (getLocation().getY() > blocks[1][5].getY1() && getLocation().getY() < blocks[3][5].getY1()
					&& getLocation().getX() > blocks[1][5].getX1() && getLocation().getX() < blocks[1][8].getX1()) {
				tank = 7;
			}
			return tank;
		}

		public void setBounds(int tank) {
			aqua aqua = Aquas.get(tank);
			if (aqua.added) {

				int x = aqua.getCurrent().getX1();
				int y = aqua.getCurrent().getY1();
				Rectangle r = new Rectangle(x + block_size / 2 + distance[aqua.getFishInTank()], y + block_size / 2,
						50 / 4 * 3, 25);
				setBounds(r);
			}

		}

		// }
		public void asigntoTank(int tank) {
			for (int i = 0; i < inTank.length - 1; i++) {
				if (inTank[i]) {
					inTank[i] = false;
				}
			}
			if (inTank() == tank) {
				setReached(true);
				setBounds(tank);
				setTank(tank);
				inTank[tank] = true;
			}
		}

		public void calculateFish(int tank) {
			if (!inTank[tank]) {
				Aquas.get(tank).setFishInTank(Aquas.get(tank).getFishInTank() + 1);
				//System.out.println("tank " + (tank + 1) + " has " + Aquas.get(tank).getFishInTank());
				inTank[tank] = true;
			} else {
				// inTank[tank] = false;
				inTank[tank] = false;
				Aquas.get(tank).setFishInTank(Aquas.get(tank).getFishInTank() - 1);
				//System.out.println("tank " + (tank + 1) + " has " + Aquas.get(tank).getFishInTank());

			}
		}

	}

	public class Mausi extends MouseAdapter {

		private JComponent owner;

		private int startx;
		private int starty;
		private JComponent c = null;

		public Mausi(JComponent owner) {
			this.owner = owner;
		}

		public void mouseEntered(MouseEvent e) {
			c = (JComponent) e.getSource();
		}

		public void mouseReleased(MouseEvent e) {
			if (!pause) {

				MyLabel alt = (MyLabel) e.getSource();
				if (alt.hasReached() == false) {
					if (alt.inTank[0]) {
						alt.calculateFish(0);

					} else if (alt.inTank[1]) {
						alt.calculateFish(1);

					} else if (alt.inTank[2]) {
						alt.calculateFish(2);

					} else if (alt.inTank[3]) {
						alt.calculateFish(3);

					} else if (alt.inTank[4]) {
						alt.calculateFish(4);

					} else if (alt.inTank[5]) {
						alt.calculateFish(5);

					} else if (alt.inTank[6]) {
						alt.calculateFish(6);

					} else if (alt.inTank[7]) {
						alt.calculateFish(7);

					}
					alt.setLocation(x.get((int) fishList.indexOf(alt)), (int) 0);
					// System.out.println(alt.getLocation().getY());
				} else {
					if (alt.getTank() == 0 && alt.inTank() == 0 && Aquas.get(0).added) {
						alt.calculateFish(0);
					} else if (alt.inTank() == 1 && Aquas.get(1).added && alt.getTank() == 1) {
						alt.calculateFish(1);
					} else if (alt.inTank() == 2 && Aquas.get(2).added && alt.getTank() == 2) {
						alt.calculateFish(2);
					} else if (alt.inTank() == 3 && Aquas.get(3).added && alt.getTank() == 3) {
						alt.calculateFish(3);
					} else if (alt.inTank() == 4 && Aquas.get(4).added && alt.getTank() == 4) {
						alt.calculateFish(4);

					} else if (alt.inTank() == 5 && Aquas.get(5).added && alt.getTank() == 5) {
						alt.calculateFish(5);

					} else if (alt.inTank() == 6 && Aquas.get(6).added && alt.getTank() == 6) {
						alt.calculateFish(6);

					} else if (alt.inTank() == 7 && Aquas.get(7).added && alt.getTank() == 7) {
						alt.calculateFish(7);
					} else {
						alt.setReached(false);
						// alt.calculateFish(alt.getTank());
						alt.setLocation(x.get((int) fishList.indexOf(alt)), (int) 0);
					}
				}
				alt.revalidate();
			}

		}

		public void mouseDragged(MouseEvent e) {
			MyLabel alt = (MyLabel) e.getSource();
			if (!pause) {

				setPosition(e);

				if (alt.inTank() != -1) {
					for (int i = 0; i < alt.inTank.length; i++) {
						alt.asigntoTank(i);
					}
				} else {
					alt.setReached(false);

				}
			}

		}

		private void setPosition(MouseEvent e) {
			int neux = c.getLocation().x + e.getX() - startx;
			int neuy = c.getLocation().y + e.getY() - starty;
			c.setLocation(neux, neuy);
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			text.setVisible(false);
			// System.out.println("1: " + e.getX() + " " + e.getY());
			MyLabel alt = (MyLabel) e.getSource();
			if (!pause) {

				if (alt.isMoveable()) {
					owner.repaint();
					c = alt;
					startx = e.getX();
					starty = e.getY();
					if (alt.getTank() != -1) {

						alt.calculateFish(alt.getTank());
						// System.out.println(alt.getTank());
					}
				}
			}

		}

	}

	public class Kl implements KeyListener {

		public boolean SolutionValid() {
			for (MyLabel f : fishList) {
				if (!f.hasReached()) {
					return false;
				}
			}
			return true;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!pause) {

				if ((e.getKeyCode() == 39 || e.getKeyCode() == e.VK_SPACE) && w.getcP() < 10) {
					if (w.getCurrent().getWalls()[1] == false) {
						w.move(1);
						// System.out.println(w.getcP());
						for (MyLabel f : fishList) {
							if (!f.hasReached()) {
								solution = false;
								// System.out.println("reached?" + w.reached);
							}
						}
						if (w.getcP() == 9) {
							repaint();

							// System.out.println("solution? " + solution);
							if (solution) {
								w.reached = true;
								settings.getTimer().stop();
								solveGame();
								Solution();
								if (!lost) {
									timer = new Timer(50, new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											for (MyLabel f : fishList) {
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
									timer.start();
								}

							}

							// System.out.println(w.getPointX());
							last_position.add(w.getcP());
							// System.out.println("last step " + last_position.get(last_position.size() -
							// 1));
							// System.out.println(solution);
						}
					}
				}
				if (e.getKeyCode() == 37) {
					if (w.getCurrent().getWalls()[3] == false && !(w.reached)) {
						solution = true;
						w.move(-1);
						last_position.add(w.getcP());

					}
				}
			}

			if (e.getKeyCode() == e.VK_P) {
				if (!pause) {
					pause = true;
					settings.getTimer().stop();
					c.getSett().getPause().setSelected(true);

//				
				} else {

					pause = false;
					settings.getTimer().start();
					c.getSett().getPause().setSelected(false);

				}

			}

			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (aqua a : Aquas) {
			if (a.added) {
				a.setcP(10);
				a.setCurrent(blocks[a.getrP()][a.getcP()]);
				repaint();
			}
		}

	}

}
