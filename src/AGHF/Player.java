package AGHF;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Units.Unit;
/*
 * Contains all information about players
 * Makes top panel active
 */
public class Player implements ActionListener {

	private int gold = 0;
	private boolean leftSide;
	private SliceController baseSlice;
	private GameController gcDelegate;
	public UnitsPanel uPnl;

	// PlayerPanel UI elements
	protected JPanel playerPnl = new JPanel();
	private JLabel nameLbl;
	private JLabel goldLbl;
	private JButton purchaseBtn;
	private JButton upgradeBtn;
	private JButton endTurnBtn;

	public Player(String name, boolean leftSide) {
		this.leftSide = leftSide;
		nameLbl = new JLabel(name, JLabel.CENTER);
		goldLbl = new JLabel(String.format("%d", gold) , JLabel.CENTER);
	}

	public void setDelegate(GameController gc) {
		gcDelegate = gc;
	}

	public void startTurn() {
		// TODO add the correct amount of gold
		// economy money stuff
		changeGold(20);
		playerPnl.setVisible(true);
	}

	public void changeGold(int offset) {
		// economy money stuff
		gold += offset;
		goldLbl.setText(String.format("%d", gold) + " Gold");
		uPnl.updateButtons(gold);
	}

	public void setBounds(int x, int y, int width, int height) {
		playerPnl.setLayout(null);
		playerPnl.setBounds(x, y, width, height);
		playerPnl.setVisible(false);

		// color changed for debugging purposes
		nameLbl.setOpaque(true);
		nameLbl.setBackground(Color.RED);

		goldLbl.setBounds(width / 3, 0, width / 3, 50);

		endTurnBtn = new JButton("End Turn");
		endTurnBtn.addActionListener(this);
		endTurnBtn.setActionCommand("end");

		purchaseBtn = new JButton("Purchase Units");
		purchaseBtn.addActionListener(this);
		purchaseBtn.setActionCommand("uPnl");

		upgradeBtn = new JButton("Upgrade Base");
		upgradeBtn.addActionListener(this);
		upgradeBtn.setActionCommand("upgrade");

		Rectangle left = new Rectangle(0, playerPnl.getHeight() - 50, playerPnl.getWidth() / 2, 50);
		Rectangle right = new Rectangle(playerPnl.getWidth() / 2, playerPnl.getHeight() - 50, playerPnl.getWidth() / 2,
				50);
		if (leftSide) {
			nameLbl.setBounds(0, 0, width / 3, 50);
			purchaseBtn.setBounds(right);
			upgradeBtn.setBounds(left);
			endTurnBtn.setBounds(playerPnl.getWidth() * 2 / 3, 0, playerPnl.getWidth() / 3, 50);
	
			
		} else {
			nameLbl.setBounds(width * 2 / 3, 0, width / 3, 50);
			purchaseBtn.setBounds(left);
			upgradeBtn.setBounds(right);
			endTurnBtn.setBounds(0, 0, playerPnl.getWidth() / 3, 50);
			
		}
		playerPnl.add(nameLbl);
		playerPnl.add(goldLbl);
		playerPnl.add(purchaseBtn);
		playerPnl.add(upgradeBtn);
		playerPnl.add(endTurnBtn);
		
	}

	public void setBaseSlice(SliceController baseSlice) {
		this.baseSlice = baseSlice;
	}

	public void setUnitsPanel(UnitsPanel pnl) {
		uPnl = pnl;
		uPnl.setPlayer(this);
	}

	public void unitPurchased(Unit newUnit, int cost) {
		// economy money stuff
		changeGold(-cost);
		baseSlice.addUnit(newUnit, null);
		gcDelegate.refocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gcDelegate.refocus();
		if (e.getActionCommand().equals("uPnl")) {
			uPnl.setVisible(!uPnl.isVisible());
		} else if (e.getActionCommand().equals("upgrade")) {
			// TODO perform necessary actions to upgrade the base
		} else if (e.getActionCommand().equals("end")) {
			uPnl.setVisible(false);
			playerPnl.setVisible(false);
			gcDelegate.turnEnded(this);
			
		} else if (e.getActionCommand().equals("quit")) {
			System.exit(0);
		}
	}
}
