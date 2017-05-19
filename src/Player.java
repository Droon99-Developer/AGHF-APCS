import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player {
	
	private int gold = 10;
	private String name;
	private boolean leftSide;
	private ArrayList<Unit> units;
	private JLabel nameLbl;
	private SliceController baseSlice;
	protected JPanel playerPnl = new JPanel();
	private UnitsPanel unitsPnl;
	
	
	public Player(String name, boolean leftSide) {
		this.name = name;
		this.leftSide = leftSide;
		nameLbl = new JLabel(name);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		 playerPnl.setLayout(null);
		 playerPnl.setBounds(x, y, width, height);
		 
		 nameLbl.setBounds(playerPnl.getWidth() / 2 - 50, 0, 100, 50);
		 playerPnl.add(nameLbl);
		 
		 unitsPnl = new UnitsPanel(0, playerPnl.getHeight(), playerPnl.getWidth() / 2, 400);
		 
	}
	
	public void setBaseSlice(SliceController baseSlice) {
		this.baseSlice = baseSlice;
		// somehow add the "base" of class "BasePanel" to the base slice controller's view
	}
	
	public void addUnit(Unit newUnit) {
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
