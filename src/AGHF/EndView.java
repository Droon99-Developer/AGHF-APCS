package AGHF;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 9008585438816874582L;
	private JLabel winLbl;
//	private JButton newGameBtn = new JButton("New Game");
	private JButton quitBtn = new JButton("Quit");
	
	private Font font = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);
	
	public EndView(Player p) {
		setLayout(null);
		if (p != null) {
			winLbl = new JLabel(p.getName() + " won the game!", SwingConstants.CENTER);
		} else {
			winLbl = new JLabel("There was a tie!", SwingConstants.CENTER);
		}
		winLbl.setFont(new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 36));
		winLbl.setForeground(Color.GREEN);
		winLbl.setBackground(Color.getHSBColor(0f, 0f, 0.1f));
		this.setBackground(Color.getHSBColor(0f, 0f, 0.1f));
		add(winLbl);
		
//		newGameBtn.setActionCommand("new");
//		newGameBtn.addActionListener(this);
//		add(newGameBtn);
		
		quitBtn.setActionCommand("quit");
		quitBtn.addActionListener(this);
		add(quitBtn);
	}

	public void setBoundsAndRender(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		winLbl.setBounds(x, y, width, height * 2 / 3);
		int size = 200;
//		i don't feel like actually implementing this
//		newGameBtn.setBounds(width / 3 - size / 2, height * 2 / 3 - size / 6, size, size / 3);
//		newGameBtn.setFont(font);
//		quitBtn.setBounds(width * 2 / 3 - size / 2, height * 2 / 3 - size / 6, size, size / 3);
		quitBtn.setBounds(width / 2 - size / 2, height * 2 / 3 - size / 6, size, size / 3);
		quitBtn.setFont(font);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new")) {
			// TODO create a new game?
		} else {
			System.exit(0);
		}
	}

}
