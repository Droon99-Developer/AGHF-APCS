package AGHF;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import Units.*;

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
	private String codeWord = "peckmanisbad";
	private int index = 0;
	private String codeWord2 = "cool";
	private int index2 = 0;
	public static int turnNumber = 0;
	public static int turnRamp = 3;
	public static int rampNumber = 2;
	// scroll with left and right arrow keys and hold shift for speed
	private boolean leftScroll = false;
	private boolean rightScroll = false;
	private boolean fastScroll = false;
	private final int SCROLL_SPEED = 4;
	private Timer timer;
	private boolean leftTurn = true;
	private EndView end;

	public void refocus() {
		frame.requestFocus();
	}

	public void unitPurchased(Unit newUnit, Player p) {
		frame.requestFocus();
		newUnit.setTurnChecker(this);
		boolean left = p == p1;
		SliceController slice = left ? slices[0] : slices[slices.length - 1];
		// set the y location to middle of unit area
		newUnit.setLocation(0, frame.getHeight() * 5 / 6 - 14 - newUnit.getHeight() / 2);
		ArrayList<Unit> newUnits = new ArrayList<Unit>();
		newUnits.add(newUnit);
		slice.addUnits(newUnits, left);
	}

	public boolean checkTurn(boolean left) {
		return left == leftTurn;
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
		// move two pointers outward from the middle by one slice at a time,
		// advancing each unit by one slice towards the middle
		// move pointers back inward towards the center, this time moving units
		// outward
		// advance units on the middle slice until they are all done advancing
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

		// reset unit advances
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

		Base b1 = new Base();
		p1.setBase(b1);
		slices[0].setBase(b1);

		Base b2 = new Base();
		p2.setBase(b2);
		slices[slices.length - 1].setBase(b2);

		MIDDLE = slices.length / 2;

		gv.addComponents();

		timer = new Timer(5, this);
		timer.setActionCommand("tick");
		timer.start();
		p1.startTurn();
	}

	public void turnEnded(Player p) {
		leftTurn = !leftTurn;
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
			GameController.turnNumber++;
			if (GameController.turnNumber >= GameController.turnRamp) {
				Economy.STARTVALUE = Economy.STARTVALUE * GameController.rampNumber;
				if (Economy.STARTVALUE > Economy.AIRSTRIKECOST * 1000000) {
					Economy.STARTVALUE = Economy.AIRSTRIKECOST * 1000000;
					GameController.rampNumber = 1;
				}
				GameController.turnNumber = 0;
				GameController.turnRamp++;
				if (GameController.turnNumber % 2 == 0) {
					GameController.rampNumber++;
				}
			}
			// check if anyone has lost
			if (slices[0].myBase.healthLeft == 0 || slices[slices.length - 1].myBase.healthLeft == 0) {
				if (slices[0].myBase.healthLeft != 0) {
					// p2 lost
					end = new EndView(p1);
				} else if (slices[slices.length - 1].myBase.healthLeft != 0) {
					// p1 lost
					end = new EndView(p2);
				} else {
					// tie
					end = new EndView(null);
					System.out.println("The game ended in a tie!");
				}
				timer.stop();
				end.setBoundsAndRender(0, 0, frame.getWidth(), frame.getHeight());
				gv.add(end, 1);
			} else {
				p1.startTurn();
			}
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
				if (leftTurn) {
					p1.changeGold(Economy.AIRSTRIKECOST * 10000000);
				} else {
					p2.changeGold(Economy.AIRSTRIKECOST * 10000000);
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
		} else if (e.getKeyCode() == 49 || e.getKeyCode() == 97) {
			if (leftTurn && p1.getGold() >= Economy.AIRSTRIKECOST) {
				p1.unitPurchased(new AirStrike(false), Economy.AIRSTRIKECOST);
			} else if (p2.getGold() >= Economy.AIRSTRIKECOST) {
				p2.unitPurchased(new AirStrike(false), Economy.AIRSTRIKECOST);
			}
			p1.uPnl.setVisible(false);
			p2.uPnl.setVisible(false);
		} else if (e.getKeyCode() == 50 || e.getKeyCode() == 98) {
			if (leftTurn && p1.getGold() >= Economy.TANKCOST) {
				p1.unitPurchased(new Tank(false), Economy.TANKCOST);
			} else if (p2.getGold() >= Economy.TANKCOST) {
				p2.unitPurchased(new Tank(false), Economy.TANKCOST);
			}
			p1.uPnl.setVisible(false);
			p2.uPnl.setVisible(false);
		} else if (e.getKeyCode() == 51 || e.getKeyCode() == 99) {
			if (leftTurn && p1.getGold() >= Economy.SCOUTCOST) {
				p1.unitPurchased(new Scout(false), Economy.SCOUTCOST);
			} else if (p2.getGold() >= Economy.SCOUTCOST) {
				p2.unitPurchased(new Scout(false), Economy.SCOUTCOST);
			}
			p1.uPnl.setVisible(false);
			p2.uPnl.setVisible(false);
		} else if (e.getKeyCode() == 52 || e.getKeyCode() == 100) {
			if (leftTurn && p1.getGold() >= Economy.INFANTRYCOST) {
				p1.unitPurchased(new Infantry(false), Economy.INFANTRYCOST);
			} else if (p2.getGold() >= Economy.INFANTRYCOST) {
				p2.unitPurchased(new Infantry(false), Economy.INFANTRYCOST);
			}
			p1.uPnl.setVisible(false);
			p2.uPnl.setVisible(false);
		} else if (e.getKeyCode() == 53 || e.getKeyCode() == 101) {
			if (leftTurn && p1.getGold() >= Economy.MEDICCOST) {
				p1.unitPurchased(new Medic(false), Economy.MEDICCOST);
			} else if (p2.getGold() >= Economy.MEDICCOST) {
				p2.unitPurchased(new Medic(false), Economy.MEDICCOST);
			}
			p1.uPnl.setVisible(false);
			p2.uPnl.setVisible(false);
		} else if (e.getKeyCode() == 10) {
			if (leftTurn) {
				p1.uPnl.setVisible(false);
				p1.playerPnl.setVisible(false);
				// we are currently on the AWT-EventQueue-0 thread
				// we are about to do animations which use Thread.sleep()
				// so we need to move to a new thread
				Thread t = new Thread(p1, "Advance & Attack Animation Thread");
				t.start();
			} else {
				p2.uPnl.setVisible(false);
				p2.playerPnl.setVisible(false);
				// we are currently on the AWT-EventQueue-0 thread
				// we are about to do animations which use Thread.sleep()
				// so we need to move to a new thread
				Thread t = new Thread(p2, "Advance & Attack Animation Thread");
				t.start();
			}
		} else if (e.getKeyCode() == 61) {
			if (leftTurn) {
				if (p1.uPnl.getVisible()) {
					p1.uPnl.setVisible(false);
				} else {
					p1.uPnl.setVisible(true);
				}
			} else {
				if (p2.uPnl.getVisible()) {
					p2.uPnl.setVisible(false);
				} else {
					p2.uPnl.setVisible(true);
				}
			}
		} else if (e.getKeyCode() == 45) {
			if (leftTurn && p1.myBase.getLevel() < 3
					&& p1.getGold() > (p1.myBase.getLevel() + 1) * Economy.LEVELUPGRADE) {
				p1.upgradeBtn.setEnabled(p1.myBase.upgrade());
				p1.changeGold(-1 * (p1.myBase.getLevel()) * Economy.LEVELUPGRADE);
			} else if (p2.myBase.getLevel() < 3 && p2.getGold() > (p2.myBase.getLevel() + 1) * Economy.LEVELUPGRADE) {
				p2.upgradeBtn.setEnabled(p2.myBase.upgrade());
				p2.changeGold(-1 * (p2.myBase.getLevel()) * Economy.LEVELUPGRADE);
			}
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
