package AGHF;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SlicePanel extends JPanel {
	private static final long serialVersionUID = 1521411056254603629L;

	public SlicePanel() {
		setLayout(null);
		Color a = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
		setBackground(a);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2  = (Graphics2D)g;
		// paint all of the units
	}
}
