package Units;

public class AirStrike extends Unit {
	static final long serialVersionUID = 5667678708220137591L;

	public AirStrike(boolean forDefense) {
		// SPEED: 5
		// DAMAGE: 40
		// MAX HEALTH: 40 (only killed by anothre air strike)
		// GPK: 10
		// constructor structure: int speed, int damage, int maxHealth, int GPK,
		// boolean forDefense
		super(5, 40, 1, 10, forDefense, "Jet");
	}
}
