package AGHF;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import Units.*;

/*
 * Logic for slices
 * Attack animation
 * Advancement animation
 * etc.
 */
public class SliceController {
	public SlicePanel myPanel;
	// leftUnits has p1 units
	// rightUnits has p2 units
	private ArrayList<Unit> leftUnits = new ArrayList<Unit>();
	private ArrayList<Unit> rightUnits = new ArrayList<Unit>();

	private ArrayList<Unit> leftAir = new ArrayList<Unit>();
	private ArrayList<Unit> rightAir = new ArrayList<Unit>();

	private int index;

	public Base myBase = null;

	public SliceController(Rectangle bounds, int index) {
		this.index = index;
		myPanel = new SlicePanel(index);
		myPanel.setBounds(bounds);
	}

	public void setBase(Base b) {
		myBase = b;
		myPanel.addBase(myBase);
	}

	private void attackAnimation(ArrayList<Unit> attacking, ArrayList<Unit> defending) {
		for (Unit attackU : attacking) {
			String type = attackU.getClass().getName();
			if (!type.equals("Units.Medic")) {
				if (!defending.isEmpty()) {
					for (Unit defendU : defending) {
						if (!defendU.dead()) {
							Point orig = attackU.getLocation();
							myPanel.translateUnit(attackU, defendU.getLocation());
							attackU.attack(defendU);
							defendU.repaint();
							myPanel.translateUnit(attackU, orig);
						}
					}
				// ugly if statement to tell if units should attack the base with variable number of slices
				} else if (index == 0 && (attacking == rightUnits || attacking == rightAir)
						|| myBase != null && index != 0 && (attacking == leftUnits || attacking == leftAir)) {
					Point orig = attackU.getLocation();
					myPanel.translateUnit(attackU, myBase.getLocation());
					attackU.attack(myBase);
					myBase.repaint();
					myPanel.translateUnit(attackU, orig);
					if (attackU.getClass().getName().equals("Units.AirStrike")) {
						attackU.kill();
					}
				}
			}
		}
	}

	private void healAnimation(ArrayList<Unit> units) {
		for (Unit medic : units) {
			if (medic.getClass().getName().equals("Units.Medic") && !medic.dead()) {
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

	private void disposeDead(ArrayList<Unit> units, boolean left, int[] gold) {
		int i = 0;
		int goldI = left ? 1 : 0;
		boolean reposition = false;
		while (i < units.size()) {
			if (units.get(i).dead()) {
				myPanel.remove(units.get(i));
				gold[goldI] += units.get(i).GPK;
				units.remove(i);
				reposition = true;
			} else {
				i++;
			}
		}
		if (reposition) {
			if (!units.isEmpty()) {
				myPanel.incomingUnits(units, left, 0);
			}
			myPanel.repaint();
		}
	}

	public int[] performAttacks() {
		attackAnimation(leftUnits, rightUnits);
		attackAnimation(rightUnits, leftUnits);
		attackAnimation(leftAir, rightAir);
		attackAnimation(rightAir, leftAir);
		healAnimation(leftUnits);
		healAnimation(rightUnits);
		int[] goldEarned = {0, 0};
		disposeDead(leftUnits, true, goldEarned);
		disposeDead(rightUnits, false, goldEarned);
		disposeDead(leftAir, true, goldEarned);
		disposeDead(rightAir, false, goldEarned);
		return goldEarned;
	}

	// add units to the panel and correct ArrayList
	public void addUnits(ArrayList<Unit> units, boolean left) {
		ArrayList<Unit> correctGround = left ? leftUnits : rightUnits;
		ArrayList<Unit> correctAir = left ? leftAir : rightAir;
		int numOfNewAir = 0;
		for (Unit u : units) {
			myPanel.add(u);
			if (u.getClass().getName().equals("Units.AirStrike")) {
				correctAir.add(u);
				numOfNewAir++;
			} else {
				correctGround.add(u);
			}
		}
		if (correctGround.size() > 0 && units.size() - numOfNewAir > 0) {
			myPanel.incomingUnits(correctGround, left, units.size() - numOfNewAir);
		}
		if (correctAir.size() > 0 && numOfNewAir > 0) {
			myPanel.incomingUnits(correctAir, left, numOfNewAir);
		}
	}

	public void resetUnitAdvances() {
		for (Unit u : leftUnits) {
			u.resetAdvances();
		}
		for (Unit u : leftAir) {
			u.resetAdvances();
		}
		for (Unit u : rightUnits) {
			u.resetAdvances();
		}
		for (Unit u : rightAir) {
			u.resetAdvances();
		}
	}

	// return units to advance from an ArrayList of units
	private ArrayList<Unit> uta(ArrayList<Unit> units, boolean justScouts, boolean left) {
		ArrayList<Unit> retArr = new ArrayList<Unit>();
		int i = 0;
		while (i < units.size()) {
			Unit u = units.get(i);
			if (u.advancesLeft > 0 && !u.forDefense
					&& (justScouts && u.getClass().getName().equals("Units.Scout") || !justScouts)) {
				u.advancesLeft--;
				retArr.add(units.remove(i));
			} else {
				i++;
			}
		}
		if (retArr.size() > 0) {
			myPanel.advanceUnits(retArr, units, left);
			for (Unit u : retArr) {
				myPanel.remove(u);
			}
		}
		return retArr;
	}

	// returns the all units that can advance from a given side
	public ArrayList<Unit> unitsToAdvance(boolean left) {
		ArrayList<Unit> retArr = new ArrayList<Unit>();
		if (left) {
			if (!leftUnits.isEmpty()) {
				retArr.addAll(uta(leftUnits, !rightUnits.isEmpty(), true));
			}
			if (!leftAir.isEmpty()) {
				retArr.addAll(uta(leftAir, !rightAir.isEmpty(), true));
			}
		} else {
			if (!rightUnits.isEmpty()) {
				retArr.addAll(uta(rightUnits, !leftUnits.isEmpty(), false));
			}
			if (!rightAir.isEmpty()) {
				retArr.addAll(uta(rightAir, !leftAir.isEmpty(), false));
			}
		}

		return retArr;
	}

}
