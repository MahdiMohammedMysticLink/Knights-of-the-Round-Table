package com.COMP3004.Maven.TableDeck;

import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Story.eventCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;

public class storyDeckFactory {

	public storyDeckFactory() {}
	
	public List<storyCard> buildDeck(){
		List<storyCard> deck = new ArrayList<storyCard>();
		
		// Quests
		deck.add(new questCard("Search for the Holy Grail", 5, ""));
		deck.add(new questCard("Test of the Green Knight", 4, "Green Knight"));
		deck.add(new questCard("Search for the Questing Beast", 4, "NULL"));
		deck.add(new questCard("Defend the Queen's Honor", 4, ""));
		deck.add(new questCard("Rescue the Fair Maiden", 3, "Black"));
		deck.add(new questCard("Journey through the Enchanted Forest", 3, "Evil"));
		deck.add(new questCard("Vanquish King Arthur's Enemies", 3, "NULL"));
		deck.add(new questCard("Vanquish King Arthur's Enemies", 3, "NULL"));
		deck.add(new questCard("Slay the Dragon", 3, "Dragon"));
		deck.add(new questCard("Boar Hunt", 2, "Boar"));
		deck.add(new questCard("Boar Hunt", 2, "Boar"));
		deck.add(new questCard("Repel the Saxon Raiders", 2, "Saxon"));
		deck.add(new questCard("Repel the Saxon Raiders", 2, "Saxon"));
		Model.log("Quests cards added to deck", "storyDeckFactory", "buildDeck");

		// Events
		deck.add(new eventCard("King's Recognition"));
		deck.add(new eventCard("King's Recognition"));
		deck.add(new eventCard("Queen's Favor"));
		deck.add(new eventCard("Queen's Favor"));
		deck.add(new eventCard("Court Called Camelot"));
		deck.add(new eventCard("Court Called Camelot"));
		deck.add(new eventCard("Pox"));
		deck.add(new eventCard("Plague"));
		deck.add(new eventCard("Chivalrous Deed"));
		deck.add(new eventCard("Prosperity Throughout the Realm"));
		deck.add(new eventCard("King's Call to Arms"));
		Model.log("Events cards added to deck", "storyDeckFactory", "buildDeck");

		// Tournaments
		deck.add(new tournamentCard("Camelot", 3));
		deck.add(new tournamentCard("Orkney", 2));
		deck.add(new tournamentCard("Tintagel", 1));
		deck.add(new tournamentCard("York", 0));
		Model.log("Tournaments cards added to deck", "storyDeckFactory", "buildDeck");
		
		return deck;
		}
	
	public List<storyCard> buildDeck(int scen){
		List<storyCard> deck = new ArrayList<storyCard>();
		
		switch(scen) {
		case 1:
			deck.add(new questCard("Search for the Holy Grail", 5, ""));
			deck.add(new questCard("Test of the Green Knight", 4, "Green Knight"));
			deck.add(new questCard("Search for the Questing Beast", 4, "NULL"));
			deck.add(new questCard("Defend the Queen's Honor", 4, ""));
			deck.add(new questCard("Rescue the Fair Maiden", 3, "Black Knight"));
			deck.add(new questCard("Journey through the Enchanted Forest", 3, "Evil Knight"));
			deck.add(new questCard("Vanquish King Arthur's Enemies", 3, "NULL"));
			deck.add(new questCard("Vanquish King Arthur's Enemies", 3, "NULL"));

			deck.add(new questCard("Slay the Dragon", 3, "Dragon"));
			deck.add(new questCard("Boar Hunt", 2, "Boar"));
			deck.add(new questCard("Boar Hunt", 2, "Boar"));
			deck.add(new questCard("Repel the Saxon Raiders", 2, "Saxon"));
			deck.add(new questCard("Repel the Saxon Raiders", 2, "Saxon"));

			deck.add(new questCard("Boar Hunt", 2, "Boar"));
			deck.add(new questCard("Boar Hunt", 2, "Boar"));

			deck.add(new eventCard("Chivalrous Deed"));
			deck.add(new eventCard("Prosperity Throughout the Realm"));
		}
		
		return deck;
		}
}
