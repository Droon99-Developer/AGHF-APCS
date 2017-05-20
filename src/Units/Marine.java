package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Marine extends Unit{
	public static final int COST = 5;
	public Marine(boolean forDefense) {
//		SPEED: 2
//		DAMAGE: 8
//		MAX HEALTH: 3
//		GPK: 1
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 8, 3, 1, forDefense);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		// paint the asset image
	}
}
