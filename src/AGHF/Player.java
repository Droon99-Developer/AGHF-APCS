package AGHF;

import java.awt.Color;
import java.awt.Font;
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
public class Player implements ActionListener, Runnable {

	private int gold = 0;
	private boolean leftSide;
	private GameController gcDelegate;
	public UnitsPanel uPnl;

	// PlayerPanel UI elements
	protected JPanel playerPnl = new JPanel();
	private JLabel nameLbl;
	private JLabel goldLbl;
	private JButton purchaseBtn;
	private JButton upgradeBtn;
	private JButton endTurnBtn;
	private Font font = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);
	private Base myBase;
	
	public void setBase(Base b) {
		myBase = b;
	}
	
	public Player(String name, boolean leftSide) {
		this.leftSide = leftSide;
		nameLbl = new JLabel(name, JLabel.CENTER);
		nameLbl.setFont(font);
		goldLbl = new JLabel(String.format("%d", gold), JLabel.CENTER);
		goldLbl.setFont(font);
	}

	public String getName() {
		return nameLbl.getText();
	}

	public void setDelegate(GameController gc) {
		gcDelegate = gc;
	}

	public void startTurn() {
		// TODO add the correct amount of gold
		// economy money stuff
		changeGold(Economy.STARTVALUE);
		playerPnl.setVisible(true);
	}

	public void changeGold(int offset) {
		// economy money stuff
		gold += offset;
		if(gold > 2000000000 || gold < 0){//2 BILLION Golds
			gold = 2000000000;
		}
			
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
		endTurnBtn.setFont(font);
		endTurnBtn.addActionListener(this);
		endTurnBtn.setActionCommand("end");

		purchaseBtn = new JButton("Purchase Units");
		purchaseBtn.setFont(font);
		purchaseBtn.addActionListener(this);
		purchaseBtn.setActionCommand("uPnl");

		upgradeBtn = new JButton("Upgrade Base");
		upgradeBtn.setFont(font);
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

	public void setUnitsPanel(UnitsPanel pnl) {
		uPnl = pnl;
		uPnl.setPlayer(this);
	}

	public void unitPurchased(Unit newUnit, int cost) {
		// economy money stuff
		changeGold(-cost);
		newUnit.setSide(leftSide);

		// prevent Player from purchasing unit while animation is playing
		playerPnl.setVisible(false);
		uPnl.setVisible(false);
		gcDelegate.unitPurchased(newUnit, this);
		playerPnl.setVisible(true);
		uPnl.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gcDelegate.refocus();
		if (e.getActionCommand().equals("uPnl")) {
			uPnl.setVisible(!uPnl.isVisible());
		} else if (e.getActionCommand().equals("upgrade")) {
			upgradeBtn.setEnabled(myBase.upgrade());
		} else if (e.getActionCommand().equals("end")) {
			uPnl.setVisible(false);
			playerPnl.setVisible(false);
			// we are currently on the AWT-EventQueue-0 thread
			// we are about to do animations which use Thread.sleep()
			// so we need to move to a new thread
			Thread t = new Thread(this, "Advance & Attack Animation Thread");
			t.start();
		}
	}

	@Override
	public void run() {
		gcDelegate.turnEnded(this);
	}

	public int getGold() {
		return gold;
	}
}
