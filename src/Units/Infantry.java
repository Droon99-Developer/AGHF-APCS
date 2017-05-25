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
//		DAMAGE: 3
//		MAX HEALTH: 7
//		GPK: 3
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(2, 3, 7, 3, forDefense);
		try {
		    img = ImageIO.read(new File("assets/infantry1new.png"));
		    setSize(img.getWidth(), img.getHeight());
		} catch (IOException e) {
			System.out.println("Infantry image didn't load");
		}
	}
}
