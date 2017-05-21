package AGHF;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Units.Unit;

public class SlicePanel extends JPanel {
	private static final long serialVersionUID = 1521411056254603629L;

	public SlicePanel() {
		setLayout(null);
		Color a = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		setBackground(a);
	}

	public void renderNewUnit(Unit newUnit, ArrayList<Unit> units, boolean left) {
		add(newUnit);
		renderUnits(units, left);
	}
	
	public void renderUnits(ArrayList<Unit> units, boolean left) {
		int x;
		if (left) {
			x = getWidth() / 4 - units.get(0).getWidth() / 2;
		} else {
			x = getWidth() * 3 / 4 - units.get(0).getWidth() / 2;
		}
		// the landscape starts 2/3 of the way down the screen
		int y = getHeight() * 2 / 3;
		int height = getHeight() / 3 / (units.size() + 1);
		for (Unit u : units) {
			y += height;
			u.setLocation(x, y - u.getHeight() / 2);
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		// paint all of the units
	}
}
