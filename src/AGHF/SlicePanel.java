package AGHF;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Units.Unit;

/*
 * Renders the units and the map
 */
public class SlicePanel extends JPanel {
	private static final long serialVersionUID = 1521411056254603629L;
	protected BufferedImage img = null;

	private final int ANIMATION_SPEED = 7;
	private final double FRAMES = 20;

	public SlicePanel(int index) {
		setLayout(null);
		setOpaque(false);
		try {
			img = ImageIO.read(new File(String.format("assets/Desert Map/APCSmap0slide%d.png", index)));
		} catch (IOException e) {
			System.out.println("map image didn't load");
		}
	}

	public void addBase(Base b) {
		b.setLocation(getWidth() / 2 - b.getWidth() / 2, getHeight() * 2 / 3 - b.getHeight() - 14);
		add(b);
	}

	public void translateUnit(Unit u, Point dest) {
		// don't waste time staying in place
		if (!u.getLocation().equals(dest)) {
			double currF = 0;
			Point start = u.getLocation();
			double xMove = dest.x - start.x;
			double yMove = dest.y - start.y;
			while (currF < FRAMES) {
				currF++;
				u.setLocation((int) (start.x + xMove * (currF / FRAMES)), (int) (start.y + yMove * (currF / FRAMES)));
				try {
					Thread.sleep(ANIMATION_SPEED);
				} catch (InterruptedException e) {
					System.err.println("attacking animation thread interrupted");
					e.printStackTrace();
				}
			}
		}
	}

	private int[] createYDest(ArrayList<Unit> units) {
		int[] yDest = new int[units.size()];
		double y = getHeight() * 2 / 3 - 14;
		double height = getHeight() / 3;
		for (int i = 0; i < units.size(); i++) {
			yDest[i] = Math.round((float) ((double) (y + (i + 1) / (double) (units.size() + 1) * height)))
					- units.get(i).getHeight() / 2;
		}
		return yDest;
	}

	private int longestWidth(ArrayList<Unit> units) {
		if (units.size() > 0) {
			int longest = units.get(0).getWidth();
			for (int i = 1; i < units.size(); i++) {
				if (longest < units.get(i).getWidth()) {
					longest = units.get(i).getWidth();
				}
			}
			return longest;
		} else {
			return 0;
		}
	}

	public void incomingUnits(ArrayList<Unit> newUnits, ArrayList<Unit> oldUnits, boolean left) {
		for (Unit u : newUnits) {
			add(u);
			oldUnits.add(u);
		}
		int[] yOrig = new int[oldUnits.size()];
		int[] yDest = createYDest(oldUnits);
		int[] yMove = new int[oldUnits.size()];
		for (int i = 0; i < yMove.length; i++) {
			yOrig[i] = oldUnits.get(i).getLocation().y;
			yMove[i] = yDest[i] - yOrig[i];
		}
		int widest = longestWidth(oldUnits);
		int xOrig = left ? -widest : getWidth() + widest;
		int xDest = left ? getWidth() / 4 - widest / 2 : getWidth() * 3 / 4 - widest / 2;
		double xMove = xDest - xOrig;
		// the index at which we are dealing with new units
		int newThreshold = oldUnits.size() - newUnits.size();
		for (double currF = 0; currF <= FRAMES; currF++) {
			for (int i = 0; i < oldUnits.size(); i++) {
				double percent = currF / FRAMES;
				int x = i < newThreshold ? xDest : (int) (xOrig + percent * xMove);
				oldUnits.get(i).setLocation(x, (int) (yOrig[i] + percent * yMove[i]));
			}
			try {
				Thread.sleep(ANIMATION_SPEED);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void advanceUnits(ArrayList<Unit> moveUnits, ArrayList<Unit> stayUnits, boolean left) {
		if (moveUnits.size() > 0) {
			int widest = longestWidth(moveUnits);
			int xDest = left ? getWidth() + widest : -widest;
			// all units have same x position
			double xStart = (int) moveUnits.get(0).getLocation().x;
			double xMove = xDest - xStart;

			int[] yOrig = new int[stayUnits.size()];
			int[] yDest = createYDest(stayUnits);
			int[] yMove = new int[stayUnits.size()];
			for (int i = 0; i < yMove.length; i++) {
				yOrig[i] = stayUnits.get(i).getLocation().y;
				yMove[i] = yDest[i] - yOrig[i];
			}

			for (double currF = 0; currF <= FRAMES; currF++) {
				double percent = currF / FRAMES;
				for (int i = 0; i < stayUnits.size(); i++) {
					stayUnits.get(i).setLocation(stayUnits.get(i).getLocation().x,
							Math.round((float) (yOrig[i] + percent * yMove[i])));
				}
				for (Unit u : moveUnits) {
					u.setLocation(Math.round((float) (percent * xMove + xStart)), u.getLocation().y);
				}
				try {
					Thread.sleep(ANIMATION_SPEED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// public void renderUnits(ArrayList<Unit> units, boolean left) {
	// if (units.size() > 0) {
	// int x;
	// if (left) {
	// x = getWidth() / 4 - units.get(0).getWidth() / 2;
	// } else {
	// x = getWidth() * 3 / 4 - units.get(0).getWidth() / 2;
	// }
	// // the landscape starts 2/3 of the way down the screen
	// int y = getHeight() * 2 / 3;
	// int height = getHeight() / 3 / (units.size() + 1);
	// for (Unit u : units) {
	// y += height;
	// u.setLocation(x, y - u.getHeight() / 2);
	// }
	// }
	// repaint();
	// }

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, AffineTransform.getScaleInstance((double) getWidth() / (double) img.getWidth(), 1), null);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
	}
}
