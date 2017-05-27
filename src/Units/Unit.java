package Units;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/*
 * Superclass for units
 */
public abstract class Unit extends JPanel implements MouseListener {
	private static final long serialVersionUID = 7989260034732663649L;
	// SPEED is the total number of advances the unit can make in a single turn
	// advancing: move 1 slice forward
	protected final int SPEED;
	protected final int DAMAGE;
	protected final int MAX_HEALTH;
	// GPK: Gold Per Kill (the amount of gold YOUR OPPONENT RECEIVES when they kill your unit)
	public final int GPK;
	
	// the actual COST of each unit is static in each subclass so it is not included here
	
	// keeps track of how many advances are left
	public int advancesLeft;
	protected int healthLeft;
	public boolean forDefense;
	
	protected BufferedImage img = null;
	
	private final int HEALTH_BAR_HEIGHT = 5;
	
	public Unit(int speed, int damage, int maxHealth, int GPK, boolean forDefense, String filePath) {
		setLayout(null);
		setOpaque(false);
		this.SPEED = speed;
		this.DAMAGE = damage;
		this.MAX_HEALTH = maxHealth;
		this.GPK = GPK;
		this.forDefense = forDefense;
		addMouseListener(this);
		advancesLeft = SPEED;
		healthLeft = MAX_HEALTH;
		try {
		    img = ImageIO.read(new File(filePath));
		    setSize(img.getWidth(), img.getHeight() + HEALTH_BAR_HEIGHT);
		} catch (IOException e) {
			System.out.println("unit image didn't load");
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e){
        if(e.getClickCount()==2){
            forDefense = !forDefense;
        }
        repaint();
    }
	
	// used to determine if the medic should visit them
	// they must be partially wounded but not dead
	public boolean healMe() {
		return (healthLeft < MAX_HEALTH && healthLeft > 0);
	}
	
	public boolean dead() {
		return healthLeft == 0;
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
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(0, getHeight() - HEALTH_BAR_HEIGHT, getWidth() - 1, HEALTH_BAR_HEIGHT - 1);
		if (forDefense) {
			g2.setColor(Color.RED);
		} else {
			g2.setColor(Color.GREEN);
		}
		int width =  (int)(((double)healthLeft / (double)MAX_HEALTH) * (double)(getWidth() - 2));
		g2.fillRect(1, getHeight() - HEALTH_BAR_HEIGHT + 1, width, HEALTH_BAR_HEIGHT - 2);
	}
	
}
