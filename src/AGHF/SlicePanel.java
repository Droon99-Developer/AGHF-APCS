package AGHF;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Units.Infantry;
import Units.Unit;

/*
 * Renders the units and the map
 */
public class SlicePanel extends JPanel {
	private static final long serialVersionUID = 1521411056254603629L;
	protected BufferedImage img = null;

	public SlicePanel(int index) {
		setLayout(null);
		setOpaque(false);
		try {
			img = ImageIO.read(new File(String.format("assets/Desert Map/APCSmap0slide%d.png", index)));
		} catch (IOException e) {
			System.out.println("map image didn't load");
		}
	}
	
	public void translateUnit(Unit u, Point dest) {
		// don't waste time staying in place
		if (!u.getLocation().equals(dest)) {
			final double FRAMES = 20;
			double currF = 0;
			Point start = u.getLocation();
			double xMove = dest.x - start.x;
			double yMove = dest.y - start.y;
			while (currF < FRAMES) {
				currF++;
				u.setLocation((int) (start.x + xMove * (currF / FRAMES)), (int) (start.y + yMove * (currF / FRAMES)));
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					System.err.println("attacking animation thread interrupted");
					e.printStackTrace();
				}
			}
		}
	}

	public void renderUnits(ArrayList<Unit> units, boolean left) {
		if (units.size() > 0) {
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
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, AffineTransform.getScaleInstance((double) getWidth() / (double) img.getWidth(), 1), null);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
	}
}
