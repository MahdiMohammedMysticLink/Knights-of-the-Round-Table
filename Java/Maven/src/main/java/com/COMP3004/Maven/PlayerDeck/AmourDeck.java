package com.COMP3004.Maven.PlayerDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Adventure.amourCard;

public class AmourDeck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int size;
	List<amourCard> deck;

	public AmourDeck() {
		// TODO Auto-generated constructor stub
		size = 0;
		deck = new ArrayList<amourCard>();
	}

	public int getSize() {
		return size;
	}

	public void add(amourCard c) {
		deck.add(c);
		size++;
	}

	public boolean remove(String n) {
		if (isFound(n)) {
			deck.remove(findIndex(n));
			size--;
			return true;
		}
		return false;
	}

	public boolean isFound(String n)

	{
		for (int i = 0; i < size; i++) {
			if ((deck.get(i)).getName().equals(n))
				return true;
		}
		return false;
	}

	public amourCard get(int index) {
		if (index >= size) {
			return null;
		}
		return (deck.get(index));
	}

	public amourCard find(String n) {
		for (int i = 0; i < size; i++) {
			if ((deck.get(i)).getName().equals(n))
				return (deck.get(i));
		}
		return null;
	}

	public int findIndex(String n) {
		for (int i = 0; i < size; i++) {
			if ((deck.get(i)).getName().equals(n))
				return i;
		}
		return -1;
	}

	public List<amourCard> toList() {
		return deck;
	}

}
