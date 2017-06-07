package Units;

public class Infantry extends Unit {
	private static final long serialVersionUID = -8817023425073765339L;

	public Infantry(boolean forDefense) {
		// SPEED: 2
		// DAMAGE: 2
		// MAX HEALTH: 9
		// GPK: 3
		// constructor structure: int speed, int damage, int maxHealth, int GPK,
		// boolean forDefense
		super(2, 2, 9, 3, forDefense, "assets/InfantrySprite1.png");
	}
}
