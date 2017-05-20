package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Tank extends Unit {
	public static final int COST = 18; 
	public Tank() {
//		SPEED: 1
//		DAMAGE: 7
//		MAX HEALTH: 10
//		GPK: 5
//		constructor structure: int speed, int damage, int maxHealth, int GPK
		super(1, 7, 10, 5);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		// paint the asset image
	}
}
