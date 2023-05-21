package layout_frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Spiel3 extends JPanel {
	
	public JButton button ;
	public Spiel3()
	{
		
		 button = new JButton();
		 add(button);
		 button.setText("zurück");
			setBackground(Color.BLUE);
			
		
	}

}