package com.COMP3004.Maven.TableDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.Adventure.allyCard;
import com.COMP3004.Maven.Adventure.amourCard;
import com.COMP3004.Maven.Adventure.foeCard;
import com.COMP3004.Maven.Adventure.testCard;
import com.COMP3004.Maven.Adventure.weaponCard;

public class adventureDeckFactory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<adventureCard> buildDeck() {
		List<adventureCard> deck = new ArrayList<adventureCard>();
		
		//Weapons
		deck.add(new weaponCard("Excalibur", 30));
		deck.add(new weaponCard("Excalibur", 30));
		for (int i = 0; i < 10; i++) {deck.add(new weaponCard("Battle-ax", 15));}
		for (int i = 0; i < 6; i++) {deck.add(new weaponCard("Dagger", 5));}
		for (int i = 0; i < 11; i++) {deck.add(new weaponCard("Horse", 10));}
		for (int i = 0; i < 16; i++) {deck.add(new weaponCard("Sword", 10));}
		for (int i = 0; i < 6; i++) {deck.add(new weaponCard("Lance", 20));}
			
		//Foes
		deck.add(new foeCard("Dragon", 50, 70));
		deck.add(new foeCard("Giant", 40));
		deck.add(new foeCard("Giant", 40));
		for (int i = 0; i < 4; i++) {deck.add(new foeCard("Mordred", 30));}
		for (int i = 0; i < 8; i++) {deck.add(new foeCard("Thieves", 5));}
		for (int i = 0; i < 6; i++) {deck.add(new foeCard("Evil Knight", 20, 30));}
		for (int i = 0; i < 4; i++) {deck.add(new foeCard("Boar", 5, 15));}
		for (int i = 0; i < 7; i++) {deck.add(new foeCard("Robber Knight", 15));}
		deck.add(new foeCard("Black Knight", 25, 35));
		deck.add(new foeCard("Black Knight", 25, 35));
		deck.add(new foeCard("Black Knight", 25, 35));
		deck.add(new foeCard("Green Knight", 25, 40));
		deck.add(new foeCard("Green Knight", 25, 40));
		for (int i = 0; i < 8; i++) {deck.add(new foeCard("Saxon Knight", 15, 25));}
		for (int i = 0; i < 5; i++) {deck.add(new foeCard("Saxons", 10, 20));}
		
		//Allies
		deck.add(new allyCard("Sir Gawain", 10,20, "POINTS","POINTS", "Test of the Green Knight"));
		deck.add(new allyCard("King Pellinore", 10, 4, "POINTS", "BIDS", "Search for the Questing Beast"));
		deck.add(new allyCard("Sir Percival", 5, 20, "POINTS", "POINTS", "Search for the Holy Grail"));
		deck.add(new allyCard("Sir Tristan", 10 , 20, "POINTS", "POINTS", "Queen Iseult"));
		deck.add(new allyCard("King Arthur", 10 , 2, "POINTS", "BIDS", "NULL"));
		deck.add(new allyCard("Queen Guinevere", 3 , 0, "BIDS", "NULL", "NULL"));
		deck.add(new allyCard("Queen Iseult", 2, 4,"BIDS", "BIDS", "Sir Tristan" ));
		deck.add(new allyCard("Sir Lancelot", 15, 25, "POINTS", "POINTS", "Defend the Queen's Honor"));
		deck.add(new allyCard("Sir Galahad", 15, 0, "POINTS", "NULL","NULL"));

		//Amours
		for (int i = 0; i < 8; i++) {deck.add(new amourCard());}
		
		//Tests
		deck.add(new testCard("Test of the Questing Beast", 4));
		deck.add(new testCard("Test of the Questing Beast", 4));
		deck.add(new testCard("Test of Temptation"));
		deck.add(new testCard("Test of Temptation"));
		deck.add(new testCard("Test of Valor"));
		deck.add(new testCard("Test of Valor"));
		deck.add(new testCard("Test of Morgan Le Fey", 3));
		deck.add(new testCard("Test of Morgan Le Fey", 3));
		
