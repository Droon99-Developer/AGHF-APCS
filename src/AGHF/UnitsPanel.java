package AGHF;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UnitsPanel extends JPanel {
	// if we had any more units it would be better to make this an array but i guess this is fine for just 5 units
	private SingleUnitPanel airplanePnl = new SingleUnitPanel("Airplane", 100);
	private SingleUnitPanel tankPnl = new SingleUnitPanel("Tank", 100);
	private SingleUnitPanel infantryPnl = new SingleUnitPanel("Infantry", 100);
	private SingleUnitPanel scoutPnl = new SingleUnitPanel("Scout", 100);
	private SingleUnitPanel medicPnl = new SingleUnitPanel("Medic", 100);
	
	public UnitsPanel(int x, int y, int width, int height) {
		setLayout(null);
		setBounds(x,y,width,height);

	}
	
}
