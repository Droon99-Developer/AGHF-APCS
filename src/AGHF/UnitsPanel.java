package AGHF;

import javax.swing.JPanel;

import Units.*;
/*
 * Lists all units in a slice
 */
public class UnitsPanel extends JPanel {
	private static final long serialVersionUID = 8863470085783682511L;
	private SingleUnitPanel[] SUPnls = new SingleUnitPanel[5];
	private Player delegate;

	public UnitsPanel(int x, int y, int width, int height) {
		setLayout(null);
		setBounds(x, y, width, height);
		SUPnls[0] = new SingleUnitPanel("Air Strike", Economy.AIRSTRIKECOST, width, height / 5, 0);
		SUPnls[1] = new SingleUnitPanel("Tank", Economy.TANKCOST, width, height / 5, height / 5);
		SUPnls[2] = new SingleUnitPanel("Scout", Economy.SCOUTCOST, width, height / 5, height * 2 / 5);
		SUPnls[3] = new SingleUnitPanel("Infantry", Economy.INFANTRYCOST, width, height / 5, height * 3 / 5);
		SUPnls[4] = new SingleUnitPanel("Medic", Economy.MEDICCOST, width, height / 5, height * 4 / 5);

		for (SingleUnitPanel pnl : SUPnls) {
			add(pnl);
			pnl.setDelegate(this);
		}
	}

	public void setPlayer(Player delegate) {
		this.delegate = delegate;
	}

	public void updateButtons(int gold) {
		for (SingleUnitPanel sup : SUPnls) {
			sup.setEnabled(sup.price <= gold);
		}
	}
	
	public void unitPurchased(SingleUnitPanel source, boolean forDefense) {
		int i = -1;
		// finds the index of the source in SUPnls
		while (++i < SUPnls.length && SUPnls[i] != source){}
		int cost = 0;
		Unit newUnit = null;
		switch (i) {
		case 0:
			newUnit = new AirStrike(forDefense);
			cost = Economy.AIRSTRIKECOST;
			break;
		case 1:
			newUnit = new Tank(forDefense);
			cost = Economy.TANKCOST;
			break;
		case 2:
			newUnit = new Scout(forDefense);
			cost = Economy.SCOUTCOST;
			break;
		case 3:
			newUnit = new Infantry(forDefense);
			cost = Economy.INFANTRYCOST;
			break;
		case 4:
			newUnit = new Medic(forDefense);
			cost = Economy.MEDICCOST;
			break;
		default:
			System.out.println("error");
			break;
		}
		delegate.unitPurchased(newUnit, cost);
	}
	
	public boolean getVisible(){
		return this.isVisible();
	}

}
