package Units;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tank extends Unit {
	public static final int COST = 18; 
	public Tank(boolean forDefense) {
//		SPEED: 1
//		DAMAGE: 7
//		MAX HEALTH: 10
//		GPK: 5
//		constructor structure: int speed, int damage, int maxHealth, int GPK, boolean forDefense
		super(1, 7, 10, 5, forDefense);
		try {
		    img = ImageIO.read(new File("assets/tank1.png"));
		    setSize(img.getWidth(), img.getHeight());
		} catch (IOException e) {
			System.out.println("Tank image didn't load");
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(img, null, 0, 0);
	}
}
