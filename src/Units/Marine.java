package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Marine extends Unit{
	public static final int COST = 5;
	public Marine(boolean forDefense) {
//		SPEED: 2
//		DAMAGE: 4
//		MAX HEALTH: 7
//		GPK: 1
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 4, 7, 1, forDefense, "assets/MarineSprite1.png");
	}
}
