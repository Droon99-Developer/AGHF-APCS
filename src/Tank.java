
public class Tank extends Unit {
	private int speed = 10/10;
	private int health = 10;
	private int strength = 7;
	private int cost = 18;
	private int GPK = 5;
	
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
