package AGHF;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import Units.*;

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
			if (!attackU.getClass().toString().equals("AirStrike")) {
				for (Unit defendU : defending) {
					if (!defendU.getClass().toString().equals("AirStrike")) {
						final int FRAMES = 40;
						int time = 0;
						double xMove = (defendU.getLocation().x - attackU.getLocation().x) / FRAMES;
						double yMove = (defendU.getLocation().y - attackU.getLocation().y) / FRAMES;
						while (time < FRAMES) {
							time++;
							attackU.setLocation((int)(attackU.getLocation().x + xMove), (int)(attackU.getLocation().y + yMove));
							try {
								Thread.sleep(4);
							} catch (InterruptedException e) {
								System.out.println("failed");
								e.printStackTrace();
							}
						}
						attackU.attack(defendU);
						defendU.repaint();
						time = 0;
						while (time < FRAMES) {
							time++;
							attackU.setLocation((int)(attackU.getLocation().x - xMove), (int)(attackU.getLocation().y - yMove));
							try {
								Thread.sleep(4);
							} catch (InterruptedException e) {
								System.out.println("failed");
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void run() {
		attackAnimation(leftUnits, rightUnits);
		attackAnimation(rightUnits, leftUnits);
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
		// TODO each unit from each side does damage on every unit from opposite
		// side
		// after all units have attacked, health is subtracted from each unit
		// if all enemy units are dead, then surviving units subtract one from
		// turns until next advance
		Thread t = new Thread(this);
		t.start();
	}
	
	public void addUnit(Unit unit, SliceController source) {
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
