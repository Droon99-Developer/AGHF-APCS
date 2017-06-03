package AGHF;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DirectionPanel extends JPanel {
	private int slide = 0;
	private JLabel slideTitle = new JLabel();
	private ArrayList<JLabel> instructions = new ArrayList<JLabel>();
	private JTable units;
	private JTable KeyShorts;
	private JLabel extra;
	private JLabel extra2;
	private Font font = new Font("Dialog", Font.BOLD | Font.HANGING_BASELINE, 110);
	private Font font2 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);
	private Font font3 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 35);
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
		slideTitle.setFont(font);
		slideTitle.setBounds(75, 20, this.getWidth(),140);
		slideTitle.setVisible(true);
		add(slideTitle);
		
		instructions.add(new JLabel());
		instructions.get(0).setText("The goal of the game is to destroy the base on the opposite side of the map as yours.");
		instructions.add(new JLabel());
		instructions.get(1).setText("This is accomplished by sending military units to war with the other player.");
		instructions.add(new JLabel());
		instructions.get(2).setText(" Diplomacy doesn't work, so don't try it.");
		
		//"The goal of the game is to destroy the base on the opposite side of the map as yours. /n This is accomplished by sending military units to war with the other player. /n Diplomacy doesn/'t work, so don/'t /try it.");
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setFont(font2);
			instructions.get(i).setVisible(true);
			instructions.get(i).setBounds(this.getWidth() / 2 - instructions.get(i).getWidth() / 2 - 500, slideTitle.getHeight() + 140  + 80*i,1000,50);
			add(instructions.get(i));
		}
	}
	
	private void turn(){
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setVisible(false);
			instructions.remove(i);
			i--;
		}
		slideTitle.setText("TURN");
		
		instructions.add(new JLabel());
		instructions.get(0).setText("1. Income: This is the phase where you get the gold from killing opposing units and from your");
		instructions.add(new JLabel());
		instructions.get(1).setText(" fixed income.");
		instructions.add(new JLabel());
		instructions.get(2).setText("2. Buying: This is the phase where you buy your units for a specific price. The Units are on the next");
		instructions.add(new JLabel());
		instructions.get(3).setText(" page of the instructions");
		instructions.add(new JLabel());
		instructions.get(4).setText("3. Waiting: The other player will now do steps 1 and 2. This may have happened before you did your ");
		instructions.add(new JLabel());
		instructions.get(5).setText("steps 1 and 2.");
		instructions.add(new JLabel());
		instructions.get(6).setText("4. Skirmishes/Moving: All units will be deployed and attack opposing units or the enemy base,");
		instructions.add(new JLabel());
		instructions.get(7).setText(" depending on where they are.  If the unit is unable to move before attacking, it will not");
		instructions.add(new JLabel());
		instructions.get(8).setText("move that turn.");
		//"The goal of the game is to destroy the base on the opposite side of the map as yours. /n This is accomplished by sending military units to war with the other player. /n Diplomacy doesn/'t work, so don/'t /try it.");
		
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setSize(1000, 50);
			instructions.get(i).setFont(font2);
			instructions.get(i).setVisible(true);
			if(i == 8){
				instructions.get(i).setLocation(50 + this.getWidth() / 2 - instructions.get(i).getWidth() / 2, slideTitle.getHeight() - 60 + 80*(i/2 + 1));
			}else if(i%2 == 0){
				instructions.get(i).setLocation(this.getWidth() / 2 - instructions.get(i).getWidth() / 2, slideTitle.getHeight() +40  + 80*(i/2));
			} else if(i%2 == 1){
				instructions.get(i).setLocation(40 + this.getWidth() / 2 - instructions.get(i).getWidth() / 2, slideTitle.getHeight() - 10 + 80*(i/2 + 1));
			}
			add(instructions.get(i));
		}
	}

	private void unitTable() {
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setVisible(false);
			instructions.remove(i);
			i--;
		}
		
		slideTitle.setText("UNIT TABLE");
		
		String[] columnNames = { "Vehicle", "Air Strike", "Tank", "Infantry", "Marine", "Medic" };
		Object[][] data = { 
				{ "Vehicle", "Air Strike", "Tank", "Infantry", "Marine", "Medic" },
				{ "Speed", 5, 1, 2, 4, 1 },
				{ "Strength", 40, 7, 3, 1, "n/a" }, { "Health", 40, 10, 7, 2, 7 },
				{ "Cost", Economy.AIRSTRIKECOST, Economy.TANKCOST, Economy.INFANTRYCOST, Economy.SCOUTCOST,
						Economy.MEDICCOST },
				{ "Reward", 10, 5, 3, 1, 4 }};
		
		units = new JTable(data, columnNames);
		units.setBounds(this.getWidth() / 2 - units.getWidth() / 2 - 625, slideTitle.getHeight() + 50, this.getWidth() - 200, this.getHeight()- 450);
		units.setFont(font3);
		units.setRowHeight(45);
		for(int i = 0; i < data.length; i++){
			units.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		extra = new JLabel("Speed: Number of slices moved per turn");
		extra2 = new JLabel("Medics heal their own team by 2 each turn");
		extra.setBounds(50, this.getHeight()- 190, 500, 40);
		extra2.setBounds(50, this.getHeight()- 230, 500, 40);
		units.setVisible(true);
		extra.setVisible(true);
		extra2.setVisible(true);
		extra.setFont(font2);
		extra2.setFont(font2);
		add(extra);
		add(extra2);
		add(units);
	}

	private void shortcuts() {
		units.setVisible(false);
		
		slideTitle.setText("KEYBOARD SHORTCUTS");
		
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
				{ "Scroll", "A and D or Left and Right Arrow Keys" }};
		
		KeyShorts = new JTable(data, columnNames);
		KeyShorts.setSize(800, 160);
		KeyShorts.setFont(font2);
		KeyShorts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		KeyShorts.setLocation(this.getWidth() / 2 - KeyShorts.getWidth() / 2, slideTitle.getHeight() + 50);
		KeyShorts.setVisible(true);
		add(KeyShorts);
	}
}
