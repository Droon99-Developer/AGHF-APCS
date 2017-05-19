import java.awt.Rectangle;
import java.util.ArrayList;

public class SliceController {
	public SlicePanel myPanel;
	// rightUnits will have all the units coming from player 2 from the right side
	private ArrayList<Unit> leftUnits;
	private ArrayList<Unit> rightUnits;
	
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
		// each unit from each side does damage on every unit from opposite side
		// after all units have attacked, health is subtracted from each unit
		// if all enemy units are dead, then surviving units subtract one from turns until next advance
		if (rightSlice != null) {
			rightSlice.unitsAttack();
		}
	}
	
	// not yet sure where we'll keep reference to the units and how that will all work
	public void addUnits(Unit[] units, boolean fromLeftSide) {
		// add units to correct arraylist
	}
	
	
	
	public void unitsAdvance() {
		// if a unit is ready to advance, rightSlice.addUnit(...) or leftSlice.addUnit(...) and reset turns until next advance
		// we could give scouts the ability to advance even when there are enemy units still alive??
		// (placeholder:)
		if (rightSlice != null) {
			rightSlice.unitsAdvance();
		}
	}
	
}
