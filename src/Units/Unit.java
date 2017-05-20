package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class Unit extends JPanel {
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
	
	public Unit(int speed, int damage, int maxHealth, int GPK, boolean forDefense) {
		this.SPEED = speed;
		this.MAX_HEALTH = maxHealth;
		this.DAMAGE = damage;
		this.GPK = GPK;
		this.forDefense = forDefense;
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
	
	// paint is declared here so polymorphism works
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	};
	
}