		return deck;
	}
	
	public List<adventureCard> buildDeck(int scen) { //Can use if-statement for different scenarios when required
		List<adventureCard> deck = new ArrayList<adventureCard>();
		
		//Weapons
		deck.add(new weaponCard("Excalibur", 30));
		deck.add(new weaponCard("Excalibur", 30));
		for (int i = 0; i < 10; i++) {deck.add(new weaponCard("Battle-ax", 15));}
		for (int i = 0; i < 6; i++) {deck.add(new weaponCard("Dagger", 5));}
		for (int i = 0; i < 11; i++) {deck.add(new weaponCard("Horse", 10));}
		for (int i = 0; i < 16; i++) {deck.add(new weaponCard("Sword", 10));}
		for (int i = 0; i < 6; i++) {deck.add(new weaponCard("Lance", 20));}
		Model.log("Weapons deck cards", "adventureDeckFactory", "buildDeck");
			
		//Foes
		deck.add(new foeCard("Dragon", 50, 70));
		deck.add(new foeCard("Giant", 40));
		deck.add(new foeCard("Giant", 40));
		for (int i = 0; i < 4; i++) {deck.add(new foeCard("Mordred", 30));}
		for (int i = 0; i < 8; i++) {deck.add(new foeCard("Thieves", 5));}
		for (int i = 0; i < 6; i++) {deck.add(new foeCard("Evil Knight", 20, 30));}
		for (int i = 0; i < 4; i++) {deck.add(new foeCard("Boar", 5, 15));}
		for (int i = 0; i < 7; i++) {deck.add(new foeCard("Robber Knight", 15));}
		deck.add(new foeCard("Black Knight", 25, 35));
		deck.add(new foeCard("Black Knight", 25, 35));
		deck.add(new foeCard("Black Knight", 25, 35));
		deck.add(new foeCard("Green Knight", 25, 40));
		deck.add(new foeCard("Green Knight", 25, 40));
		for (int i = 0; i < 8; i++) {deck.add(new foeCard("Saxon Knight", 15, 25));}
		for (int i = 0; i < 5; i++) {deck.add(new foeCard("Saxons", 10, 20));}
		Model.log("Foes deck cards", "adventureDeckFactory", "buildDeck");
		
		//Allies
		deck.add(new allyCard("Sir Gawain", 10,20, "POINTS","POINTS", "Test of the Green Knight"));
		deck.add(new allyCard("King Pellinore", 10, 4, "POINTS", "BIDS", "Search for the Questing Beast"));
		deck.add(new allyCard("Sir Percival", 5, 20, "POINTS", "POINTS", "Search for the Holy Grail"));
		deck.add(new allyCard("Sir Tristan", 10 , 20, "POINTS", "POINTS", "Queen Iseult"));
		deck.add(new allyCard("King Arthur", 10 , 2, "POINTS", "BIDS", "NULL"));
		deck.add(new allyCard("Queen Guinevere", 3 , 0, "BIDS", "NULL", "NULL"));
		deck.add(new allyCard("Queen Iseult", 2, 4,"BIDS", "BIDS", "Sir Tristan" ));
		deck.add(new allyCard("Sir Lancelot", 15, 25, "POINTS", "POINTS", "Defend the Queen's Honor"));
		deck.add(new allyCard("Sir Galahad", 15, 0, "POINTS", "NULL","NULL"));
		Model.log("Allies deck cards", "adventureDeckFactory", "buildDeck");

		//Amours
		for (int i = 0; i < 8; i++) {deck.add(new amourCard());}
		
		//Tests
		deck.add(new testCard("Test of the Questing Beast", 4));
		deck.add(new testCard("Test of the Questing Beast", 4));
		deck.add(new testCard("Test of Temptation"));
		deck.add(new testCard("Test of Temptation"));
		deck.add(new testCard("Test of Valor"));
		deck.add(new testCard("Test of Valor"));
		deck.add(new testCard("Test of Morgan Le Fey", 3));
		deck.add(new testCard("Test of Morgan Le Fey", 3));
		Model.log("Test deck cards", "adventureDeckFactory", "buildDeck");
		return deck;
	}

}
