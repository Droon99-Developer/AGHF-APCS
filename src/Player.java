import javax.swing.JLabel;

public class Player {
	
	private int gold = 10;
	private String name;
	private int[][] tanks;
	private JLabel nameLbl;
	
	public Player(String name){
		this.name = name;
		nameLbl = new JLabel(name);
	}
	
	public JLabel getNameLbl() {
		return nameLbl;
	}
	
	public String getName(){
		return name;
	}
	
	public int amtGold(){
		return gold;
	}
}
