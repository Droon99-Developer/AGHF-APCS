package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class AirStrike extends Unit {
	public static final int COST = 50; 
	public AirStrike(boolean forDefense) {
//		SPEED: 5
//		DAMAGE: 40
//		MAX HEALTH: 40 (only killed by anothre air strike)
//		GPK: 10
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(5, 40, 1, 10, forDefense);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		// paint the asset image
	}
}
