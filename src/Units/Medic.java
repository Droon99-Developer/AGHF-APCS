package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Medic extends Unit {
	public static final int COST = 12;
	private final int HEAL_AMT = 2;
	public Medic(boolean forDefense) {
//		SPEED: 1
//		DAMAGE: 0
//		MAX HEALTH: 6
//		GPK: 4
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(1, 0, 6, 4, forDefense, "assets/MedicSprite1.png");
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
