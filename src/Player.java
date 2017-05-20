import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player implements ActionListener {
	
	private int gold = 10;
	private String name;
	private boolean leftSide;
	private ArrayList<Unit> units;
	private JLabel nameLbl;
	private SliceController baseSlice;
	protected JPanel playerPnl = new JPanel();
	private GameController gcDelegate;
	private UnitsPanel uPnl;
	private JButton purchaseBtn;
	private JButton upgradeBtn;
	
	public Player(String name, boolean leftSide) {
		this.name = name;
		this.leftSide = leftSide;
		nameLbl = new JLabel(name, JLabel.CENTER);
	}
	
	public void setDelegate(GameController gc) {
		gcDelegate = gc;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		 playerPnl.setLayout(null);
		 playerPnl.setBounds(x, y, width, height);
		 if (leftSide) {
			 nameLbl.setBounds(0, 0, 100, 50);
		 } else {
			 nameLbl.setBounds(playerPnl.getWidth() - 100, 0, 100, 50);
		 }
		 nameLbl.setOpaque(true);
		 nameLbl.setBackground(Color.RED);
		 playerPnl.add(nameLbl);
		 
		 purchaseBtn = new JButton("Purchase Units");
		 purchaseBtn.addActionListener(this);
		 purchaseBtn.setActionCommand("uPnl");
		 
		 upgradeBtn = new JButton("Upgrade Base");
		 upgradeBtn.addActionListener(this);
		 upgradeBtn.setActionCommand("upgrade");
		 
		 Rectangle left = new Rectangle(0, playerPnl.getHeight() - 50, playerPnl.getWidth() / 2, 50);
		 Rectangle right = new Rectangle(playerPnl.getWidth() / 2, playerPnl.getHeight() - 50, playerPnl.getWidth() / 2, 50);
		 if (leftSide) {
			 purchaseBtn.setBounds(left);
			 upgradeBtn.setBounds(right);
		 } else {
			 purchaseBtn.setBounds(right);
			 upgradeBtn.setBounds(left);
		 }
		 
		 playerPnl.add(purchaseBtn);
		 playerPnl.add(upgradeBtn);
	}
	
	public void setBaseSlice(SliceController baseSlice) {
		this.baseSlice = baseSlice;
	}
	
	public void setUnitsPanel(UnitsPanel pnl) {
		uPnl = pnl;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("uPnl")) {
			// toggle whether UnitsPanel is visible or not
			uPnl.setVisible(!uPnl.isVisible());
		} else if (e.getActionCommand().equals("upgrade")) {
			// perform necessary actions to upgrade the base
		}
	}
}
