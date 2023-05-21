package layout_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import mazegen.MazePanel;


public class Spielbereich  extends JPanel{

	private static MazePanel panel;
	
	public Spielbereich() {
		// setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension(800, 800));
		
		setLayout(new BorderLayout());
		setBackground(Color.YELLOW);
		panel = new MazePanel(Color.white);
		add(panel, BorderLayout.CENTER);

		
	}
	public MazePanel getPanel() {
		return panel;
	}

	public void setP(MazePanel p) {
		panel = p;
	}
}
