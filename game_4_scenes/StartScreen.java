package game_4_scenes;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class StartScreen extends JPanel {

	private Image image;
	
	public StartScreen() {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("game_4_res/titelbild.png"));
		setImage(icon);
	}
	
	private void setImage(ImageIcon icon) {
		this.image = icon.getImage();
		setOpaque(false);
		repaint();
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		super.paint(g);
	}
}
