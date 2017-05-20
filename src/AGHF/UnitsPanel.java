package AGHF;
import javax.swing.JButton;
import javax.swing.JPanel;

import Units.*;

public class UnitsPanel extends JPanel {
	private SingleUnitPanel[] SUPnls = new SingleUnitPanel[5];
	public UnitsPanel(int x, int y, int width, int height) {
		setLayout(null);
		setBounds(x,y,width,height);
		SUPnls[0] = new SingleUnitPanel("Air Strike", AirStrike.COST, width, height / 5, 0);
		SUPnls[1] = new SingleUnitPanel("Tank", Tank.COST, width, height / 5, height / 5);
		SUPnls[2] = new SingleUnitPanel("Marine", Marine.COST, width, height / 5, height * 2 / 5);
		SUPnls[3] = new SingleUnitPanel("Infantry", Infantry.COST, width, height / 5, height * 3 / 5);
		SUPnls[4] = new SingleUnitPanel("Medic", Medic.COST, width, height / 5, height * 4 / 5);
		
		for (SingleUnitPanel pnl : SUPnls) {
			add(pnl);
		}
	}
	
}
