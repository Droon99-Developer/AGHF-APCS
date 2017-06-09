package AGHF;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreditsPnl extends JPanel {
	private Font font = new Font("Dialog", Font.BOLD | Font.HANGING_BASELINE, 100);
	private Font font2 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);
	public void show(){
		JLabel title = new JLabel("CREDITS");
		title.setFont(font);
		title.setBounds(65, 20, this.getWidth(), 140);
		title.setVisible(true);
		add(title);
		String[] names = {
				"Project Manager: Connor Black",
				"Lead Programmer: Andrew Burford",
				"Programmer: Tim Frieden",
				"Programmer: Julie Fleischman",
				"Programmer: Sam Zhang",
				"Programmer: Alex Abriola",
				"Programmer: Andy Yu",
				"Assets: James Horbury",
				"Assets: Ray Tian"
		};
		JLabel[] labels = new JLabel[names.length];
		for(int i = 0; i < names.length; i++){
			labels[i] = new JLabel(names[i]);
			labels[i].setFont(font2);
			labels[i].setBounds(200, i*50 + 200, 400, 40);
			labels[i].setVisible(true);
			add(labels[i]);
		}
		
	}
}
