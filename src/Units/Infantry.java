package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Infantry extends Unit{
	public static final int COST = 8;
	public Infantry(boolean forDefense) {
//		SPEED: 2
//		DAMAGE: 3
//		MAX HEALTH: 7
//		GPK: 3
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 3, 7, 3, forDefense);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		// paint the asset image
	}
}
