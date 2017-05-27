package Units;

import java.awt.event.MouseEvent;

public class Scout extends Unit {
	private static final long serialVersionUID = -3919096808132824140L;
	public static final int COST = 5;
	public Scout(boolean forDefense) {
//		SPEED: 2
//		DAMAGE: 3
//		MAX HEALTH: 6
//		GPK: 1
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 3, 6, 1, forDefense, "assets/MarineSprite1.png");
	}
}
