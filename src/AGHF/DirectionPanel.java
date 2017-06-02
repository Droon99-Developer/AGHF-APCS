package AGHF;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DirectionPanel extends JPanel {
	private int slide = 0;
	private JLabel slideTitle = new JLabel();
	private JLabel words = new JLabel();
	private JTable units;
	public void nextSlide(){
		slide++;
		if(slide % 4 == 1){
			Objectives();
		}else if(slide %4 == 2){
			Turn();
		}else if(slide % 4== 3){
			unitTable();
		}else if(slide%4 == 4){
			shortcuts();
		}
	}
	private void Objectives(){
		slideTitle.setText("OBJECTIVE");
		slideTitle.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, 20);
		slideTitle.setVisible(true);
		this.add(slideTitle);
		words.setText("The goal of the game is to destroy the base on the opposite side of the map as yours. /n This is accomplished by sending military units to war with the other player. /n Diplomacy doesn/'t work, so don/'t /try it.");
		words.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, slideTitle.getHeight() + 50);
		words.setVisible(true);
	}
	
	private void Turn(){
		slideTitle.setVisible(false);
		words.setVisible(false);
		slideTitle.setText("TURN");
		slideTitle.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, 20);
		slideTitle.setVisible(true);
		words.setText("1. Income /n This is the phase where you get the gold from killing opposing units and from your fixed income. /n 2. Buying /n This is the phase where you buy your units for a specific price. The 5 units are: /n a. Air Strike - 50 Gold /n b. Infantry - 8 Gold /n c. Tank - 18 Gold /n d. Marine - 2 Gold /n e. Medic - 12 Gold /n 3. Waiting The other player will now do steps 1 and 2. This may have happened before you did your steps 1 and 2. /n 4. Skirmishes/Moving /n All units will be deployed and attack opposing units or the enemy base, depending on where they are. If the unit is unable to move before attacking, it will not move that turn.");
		words.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, slideTitle.getHeight() + 50);
		words.setVisible(true);
	}
	
	private void unitTable() {
		slideTitle.setVisible(false);
		words.setVisible(false);
		slideTitle.setText("UNIT TABLE");
		slideTitle.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, 20);
		slideTitle.setVisible(true);
		String[] columnNames = {"Vehicle", "Air Strike", "Tank", "Infantry", "Marine", "Medic"};
		Object[][] data = {
				{"Speed (#Number of slices moved per turn)", 5, 1, 2, 4, 1},
				{"Strength", 40, 7, 3, 1, "Heals own team by 2 per turn"},
				{"Health", 40, 10, 7, 2, 7},
				{"Cost", Economy.AIRSTRIKECOST, Economy.TANKCOST, Economy.INFANTRYCOST, Economy.SCOUTCOST, Economy.MEDICCOST},
				{"Reward", 10, 5, 3, 1, 4}
		};
		units = new JTable(data, columnNames);
		units.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, slideTitle.getHeight() + 50);
		units.setVisible(true);
	}
	
	private void shortcuts(){
		slideTitle.setVisible(false);
		units.setVisible(false);
		slideTitle.setText("Keyboard Shortcuts");
		slideTitle.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, 20);
		slideTitle.setVisible(true);
		String[] columnNames = {"Operation", "Key"};
		Object[][] data = {
				{"Upgrade Base", "-"},
				{"End Turn", "[Enter]"},
				{"Show Units Panel", "="},
				{"Buy Air Strike", "1"},
				{"Buy Tank", "2"},
				{"Buy Marine", "3"},
				{"Buy Infantry", "4"},
				{"Buy Medic", "5"},
				{"Scroll", "A and D or Left and Right Arrow Keys"},
		};
		JTable KeyShorts = new JTable(data, columnNames);
		KeyShorts.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, slideTitle.getHeight() + 50);
		KeyShorts.setVisible(true);
	}
}
