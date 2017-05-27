package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Infantry extends Unit {
	private static final long serialVersionUID = -8817023425073765339L;
	public static final int COST = 8;
	public Infantry(boolean forDefense) {
//		SPEED: 2
//		DAMAGE: 2
//		MAX HEALTH: 9
//		GPK: 3
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 2, 9, 3, forDefense, "assets/InfantrySprite1.png");
	}
}
