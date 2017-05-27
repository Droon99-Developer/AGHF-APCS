package Units;

import java.awt.event.MouseEvent;

public class Tank extends Unit {
	private static final long serialVersionUID = 6718417382710113994L;
	public static final int COST = 18; 
	public Tank(boolean forDefense) {
//		SPEED: 1
//		DAMAGE: 6
//		MAX HEALTH: 13
//		GPK: 5
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(1, 7, 10, 5, forDefense, "assets/TankSprite1.png");
	}
}
