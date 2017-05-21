package AGHF;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import Units.Unit;

public class SliceController {
	public SlicePanel myPanel;
	// rightUnits will have all the units coming from player 2 from the right
	// side
	private ArrayList<Unit> leftUnits = new ArrayList<Unit>();
	private ArrayList<Unit> rightUnits = new ArrayList<Unit>();

	private SliceController rightSlice;
	private SliceController leftSlice;

	public SliceController(Rectangle bounds) {
		myPanel = new SlicePanel();
		myPanel.setBounds(bounds);
	}

	public void link(SliceController left, SliceController right) {
		rightSlice = right;
		leftSlice = left;
	}

	public void unitsAttack() {
		// TODO each unit from each side does damage on every unit from opposite
		// side
		// after all units have attacked, health is subtracted from each unit
		// if all enemy units are dead, then surviving units subtract one from
		// turns until next advance

		if (rightSlice != null) {
			rightSlice.unitsAttack();
		}
	}
	
	public void addUnit(Unit unit, SliceController source) {
		ArrayList<Unit> correctSide;
		if (source == leftSlice) {
			correctSide = leftUnits;
		} else {
			correctSide = rightUnits;
		}
		correctSide.add(unit);
		myPanel.renderNewUnit(unit, correctSide, source == leftSlice);
	}

	public void advanceUnits(boolean left) {
		System.out.println(left);
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
