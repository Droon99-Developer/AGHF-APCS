package AGHF;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SingleUnitPanel extends JPanel {
	private JLabel nameLbl;
	private JLabel costLbl;
	private JButton attackBtn = new JButton("Attack");
	private JButton defendBtn = new JButton("Defend");
	private int width;
	
	public SingleUnitPanel(String name, int cost, int width, int height, int y) {
		setLayout(null);
		setBounds(0, y, width, height);
		nameLbl = new JLabel(name, JLabel.CENTER);
		nameLbl.setBounds(0, 0, width, height / 4);
		costLbl = new JLabel(String.format("%d Gold", cost), JLabel.CENTER);
		costLbl.setBounds(0,height / 4, width, height / 4);
		add(nameLbl);
		add(costLbl);
		
		attackBtn.setBounds(0, height / 2, width / 2, height / 2);
		defendBtn.setBounds(width / 2, height / 2, width / 2, height / 2);
		add(attackBtn);
		add(defendBtn);
		
		this.width = width;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(0, 0, width, 0);
		
	}
	
}
