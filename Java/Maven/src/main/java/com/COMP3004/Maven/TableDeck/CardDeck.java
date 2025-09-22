package com.COMP3004.Maven.TableDeck;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.COMP3004.Maven.Card;

public abstract class CardDeck {

	List<Card> deck;

	public CardDeck() {
		// TODO Auto-generated constructor stub

		@SuppressWarnings("unused")
		List<Card> deck = new ArrayList<Card>();
	}

	// Getters
	public int size() {
		return deck.size();
	}

	public Card get(int index) {
		return deck.get(index);
	}

	public Card get() {
		return get(0);
	}

	public Card find(String name) {

		for (int i = 0; i < deck.size(); i++) {

			if (deck.get(i).getName() == name) {

				return deck.get(i);

			}

		}
		return null;

	}

	public void add(Card c) {
		deck.add(c);
	}

	public void remove(int index) {
		deck.remove(index);
	}

	public void remove() {
		remove(0);
	}

	// Methods
	public abstract void init(int scen);// Initializes based on type of deck

	public void shuffleCards() {
		Collections.shuffle(deck);
	}

	public Card dealCard() // Deal the card from the front/top
	{

		Card c = get();
		remove();
		return c;
	}

}
