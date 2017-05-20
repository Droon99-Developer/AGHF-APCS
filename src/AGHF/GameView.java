package AGHF;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class GameView extends JPanel {
	private static final long serialVersionUID = -4430897747513766248L;
	// this class is responsible for drawing the mini map overview and quit
	// button
	// and also rendering stuff

	private final int SLICES_ON_SCREEN = 2;
	private Player p1;
	private Player p2;
	public JPanel sliceContainer;

	public GameView() {
		setLayout(null);
	}

	public void renderPlayers(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		p1.setBounds(0, 0, getWidth() / 2, 100);
		p2.setBounds(getWidth() / 2, 0, getWidth() / 2, 100);
		add(p1.playerPnl);
		add(p2.playerPnl);
	}

	public void renderUnitPanels() {
		UnitsPanel leftUnitsPnl = new UnitsPanel(0, p1.playerPnl.getHeight(), getWidth() / 4, 500);
		leftUnitsPnl.setVisible(false);
		UnitsPanel rightUnitsPnl = new UnitsPanel(getWidth() * 3 / 4, p2.playerPnl.getHeight(), getWidth() / 4, 500);
		rightUnitsPnl.setVisible(false);
		add(leftUnitsPnl);
		add(rightUnitsPnl);
		p1.setUnitsPanel(leftUnitsPnl);
		p2.setUnitsPanel(rightUnitsPnl);
	}

	// returns the first slice controller to GameController
	public SliceController renderSlices() {
		sliceContainer = new JPanel();
		sliceContainer.setLayout(null);
		int x = 0;
		int sliceWidth = getWidth() / SLICES_ON_SCREEN;
		SliceController[] slices = new SliceController[10];
		for (int i = 0; i < slices.length; i++) {
			Rectangle bounds = new Rectangle(x, 0, sliceWidth, getHeight());
			slices[i] = new SliceController(bounds);
			// add all of the slice panels to the sliceContainer
			sliceContainer.add(slices[i].myPanel);
			x += sliceWidth;
		}
		sliceContainer.setBounds(0, 0, x, getHeight());
		add(sliceContainer);

		// create a sort of two way linked list of slices
		slices[0].link(null, slices[1]);
		p1.setBaseSlice(slices[0]);
		for (int i = 1; i < slices.length - 1; i++) {
			slices[i].link(slices[i - 1], slices[i + 1]);
		}
		slices[slices.length - 1].link(slices[slices.length - 2], null);
		p2.setBaseSlice(slices[slices.length - 1]);
		return slices[0];
	}

	public void scroll(int offset) {
		Point newP = sliceContainer.getLocation();
		newP.x += offset;
		if (newP.x > 0) {
			newP.x = 0;
		} else if (newP.x < getWidth() - sliceContainer.getWidth()) {
			newP.x = getWidth() - sliceContainer.getWidth();
		}
		sliceContainer.setLocation(newP);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2  = (Graphics2D)g;
		// paint all of the units
	}
}
