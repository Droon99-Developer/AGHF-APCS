
public class Unit {
	public int speed;
	public int health;
	public int strength;
	public int cost;
	public int GPK;
	public int maxHealth;
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
	public int getMaxHealth(){
		return maxHealth;
	}
	public void addHealth(){
		health += 2;
	}
}
