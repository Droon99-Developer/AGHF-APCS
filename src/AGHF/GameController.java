package AGHF;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameController implements KeyListener, ActionListener {
	private Player p1;
	private Player p2;
	private JFrame frame;
	private GameView gv;
	private SliceController firstSlice;

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

		gv.renderPlayers(p1, p2);
		
		gv.renderUnitPanels();
		
		firstSlice = gv.renderSlices();

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
			firstSlice.startAttacks(this);
		}
	}
	
	public void attacksDone() {
		// TODO check if either user has lost the game
		// if nobody lost the game: 
			p1.startTurn();
		// we may need to call p1.startTurn(); somewhere else in this class if we want to
		// somehow make the units animate while they attack and advance
		firstSlice.advanceUnits(true);
		p1.startTurn();
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
			if (leftScroll && gv.sliceContainer.getLocation().x < 0) {
				if (fastScroll) {
					gv.scroll(SCROLL_SPEED * 8);
				} else {
					gv.scroll(SCROLL_SPEED);
				}
			}
			if (rightScroll && gv.sliceContainer.getLocation().x + gv.sliceContainer.getSize().width > frame.getWidth()) {
				if (fastScroll) {
					gv.scroll(-SCROLL_SPEED * 8);
				} else {
					gv.scroll(-SCROLL_SPEED);
				}
			}
		}
	}
	
	
}
