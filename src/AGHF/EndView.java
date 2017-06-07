package AGHF;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndView extends JPanel {
	private JLabel winner;
	public EndView(Player p){
		if(p.getName() != "tie"){
			winner = new JLabel(p.getName() + " won the game");
		}else{
			winner = new JLabel("There was a tie");
		}
		winner.setBounds(0, 0, 400, 50);
		winner.setForeground(Color.GRAY);
		winner.setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		add(winner);
	}
}
