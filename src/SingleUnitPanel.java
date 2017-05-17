import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SingleUnitPanel extends JPanel {
	private JLabel nameLbl;
	private JLabel costLbl;
	private JButton attackBtn = new JButton("Attack");
	private JButton defenseBtn = new JButton("Defense");
	
	public SingleUnitPanel(String name, int cost) {
		nameLbl = new JLabel(name);
		costLbl = new JLabel(String.format("%d", cost));
		add(nameLbl);
		add(costLbl);
	}
	
}
