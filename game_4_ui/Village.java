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

public class Village {

	private BufferedImage sprite;
	private BufferedImage sprite2;

	public Village() {
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getResource("game_4_res/village.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public BufferedImage getSprite2() {
		try {
			sprite2 = ImageIO.read(getClass().getClassLoader().getResource("game_4_res/protectedCastle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite2;
	}
	
}
