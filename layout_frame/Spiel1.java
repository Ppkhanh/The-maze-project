package layout_frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Spiel1 extends JPanel {

	public game_1.Auswahlbereich1 auswahlbereich;
	public game_1.Spielbereich1 spielbereich;

	public Spiel1() {

		setBackground(Color.BLUE);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		auswahlbereich = new game_1.Auswahlbereich1();
		spielbereich = new game_1.Spielbereich1();

		gbc.fill = gbc.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.8;
		gbc.weighty = 1;
		add(spielbereich, gbc);

		gbc.fill = gbc.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.weighty = 1;
		add(auswahlbereich, gbc);

		spielbereich.setVisible(true);
		auswahlbereich.setVisible(true);

//		System.out.println("Gr��e: " + getWidth() + "x" + spielbereich.getHeight());
		setVisible(true);

	}

}