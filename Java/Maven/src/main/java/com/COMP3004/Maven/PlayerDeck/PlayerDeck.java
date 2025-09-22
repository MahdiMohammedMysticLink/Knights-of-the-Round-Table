package com.COMP3004.Maven.PlayerDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.Adventure.allyCard;
import com.COMP3004.Maven.Adventure.amourCard;
import com.COMP3004.Maven.Adventure.foeCard;
import com.COMP3004.Maven.Adventure.weaponCard;
import com.COMP3004.Maven.Adventure.testCard;

public class PlayerDeck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<adventureCard> deck;
	private FoeDeck foes;
	private WeaponDeck weapons;
	private AllyDeck allies;
	private TestDeck tests;
	private AmourDeck amours;

	public PlayerDeck() {
		deck = new ArrayList<adventureCard>();
		foes = new FoeDeck();
		weapons = new WeaponDeck();
		allies = new AllyDeck();
		tests = new TestDeck();
		amours = new AmourDeck();
	}
	
	// Getters
	public int getSize() {

		return deck.size();

	}

	public FoeDeck getFoes() {
		foes.sort();
		return foes;
	}

	public WeaponDeck getWeapons() {
		weapons.sort();
		return weapons;
	}

	public AmourDeck getAmours() {
		return amours;
	}

	public TestDeck getTests() {

		tests.sort();

		return tests;
	}

	public AllyDeck getAllies() {

		allies.sort();
		return allies;

	}

	// Methods
	public boolean isFound(String n) {
		for (int i = 0; i < deck.size(); i++)

		{

			if ((deck.get(i)).getName().equals(n)) {
				Model.log("Deck name found", "PlayerDeck", "isFound");	
				return true;
			}
		}
		Model.log("Deck name not found", "PlayerDeck", "isFound");	
		return false;

	}
	
	
	public boolean hasType(String t) {
		for (int i = 0; i < deck.size(); i++)

		{

			if ((deck.get(i)).getType().equals(t))

				return true;

		}

		return false;

	}


	public adventureCard find(String n)

	{

		for (int i = 0; i < deck.size(); i++)

		{

			if ((deck.get(i)).getName().equals(n))

				return deck.get(i);

		}

		return null;

	}

	public adventureCard get(int index)

	{

		if (index >= deck.size())

		{

			return null;

		}

		return (deck.get(index));

	}

	public int findIndex(String n)

	{

		for (int i = 0; i < deck.size(); i++)

		{

			if ((deck.get(i)).getName().equals(n))

				return i;

		}

		return -1;

	}

	public void add(adventureCard c)

	{
		Model.log("Adventure card type"+c.getType()+" added", "PlayerDeck", "add");	
		deck.add(c);

		if (c.getType().equals("FOE"))

		{

			foes.add((foeCard)c);
			
		}

		else if (c.getType().equals("WEAPON"))

		{

			weapons.add((weaponCard)c);

		}
		
		else if (c.getType().equals("AMOUR"))

		{

			amours.add((amourCard)c);

		}

		else if (c.getType().equals("ALLY"))

		{

			allies.add((allyCard)c);

		}

		else if (c.getType().equals("TEST"))

		{

			tests.add((testCard)c);

		}

		

	}

	public boolean remove(adventureCard c)

	{
		Model.log("Adventure card type"+c.getType()+" removed", "PlayerDeck", "remove");
		if (c.getType().equals("FOE"))

		{

			if (isFound(c.getName()))

			{

				int index = findIndex(c.getName());

				deck.remove(index);

			}

			return foes.remove(c.getName());

		}

		if (c.getType().equals("WEAPON"))

		{

			if (isFound(c.getName()))

			{

				int index = findIndex(c.getName());

				deck.remove(index);

			}

			return weapons.remove(c.getName());

		}

		if (c.getType().equals("ALLY"))

		{

			if (isFound(c.getName()))

			{

				int index = findIndex(c.getName());

				deck.remove(index);

			}

			return allies.remove(c.getName());

		}

		if (c.getType().equals("TEST"))

		{

			if (isFound(c.getName()))

			{

				int index = findIndex(c.getName());

				deck.remove(index);

			}

			return tests.remove(c.getName());

		}

		if (c.getType().equals("AMOUR"))

		{

			if (isFound(c.getName()))

			{

				int index = findIndex(c.getName());

				deck.remove(index);

			}

			return amours.remove(c.getName());

		}

		return false;

	}

	public boolean remove(String name)

	{

		adventureCard c = find(name);

		if (isFound(name))

		{

			deck.remove(findIndex(name));

		}

		if (c.getType().equals("FOE"))

		{

			return foes.remove(name);

		}

		else if (c.getType().equals("WEAPON"))

		{

			return weapons.remove(name);

		}

		else if (c.getType().equals("ALLY"))

		{

			return allies.remove(name);

		}

		else if (c.getType().equals("TEST"))

		{

			return tests.remove(name);

		}

		else if (c.getType().equals("AMOUR"))

		{

			return amours.remove(name);

		}

		return false;

	}

	public void removeExtraFoes() {

		int tempSize = foes.getSize();

		if (tempSize > 1) {

			FoeDeck tempFoes = new FoeDeck();

			for (int i = 1; i < tempSize; i++) {

				tempFoes.add(foes.get(i));

			}

			for (int i = 0; i < tempFoes.getSize(); i++) {

				remove(tempFoes.get(i).getName());

			}

		}

	}

	public void removeDuplicates() {

		if (getSize() < 2) {

			return;

		}

		sort();

		adventureCard previous = null;

		int duplicates = 0;

		for (int i = 0, j = 1; i < getSize() - 1; i++, j++) {

			previous = get(i);

			if (previous.getName() == get(j).getName())
				duplicates++;

		}

		int[] duplicateIndexes = new int[duplicates];

		int counter = 0;

		if (duplicates == 0) {

			return;
		}

		for (int i = 0, j = 1; i < getSize() - 1 && counter < duplicates; i++, j++) {

			previous = get(i);

			if (previous.getName() == get(j).getName()) {
				duplicateIndexes[counter] = j;

				counter++;

			}
		}

		List<adventureCard> tempDeck = new ArrayList<adventureCard>();

		for (int i = 0; i < duplicateIndexes.length; i++) {

			tempDeck.add(get(duplicateIndexes[i]));

		}

		for (int i = 0; i < duplicateIndexes.length; i++) {

			remove(tempDeck.get(i));

		}

	}

	public int calculatePoints() {

		int amourPoints = 0;
		if (amours.getSize() > 0) {

			amourPoints = 10;
		}
		int totalPoints = (amourPoints + foes.calculatePoints() + weapons.calculatePoints() + allies.calculatePoints());
		Model.log("Total foes, weapons and allies points: "+totalPoints, "PlayerDeck", "calculatePoints");
		return totalPoints;

	}

	public int calculatePointsWithoutFoes() {

		int amourPoints = 0;
		if (amours.getSize() > 0) {

			amourPoints = 10;
		}
		int totalPoints = (amourPoints + weapons.calculatePoints() + allies.calculatePoints());
		Model.log("Total weapons and allies points: "+totalPoints, "PlayerDeck", "calculatePointsWithoutFoes");
		return totalPoints;

	}

	public int calculatePointsWithoutFoesAndWithAllAmours() {
		int totalPoints = (10 * amours.getSize() + weapons.calculatePoints() + allies.calculatePoints());
		Model.log("Total amours, weapons and allies points: "+totalPoints, "PlayerDeck", "calculatePointsWithoutFoesAndWithAllAmours");
		return totalPoints;

	}

	public int calculatePointsWithAllAmours() {

		int amourPoints = 10 * (amours.getSize());
		int totalPoints = (amourPoints + foes.calculatePoints() + weapons.calculatePoints() + allies.calculatePoints());
		Model.log("Total amours, foes, weapons and allies points: "+totalPoints, "PlayerDeck", "calculatePointsWithAllAmours");
		return totalPoints;

	}

	public int calculatePointsWithoutAllies() {
		int totalPoints = (foes.calculatePoints() + weapons.calculatePoints());
		Model.log("Total weapons and foes points: "+totalPoints, "PlayerDeck", "calculatePointsWithoutAllies");
		return totalPoints;

	}
	
	public int calculateWeapons() {
		
		Model.log("Total weapons points: "+weapons.calculatePoints(), "PlayerDeck", "calculateWeapons");
		return (weapons.calculatePoints());
	}

	public int calculateBids() {

		int amourBids = 0;
		if (amours.getSize() > 0) {

			amourBids = 1;
		}

		return (allies.calculateBids() + amourBids);

	}

	public int calculateBidsWithAllAmours() {

		return (allies.calculateBids() + amours.getSize());

	}

	public int calculateQuest(String specialFoe) {

		return (weapons.calculatePoints() + foes.calculateFoePoints(specialFoe));

	}

	public void printCards() {

		for (int i = 0; i < deck.size(); i++) {

			Model.log(deck.get(i).getName(), "PlayerDeck.java", "printCards");

		}

	}

	public void sort() {

		if (getSize() > 0) {
			adventureCard[] tempDeck = new adventureCard[getSize()];
			deck.toArray(tempDeck);
			Arrays.sort(tempDeck);

			deck = new ArrayList<adventureCard>(Arrays.asList(tempDeck));
		}
	}

	public void shuffle() {

		Collections.shuffle(deck);

	}

	public int calculatePoints(boolean tristAndQueenInPlay) {
		
		int amourPoints = 0;
		if (amours.getSize()>0) {
			
			amourPoints = 10;
			
		}
		Model.log("amourPoints: "+amourPoints+", weapons points: "+weapons.calculatePoints()+", special feature points: "+allies.calculateSpecialFeaturePoints("Nothing", tristAndQueenInPlay), "PlayerDeck", "calculatePoints");	
		return (amourPoints + weapons.calculatePoints() + allies.calculateSpecialFeaturePoints("Nothing", tristAndQueenInPlay));
	}

}
