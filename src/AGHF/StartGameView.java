package AGHF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
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
	private JButton creditsBtn;
	private JTextField p1Name;
	private JTextField p2Name;
	private JLabel p1Lbl;
	private JLabel p2Lbl;
	private JLabel lblTitle;
	private Player p1;
	private Player p2;
	private JFrame frame;
	private DirectionPanel d;
	private CreditsPnl c;
	private Font font;
	private Font font2;

	public StartGameView() {
		frame = new JFrame();
		frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 30);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setName("AGHF");

		// need to pick a color if we want a diff color background
		frame.getContentPane().setBackground(Color.getHSBColor(0f, 0f, 0.1f));

		font = new Font("Dialog", Font.BOLD | Font.HANGING_BASELINE, 180);
		font2 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);

		ImageIcon icon = new ImageIcon("src/assets/GameTitle.png");
		lblTitle = new JLabel(icon);
		lblTitle.setSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		lblTitle.setLocation(frame.getWidth() / 2 - lblTitle.getWidth() / 2,
				frame.getHeight() / 4 - lblTitle.getHeight() / 2);

		directionsBtn = new JButton("Directions");
		directionsBtn.setBounds(frame.getWidth() / 2 + 140, frame.getHeight() * 3 / 4, 160, 70);
		directionsBtn.setFont(font2);
		directionsBtn.addActionListener(this);
		directionsBtn.setActionCommand("directions");
		
		creditsBtn = new JButton("Credits");
		creditsBtn.setBounds(frame.getWidth() / 2-80, frame.getHeight() * 3 / 4, 160, 70);
		creditsBtn.setFont(font2);
		creditsBtn.addActionListener(this);
		creditsBtn.setActionCommand("credits");

		quitBtn = new JButton("Quit");
		quitBtn.setBounds(frame.getWidth() / 2 - 300, frame.getHeight() * 3 / 4, 160, 70);
		quitBtn.setFont(font2);
		quitBtn.addActionListener(this);
		quitBtn.setActionCommand("quit");

		newGameBtn = new JButton("Create New Game");
		newGameBtn.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() * 3 / 4 - 150, 300, 70);
		newGameBtn.setFont(font2);
		newGameBtn.addActionListener(this);
		newGameBtn.setActionCommand("setup");

		frame.add(lblTitle);
		frame.add(newGameBtn);
		frame.add(quitBtn);
		frame.add(directionsBtn);
		frame.add(creditsBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("setup")) {
			font2 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);

			p1Lbl = new JLabel("Player 1 Name:");
			p1Lbl.setFont(font2);
			p1Lbl.setForeground(Color.WHITE);

			p2Lbl = new JLabel("Player 2 Name:");
			p2Lbl.setFont(font2);
			p2Lbl.setForeground(Color.WHITE);

			p1Name = new JTextField("Player 1");
			p1Name.setForeground(Color.GRAY);
			p1Name.setFont(font2);

			p2Name = new JTextField("Player 2");
			p2Name.setForeground(Color.GRAY);
			p2Name.setFont(font2);

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
			p1Lbl.setBounds(ref.x - 94, ref.y - 40, 200, 40);

			ref.x = frame.getWidth() * 3 / 4;
			p2Name.setBounds(ref.x - 100, ref.y, 200, 40);
			p2Lbl.setBounds(ref.x - 94, ref.y - 40, 200, 40);

			quitBtn.setText("Back");
			quitBtn.setActionCommand("back");
			quitBtn.setLocation(frame.getWidth() / 2 - 78, frame.getHeight() / 2 + 200);
			creditsBtn.setVisible(false);
			frame.add(p1Lbl);
			frame.add(p2Lbl);
			frame.add(p1Name);
			frame.add(p2Name);
			frame.remove(directionsBtn);
			frame.repaint();
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
			frame.remove(lblTitle);
			frame.remove(creditsBtn);
			frame.repaint();
			GameController gc = new GameController(frame, p1, p2);
			p1.setDelegate(gc);
			p2.setDelegate(gc);
		} else if (e.getActionCommand().equals("directions")) {
			newGameBtn.setVisible(false);
			creditsBtn.setVisible(false);
			quitBtn.setText("Back");
			quitBtn.setActionCommand("bacc");
			directionsBtn.setText("Next");
			directionsBtn.setActionCommand("next");
			d = new DirectionPanel();
			d.setLayout(null);
			d.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			d.nextSlide();
			frame.add(d);
			frame.remove(lblTitle);
			frame.repaint();
		} else if (e.getActionCommand().equals("next")) {
			d.nextSlide();
			frame.add(d);
			frame.repaint();
		} else if (e.getActionCommand().equals("back")) {
			frame.remove(p1Lbl);
			frame.remove(p2Lbl);
			frame.remove(p1Name);
			frame.remove(p2Name);
			frame.add(lblTitle);
			creditsBtn.setVisible(true);
			newGameBtn.setActionCommand("setup");
			newGameBtn.setText("Create New Game");
			quitBtn.setText("Quit");
			quitBtn.setActionCommand("quit");
			quitBtn.setBounds(frame.getWidth() / 2 - 300, frame.getHeight() * 3 / 4, 160, 70);
			frame.add(directionsBtn);
			newGameBtn.setVisible(true);
			frame.repaint();
		} else if (e.getActionCommand().equals("bacc")) {
			newGameBtn.setActionCommand("setup");
			newGameBtn.setText("Create New Game");
			quitBtn.setText("Quit");
			quitBtn.setActionCommand("quit");
			directionsBtn.setText("Directions");
			directionsBtn.setActionCommand("directions");
			creditsBtn.setVisible(true);
			d.setVisible(false);
			newGameBtn.setVisible(true);
			frame.add(lblTitle);
			frame.repaint();
		} else if (e.getActionCommand().equals("quit")) {
			System.exit(0);
		}else if(e.getActionCommand().equals("credits")){
			newGameBtn.setVisible(false);
			directionsBtn.setVisible(false);
			quitBtn.setVisible(false);
			creditsBtn.setText("Back");
			creditsBtn.setActionCommand("baq");
			c = new CreditsPnl();
			c.setLayout(null);
			c.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			c.setVisible(true);
			c.show();
			frame.add(c);
			frame.remove(lblTitle);
			frame.repaint();
		}else if(e.getActionCommand().equals("baq")){
			newGameBtn.setVisible(true);
			directionsBtn.setVisible(true);
			quitBtn.setVisible(true);
			creditsBtn.setText("Credits");
			creditsBtn.setActionCommand("credits");
			frame.remove(c);
			frame.add(lblTitle);
			frame.repaint();
		}
	}
}
