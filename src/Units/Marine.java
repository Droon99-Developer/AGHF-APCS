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
//		DAMAGE: 8
//		MAX HEALTH: 3
//		GPK: 1
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 8, 3, 1, forDefense);
		try {
			// TODO create a marine asset
		    img = ImageIO.read(new File("assets/scout1.png"));
		    setSize(img.getWidth(), img.getHeight());
		} catch (IOException e) {
			System.out.println("Marine image didn't load");
		}
	}
}
