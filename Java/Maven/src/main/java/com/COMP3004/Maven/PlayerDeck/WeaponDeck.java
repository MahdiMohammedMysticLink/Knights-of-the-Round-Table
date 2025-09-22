
package com.COMP3004.Maven.PlayerDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Adventure.weaponCard;

public class WeaponDeck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
	private List<weaponCard> deck;

	public WeaponDeck() {
		// TODO Auto-generated constructor stub
		size = 0;
		deck = new ArrayList<weaponCard>();
	}

	public int getSize() {
		return size;
	}

	public void add(weaponCard c) {
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

	public weaponCard get(int index) {
		if (index >= size) {
			return null;
		}
		return (deck.get(index));
	}

	public weaponCard find(String n) {
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

	public List<weaponCard> toList() {
		return deck;
	}

	public int calculatePoints() {
		int total = 0;

		for (int i = 0; i < size; i++)

		{
			weaponCard weapon = deck.get(i);
			
			Model.log(weapon.getName()+ " contributed:" + weapon.getPoints(), "WeaponDeck", "calculatePoints" );
			total += weapon.getPoints();
		}
		
		Model.log("Weapons contributed : " +total + " points", "WeaponDeck", "calculatePoints" );
	
		return total;
	}

	public int calculateHighestPoints(int amount) {

		int total = 0;

		int counter = 0;

		for (int i = size; i >= size - amount; i--) {

			counter++;

			weaponCard weapon = deck.get(i);

			if (counter >= size) {
				return total;
			}

			total += weapon.getPoints();

		}

		return total;
	}

	public void removeHighestPointsCards(int amount) {

		int counter = 0;

		for (int i = size; i >= size - amount; i--) {

			counter++;
			if (counter >= size) {
				return;
			}

			deck.remove(i);

		}

	}

	public int calculateLowestsPoints(int amount) {

		int total = 0;

		for (int i = 0; i < amount; i++) {

			weaponCard weapon = deck.get(i);

			if (i >= size) {

				return total;
			}

			total += weapon.getPoints();

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
			weaponCard[] tempDeck = new weaponCard[size];
			deck.toArray(tempDeck);
			Arrays.sort(tempDeck);

			deck = new ArrayList<weaponCard>(Arrays.asList(tempDeck));
		}
	}
	
	public void sortHighest() {

		sortLowest();
		if (size > 0) {
			weaponCard[] reverse = new weaponCard[size];
			for(int i  = 0; i < size; i++)
			{
				reverse[i] = deck.get(size - 1 - i);
			}
			deck = new ArrayList<weaponCard>(Arrays.asList(reverse));
		}
		
	}
	
	public void sortLowest() {sort();}
}
