package AGHF;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Base extends JPanel {
	private static final long serialVersionUID = 7586801578776886932L;
	private double maxHealth = 100;
	public double healthLeft;
	public int level = 1;

	private BufferedImage img;

	private final int HEALTH_BAR_HEIGHT = 5;

	public Base() {
		healthLeft = maxHealth;
		try {
			img = ImageIO.read(new File("assets/baseholder.png"));
			setSize(img.getWidth(), img.getHeight() + HEALTH_BAR_HEIGHT);
		} catch (IOException e) {
			System.out.println("base image didn't load");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, null, 0, 0);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(0, getHeight() - HEALTH_BAR_HEIGHT, getWidth() - 1, HEALTH_BAR_HEIGHT - 1);
		g2.setColor(Color.RED);
		int width = (int) (((double) healthLeft / (double) maxHealth) * (double) (getWidth() - 2));
		g2.fillRect(1, getHeight() - HEALTH_BAR_HEIGHT + 1, width, HEALTH_BAR_HEIGHT - 2);
	}

	public void upgrade() {
		level++;
		healthLeft *= 1.5;
		maxHealth *= 1.5;
	}
}
