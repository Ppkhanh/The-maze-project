package mazegen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import java.lang.Math;
import javax.swing.JPanel;
import layout_frame.Auswahlbereich;
import layout_frame.Frame;
import layout_frame.Player;

public class MazePanel extends JPanel implements KeyListener {
	private static int WIDTH;

	public static int HEIGHT;

	public int ROWS = 20;
	public int COLS = ROWS;

	public static int block_size;

	private static Block[][] blocks;

	public static Block[][] getBlocks() {
		return MazePanel.blocks;
	}

	public void setBlocks(Block[][] blocks) {
		MazePanel.blocks = blocks;
	}

	private static Player p;
	private String direction = "down";
	public static List<Path> footsteps; // path class = footprints are drawn
	public static List<Block> blockPath ;

	// End Rectangles
	public Rect end_g1;
	public Rect end_g2;
	public Rect end_g3;
	public Rect end_g4;
	public Rect start_field;

	private Graphics g;
	public static List<int[]> moves = new ArrayList<int[]>();

	// List for visited Blocks
	private static List<Block> visitedBlocks = new ArrayList<Block>();

	public MazePanel(Color c) {
		block_size = 40;
		WIDTH = 800;
		setBackground(c);
		setLayout(new BorderLayout());

		createRects();
		createBlocks();

		// OBEN LINKS
		computeMaze(blocks[ROWS / 2 - 1][COLS / 2 - 1], 1);
		// OBEN RECHTS
		computeMaze(blocks[ROWS / 2 - 1][COLS / 2], 2);
		// UNTEN LINKS
		computeMaze(blocks[ROWS / 2][COLS / 2 - 1], 3);
		// UNTEN RECHTS
		computeMaze(blocks[ROWS / 2][COLS / 2], 4);

		initialRemove();
		removeRandomWallBetweenMazes();

		DijkstraSolve dj = new DijkstraSolve(blocks, this);

		setP(new Player());

		p.setMove_counter(0);
		footsteps = new ArrayList<Path>(); // path class = footprints are drawn
		blockPath = new ArrayList<Block>();
		// Adds keyboard event listener
		addKeyListener(this);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				WIDTH = getSize().width;
				block_size = Math.min(getSize().width / 20, getSize().height / 20);
//
//				if (getP().getX() == 0 && getP().getY() == 0) {
//					initPlayer(block_size / 3 * 2);
				//{
					updatePlayer(WIDTH, block_size / 3 * 2);
					updateFootstep(block_size - 20, g);
				//}

				Auswahlbereich.zurueck_start_b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// calculate the move needed to get back to start position
						// [ROWS/2 - 1][ROWS/2-1] is start row block (cols = rows so we only need one
						// variable)

						int og_x = blocks[ROWS / 2 - 1][COLS / 2 - 1].getX1();
						int og_r = ROWS / 2 - 1;
						int x = (og_x - getP().getX()) / getP().getPlayer_size();
						int y = (og_x - getP().getY()) / getP().getPlayer_size();
						getP().move(x, y);
						moves.clear();
						moves.add(new int[] { og_r, og_r });
						footsteps.clear();

						MazePanel.getP().setMove_counter(0);
						getP().setCostume_change(false);
						end_g1.setPlayerTouched(false);
						end_g2.setPlayerTouched(false);
						end_g3.setPlayerTouched(false);
						end_g4.setPlayerTouched(false);

						getVisitedBlocks().clear();

						repaint();
					}
				});

				Auswahlbereich.eins_zureuck_b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (moves.size() >= 1) {
							int x1 = moves.get(moves.size() - 1)[0];
							int y1 = moves.get(moves.size() - 1)[1];
							int x2 = moves.get(moves.size() - 2)[0];
							int y2 = moves.get(moves.size() - 2)[1];
							getP().move(y2 - y1, x2 - x1);
							moves.clear();
							moves.add(new int[] { x2, y2 });
						}
						// Decrease move-counter by 1!
						getP().setMove_counter(MazePanel.getP().getMove_counter() - 1);
						getP().setCostume_change(false);

						// if does not do anything really...
						if (getP().getCurrent() != blocks[1][0] || getP().getCurrent() != blocks[1][19]
								|| getP().getCurrent() != blocks[18][0] || getP().getCurrent() != blocks[18][19]) {
							end_g1.setPlayerTouched(false);
							end_g2.setPlayerTouched(false);
							end_g3.setPlayerTouched(false);
							end_g4.setPlayerTouched(false);
						}
						// removes last block input in the visited blocks list
						if (getVisitedBlocks().get(getVisitedBlocks().size() - 1) != null) {
							getVisitedBlocks().remove(getVisitedBlocks().get(getVisitedBlocks().size() - 1));
						}
						repaint();
					}
				});
				updateRects(block_size);
				repaint();
			}
		});
		setFocusable(true);
	}

	// Get shortestPath in reverse
	private List<Block> getShortestPath(Block start, Block b) {// b is the end block, we need to run this 4 times
		List<Block> shortestPath = new ArrayList<Block>();
		shortestPath.add(b);

		while (b.getParent() != null) { // or unequal to start.. since start node has no neighbor
			shortestPath.add(b.getParent());
			b = b.getParent();
		}
		return shortestPath;
	}

	// Create content of the array in form of blocks
	private void createBlocks() {
		blocks = new Block[ROWS][COLS];
		Block block;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				block = new Block(i, j, this);
				// set walls for most outer rows and columns so that there're no walls
				if (i == 0) {
					block.setWalls(new boolean[] { false, false, true, false });
				}
				if (i == ROWS - 1) {

					block.setWalls(new boolean[] { true, false, false, false });
				}
				if (j == 0) {
					if (i == 0) {
						block.setWalls(new boolean[] { false, false, false, false });
					} else if (i == ROWS - 1) {
						block.setWalls(new boolean[] { false, false, false, false });
					} else {
						block.setWalls(new boolean[] { false, true, false, false });
					}
				}
				if (j == COLS - 1) {
					if (i == 0) {
						block.setWalls(new boolean[] { false, false, false, false });
					} else if (i == ROWS - 1) {
						block.setWalls(new boolean[] { false, false, false, false });
					} else {
						block.setWalls(new boolean[] { false, false, false, true });
					}
				}
				blocks[i][j] = block;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updatePlayer(WIDTH, block_size / 3 * 2);
		
		Block block;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				block = blocks[i][j];
				if (block != null) {
					block.draw(g);
				}
			}
		}

		drawFields(g);
		if (footsteps.size() > 0) {
			for (Path f : footsteps) {
				f.drawFootstep(g);
			}
		}
		drawPlayer(g);
	}

	private void drawFields(Graphics g) {
		end_g1.draw(g, Color.BLUE, "SPIEL 1");
		end_g2.draw(g, Color.GREEN, "SPIEL 2");
		end_g3.draw(g, Color.PINK, "SPIEL 3");
		end_g4.draw(g, Color.YELLOW, "SPIEL 4");
		start_field.draw(g, Color.RED, "START");
	}

	private void createRects() {
		end_g1 = new Rect(1, 0, block_size);
		end_g2 = new Rect(1, COLS - 1, block_size);
		end_g3 = new Rect(ROWS - 2, 0, block_size);
		end_g4 = new Rect(ROWS - 2, COLS - 1, block_size);
		start_field = new Rect(COLS / 2 - 1, ROWS / 2 - 1, block_size);
	}

	public void updateRects(int block_size) {
		end_g1 = new Rect(1, 0, block_size);
		end_g2 = new Rect(1, COLS - 1, block_size);
		end_g3 = new Rect(ROWS - 2, 0, block_size);
		end_g4 = new Rect(ROWS - 2, COLS - 1, block_size);
		start_field = new Rect(COLS / 2 - 1, ROWS / 2 - 1, block_size);
	}

	public void updatePlayer(int width, int size) {
		Block current_tem = getP().getCurrent();
		int rp_tem = getP().getrP();
		int cp_tem = getP().getcP();
		int x_tem = current_tem.getX1();
		int y_tem = current_tem.getY1();
		setP(new Player());
		getP().setrP(rp_tem);
		getP().setcP(cp_tem);
		getP().setCurrent(current_tem);
		getP().setX(x_tem);
		getP().setY(y_tem);
		getP().setPlayer_size(size);
	}

	public void initPlayer(int size) {
		getP().setPlayer_size(size);
		getP().setrP(9);
		getP().setcP(9);
		getP().setCurrent(blocks[getP().getrP()][getP().getcP()]);
		getP().setX(360);
		getP().setY(360);
//		System.out.println(getP().getX());
//		System.out.println(getP().getY());
	}

	public void updateFootstep(int size, Graphics g) {
		for (int i = 0; i < footsteps.size(); i++) {
			Path f = footsteps.get(i);
			f.setSize(size);
			f.setX(blockPath.get(i).getX1());
			f.setY(blockPath.get(i).getY1());
			repaint();
		}
	}

	private void drawPlayer(Graphics g) {
		g.drawImage(getP().getImg(direction), getP().getCurrent().getX1(), getP().getCurrent().getY1(), this);
	}

	/*
	 * CALCULATING THE ACTUAL MAZE
	 */
	private void computeMaze(Block b, int position) {
		Random random = new Random();
		Stack<Block> stack = new Stack<Block>();
		Block current = b;
		current.setVisited(true);
		int unVisitedCount = (ROWS / 2 - 1) * (COLS / 2 - 1) - 1;

		// Create a list
		List<Block> neighbors;
		Block next;

		while (unVisitedCount > 0) {
			// Find neighbor collection (unreachable)
			neighbors = current.findNeighbors(position);
			// If the current cell has unvisited neighbors then:
			if (neighbors.size() > 0) {
				// Randomly select a neighbor from this list
				int index = random.nextInt(neighbors.size());
				next = neighbors.get(index);
				// Stack the current cell
				stack.push(current);
				// Remove the walls between the current maze cell and the randomly selected
				// neighbor cell
				this.removeWall(current, next);
				// Mark the neighbor cell as visited and use it as the current cell
				next.setVisited(true);
				current = next;
				unVisitedCount--;
			} else if (stack.isEmpty() == false) { // if we got no neighbors, we go back to the stack
				Block cell = stack.pop();
				current = cell;
			}
		}
	}

	// Remove the walls between the current maze cell and the adjacent maze cell
	private void removeWall(Block current, Block next) {
		// Neighbor is on the same row:
		if (current.getI() == next.getI()) {
			// The match is the left neighbor
			if (current.getJ() > next.getJ()) {
				current.getWalls()[3] = false;
				next.getWalls()[1] = false;
			}
			// The match is the neighbor on the right
			else {
				current.getWalls()[1] = false;
				next.getWalls()[3] = false;
			}
		}
		// Neighbor is on the same column:
		else if (current.getJ() == next.getJ()) {
			// It matches the upper neighbor
			if (current.getI() > next.getI()) {
				current.getWalls()[0] = false;
				next.getWalls()[2] = false;
			}
			// The match is the lower neighbor
			else {
				current.getWalls()[2] = false;
				next.getWalls()[0] = false;
			}
		}
	}

	public void initialRemove() {
		// Startarea is free empty square!
		Block block1 = blocks[ROWS / 2 - 1][COLS / 2 - 1];
		Block block2 = blocks[ROWS / 2 - 1][COLS / 2];
		Block block3 = blocks[ROWS / 2][COLS / 2 - 1];
		Block block4 = blocks[ROWS / 2][COLS / 2];
		removeWall(block1, block2);
		removeWall(block2, block4);
		removeWall(block3, block4);
		removeWall(block1, block3);

		// If conditions right we can create more than one entry way to each "maze"
		Block block13 = blocks[ROWS / 2 - 1][COLS / 2 - 2];
		Block block14 = blocks[ROWS / 2 - 2][COLS / 2 - 1];
		Block leftUPdia = blocks[ROWS / 2 - 2][COLS / 2 - 2];
		if (leftUPdia.getWalls()[1] == true && leftUPdia.getWalls()[2] == true
				|| leftUPdia.getWalls()[1] == false && leftUPdia.getWalls()[2] == true
				|| leftUPdia.getWalls()[1] == true && leftUPdia.getWalls()[2] == false) {
			removeWall(block1, block13);
			removeWall(block1, block14);
		}

		Block block15 = blocks[ROWS / 2 - 1][COLS / 2 + 1];
		Block block16 = blocks[ROWS / 2 - 2][COLS / 2];
		Block rightUPdia = blocks[ROWS / 2 - 2][COLS / 2 + 1];
		if (rightUPdia.getWalls()[3] == true && rightUPdia.getWalls()[2] == true
				|| rightUPdia.getWalls()[3] == false && rightUPdia.getWalls()[2] == true
				|| rightUPdia.getWalls()[3] == true && rightUPdia.getWalls()[2] == false) {
			removeWall(block2, block15);
			removeWall(block2, block16);
		}

		Block block17 = blocks[ROWS / 2][COLS / 2 - 2];
		Block block18 = blocks[ROWS / 2 + 1][COLS / 2 - 1];
		Block leftDOWNdia = blocks[ROWS / 2 + 1][COLS / 2 - 2];
		if (leftDOWNdia.getWalls()[0] == true && leftDOWNdia.getWalls()[1] == true
				|| leftDOWNdia.getWalls()[0] == false && leftDOWNdia.getWalls()[1] == true
				|| leftDOWNdia.getWalls()[0] == true && leftDOWNdia.getWalls()[1] == false) {
			removeWall(block3, block17);
			removeWall(block3, block18);
		}

		Block block19 = blocks[ROWS / 2][COLS / 2 + 1];
		Block block20 = blocks[ROWS / 2 + 1][COLS / 2];
		Block rightDOWNdia = blocks[ROWS / 2 + 1][COLS / 2 + 1];
		if (rightDOWNdia.getWalls()[0] == true && rightDOWNdia.getWalls()[3] == true
				|| rightDOWNdia.getWalls()[0] == false && rightDOWNdia.getWalls()[3] == true
				|| rightDOWNdia.getWalls()[0] == true && rightDOWNdia.getWalls()[3] == false) {
			removeWall(block4, block19);
			removeWall(block4, block20);
		}
	}

	public void removeRandomWallBetweenMazes() { // need random measures from 1 to 7 else risk empty square in beginning
		Random rand = new Random();

		// Creates gates to the end-goals!
		Block gate_1 = blocks[1][1];
		removeWall(gate_1, blocks[1][0]);
		Block gate_2 = blocks[1][COLS - 2];
		removeWall(gate_2, blocks[1][COLS - 1]);
		Block gate_3 = blocks[ROWS - 2][1];
		removeWall(gate_3, blocks[ROWS - 2][0]);
		Block gate_4 = blocks[ROWS - 2][COLS - 1];
		removeWall(gate_4, blocks[ROWS - 2][COLS - 2]);

		// Creates random passage connecting left/right left/leftdown and
		// right/rightdown mazes!
		int random_row1 = rand.nextInt(1, COLS / 2 - 2); // 1-7
		Block passageMiddle_right = blocks[random_row1][COLS / 2 - 1];
		Block passageMiddle_right_n = blocks[random_row1][COLS / 2];
		removeWall(passageMiddle_right, passageMiddle_right_n);

		int random_col1 = rand.nextInt(1, COLS / 2 - 2); // 1-7
		Block passageMiddle_down = blocks[ROWS / 2 - 1][random_col1];
		Block passageMiddle_down_n = blocks[ROWS / 2][random_col1];
		removeWall(passageMiddle_down, passageMiddle_down_n);

		int random_col2 = rand.nextInt(COLS / 2 + 2, COLS - 1); // 12-18
		Block passageMiddle_upDIA = blocks[ROWS / 2][random_col2];
		Block passageMiddle_upDIA_n = blocks[ROWS / 2 - 1][random_col2];
		removeWall(passageMiddle_upDIA, passageMiddle_upDIA_n);

		int random_row2 = rand.nextInt(ROWS / 2 + 2, ROWS - 1); // 12-18
		Block passageMiddle_leftDIA = blocks[random_row2][COLS / 2];
		Block passageMiddle_leftDIA_n = blocks[random_row2][COLS / 2 - 1];
		removeWall(passageMiddle_leftDIA, passageMiddle_leftDIA_n);
	}

	// Probably not needed anymore... since we got dijkstra algo working...
	private int getDistanceToGoal(int goal) {
		switch (goal) {
		case 1:
			Block ende1 = blocks[0][0];
			return ende1.getDistance();
		case 2:
			Block ende2 = blocks[0][COLS - 1];
			return ende2.getDistance();
		case 3:
			Block ende3 = blocks[ROWS - 1][0];
			return ende3.getDistance();
		case 4:
			Block ende4 = blocks[ROWS - 1][COLS - 1];
			return ende4.getDistance();
		}
		return -1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// creates list for each short path + adds the out-of-game-block as well to the
		// solution path
		List<Block> shortPath1 = getShortestPath(blocks[ROWS / 2 - 1][COLS / 2 - 1], blocks[1][1]);
		shortPath1.add(blocks[1][0]);
		List<Block> shortPath2 = getShortestPath(blocks[ROWS / 2 - 1][COLS / 2 - 1], blocks[1][18]);
		shortPath1.add(blocks[1][19]);
		List<Block> shortPath3 = getShortestPath(blocks[ROWS / 2 - 1][COLS / 2 - 1], blocks[18][1]);
		shortPath1.add(blocks[18][0]);
		List<Block> shortPath4 = getShortestPath(blocks[ROWS / 2 - 1][COLS / 2 - 1], blocks[18][18]);
		shortPath1.add(blocks[18][19]);

		if (movementOutsideOfMaze() == false) { // check that not on outside...
			if (e.getKeyCode() == e.VK_S || e.getKeyCode() == e.VK_DOWN) {
				// after figure moves can button be clicked again
				if (getP().getCurrent().getWalls()[2] == false) {
					Auswahlbereich.eins_zureuck_b.setEnabled(true);
					Auswahlbereich.zurueck_start_b.setEnabled(true);

					Path path1 = new Path();
					Block block1 = getP().getCurrent(); // visited block is added to our path list
					blockPath.add(block1);
					path1.setX(block1.getX1()); // sets the x1 and y1 of where the footprints should be painted i think.
					path1.setY(block1.getY1());

					footsteps.add(path1);
					getP().move(0, 1);
					int[] last_block = { getP().getrP(), getP().getcP() };
					moves.add(last_block);

					direction = "down";
					if (movementOnSquare() == false) {
						getP().setMove_counter(getP().getMove_counter() + 1);
					}
				}
			}

			if (e.getKeyCode() == e.VK_W || e.getKeyCode() == e.VK_UP) {
				if (getP().getCurrent().getWalls()[0] == false) {
					Auswahlbereich.eins_zureuck_b.setEnabled(true);
					Auswahlbereich.zurueck_start_b.setEnabled(true);

					Path path2 = new Path();
					Block block2 = getP().getCurrent();
					blockPath.add(block2);
					path2.setX(block2.getX1());
					path2.setY(block2.getY1());

					footsteps.add(path2);

					getP().move(0, -1);
					int[] last_block = { getP().getrP(), getP().getcP() };
					moves.add(last_block);

					direction = "up";
					if (movementOnSquare() == false) {
						getP().setMove_counter(getP().getMove_counter() + 1);
					}
				}
			}

			if (e.getKeyCode() == e.VK_A || e.getKeyCode() == e.VK_LEFT) {
				if (getP().getCurrent().getWalls()[3] == false) {
					Auswahlbereich.eins_zureuck_b.setEnabled(true);
					Auswahlbereich.zurueck_start_b.setEnabled(true);

					Path path3 = new Path();
					Block block3 = getP().getCurrent();
					blockPath.add(block3);
					path3.setX(block3.getX1());
					path3.setY(block3.getY1());

					footsteps.add(path3);

					getP().move(-1, 0);
					int[] last_block = { getP().getrP(), getP().getcP() };
					moves.add(last_block);

					direction = "left";

					if (movementOnSquare() == false) {
						getP().setMove_counter(getP().getMove_counter() + 1);
					}
				}
			}

			if (e.getKeyCode() == e.VK_D || e.getKeyCode() == e.VK_RIGHT) {
				if (getP().getCurrent().getWalls()[1] == false) {
					Auswahlbereich.eins_zureuck_b.setEnabled(true);
					Auswahlbereich.zurueck_start_b.setEnabled(true);

					Path path4 = new Path();
					Block block4 = getP().getCurrent();
					blockPath.add(block4);
					path4.setX(block4.getX1());
					path4.setY(block4.getY1());

					footsteps.add(path4);

					getP().move(1, 0);
					int[] last_block = { getP().getrP(), getP().getcP() };
					moves.add(last_block);

					direction = "right";
					if (movementOnSquare() == false) {
						getP().setMove_counter(getP().getMove_counter() + 1);
					}
				}
			}
		}

		// System.out.println("Player: "+getP().getCurrent().getI()+",
		// "+getP().getCurrent().getJ()+" Schritte: "+getP().getMove_counter());

		// change color if not on right track
		if (movementOnSquare() == false) { // so we can move on the start square, even if not every block belongs to
											// shortest path
			if ((!shortPath1.contains(getP().getCurrent()) && !shortPath2.contains(getP().getCurrent())
					&& !shortPath3.contains(getP().getCurrent()) && !shortPath4.contains(getP().getCurrent()))) {
				getP().setCostume_change(true);
			}
		}

		// Change font if touched end...
		if (getP().getCurrent() == blocks[1][0] || getP().getCurrent() == blocks[1][19]
				|| getP().getCurrent() == blocks[18][0] || getP().getCurrent() == blocks[18][19]) {

			if (getP().getCurrent() == blocks[1][0]) {
				if (getP().isCostume_change() == false) {
					end_g1.setPlayerTouched(true);
					if (e.getKeyCode() == e.VK_ENTER) {
						// Code so the game opens on click :||||
						Frame.cl.show(Frame.cont, "spiel1");
					}
				}
			}
			if (getP().getCurrent() == blocks[1][19]) {
				if (getP().isCostume_change() == false) {
					end_g2.setPlayerTouched(true);
					if (e.getKeyCode() == e.VK_ENTER) {
						// Code so the game opens on click :||||
						Frame.cl.show(Frame.cont, "spiel2");
					}
				}
			}
			if (getP().getCurrent() == blocks[18][0]) {
				if (getP().isCostume_change() == false) {
					end_g3.setPlayerTouched(true);
					if (e.getKeyCode() == e.VK_ENTER) {
						// Code so the game opens on click :||||
						Frame.cl.show(Frame.cont, "spiel3");
					}
				}
			}
			if (getP().getCurrent() == blocks[18][19]) {
				if (getP().isCostume_change() == false) {
					end_g4.setPlayerTouched(true);
					if (e.getKeyCode() == e.VK_ENTER) {
						// Code so the game opens on click :||||
						Frame.cl.show(Frame.cont, "spiel4");
					}
				}
			}
		} else {// else set this on false again; make font smaller
			end_g1.setPlayerTouched(false);
			end_g2.setPlayerTouched(false);
			end_g3.setPlayerTouched(false);
			end_g4.setPlayerTouched(false);
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	private boolean movementOnSquare() {
		if (getP().getCurrent() == blocks[ROWS / 2 - 1][COLS / 2 - 1]
				|| getP().getCurrent() == blocks[ROWS / 2][COLS / 2 - 1]
				|| getP().getCurrent() == blocks[ROWS / 2 - 1][COLS / 2]
				|| getP().getCurrent() == blocks[ROWS / 2][COLS / 2]) {
			return true;
		}
		return false;
	}

	// keys should not even be able to work if we have movement outside detected;
	// tho still is able to make 1 move... :?
	private boolean movementOutsideOfMaze() {
		for (int i = 0; i < ROWS; i++) {
			if (getP().getCurrent() == blocks[0][i] || getP().getCurrent() == blocks[19][i]) {
				return true;
			}
		}
		for (int i = 1; i < ROWS - 1; i++) {
			if (getP().getCurrent() == blocks[i][0] || getP().getCurrent() == blocks[i][19]) {
				return true;
			}
		}
		return false;
	}

	public static boolean getVarAlreadyVisited() {
		return Block.isAlreadyVisited();
	}

	public static void AlreadyVisited() {
		Block b = getP().getCurrent();
		boolean alreadyInList = false;

		// see if curernt Block is already in visitedBlocks
		for (int i = 0; i < getVisitedBlocks().size(); i++) {
			if (getP().getCurrent() == getVisitedBlocks().get(i)) {
				alreadyInList = true;
				getP().setCostume_change(true);
				//System.out.println("Block already in visited-List !");
				break;
			}
		}
		// if Block is already in visitedBlocks, nothing happens, if Block is not in
		// visitedBlocks, then it is added to the List
		if (alreadyInList == true) {

		} else if (getVarAlreadyVisited() == true) {
			//System.out.println("Block added to the 'visited'-List !");
			getVisitedBlocks().add(b);
		}
	}

	// GETTERS AND SETTERS
	public static Player getP() {
		return MazePanel.p;
	}

	public void setP(Player p) {
		MazePanel.p = p;
	}

	public static int getBlock_size() {
		return block_size;
	}

	public static void setBlock_size(int block_size) {
		MazePanel.block_size = block_size;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public static List<Block> getVisitedBlocks() {
		return visitedBlocks;
	}

	public static void setVisitedBlocks(List<Block> visitedBlocks) {
		MazePanel.visitedBlocks = visitedBlocks;
	}

}
