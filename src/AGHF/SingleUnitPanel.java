package AGHF;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SingleUnitPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 122938938166445779L;
	private JLabel nameLbl;
	private JLabel costLbl;
	public int price;
	private JButton attackBtn = new JButton("Attack");
	private JButton defendBtn = new JButton("Defend");
	private int width;
	private UnitsPanel unitsPnl;
	
	public SingleUnitPanel(String name, int cost, int width, int height, int y) {
		setLayout(null);
		setBounds(0, y, width, height);
		nameLbl = new JLabel(name, JLabel.CENTER);
		nameLbl.setBounds(0, 0, width, height / 4);
		costLbl = new JLabel(String.format("%d Gold", cost), JLabel.CENTER);
		costLbl.setBounds(0,height / 4, width, height / 4);
		price = cost;
		add(nameLbl);
		add(costLbl);
		
		attackBtn.setBounds(0, height / 2, width / 2, height / 2);
		defendBtn.setBounds(width / 2, height / 2, width / 2, height / 2);
		add(attackBtn);
		add(defendBtn);
		attackBtn.addActionListener(this);
		defendBtn.addActionListener(this);
		
		this.width = width;
	}
	
	public void setEnabled(boolean enabled) {
		attackBtn.setEnabled(enabled);
		defendBtn.setEnabled(enabled);
	}
	
	public void setDelegate(UnitsPanel unitsPnl) {
		this.unitsPnl = unitsPnl;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(0, 0, width, 0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		unitsPnl.unitPurchased(this, (e.getSource() == defendBtn));
	}
	
}
