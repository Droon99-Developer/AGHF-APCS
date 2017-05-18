
public class Medic extends Unit{
	private Unit linker;
	private int healAmt = 2;
	private int cost = 12;
	private int GPK = 4;
	private int speed = linker.getSpeed();
	private int health = linker.getHealth();
	private int maxHealth = linker.getHealth();
	public Medic(Unit attachment){
		linker = attachment;
	}
	public int getSpeed() {
		return super.getSpeed();
	}

	public int getHealth() {
		return super.getHealth();
	}

	public int getStrength() {
		return super.getStrength();
	}
	
	public int getCost(){
		return super.getCost();
	}
	
	public int getPerKill(){
		return super.getPerKill();
	}
	public void heal(){
		if(linker.getHealth() != linker.getMaxHealth()){
			linker.addHealth();
		}
	}
}
