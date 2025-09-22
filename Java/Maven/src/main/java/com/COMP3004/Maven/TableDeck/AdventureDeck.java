package com.COMP3004.Maven.TableDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Adventure.*;
import com.COMP3004.Maven.TableDeck.adventureDeckFactory;

public class AdventureDeck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<adventureCard> deck;
	adventureDeckFactory  factory = new adventureDeckFactory();
	List<adventureCard> discardPile;

	// Constructor
	public AdventureDeck() {
		Model.log("initializing a new adventure deck", "AdventureDeck", "AdventureDeck");	
		deck = factory.buildDeck();
		discardPile = new ArrayList<adventureCard>();
		shuffleCards();
	}

	public AdventureDeck(int scen) {
		Model.log("initializing a new adventure deck with scenario " + scen , "AdventureDeck", "AdventureDeck");

		deck = factory.buildDeck(scen);
		discardPile = new ArrayList<adventureCard>();
		shuffleCards();
	}

	// Getters
	public int size() {
		return deck.size();
	}

	public adventureCard get(int index) {
		return deck.get(index);
	}

	public adventureCard get() {
		return deck.get(0);
	}

	public adventureCard find(String name) {

		
		
		
		
		
		
		for (int i = 0; i < deck.size(); i++) {

			
			System.out.println("passed in: " + name);
			
			System.out.println("name in deck: " + deck.get(i).getName());
			
			if (deck.get(i).getName().equals(name)) {

				return deck.get(i);

			}

		}
		return null;

	}

	public void remove(int index) {
		deck.remove(index);
	}
	public void remove(adventureCard c) {
		
		deck.remove(c);
	}
	public void remove() {
		deck.remove(0);
	}

	// Methods

	public void shuffleCards() {
		String currCards = "";
		for(int i = 0; i < deck.size(); i++) {
			currCards = currCards + deck.get(i).getName() + "\n";
		}
		Model.log("shuffling cards, the deck was:\n" + currCards, "AdventureDeck", "shuffleCards");
		Collections.shuffle(deck);
		currCards = "";
		for(int i = 0; i < deck.size(); i++) {
			currCards = currCards + deck.get(i).getName() + "\n";
		}
		Model.log("the deck is now:\n" + currCards, "AdventureDeck", "shuffleCards");
		
	}
	
	public void removeFromDiscardPile(adventureCard c) {
		
		Model.log("removing " + c.getName() + " to the discard pile", "AdventureDeck", "addToDiscardPile");
		discardPile.remove(c);
	}

	public adventureCard dealCard() // Deal the card from the front/top
	{
		
        		
		if (deck.size()==0) {
			
			
			Model.log("out of cards, now getting and shuffling discard pile", "AdventureDeck", "dealCard");
			
			
			deck = discardPile;
			shuffleCards();
			
			discardPile = new ArrayList<adventureCard>();
		}
		adventureCard c = get();
		remove();
		

		
		Model.log("dealing the card " + c.getName(), "AdventureDeck", "dealCard");

		
		return c;
	}
	

	
	public void addToDiscardPile(adventureCard c) {
		Model.log("adding " + c.getName() + " to the discard pile", "AdventureDeck", "addToDiscardPile");

		
		discardPile.add(c);
	}
	
	public void add(adventureCard c) {
		Model.log("adding " + c.getName() + " to the deck", "AdventureDeck", "add");

		deck.add(c);

	}

	public boolean isFound(String n) {
		for (int i = 0; i < deck.size(); i++)

		{

			if ((deck.get(i)).getName().equals(n))

				return true;

		}

		return false;

	}

}
