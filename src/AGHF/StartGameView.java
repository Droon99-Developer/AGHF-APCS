package AGHF;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
/*
 * Sets up frame
 * Sets up start view
 */
public class StartGameView implements ActionListener {
	private JButton newGameBtn;
	private JButton quitBtn;
	private JButton directionsBtn;
	private JTextField p1Name;
	private JTextField p2Name;
	private JLabel p1Lbl;
	private JLabel p2Lbl;
	private Player p1;
	private Player p2;
	private JFrame frame;
	private DirectionPanel d;
	
	public StartGameView() {
		frame = new JFrame();
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 30);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setName("AGHF");
		directionsBtn = new JButton("Directions");
		directionsBtn.setBounds(frame.getWidth() / 2 - 80, frame.getHeight() * 3 / 4 - 50, 160, 70);
		directionsBtn.addActionListener(this);
		directionsBtn.setActionCommand("directions");
		newGameBtn = new JButton("Create New Game");
		quitBtn = new JButton("Quit");
		quitBtn.setBounds(frame.getWidth() / 2 - 80, frame.getHeight() * 3 / 4 + 50, 160, 70);
		quitBtn.addActionListener(this);
		quitBtn.setActionCommand("quit");
		newGameBtn.setBounds(frame.getWidth() / 2 - 80, frame.getHeight() * 3 / 4 - 150, 160, 70);
		newGameBtn.addActionListener(this);
		newGameBtn.setActionCommand("setup");
		frame.add(newGameBtn);
		frame.add(quitBtn);
		frame.add(directionsBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("setup")) {
			p1Lbl = new JLabel("Player 1 Name:");
			p2Lbl = new JLabel("Player 2 Name:");
			p1Name = new JTextField("Player 1");
			p1Name.setForeground(Color.GRAY);
			
			p2Name = new JTextField("Player 2");
			p2Name.setForeground(Color.GRAY);
			
			p1Name.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (p1Name.getForeground() == Color.GRAY) {
						p1Name.setText("");
						p1Name.setForeground(Color.BLACK);
					}
				}
				@Override
				public void focusLost(FocusEvent e) {
					if (p1Name.getText().isEmpty()) {
						p1Name.setForeground(Color.GRAY);
						p1Name.setText("Player 1");
					}
				}
			});
			p2Name.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (p2Name.getForeground() == Color.GRAY) {
						p2Name.setText("");
						p2Name.setForeground(Color.BLACK);
					}
				}
				@Override
				public void focusLost(FocusEvent e) {
					if (p2Name.getText().isEmpty()) {
						p2Name.setForeground(Color.GRAY);
						p2Name.setText("Player 2");
					}
				}
			});
			
			Point ref = new Point(frame.getWidth() / 4, frame.getHeight() / 2);
			p1Name.setBounds(ref.x - 100, ref.y, 200, 40);
			p1Lbl.setBounds(ref.x - 50, ref.y - 40, 100, 40);
			
			ref.x = frame.getWidth() * 3 / 4;
			p2Name.setBounds(ref.x - 100, ref.y, 200, 40);
			p2Lbl.setBounds(ref.x - 50, ref.y - 40, 100, 40);
			quitBtn.setText("Back");
			quitBtn.setActionCommand("back");
			frame.add(p1Lbl);
			frame.add(p2Lbl);
			frame.repaint();
			frame.add(p1Name);
			frame.add(p2Name);
			newGameBtn.setText("Begin!");
			newGameBtn.setActionCommand("begin");
		} else if (e.getActionCommand().equals("begin")) {
			p1 = new Player(p1Name.getText(), true);
			p2 = new Player(p2Name.getText(), false);
			frame.remove(p1Lbl);
			frame.remove(p2Lbl);
			frame.remove(p1Name);
			frame.remove(p2Name);
			frame.remove(newGameBtn);
			frame.remove(quitBtn);
			frame.remove(directionsBtn);
			//quitBtn.setBounds(frame.getWidth() / 2 - 80, frame.getHeight() * 3 / 4 - 500, 160, 70);
			//frame.add(quitBtn);
			frame.repaint();
			GameController gc = new GameController(frame,p1,p2);
			p1.setDelegate(gc);
			p2.setDelegate(gc);
		}else if(e.getActionCommand().equals("quit")){
			System.exit(0);		
		}else if(e.getActionCommand().equals("back")){
			frame.remove(p1Lbl);
			frame.remove(p2Lbl);
			frame.remove(p1Name);
			frame.remove(p2Name);
			newGameBtn.setActionCommand("setup");
			newGameBtn.setText("Create New Game");
			quitBtn.setText("Quit");
			quitBtn.setActionCommand("quit");
			newGameBtn.setVisible(true);
			frame.repaint();
		}else if(e.getActionCommand().equals("directions")){
			newGameBtn.setVisible(false);
			quitBtn.setText("Back");
			quitBtn.setActionCommand("bacc");
			directionsBtn.setText("Next");
			directionsBtn.setActionCommand("next");
			d = new DirectionPanel();
			d.nextSlide();
			frame.add(d);
			frame.repaint();
		}else if(e.getActionCommand().equals("next")){
			d.nextSlide();
			frame.add(d);
			frame.repaint();
		}else if(e.getActionCommand().equals("bacc")){
			newGameBtn.setActionCommand("setup");
			newGameBtn.setText("Create New Game");
			quitBtn.setText("Quit");
			quitBtn.setActionCommand("quit");
			directionsBtn.setText("Directions");
			directionsBtn.setActionCommand("directions");
			newGameBtn.setVisible(true);
			frame.repaint();
		}
	}
}
