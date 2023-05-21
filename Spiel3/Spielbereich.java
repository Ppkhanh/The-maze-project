package Spiel3;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;





public class Spielbereich  extends JPanel{

	private static Spielpanel panel;
	private Image image;
	private Icon icon;

	public Spielbereich() {
		
		setPreferredSize(new Dimension(800, 800));
		setLayout(new BorderLayout());
//		setBackground(Color.CYAN);
		panel = new Spielpanel(Color.CYAN);
		add(panel, BorderLayout.CENTER);
			
	    
	}
	
		
	public static Spielpanel getPanel() {
		return panel;
	}

	public void setP(Spielpanel p) {
		panel = p;
	}
}
