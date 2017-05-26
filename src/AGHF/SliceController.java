package AGHF;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import Units.*;

/*
 * Logic for slices
 * Attack animation
 * etc.
 */
public class SliceController implements Runnable {
	public SlicePanel myPanel;
	// rightUnits will have all the units coming from player 2 from the right
	// side
	private ArrayList<Unit> leftUnits = new ArrayList<Unit>();
	private ArrayList<Unit> rightUnits = new ArrayList<Unit>();

	private GameController gcDelegate;

	private SliceController rightSlice;
	private SliceController leftSlice;

	public SliceController(Rectangle bounds, int index) {
		myPanel = new SlicePanel(index);
		myPanel.setBounds(bounds);
	}

	public void link(SliceController left, SliceController right) {
		rightSlice = right;
		leftSlice = left;
	}

	private void attackAnimation(ArrayList<Unit> attacking, ArrayList<Unit> defending) {
		for (Unit attackU : attacking) {
			String type = attackU.getClass().getName();
			if (!type.equals("Units.AirStrike") && !type.equals("Units.Medic")) {
				for (Unit defendU : defending) {
					if (!defendU.getClass().toString().equals("Units.AirStrike") && !defendU.dead()) {
						Point orig = attackU.getLocation();
						translateUnit(attackU, defendU.getLocation());
						attackU.attack(defendU);
						defendU.repaint();
						translateUnit(attackU, orig);
					}
				}
			}
		}
	}

	private void translateUnit(Unit u, Point dest) {
		// don't waste time staying in place
		if (!u.getLocation().equals(dest)) {
			final double FRAMES = 20;
			double currF = 0;
			Point start = u.getLocation();
			double xMove = dest.x - start.x;
			double yMove = dest.y - start.y;
			while (currF < FRAMES) {
				currF++;
				u.setLocation((int) (start.x + xMove * (currF / FRAMES)), (int) (start.y + yMove * (currF / FRAMES)));
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					System.err.println("attacking animation thread interrupted");
					e.printStackTrace();
				}
			}
		}
	}

	private void healAnimation(ArrayList<Unit> units) {
		for (Unit medic : units) {
			if (medic.getClass().getName().equals("Units.Medic")) {
				for (Unit patient : units) {
					if (patient.healMe()) {
						Point orig = medic.getLocation();
						translateUnit(medic, patient.getLocation());
						((Medic) medic).heal(patient);
						patient.repaint();
						translateUnit(medic, orig);
					}
				}
			}
		}
	}

	private int disposeDead(ArrayList<Unit> units, boolean left) {
		int i = 0;
		int goldEarned = 0;
		while (i < units.size()) {
			if (units.get(i).dead()) {
				myPanel.remove(units.get(i));
				goldEarned += units.get(i).GPK;
				units.remove(i);
			} else {
				i++;
			}
		}
		return goldEarned;
	}
	
	@Override
	public void run() {
		attackAnimation(leftUnits, rightUnits);
		attackAnimation(rightUnits, leftUnits);
		healAnimation(leftUnits);
		healAnimation(rightUnits);
		// TODO somehow figure out how GPK is returned to player
		// I may want to rewrite the whole SliceController logic so that they are all
		// in an array in GameController before doing this instead of having a linked list
		disposeDead(leftUnits, true);
		disposeDead(rightUnits, false);
		myPanel.renderUnits(leftUnits, true);
		myPanel.renderUnits(rightUnits, false);
		
		if (rightSlice != null) {
			rightSlice.unitsAttack();
		} else {
			leftSlice.doneAttacking();
		}
	}

	private void doneAttacking() {
		if (leftSlice != null) {
			leftSlice.doneAttacking();
		} else {
			gcDelegate.attacksDone();
		}
	}

	public void startAttacks(GameController delegate) {
		gcDelegate = delegate;
		unitsAttack();
	}

	private void unitsAttack() {
		Thread t = new Thread(this);
		t.start();
	}

	public void addUnit(Unit unit, SliceController source) {
		// TODO fix bug where units being added from the previous slice will calculate position
		// based off of number of units before advancement
		// this might be easier to fix with an array of SliceControllers instead of linked list
		ArrayList<Unit> correctSide = (source == leftSlice) ? leftUnits : rightUnits;
		correctSide.add(unit);
		myPanel.renderNewUnit(unit, correctSide, source == leftSlice);
		myPanel.repaint();
	}

	public void advanceUnits(boolean left) {
		// we could give scouts the ability to advance even when there are enemy
		// units still alive??
		ArrayList<Unit> unitsToAdvance = null;
		SliceController nextSlice = left ? rightSlice : leftSlice;
		if (left && !leftUnits.isEmpty() && rightUnits.isEmpty()) {
			unitsToAdvance = leftUnits;
		} else if (!left && leftUnits.isEmpty() && !rightUnits.isEmpty()) {
			unitsToAdvance = rightUnits;
		}
		if (unitsToAdvance != null && nextSlice != null) {
			int i = 0;
			while (i < unitsToAdvance.size()) {
				Unit u = unitsToAdvance.get(i);
				if (!u.forDefense) {
					if (u.advancesLeft > 0) {
						u.advancesLeft--;
						myPanel.remove(unitsToAdvance.get(i));
						myPanel.repaint();
						nextSlice.addUnit(unitsToAdvance.remove(i), this);
						i--;
					} else {
						u.resetAdvances();
					}
				}
				i++;
			}
		}

		if (nextSlice != null) {
			nextSlice.advanceUnits(left);
		} else if (nextSlice == rightSlice) {
			advanceUnits(false);
		}
	}

}
