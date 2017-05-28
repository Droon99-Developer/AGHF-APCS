package AGHF;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import Units.Unit;

/*
 * Controls map view, quit button, and scrolling
 * Tells GameView to render units and slices
 * Implements advancement algorithm
 * Core turn logic
 */
public class GameController implements KeyListener, ActionListener {
	private Player p1;
	private Player p2;
	private JFrame frame;
	private GameView gv;
	private SliceController[] slices;
	private final int MIDDLE;
	private String codeWord = "jarjascat"; // pronounced JAR-ja-scat
	private int index = 0;

	// scroll with left and right arrow keys and hold shift for speed
	private boolean leftScroll = false;
	private boolean rightScroll = false;
	private boolean fastScroll = false;
	private final int SCROLL_SPEED = 4;
	private Timer timer;

	public void refocus() {
		frame.requestFocus();
	}

	public void unitPurchased(Unit newUnit, Player p) {
		frame.requestFocus();
		boolean left = p == p1;
		SliceController slice = left ? slices[0] : slices[slices.length - 1];
		// set the y location to the door of the base so units appear in base and slide to position
		newUnit.setLocation(0, frame.getHeight() * 5 / 6 - 14 - newUnit.getHeight() / 2);
		ArrayList<Unit> newUnits = new ArrayList<Unit>();
		newUnits.add(newUnit);
		slice.addUnits(newUnits, left);
	}

	// advances the units to or from the given the 2 pointers (MIDDLE + i and
	// MIDDLE - i)
	// returns true if any units were advanced forward
	private boolean advancePointers(int i, boolean movingOut) {
		int offset = movingOut ? 1 : -1;
		ArrayList<Unit> rightIncoming = slices[MIDDLE + i].unitsToAdvance(!movingOut);
		slices[MIDDLE + i - offset].addUnits(rightIncoming, !movingOut);
		ArrayList<Unit> leftIncoming = slices[MIDDLE - i].unitsToAdvance(movingOut);
		slices[MIDDLE - i + offset].addUnits(leftIncoming, movingOut);
		// we are not done until there are no more units that can advance
		return leftIncoming.isEmpty() && rightIncoming.isEmpty();
	}

	private void advanceUnits() {
		// THE ALGORITHM:
		// first: repeat this until no more units can advance:
		// 		move two pointers outward from the middle by one slice at a time, advancing each unit by one slice towards the middle
		// 		move pointers back inward towards the center, this time moving units outward
		// 		advance units on the middle slice until they are all done advancing
		// second: update all of the graphics and reset the advances of all
		// units
		boolean done = false;
		// main loop to advance units not on middle slice
		while (!done) {
			done = true;
			int i = 1;
			// move pointers out
			while (i <= MIDDLE) {
				done = done && advancePointers(i, true);
				i++;
			}
			// move pointers back in
			i -= 2;
			while (i > 0) {
				done = done && advancePointers(i, false);
				i--;
			}
			// advance units on middle slice
			done = done && advanceMiddle(true);
			done = done && advanceMiddle(false);
			// in case the right side had just scouts that are gone now
			done = done && advanceMiddle(true);
		}

		// update graphics and reset unit advances
		for (SliceController sc : slices) {
			sc.resetUnitAdvances();
		}
	}

	private boolean advanceMiddle(boolean left) {
		ArrayList<Unit> units = slices[MIDDLE].unitsToAdvance(left);
		int i = left ? 1 : -1;
		slices[MIDDLE + i].addUnits(units, left);
		return units.isEmpty();
	}

	public GameController(JFrame frame, Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.frame = frame;
		frame.addKeyListener(this);
		frame.requestFocus();

		gv = new GameView();
		gv.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.add(gv);

		gv.renderPlayers(p1, p2);

		gv.renderUnitPanels();

		slices = gv.renderSlices();
		MIDDLE = slices.length / 2;

		gv.addComponents();

		timer = new Timer(5, this);
		timer.setActionCommand("tick");
		timer.start();
		p1.startTurn();
	}

	public void turnEnded(Player p) {
		if (p == p1) {
			p2.startTurn();
		} else {
			advanceUnits();
			// perform attacking
			for (SliceController sc : slices) {
				int[] goldEarned = sc.performAttacks();
				p1.changeGold(goldEarned[0]);
				p2.changeGold(goldEarned[1]);
			}
			p1.startTurn();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == (int) (codeWord.charAt(index)) - 32) {
			index++;
			if (index == codeWord.length()) {
				index = 0;
				System.out.println("You Win");
				if (p1.turn()) {
					p1.changeGold(100000);
				} else {
					p2.changeGold(100000);
				}
			}
		} else {
			index = 0;
		}
		if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
			leftScroll = true;
		} else if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
			rightScroll = true;
		} else if (e.getKeyCode() == 16) {
			fastScroll = true;
		}
		if (e.getKeyCode() == 74) {
			p1.changeGold(5000);
		} else if (e.getKeyCode() == 84) {
			p2.changeGold(5000);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
			leftScroll = false;
		} else if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
			rightScroll = false;
		} else if (e.getKeyCode() == 16) {
			fastScroll = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("tick")) {
			// called for every tick of the timer
			if (leftScroll && gv.sliceContainer.getLocation().x < 0) {
				if (fastScroll) {
					gv.scroll(SCROLL_SPEED * 8);
				} else {
					gv.scroll(SCROLL_SPEED);
				}
			}
			if (rightScroll
					&& gv.sliceContainer.getLocation().x + gv.sliceContainer.getSize().width > frame.getWidth()) {
				if (fastScroll) {
					gv.scroll(-SCROLL_SPEED * 8);
				} else {
					gv.scroll(-SCROLL_SPEED);
				}
			}
		}
	}

}
