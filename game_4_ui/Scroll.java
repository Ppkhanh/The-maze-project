package game_4_ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import game_4_algo.GamePlay;

public class Scroll {

	private BufferedImage sprite;
	
	public Scroll() {
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getResource("game_4_res/scroll.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
}
