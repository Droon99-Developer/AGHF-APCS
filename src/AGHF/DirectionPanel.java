package AGHF;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DirectionPanel extends JPanel {
	private int slide = 0;
	private JLabel slideTitle = new JLabel();
	private JLabel words = new JLabel();
	private ArrayList<JLabel> instructions = new ArrayList<JLabel>();
	private JTable units;
	private JTable KeyShorts;
	public void nextSlide() {
		slide++;
		if(slide % 4 == 1){
			objectives();
		}else if(slide % 4 == 2){
			turn();
		}else if(slide % 4 == 3){
			unitTable();
		} else if (slide % 4 == 0) {
			shortcuts();
		}
	}
	private void objectives() {
		if(slide != 1){
			KeyShorts.setVisible(false);
		}
		slideTitle.setText("OBJECTIVE");
		slideTitle.setSize(100,50);
		slideTitle.setLocation(this.getWidth()/2 - slideTitle.getWidth()/2, 20);
		slideTitle.setVisible(true);
		add(slideTitle);
		instructions.add(new JLabel());
		instructions.get(0).setText("The goal of the game is to destroy the base on the opposite side of the map as yours.");
		instructions.add(new JLabel());
		instructions.get(1).setText("This is accomplished by sending military units to war with the other player.");
		instructions.add(new JLabel());
		instructions.get(2).setText("Diplomacy doesn't work, so don't try it.");
				//"The goal of the game is to destroy the base on the opposite side of the map as yours. /n This is accomplished by sending military units to war with the other player. /n Diplomacy doesn/'t work, so don/'t /try it.");
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setSize(600, 50);
			instructions.get(i).setLocation(this.getWidth() / 2 - instructions.get(i).getWidth() / 2, slideTitle.getHeight() + 50 + 80*i);
			instructions.get(i).setVisible(true);
			add(instructions.get(i));
		}
	}
	
	private void turn(){
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setVisible(false);
			instructions.remove(i);
			i--;
		}
		slideTitle.setVisible(false);
		slideTitle.setText("TURN");
		slideTitle.setLocation(this.getWidth() / 2 - slideTitle.getWidth() / 2, 20);
		slideTitle.setVisible(true);
		instructions.add(new JLabel());
		instructions.get(0).setText("1. Income: This is the phase where you get the gold from killing opposing units and from your fixed income.");
		instructions.add(new JLabel());
		instructions.get(1).setText("2. Buying: This is the phase where you buy your units for a specific price. The Units are on the next page of the instructions");
		instructions.add(new JLabel());
		instructions.get(2).setText("3. Waiting: The other player will now do steps 1 and 2. This may have happened before you did your steps 1 and 2.");
		instructions.add(new JLabel());
		instructions.get(3).setText("4. Skirmishes/Moving: All units will be deployed and attack opposing units or the enemy base, depending on where they are.");
		instructions.add(new JLabel());
		instructions.get(4).setText("         If the unit is unable to move before attacking, it will not move that turn.");
				//"The goal of the game is to destroy the base on the opposite side of the map as yours. /n This is accomplished by sending military units to war with the other player. /n Diplomacy doesn/'t work, so don/'t /try it.");
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setSize(1000, 50);
			instructions.get(i).setLocation(this.getWidth() / 2 - instructions.get(i).getWidth() / 2, slideTitle.getHeight() + 50 + 80*i);
			instructions.get(i).setVisible(true);
			if(i == 4){
				instructions.get(i).setLocation(this.getWidth() / 2 - instructions.get(i).getWidth() / 2, slideTitle.getHeight() - 10 + 80*i);
			}
			add(instructions.get(i));
		}
	}

	private void unitTable() {
		slideTitle.setVisible(false);
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setVisible(false);
			instructions.remove(i);
			i--;
		}
		slideTitle.setText("UNIT TABLE");
		slideTitle.setLocation(this.getWidth() / 2 - slideTitle.getWidth() / 2, 20);
		slideTitle.setVisible(true);
		String[] columnNames = { "Vehicle", "Air Strike", "Tank", "Infantry", "Marine", "Medic" };
		Object[][] data = { 
				{ "Vehicle", "Air Strike", "Tank", "Infantry", "Marine", "Medic" },
				{ "Speed (#Number of slices moved per turn)", 5, 1, 2, 4, 1 },
				{ "Strength", 40, 7, 3, 1, "Heals own team by 2 per turn" }, { "Health", 40, 10, 7, 2, 7 },
				{ "Cost", Economy.AIRSTRIKECOST, Economy.TANKCOST, Economy.INFANTRYCOST, Economy.SCOUTCOST,
						Economy.MEDICCOST },
				{ "Reward", 10, 5, 3, 1, 4 } };
		units = new JTable(data, columnNames);
		units.setSize(800, 100);
		units.getColumnModel().getColumn(0).setPreferredWidth(250);
		units.getColumnModel().getColumn(5).setPreferredWidth(200);
		units.setLocation(this.getWidth() / 2 - units.getWidth() / 2, slideTitle.getHeight() + 50);
		units.setVisible(true);
		add(units);
	}

	private void shortcuts() {
		units.setVisible(false);
		slideTitle.setVisible(false);
		slideTitle.setText("KEYBOARD SHORTCUTS");
		slideTitle.setLocation(this.getWidth() / 2 - slideTitle.getWidth() / 2, 20);
		slideTitle.setSize(200, 50);
		slideTitle.setVisible(true);
		String[] columnNames = { "Operation", "Key" };
		Object[][] data = { { "Operation", "Key" },
				{ "Upgrade Base", "-" }, 
				{ "End Turn", "[Enter]" }, 
				{ "Show Units Panel", "=" },
				{ "Buy Air Strike", "1" },
				{ "Buy Tank", "2" }, 
				{ "Buy Marine", "3" }, 
				{ "Buy Infantry", "4" },
				{ "Buy Medic", "5" }, 
				{ "Scroll", "A and D or Left and Right Arrow Keys" }, };
		KeyShorts = new JTable(data, columnNames);
		KeyShorts.setSize(800, 160);
		KeyShorts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		KeyShorts.setLocation(this.getWidth() / 2 - KeyShorts.getWidth() / 2, slideTitle.getHeight() + 50);
		KeyShorts.setVisible(true);
		add(KeyShorts);
	}
}
