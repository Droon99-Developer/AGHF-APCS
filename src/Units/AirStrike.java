package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AirStrike extends Unit {
	static final long serialVersionUID = 5667678708220137591L;
	public static final int COST = 50; 
	public AirStrike(boolean forDefense) {
//		SPEED: 5
//		DAMAGE: 40
//		MAX HEALTH: 40 (only killed by anothre air strike)
//		GPK: 10
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(5, 40, 1, 10, forDefense, "assets/JetSprite1.png");
	}
}
