package layout_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import layout_frame.Frame;

public class Spielanleitung extends JFrame {
	private String anleitung = "<html>\r\n"
			+ "<body>\r\n"
			+ "<p style=\"text-align: center;\"><span style='font-size: 24px; font-family: \"Comic Sans MS\", sans-serif;'><strong>Spielanleitung </strong></span></p>\r\n"
			+ "<br>"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\">Bewege dich mit W A S D oder den Pfeiltasten</span></p>\r\n"
			+ "<br>"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\"><strong>Ziel</strong>:&nbsp;</span></p>\r\n"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\">Bewege die Figur auf dem kuerzesten Weg zu einem der vier Zielfelder.</span></p>\r\n"
			+ "<br>"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\">Falls du es nicht schaffst, eines der Zielfelder auf dem kuerzesten Weg zu erreichen, faerbt sich zwar deine Figur, aber du kannst dennoch weiterspielen und versuchen zu einem der Zielfelder zu gelangen.</span></p> \r\n"
			+ "</body>\r\n"
			+ "</html>";
	
	public String getAnleitung() {
		return anleitung;
	}

	public void setAnleitung(String anleitung) {
		this.anleitung = anleitung;
	}

	private JLabel info = new JLabel(anleitung);
	
	public Spielanleitung() {
		setFocusable(false);
		setTitle("Spielanleitung");
		setPreferredSize(new Dimension(300, 300));
		
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				dispose();
//			}
//		});
		
		add(info);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		
	}
}
