import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameController {
	private Player p1;
	private Player p2;
	private JFrame frame;
	private GameView gv;
	private SliceController firstSlice;
	private final int SLICES_ON_SCREEN = 4;
	
	public GameController(JFrame frame, Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.frame = frame;
		
		gv = new GameView();
		gv.setBounds(0,0,frame.getWidth(),frame.getHeight());
		gv.setVisible(true);
		frame.add(gv);
		
		JPanel sliceContainer = new JPanel();
		int x = 0;
		int sliceWidth = frame.getWidth() / SLICES_ON_SCREEN;
		SliceController[] slices = new SliceController[12];
		for (int i = 0; i < slices.length; i++) {
			Rectangle bounds = new Rectangle(x, 0, sliceWidth, frame.getHeight());
			slices[i] = new SliceController(bounds);
			// add all of the slice panels to the sliceContainer
			sliceContainer.add(slices[i].myPanel);
			x += sliceWidth;
		}
		
		// create a sort of two way linked list of slices
		slices[0].link(null, slices[1]);
		firstSlice = slices[0];
		p1.setBaseSlice(slices[0]);
		for (int i = 1; i < slices.length - 1; i++) {
			slices[i].link(slices[i - 1], slices[i + 1]);
		}
		slices[slices.length - 1].link(slices[slices.length - 2], null);
		p2.setBaseSlice(slices[slices.length - 1]);
		
		sliceContainer.setBounds(0, 0, x, frame.getHeight());
		sliceContainer.setVisible(true);
		gv.add(sliceContainer);
	}
	
	
	// not sure how the turn system will work yet, but this is sort of what happens after each turn
	public void turn() {
		firstSlice.unitsAttack();
		firstSlice.unitsAdvance();
	}
		
}
