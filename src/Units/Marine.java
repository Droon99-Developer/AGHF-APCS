package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Marine extends Unit{
	public static final int COST = 5;
	public Marine() {
//		SPEED: 2
//		DAMAGE: 8
//		MAX HEALTH: 3
//		GPK: 1
//		constructor structure: int speed, int damage, int maxHealth, int GPK
		super(2, 8, 3, 1);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		// paint the asset image
	}
}
