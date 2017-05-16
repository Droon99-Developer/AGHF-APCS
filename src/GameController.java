import javax.swing.JFrame;

public class GameController {
	private Player p1;
	private Player p2;
	private JFrame frame;
	private GameView gv;
	public GameController(JFrame frame, Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.frame = frame;
		gv = new GameView();
		gv.setBounds(0,0,frame.getWidth(),frame.getHeight());
		frame.add(gv);
	}
}
