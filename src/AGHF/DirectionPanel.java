package AGHF;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DirectionPanel extends JPanel {
	private static final long serialVersionUID = -2489201723379904949L;
	private int slide = 0;
	private JLabel slideTitle = new JLabel();
	private ArrayList<JLabel> instructions = new ArrayList<JLabel>();
	private JTable units;
	private JTable KeyShorts;
	private JLabel extra;
	private JLabel extra2;
	private Font font = new Font("Dialog", Font.BOLD | Font.HANGING_BASELINE, 100);
	private Font font2 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 20);
	private Font font3 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 35);
	private Font font4 = new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 25);
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
		slideTitle.setBounds(65, 20, this.getWidth(),140);
		slideTitle.setVisible(true);
		add(slideTitle);
		
		instructions.add(new JLabel());
		instructions.get(0).setText("The goal of the game is to destroy the base on the");
		instructions.add(new JLabel());
		instructions.get(1).setText("opposite side of the map as yours. This is accomplished");
		instructions.add(new JLabel());
		instructions.get(2).setText("by sending military units to war with the other player. ");
		instructions.add(new JLabel());
		instructions.get(3).setText("Diplomacy doesn't work, so don't try it.");
		
		//"The goal of the game is to destroy the base on the opposite side of the map as yours. /n This is accomplished by sending military units to war with the other player. /n Diplomacy doesn/'t work, so don/'t /try it.");
		for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).setFont(font3);
			instructions.get(i).setVisible(true);
			instructions.get(i).setBounds(255, slideTitle.getHeight() + 140 + 60*i,1000,40);
			add(instructions.get(i));
		}
		instructions.get(0).setBounds(this.getWidth() / 2 - instructions.get(0).getWidth() / 2 , 280,1000,40);
		
		
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
				{ "Strength", 40, 7, 3, 1, "n/a" }, 
				{ "Health", 40, 10, 7, 2, 7 },
				{ "Cost", Economy.AIRSTRIKECOST, Economy.TANKCOST, Economy.INFANTRYCOST, Economy.SCOUTCOST,
						Economy.MEDICCOST },
				{ "Reward", 10, 5, 3, 1, 4 }};
		
		units = new JTable(data, columnNames);
		units.setBounds((this.getWidth()/2 - units.getWidth()) / 2 -150, (this.getHeight()/2 - units.getHeight()) / 2, 1000, 275);
		units.setFont(new Font("Dialog", Font.PLAIN | Font.ROMAN_BASELINE, 25));
		units.setRowHeight(45);
		for(int i = 0; i < data.length; i++){
			units.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		extra = new JLabel("Speed: Number of slices moved per turn");
		extra2 = new JLabel("Medics heal their own team by 2 each turn");
		extra.setBounds(220, units.getHeight()*1 + units.getHeight()/2  + 50, 1000, 50);
		extra2.setBounds(220, units.getHeight()*1 + units.getHeight()/2 + 100, 1000, 50);
		units.setVisible(true);
		extra.setVisible(true);
		extra2.setVisible(true);
		extra.setFont(font4);
		extra2.setFont(font4);
		add(extra);
		add(extra2);
		add(units);
	}

	private void shortcuts() {
		remove(units);
		remove(extra);
		remove(extra2);
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
		KeyShorts.setBounds(this.getWidth() / 2 - units.getWidth() / 2 + 0 , slideTitle.getHeight() + 50, this.getWidth() - 495, this.getHeight()- 450);
		KeyShorts.setFont(font3);
		KeyShorts.setRowHeight(40);
		KeyShorts.getColumnModel().getColumn(0).setPreferredWidth(55);
		KeyShorts.getColumnModel().getColumn(1).setPreferredWidth(400);
		add(KeyShorts);
	}
}
