
package com.COMP3004.Maven.PlayerDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.COMP3004.Maven.Adventure.foeCard;
import com.COMP3004.Maven.Adventure.weaponCard;

public class FoeDeck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int size;
	List<foeCard> deck;

	public FoeDeck() {
		// TODO Auto-generated constructor stub
		size = 0;
		deck = new ArrayList<foeCard>();
	}

	public int getSize() {
		return size;
	}

	public void add(foeCard c) {
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

	public foeCard get(int index) {
		if (index >= size) {
			return null;
		}
		return (deck.get(index));
	}

	public foeCard find(String n) {
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

	public List<foeCard> toList() {
		return deck;
	}

	public int calculatePoints() {

		int total = 0;
		for (int i = 0; i < size; i++)

		{

			foeCard foe = deck.get(i);
			total += (foe.getStat(0));

		}

		return total;

	}

	public int calculateHighestPoints(int amount) {

		int total = 0;

		int counter = 0;

		for (int i = size; i >= size - amount; i--) {

			foeCard foe = deck.get(i);

			counter++;
			if (counter >= size) {
				return total;
			}

			total += (foe.getStat(0));

		}

		return total;
	}

	public int calculateQuest(String name) {

		int total = 0;
		for (int i = 0; i < size; i++) {

			foeCard foe = deck.get(i);

			if (foe.getName() == name && foe.getStat(1) > foe.getStat(0)) {

				total += (foe.getStat(1));
			} else {
				total += (foe.getStat(0));
			}
		}

		return total;

	}

	public int calculateFoePoints(String keyword) {
		// Presumably, when used there should only be one foe
		
		if (size<1) {
			
			
			return 0;
		}
		
		foeCard foe = deck.get(0);

		// If the foe is special to this quest
		if (foe.getName().contains(keyword)) {
			System.out.println(foe.getName() + " is special!");
			// Special to quest
			return foe.getStat(1);
		} else {
			System.out.println(foe.getName() + " is NOT special!");
			return foe.getStat(0);
		}
	}

	public int calculateLowestsPoints(int amount) {

		int total = 0;

		for (int i = 0; i < amount; i++) {

			foeCard foe = (foeCard) deck.get(i);

			if (i >= size) {

				return total;
			}

			total += foe.getStat(0);
		}

		return total;
	}

	public void sort() {
		if (size > 0) {
			foeCard[] tempDeck = new foeCard[size];
			deck.toArray(tempDeck);
			Arrays.sort(tempDeck);

			deck = new ArrayList<foeCard>(Arrays.asList(tempDeck));

		}

	}
	
	public void sortHighest() {
		sortLowest();
		if (size > 0) {
			foeCard[] reverse = new foeCard[size];
			for(int i  = 0; i < size; i++)
			{
				reverse[i] = deck.get(size - 1 - i);
			}
			deck = new ArrayList<foeCard>(Arrays.asList(reverse));
		}

	}
	
	public void sortLowest() {
		sort();
	}

}
