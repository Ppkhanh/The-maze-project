package Spiel3;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Spielanleitung extends JFrame {
	private String anleitung = "<html>\r\n"
			+ "<body>\r\n"
			+ "<p style=\"text-align: center;\"><span style='font-size: 24px; font-family: \"Comic Sans MS\", sans-serif;'><strong>Spielanleitung Spiel 3</strong></span></p>\r\n"
			+ "<br>"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\">Und LOS!</span></p>\r\n"
			+ "<br>"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\"><strong>Ziel</strong>:&nbsp;</span></p>\r\n"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\" >Versuche ein Netzwerk zu bilden wo alle Stationen miteinander verbunden sind ohne dass du sie Kostenschranke überschreitest.</span></p>\r\n"
			+ "<br>"
			+ "<p align='justify'><span style=\"font-family: Comic Sans MS, sans-serif;\">Falls du es schaffst wirst du auf ein nächstes Level gebracht und falls nicht musst du von neu beginnen und du hast verloren. Es gibt je nach Schwierigkeitsstufe eine vorgegbene Zeit in der du das Spiel Lösen musst.</span></p> \r\n"
			+ "</body>\r\n"
			+ "</html>";
	
	private JLabel info = new JLabel(anleitung);
	
	public Spielanleitung() {
		setFocusable(false);
		setTitle("Spielanleitung");
		setPreferredSize(new Dimension(400, 400));
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				String mode = Auswahlbereichh.getCb().getSelectedItem().toString();
				if(mode == "Training" && Auswahlbereichh.click == 0 ) {
					Auswahlbereichh.training.stop();
					Spielpanel.setPause(false);
					Auswahlbereichh.click = 1;
					
					
				}else if(mode == "Training" && Auswahlbereichh.click == 1) {
					Auswahlbereichh.training.start();
					Spielpanel.setPause(true);
					Auswahlbereichh.click = 0;
					
					
				}
				if(mode == "Mittel" && Auswahlbereichh.click == 0) {
					Auswahlbereichh.medium.stop();	
					Spielpanel.setPause(false);
					
					Auswahlbereichh.click = 1;
				}else if (mode == "Mittel" && Auswahlbereichh.click == 1) {
					Auswahlbereichh.medium.start();
					Spielpanel.setPause(true);
					
					Auswahlbereichh.click = 0;
				}
				if(mode == "Schwer" && Auswahlbereichh.click == 0) {
					Auswahlbereichh.schwer.stop();
					Spielpanel.setPause(false);
					
					Auswahlbereichh.click = 1 ;
				}else if (mode == "Schwer" && Auswahlbereichh.click == 1) {
					Auswahlbereichh.schwer.start();
					Spielpanel.setPause(true);
					
					Auswahlbereichh.click = 0;
				}
			}
		});
		add(info, BorderLayout.PAGE_START);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		
	}
}
