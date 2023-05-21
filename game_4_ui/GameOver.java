package game_4_ui;

import static game_4_algo.SafetyDistanceGen.safetyDist;
import static game_4_algo.SafetyDistanceGen.safetyDistMultiplied;
import static game_4_algo.SafetyDistanceGen.safetyDistMultiplier;
import static game_4_main.Auswahlbereich.*;
import static game_4_objects.Store.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameOver extends JPanel {
	private CustomFont pixAntiqua = new CustomFont(24);
	private CustomFont pixAntiqua2 = new CustomFont(16);
	public GameOver() {
		
	}
	
	public void draw(Graphics g) {
		((Graphics2D) g).setFont(pixAntiqua.getCustomFont());
		
		//fuer 
		int x2 = cNum_button.x;
		int y2 = cNum_button.y + 40;
		
//		if(lostGame & !solutionSubmitted) {
//			g.setColor(Color.RED);
//			g.drawString("Game Over :(", x2, y2);
//		}
		if(solutionSubmitted && !lostGame) {
			((Graphics2D) g).setFont(pixAntiqua2.getCustomFont());
			g.setColor(Color.WHITE);
			g.drawString("Safety-Distance: "+safetyDist+" x "+safetyDistMultiplier+" = "+safetyDistMultiplied, x2, y2);
		}
		if(solutionSubmitted && lostGame) {
			g.setColor(Color.WHITE);
			((Graphics2D) g).setFont(pixAntiqua2.getCustomFont());
			g.drawString("Safety-Distance: "+safetyDist+" x "+safetyDistMultiplier+" = "+safetyDistMultiplied, x2, y2);
			
			g.setColor(Color.RED);
			((Graphics2D) g).setFont(pixAntiqua.getCustomFont());
			g.drawString("Game Over :(", x2, y2+20);
		}
	}
}
