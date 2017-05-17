import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Player {
	
	private int gold = 10;
	private String name;
	private ArrayList<Unit> units;
	private JLabel nameLbl;
	private SliceController baseSlice;
	private BasePanel base = new BasePanel();
	
	public Player(String name){
		this.name = name;
		nameLbl = new JLabel(name);
	}
	
	public void setBaseSlice(SliceController baseSlice) {
		this.baseSlice = baseSlice;
		// somehow add the "base" of class "BasePanel" to the base slice controller's view
	}
	
	public void addUnit(Unit newUnit, boolean leftSide) {
		// not sure if we'll actually need the units array if we're just passing the units to the SliceController objects
		// but it's there just in case
		units.add(newUnit);
		
		Unit[] newUnits = {newUnit};
		baseSlice.addUnits(newUnits, leftSide);
	}
	
	public JLabel getNameLbl() {
		return nameLbl;
	}
	
	public String getName(){
		return name;
	}
	
	public int amtGold(){
		return gold;
	}
}
