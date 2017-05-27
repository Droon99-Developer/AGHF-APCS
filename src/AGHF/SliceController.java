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
	// leftUnits has p1 units
	// rightUnits has p2 units
	private ArrayList<Unit> leftUnits = new ArrayList<Unit>();
	private ArrayList<Unit> rightUnits = new ArrayList<Unit>();

	private GameController gcDelegate;

	private int index;
	
	public SliceController(Rectangle bounds, int index) {
		this.index = index;
		myPanel = new SlicePanel(index);
		myPanel.setBounds(bounds);
	}

	private void attackAnimation(ArrayList<Unit> attacking, ArrayList<Unit> defending) {
		for (Unit attackU : attacking) {
			String type = attackU.getClass().getName();
			if (!type.equals("Units.AirStrike") && !type.equals("Units.Medic")) {
				for (Unit defendU : defending) {
					if (!defendU.getClass().toString().equals("Units.AirStrike") && !defendU.dead()) {
						Point orig = attackU.getLocation();
						myPanel.translateUnit(attackU, defendU.getLocation());
						attackU.attack(defendU);
						defendU.repaint();
						myPanel.translateUnit(attackU, orig);
					}
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
						myPanel.translateUnit(medic, patient.getLocation());
						((Medic) medic).heal(patient);
						patient.repaint();
						myPanel.translateUnit(medic, orig);
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
		myPanel.renderUnits(units, left);
		return goldEarned;
	}
	
	@Override
	public void run() {
		attackAnimation(leftUnits, rightUnits);
		attackAnimation(rightUnits, leftUnits);
		healAnimation(leftUnits);
		healAnimation(rightUnits);
		gcDelegate.doneAttacking(index, disposeDead(leftUnits, true), disposeDead(rightUnits, false));
	}

	public void startAttacks(GameController delegate) {
		gcDelegate = delegate;
		Thread t = new Thread(this);
		t.start();
	}

	// add a unit to the panel and correct ArrayList
	public void addUnits(ArrayList<Unit> units, boolean left) {
		ArrayList<Unit> correctSide = left ? leftUnits : rightUnits;
		correctSide.addAll(units);
		for (Unit u : units) {
			myPanel.add(u);
		}
	}

	public void renderUnits(boolean left) {
		ArrayList<Unit> correctSide = left ? leftUnits : rightUnits;
		myPanel.renderUnits(correctSide, left);
	}
	
	public void resetUnitAdvances() {
		for (Unit u : leftUnits) {
			u.resetAdvances();
		}
		for (Unit u : rightUnits) {
			u.resetAdvances();
		}
	}
	
	// return units to advance from an ArrayList of units
	private ArrayList<Unit> uta(ArrayList<Unit> units, boolean justScouts) {
		ArrayList<Unit> retArr = new ArrayList<Unit>();
		int i = 0;
		while (i < units.size()) {
			Unit u = units.get(i);
			if (u.advancesLeft > 0 && (justScouts && u.getClass().getName().equals("Units.Scout") || !justScouts)) {
				u.advancesLeft--;
				myPanel.remove(u);
				retArr.add(units.remove(i));
			} else {
				i++;
			}
		}
		return retArr;
	}
	
	// returns the all units that can advance from a given side
	public ArrayList<Unit> unitsToAdvance(boolean left) {
		ArrayList<Unit> retArr = new ArrayList<Unit>();
		if (left && !leftUnits.isEmpty()) {
			retArr = uta(leftUnits, rightUnits.isEmpty());
		} else if (!left && leftUnits.isEmpty()) {
			retArr = uta(rightUnits, !leftUnits.isEmpty());
		}
		return retArr;
	}

}
