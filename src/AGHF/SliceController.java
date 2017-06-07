package AGHF;

import java.awt.Color;
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

	private GameController gcDelegate;

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
		/*
		for (Unit attackU : attacking) {
			String type = attackU.getClass().getName();
			if (!type.equals("Units.Medic")) {
				if (!type.equals("Units.AirStrike") && !defending.isEmpty()) {
					for (Unit defendU : defending) {
						if (!defendU.getClass().getName().equals("Units.AirStrike") && !defendU.dead()) {
							Point orig = attackU.getLocation();
							myPanel.translateUnit(attackU, defendU.getLocation());
							attackU.attack(defendU);
							defendU.repaint();
							myPanel.translateUnit(attackU, orig);
						}
					}
					// ugly if statement to tell if units should attack the base
				} else if (index == 0 && attacking == rightUnits
						|| myBase != null && index != 0 && attacking == leftUnits) {
					Point orig = attackU.getLocation();
					myPanel.translateUnit(attackU, myBase.getLocation());
					attackU.attack(myBase);
					myBase.repaint();
					myPanel.translateUnit(attackU, orig);
				}
			}
		}
		*/
		if (attacking.size() > defending.size())
		{
			for (Unit attackU : attacking)
			{
				String type = attackU.getClass().getName();
				if (!type.equals("Units.Medic"))
				{
					if (!type.equals("Units.AirStrike") && !defending.isEmpty())
					{
						for (Unit defendU : defending)
						{
							if(defendU.dead())
							{
								break;
							}
							Point orig = attackU.getLocation();
							Point end = defendU.getLocation();
							Point middle = defendU.getLocation();
							middle.setLocation((((orig.getX())+(end.getX()))/2),(((orig.getY())+(end.getY()))/2));
							myPanel.translateUnit(attackU, middle);
							myPanel.translateUnit(defendU, middle);
							attackU.attack(defendU);
							defendU.attack(attackU);
							myPanel.translateUnit(defendU, end);
							myPanel.translateUnit(attackU, orig);
							defendU.repaint();
							attackU.repaint();
						}
					} else if (index == 0 && attacking == rightUnits
							|| myBase != null && index != 0 && attacking == leftUnits)
					{
						Point orig = attackU.getLocation();
						myPanel.translateUnit(attackU, myBase.getLocation());
						attackU.attack(myBase);
						myBase.repaint();
						myPanel.translateUnit(attackU, orig);
					}
				}
			}
		}
		else
		{
			for (Unit defendU : attacking)
			{
				String type = defendU.getClass().getName();
				if (!type.equals("Units.Medic"))
				{
					if (!type.equals("Units.AirStrike") && !attacking.isEmpty())
					{
						for (Unit attackU : attacking)
						{
							Point orig = attackU.getLocation();
							Point end = attackU.getLocation();
							Point middle = attackU.getLocation();
							middle.setLocation((((orig.getX())+(end.getX()))/2),(((orig.getY())+(end.getY()))/2));
							myPanel.translateUnit(attackU, middle);
							myPanel.translateUnit(attackU, middle);
							defendU.attack(defendU);
							myPanel.translateUnit(attackU, end);
							myPanel.translateUnit(attackU, orig);
							attackU.repaint();
							defendU.repaint();
						}
					} else if (index == 0 && attacking == rightUnits
							|| myBase != null && index != 0 && attacking == leftUnits)
					{
						Point orig = defendU.getLocation();
						myPanel.translateUnit(defendU, myBase.getLocation());
						defendU.attack(myBase);
						myBase.repaint();
						myPanel.translateUnit(defendU, orig);
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

	private int disposeDead(ArrayList<Unit> units, boolean left) {
		int i = 0;
		int goldEarned = 0;
		boolean reposition = false;
		while (i < units.size()) {
			if (units.get(i).dead()) {
				myPanel.remove(units.get(i));
				goldEarned += units.get(i).GPK;
				units.remove(i);
				reposition = true;
			} else {
				i++;
			}
		}
		if (reposition) {
			if (!units.isEmpty()) {
				ArrayList<Unit> temp = new ArrayList<Unit>();
				myPanel.incomingUnits(temp, units, left);
			}
			myPanel.repaint();
		}
		return goldEarned;
	}

	public int[] performAttacks() {
		attackAnimation(leftUnits, rightUnits);
		attackAnimation(rightUnits, leftUnits);
		healAnimation(leftUnits);
		healAnimation(rightUnits);
		int[] goldEarned = { disposeDead(leftUnits, true), disposeDead(rightUnits, false) };
		return goldEarned;
	}

	// add units to the panel and correct ArrayList
	public void addUnits(ArrayList<Unit> units, boolean left) {
		if (units.size() > 0) {
			ArrayList<Unit> correctSide = left ? leftUnits : rightUnits;
			myPanel.incomingUnits(units, correctSide, left);
		}
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
		myPanel.advanceUnits(retArr, units, left);
		for (Unit u : retArr) {
			myPanel.remove(u);
		}
		return retArr;
	}

	// returns the all units that can advance from a given side
	public ArrayList<Unit> unitsToAdvance(boolean left) {
		ArrayList<Unit> retArr = new ArrayList<Unit>();
		if (left && !leftUnits.isEmpty()) {
			retArr = uta(leftUnits, !rightUnits.isEmpty(), true);
		} else if (!left && !rightUnits.isEmpty()) {
			retArr = uta(rightUnits, !leftUnits.isEmpty(), false);
		}
		return retArr;
	}

}
