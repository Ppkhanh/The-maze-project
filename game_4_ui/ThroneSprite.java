package game_4_ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import static game_4_algo.GamePlay.tile_size;

public class ThroneSprite {
	
	private BufferedImage img;
	

	public ThroneSprite() {
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("game_4_res/throne.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImg() {
		return img;
	}
	
}
