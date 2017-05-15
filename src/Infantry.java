
public class Infantry extends Unit{
	private int speed = 10/8;
	private int health = 7;
	private int strength = 3;
	private int cost = 8;
	private int GPK = 3;
	
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
