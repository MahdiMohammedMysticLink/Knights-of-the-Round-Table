
package com.COMP3004.Maven.PlayerDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.COMP3004.Maven.Adventure.testCard;

public class TestDeck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
	private List<testCard> deck;

	public TestDeck() {
		// TODO Auto-generated constructor stub
		size = 0;
		deck = new ArrayList<testCard>();
	}

	public int getSize() {
		return size;
	}

	public void add(testCard c) {
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

	public testCard get(int index) {
		if (index >= size) {
			return null;
		}
		return (deck.get(index));
	}

	public testCard find(String n) {
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

	public List<testCard> toList() {
		return deck;
	}

	public int calculateBids() {
		int total = 0;

		for (int i = 0; i < size; i++)

		{
			testCard test = deck.get(i);
			total += test.getMinBid();
		}
		return total;
	}

	public int calculateHighestBids(int amount) {

		int total = 0;

		int counter = 0;

		for (int i = size; i >= size - amount; i--) {

			counter++;

			testCard test = deck.get(i);

			if (counter >= size) {
				return total;
			}

			total += test.getMinBid();

		}

		return total;
	}

	public void removeHighestBidCards(int amount) {

		int counter = 0;

		for (int i = size; i >= size - amount; i--) {

			counter++;
			if (counter >= size) {
				return;
			}

			deck.remove(i);

		}

	}

	public int calculateLowestsBids(int amount) {

		int total = 0;

		for (int i = 0; i < amount; i++) {

			testCard test = deck.get(i);

			if (i >= size) {

				return total;
			}

			total += test.getMinBid();

		}

		return total;
	}

	public void removeLowestsPointsCards(int amount) {

		for (int i = 0; i < amount; i++) {

			if (i >= size) {

				return;
			}

			deck.remove(i);

		}

	}

	public void sort() {
		if (size > 0) {
			testCard[] tempDeck = new testCard[size];
			deck.toArray(tempDeck);
			Arrays.sort(tempDeck);

			deck = new ArrayList<testCard>(Arrays.asList(tempDeck));
		}
	}
}
