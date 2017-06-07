package AGHF;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameView extends JLayeredPane {
	private static final long serialVersionUID = -4430897747513766248L;
	// this class is responsible for drawing the mini map overview and quit
	// button
	// and also rendering stuff

	private final int SLICES_ON_SCREEN = 4;
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
	}

	public void renderUnitPanels() {
		UnitsPanel leftUnitsPnl = new UnitsPanel(getWidth() / 4, p1.playerPnl.getHeight(), getWidth() / 4, 500);
		leftUnitsPnl.setVisible(false);
		UnitsPanel rightUnitsPnl = new UnitsPanel(getWidth() / 2, p2.playerPnl.getHeight(), getWidth() / 4, 500);
		rightUnitsPnl.setVisible(false);
		p1.setUnitsPanel(leftUnitsPnl);
		p2.setUnitsPanel(rightUnitsPnl);
	}

	public SliceController[] renderSlices() {
		sliceContainer = new JPanel();
		sliceContainer.setLayout(null);
		double sliceWidth = (double) getWidth() / (double) SLICES_ON_SCREEN;
		SliceController[] slices = new SliceController[9];
		for (int i = 0; i < slices.length; i++) {
			double x = sliceWidth * (double) i;
			Rectangle bounds = new Rectangle(Math.round((float) x), 0, Math.round((float) sliceWidth), getHeight());
			slices[i] = new SliceController(bounds, i);
			// add all of the slice panels to the sliceContainer
			sliceContainer.add(slices[i].myPanel);
		}
		sliceContainer.setBounds(0, 0, Math.round((float) (sliceWidth * (double) slices.length)), getHeight());
		return slices;
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
	public void paintComponent(Graphics g) {
		@SuppressWarnings("unused")
		Graphics2D g2 = (Graphics2D) g;
		// TODO paint the mini map
	}

	public void addComponents() {
		add(sliceContainer, 0);
		moveToBack(sliceContainer);
		add(p1.playerPnl, 0);
		add(p2.playerPnl, 0);
		add(p1.uPnl, 0);
		add(p2.uPnl, 0);
	}
	
	public void removeAll(){
		p1.playerPnl.setVisible(false);
		p2.playerPnl.setVisible(false);
		p1.uPnl.setVisible(false);
		p2.uPnl.setVisible(false);
		sliceContainer.setVisible(false);
	}

}
