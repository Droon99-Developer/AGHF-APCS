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
	
	// scroll with left and right arrow keys and hold shift for speed
	private boolean leftScroll = false;
	private boolean rightScroll = false;
	private boolean fastScroll = false;
	private final int SCROLL_SPEED = 4;
	private Timer timer;
	
	public GameController(JFrame frame, Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.frame = frame;
		frame.addKeyListener(this);
		frame.requestFocus();
		
		gv = new GameView();
		gv.setBounds(0,0,frame.getWidth(),frame.getHeight());
		frame.add(gv);
		
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
		
		timer = new Timer(5,this);
		timer.start();
	}
	
	
	// not sure how the turn system will work yet, but this is sort of what happens after each turn
	public void turn() {
		firstSlice.unitsAttack();
		firstSlice.unitsAdvance();
	}


	@Override
	public void keyTyped(KeyEvent e) {}


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
