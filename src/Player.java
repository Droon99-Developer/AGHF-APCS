
public class Player {
	
	private int gold = 10;
	private String name;
	private int[][] tanks;
	
	public Player(String Name){
		name = Name;
	}
	public Player(){
		name = "";
	}
	
	public String getName(){
		return name;
	}
	
	public int amtGold(){
		return gold;
	}
}
