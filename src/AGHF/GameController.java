package AGHF;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameController implements KeyListener, ActionListener {
	private Player p1;
	private Player p2;
	private JFrame frame;
	private GameView gv;
	private JPanel sliceContainer;
	private SliceController firstSlice;
	private final int SLICES_ON_SCREEN = 2;
	private UnitsPanel leftUnitsPnl;
	private UnitsPanel rightUnitsPnl;

	// scroll with left and right arrow keys and hold shift for speed
	private boolean leftScroll = false;
	private boolean rightScroll = false;
	private boolean fastScroll = false;
	private final int SCROLL_SPEED = 4;
	private Timer timer;

	public void refocus() {
		frame.requestFocus();
	}
	
	public GameController(JFrame frame, Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.frame = frame;
		frame.addKeyListener(this);
		frame.requestFocus();

		gv = new GameView();
		gv.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.add(gv);

		p1.setBounds(0, 0, frame.getWidth() / 2, 100);
		p2.setBounds(frame.getWidth() / 2, 0, frame.getWidth() / 2, 100);
		gv.add(p1.playerPnl);
		gv.add(p2.playerPnl);

		leftUnitsPnl = new UnitsPanel(0, p1.playerPnl.getHeight(), frame.getWidth() / 4, 400);
		leftUnitsPnl.setVisible(false);
		rightUnitsPnl = new UnitsPanel(frame.getWidth() * 3 / 4, p2.playerPnl.getHeight(), frame.getWidth() / 4, 400);
		rightUnitsPnl.setVisible(false);
		gv.add(leftUnitsPnl);
		gv.add(rightUnitsPnl);
		p1.setUnitsPanel(leftUnitsPnl);
		p2.setUnitsPanel(rightUnitsPnl);
		
		
		sliceContainer = new JPanel();
		sliceContainer.setLayout(null);
		int x = 0;
		int sliceWidth = frame.getWidth() / SLICES_ON_SCREEN;
		SliceController[] slices = new SliceController[10];
		for (int i = 0; i < slices.length; i++) {
			Rectangle bounds = new Rectangle(x, 0, sliceWidth, frame.getHeight());
			slices[i] = new SliceController(bounds);
			// add all of the slice panels to the sliceContainer
			sliceContainer.add(slices[i].myPanel);
			x += sliceWidth;
		}
		sliceContainer.setBounds(0, 0, x, frame.getHeight());
		gv.add(sliceContainer);

		// create a sort of two way linked list of slices
		slices[0].link(null, slices[1]);
		firstSlice = slices[0];
		p1.setBaseSlice(slices[0]);
		for (int i = 1; i < slices.length - 1; i++) {
			slices[i].link(slices[i - 1], slices[i + 1]);
		}
		slices[slices.length - 1].link(slices[slices.length - 2], null);
		p2.setBaseSlice(slices[slices.length - 1]);

		timer = new Timer(5, this);
		timer.setActionCommand("tick");
		timer.start();
		p1.startTurn();
	}
	
	public void turnEnded(Player p) {
		if (p == p1) {
			p2.startTurn();
		} else {
			// we just tell the first slice to make units attack and advance because the slices are linked listed
			// so the first slice will then tell the second slice to attack & advance, the second tells the third, and so on...
			firstSlice.unitsAttack();
			firstSlice.unitsAdvance();
			// TODO check if either user has lost the game
			// if nobody lost the game: 
				p1.startTurn();
			// we may need to call p1.startTurn(); somewhere else in this class if we want to
			// somehow make the units animate while they attack and advance
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			leftScroll = true;
		} else if (e.getKeyCode() == 39) {
			rightScroll = true;
		} else if (e.getKeyCode() == 16) {
			fastScroll = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			leftScroll = false;
		} else if (e.getKeyCode() == 39) {
			rightScroll = false;
		} else if (e.getKeyCode() == 16) {
			fastScroll = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("tick")) {
			// called for every tick of the timer
			if (leftScroll && sliceContainer.getLocation().x < 0) {
				if (fastScroll) {
					scroll(SCROLL_SPEED * 8);
				} else {
					scroll(SCROLL_SPEED);
				}
			}
			if (rightScroll && sliceContainer.getLocation().x + sliceContainer.getSize().width > frame.getWidth()) {
				if (fastScroll) {
					scroll(-SCROLL_SPEED * 8);
				} else {
					scroll(-SCROLL_SPEED);
				}
			}
		}
	}
	
	public void scroll(int offset) {
		Point newP = sliceContainer.getLocation();
		newP.x += offset;
		if (newP.x > 0) {
			newP.x = 0;
		} else if (newP.x < frame.getWidth() - sliceContainer.getWidth()) {
			newP.x = frame.getWidth() - sliceContainer.getWidth();
		}
		sliceContainer.setLocation(newP);
	}

}
