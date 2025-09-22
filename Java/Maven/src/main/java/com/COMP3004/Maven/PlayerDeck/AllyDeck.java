
package com.COMP3004.Maven.PlayerDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Adventure.allyCard;

public class AllyDeck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
	private List<allyCard> deck;

	public AllyDeck() {
		// TODO Auto-generated constructor stub
		size = 0;
		deck = new ArrayList<allyCard>();
	}

	public int getSize() {
		return size;
	}

	public void add(allyCard c) {
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

	public allyCard get(int index) {
		if (index >= size) {
			return null;
		}
		return (deck.get(index));
	}

	public allyCard find(String n) {
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

	public List<allyCard> toList() {
		return deck;
	}

	public void printCards() {

		for (int i = 0; i < deck.size(); i++) {

			Model.log(deck.get(i).getName(), "allyDeck.java", "printCards");

		}

	}

		
	public int calculateSpecialFeaturePoints(String special, boolean tristAndQueenInPlay) {
			
			
			
			//check if allies
			
			
			
			
		  	int total = 0;

		    for (int i = 0; i < size; i++)

	        {
	        	
	        	
	        	
	        	
	        	allyCard ally = deck.get(i);
	    		
	    		
	        	
	        	if (ally.getName().equals("Sir Tristan") && tristAndQueenInPlay) {

	    			Model.log("Sir Tristan benefited from Queen Iseult In Play with " + ally.getData2() + " points", "AllyDeck", "calculateSpecialFeaturePoints");
	    			
	    			total+=ally.getData2();
	 	        	
	 	
	 	    
	    		
	        	}
	        	else if (ally.getSpecialFeature().equals((special)) && ally.getData2Type().equals("POINTS")) {
	        
	        		Model.log(ally.getName() + " benefited from quest " + special +"with " + ally.getData2() + " points", "AllyDeck", "calculateSpecialFeaturePoints");
	        		total+=ally.getData2();
	        		
	        		
	        	}
	        	
	        	else if (ally.getData1Type().equals("POINTS") ) {
	        		Model.log(ally.getName() + "contributed " + ally.getData1() + " points", "AllyDeck", "calculateSpecialFeaturePoints");
	        		total+= ally.getData1();
	        	}
	        
	        	
	        }
	        
		   Model.log("Allies contributed " + total + " points", "AllyDeck", "calculateSpecialFeaturePoints"); 	
	        
	       return total;
	    
		}
		
		
		public int calculateSpecialBids(String special, boolean tristAndQueenInPlay) {
			//check if allies
		  	int total = 0;

	        for (int i = 0; i < size; i++)

	        {
	        	allyCard ally = deck.get(i);
	    		
	    		if (ally.getName().equals("King Pellinore")) {
	    			
	    			
	    			if (ally.getSpecialFeature().equals(special)) {
	    				
	    				Model.log("King Pellinore benefited from " + special+", contributed "+ ally.getData2() + " bids", "allyDeck", "calculateSpecialBids");
	    				
	    				total+= ally.getData2();
	    				
	    			}else {
	    				
	    				total+= 0; //asking proff how much it's supposed to be
	    				
	    			}
	    			
	    			
	    		}
	        	
	    		else if (ally.getName().equals("Queen Iseult") && tristAndQueenInPlay)  {

	    			Model.log("Queen Iseult benefited from Sir Tristan in play, contributed " + ally.getData2() +" bids", "allyDeck", "calculateSpecialBids");
	    			
	    			total+=ally.getData2();
	 	        	
	 	
	 	    
	        	
	        	}
	        	else if (ally.getSpecialFeature().equals(special) && ally.getData2Type().equals("BIDS")) {
	        
	        		Model.log(ally.getName() +" benefited from quest " + special + ", contributed " + ally.getData2() + " bids", "allyDeck", "calculateSpecialBids");
	        		total+=ally.getData2();
	        		
	        		
	        	}
	        	
	        	else if (ally.getData1Type().equals("BIDS") ) {
	        		Model.log(ally.getName() +" contributed " + ally.getData1() + " bids", "allyDeck", "calculateSpecialBids");
	        		total+= ally.getData1();
	        	}
	        
	        	else if (ally.getData2Type().equals("BIDS") ) {
	        		Model.log(ally.getName() +" contributed " + ally.getData2() + " bids", "allyDeck", "calculateSpecialBids");
	        		total+= ally.getData2();
	        	}
	        
	        	
	        }
	        
	       Model.log("Allies contributed " + total + " bids", "AllyDeck", "calculateSpecialBids"); 	
	       return total;
	    
	    
	}
		
	
	
	public int calculatePoints() {

		int total = 0;

		for (int i = 0; i < size; i++)

		{

			allyCard ally = deck.get(i);

			if (ally.getData1Type().equals("POINTS")) {

				total += ally.getData1();
				Model.log("Allies data1 type " + total + " points", "AllyDeck", "calculatePoints");
			}

			else if (ally.getData2Type().equals( "POINTS")) {

				total += ally.getData2();
				Model.log("Allies data2 type " + total + " points", "AllyDeck", "calculatePoints");
			}

		}
		return total;

	}

	public int calculateHighestPoints(int amount) {

		int total = 0;

		int counter = 0;

		for (int i = size; i >= size - amount; i--) {

			counter++;
			if (counter >= size) {
				return total;
			}

			allyCard ally = deck.get(i);

			if (ally.getData1Type().equals("POINTS")) {

				total += ally.getData1();
				Model.log("Allies data1 type " + total + " points", "AllyDeck", "calculateHighestPoints");
			}

			else if (ally.getData2Type().equals("POINTS")) {

				total += ally.getData2();
				Model.log("Allies data2 type " + total + " points", "AllyDeck", "calculateHighestPoints");
			}

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

			allyCard ally = (allyCard) deck.get(i);

			if (ally.getData1Type().equals("POINTS")) {

				deck.remove(i);
			}

			else if (ally.getData2Type().equals("POINTS")) {

				deck.remove(i);
			}

		}

	}

	public int calculateLowestsPoints(int amount) {

		int total = 0;

		for (int i = 0; i < amount; i++) {

			if (i >= size) {

				return total;
			}

			allyCard ally = (allyCard) deck.get(i);

			if (ally.getData1Type().equals("POINTS")) {

				total += ally.getData1();
				Model.log("Allies data1 type " + total + " points", "AllyDeck", "calculateLowestsPoints");
			}

			else if (ally.getData2Type().equals("POINTS")) {

				total += ally.getData2();
				Model.log("Allies data2 type " + total + " points", "AllyDeck", "calculateLowestsPoints");
			}
		}

		return total;
	}

	public int calculateBids() {

		int total = 0;

		for (int i = 0; i < size; i++)

		{
			allyCard ally = (allyCard) deck.get(i);

			if (ally.getData1Type().equals("BIDS")) {

				total += ally.getData1();
				Model.log("Allies data1 type " + total + " bids", "AllyDeck", "calculateBids");
			}

			else if (ally.getData2Type().equals("BIDS")) {

				total += ally.getData2();
				Model.log("Allies data2 type " + total + " bids", "AllyDeck", "calculateBids");
			}

		}
		return total;

	}

	public void sort() {

		if (size > 0) {
			allyCard[] tempDeck = new allyCard[size];

			deck.toArray(tempDeck);
			Arrays.sort(tempDeck);

			deck = new ArrayList<allyCard>(Arrays.asList(tempDeck));
		}

	}

}
