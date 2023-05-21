package game_4_scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import game_4_algo.GamePlay;
import game_4_keys.KeyHandler;

import static game_4_algo.GamePlay.bar_size;
import static game_4_main.Auswahlbereich.*;

public class TrainingLevel extends JPanel {
	
	public static GamePlay game;
	
	public TrainingLevel() {
		setLayout(new BorderLayout());
	    game = new GamePlay(this, 6, 3, 2.0);
	    
	    game.setFocusable(true);
	    game.setRequestFocusEnabled(true);

		addKeyListener(new KeyHandler());
	    game.requestFocusInWindow();
	    
	    add(game, BorderLayout.CENTER);
	    
	    difficultylevel = 0;
	}

}
