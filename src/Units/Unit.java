package Units;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/*
 * Superclass for units
 */
public abstract class Unit extends JPanel {
	private static final long serialVersionUID = 7989260034732663649L;
	// SPEED is the total number of advances the unit can make in a single turn
	// advancing: move 1 slice forward
	protected final int SPEED;
	protected final int DAMAGE;
	protected final int MAX_HEALTH;
	// GPK: Gold Per Kill (the amount of gold YOUR OPPONENT RECEIVES when they kill your unit)
	protected final int GPK;
	
	// the actual COST of each unit is static in each subclass so it is not included here
	
	// keeps track of how many advances are left
	public int advancesLeft;
	protected int healthLeft;
	public boolean forDefense;
	
	protected BufferedImage img = null;
	
	public Unit(int speed, int damage, int maxHealth, int GPK, boolean forDefense, String filePath) {
		setLayout(null);
		setOpaque(false);
		this.SPEED = speed;
		this.DAMAGE = damage;
		this.MAX_HEALTH = maxHealth;
		this.GPK = GPK;
		this.forDefense = forDefense;
		advancesLeft = SPEED;
		healthLeft = MAX_HEALTH;
		try {
		    img = ImageIO.read(new File(filePath));
		    setSize(img.getWidth(), img.getHeight() + 10);
		} catch (IOException e) {
			System.out.println("unit image didn't load");
		}
	}
	
	// returns the amount of gold received by this attack (0 if opponent unit doens't die)
	public int attack(Unit u) {
		u.healthLeft -= DAMAGE;
		if (u.healthLeft <= 0) {
			u.healthLeft = 0;
			return u.GPK;
		}
		return 0;
	}
	
	public void resetAdvances() {
		advancesLeft = SPEED;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(img, null, 0, 0);
		// TODO paint some sort of health bar underneath the unit
		g2.setColor(Color.RED);
//		g2.drawRect(0, getHeight(), , height);
	}
	
}
