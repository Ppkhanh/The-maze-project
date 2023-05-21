package game_4_ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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

public class CustomFont {

	private Font pixAntiqua;
	
	public CustomFont(float i) {
		try {
			pixAntiqua = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/game_4_res/PixAntiqua.ttf")).deriveFont(i);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/game_4_res/PixAntiqua.ttf")));
			
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}

	}
	
	public Font getCustomFont() {
		return pixAntiqua;
	}
	
}
