package Units;

public class Medic extends Unit {
	private static final long serialVersionUID = 1276738901146746049L;
	private final int HEAL_AMT = 2;
	public Medic(boolean forDefense) {
//		SPEED: 1
//		DAMAGE: 0
//		MAX HEALTH: 7
//		GPK: 4
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(1, 0, 7, 4, forDefense, "assets/MedicSprite1.png");
	}
	
	// it's easier to have medic resurrect people if they just died that turn
	// we can change that later if we don't like it
	public void heal(Unit u) {
		u.healthLeft += HEAL_AMT;
		if (u.healthLeft > u.MAX_HEALTH) {
			u.healthLeft = u.MAX_HEALTH;
		}
	}
}
