package com.COMP3004.Maven.TableDeck;

import java.util.Collections;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Story.*;
import com.COMP3004.Maven.TableDeck.storyDeckFactory;

public class StoryDeck {

	List<storyCard> deck;
	storyDeckFactory factory = new storyDeckFactory();
	
	
	public int scen;
	
	public StoryDeck() {
		// TODO Auto-generated constructor stub
		Model.log("initializing", "StoryDeck", "StoryDeck");
		deck = factory.buildDeck();
		shuffleCards();
	}

	public StoryDeck(int scen) {
		// TODO Auto-generated constructor stub
		Model.log("initializing for scenario: " + scen, "StoryDeck", "StoryDeck");
		
		this.scen = scen;
		deck = factory.buildDeck(scen);
		shuffleCards();
	}

	// Getters
	public int size() {
		return deck.size();
	}

	public storyCard get(int index) {
		return deck.get(index);
	}

	public storyCard get() {
		return get(0);
	}

	public storyCard find(String name) {

		for (int i = 0; i < deck.size(); i++) {

			if (deck.get(i).getName() == name) {

				return deck.get(i);

			}

		}
		return null;

	}

	public void add(storyCard c) {
		deck.add(c);
	}

	public void remove(int index) {
		deck.remove(index);
	}

	public void remove() {
		remove(0);
	}

	
	public void remove(storyCard c) {
		deck.remove(c);
	}
	// Methods

	public void shuffleCards() {
		String currCards = "";
		for(int i = 0; i < deck.size(); i++) {
			currCards = currCards + deck.get(i).getName() + "\n";
		}
		Model.log("shuffling cards, the deck was:\n" + currCards, "StoryDeck", "shuffleCards");
		Collections.shuffle(deck);
		currCards = "";
		for(int i = 0; i < deck.size(); i++) {
			currCards = currCards + deck.get(i).getName() + "\n";
		}
		Model.log("the deck is now:\n" + currCards, "StoryDeck", "shuffleCards");
	}

	public storyCard dealCard() // Deal the card from the front/top
	{

		storyCard c = get();
		remove();
		Model.log("dealing a card: " + c.getName(), "StoryDeck", "dealCard");

		return c;
	}

	public storyCard draw() {
		
		
		if (deck.size()<=0) {
			Model.log("Reinitializing deck", "StoryDeck", "draw");
			factory.buildDeck(scen);
			shuffleCards();
		}
		storyCard c = get();
		remove();
		
		Model.log("drawing a card: " + c.getName(), "StoryDeck", "draw");

		return c;
	}

}
