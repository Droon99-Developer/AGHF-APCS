
public class Marine extends Unit {
	private int speed = 10/3;
	private int health = 2;
	private int strength = 1;
	private int cost = 2;
	private int GPK = 1;
	
	public int getSpeed() {
		return speed;
	}

	public int getHealth() {
		return health;
	}

	public int getStrength() {
		return strength;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getPerKill(){
		return GPK;
	}
}
