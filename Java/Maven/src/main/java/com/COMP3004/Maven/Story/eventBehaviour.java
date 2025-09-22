package com.COMP3004.Maven.Story;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Player;
import com.COMP3004.Maven.PlayerDeck.AllyDeck;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

public class eventBehaviour implements storyBehaviour, Serializable {

	
	
	private static final long serialVersionUID = 1L;
	
	private eventCard event;
	
	
	private List<Player> players;

	public eventBehaviour(eventCard e) {
		event = e;
	}
	
	
	

	public void setPlayersFinishedTurnToFalse() {
		
		for (int i=0; i<players.size(); i++) {
			
			players.get(i).hasFinishedTurn = false;
			
		}
		
	}


	
	
	public void updateAllPlayers() {
		
		
		
		Model.log("Updating all players with information about all players", "eventBehaviour", "updateAllPlayers");
		
		for (int i= 0; i<players.size(); i++) {
			
			
			Model.log(players.get(i).getName()  +" is updated information about other players.", "eventBehaviour", "updateAllPlayers" );
			
			players.get(i).updateUI();
			
		}
		
		
		
	}
	
	public boolean playersDecksAreNotOverMax(List<Player> players) {
		
		
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).deck.getSize()> 12) {
				
				return false;
				
			}
			
			
			
		}
		
		return true;
		
		
	}
	
	public boolean allPlayersFinishedTurn(List<Player> players) {
		
		
		
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).hasFinishedTurn == false) {
				
				return false;
				
			}
			
			
			
		}
		
		return true;
		
		
		
	}

	public void play(Player curr, List<Player> players, AdventureDeck deck, int bonusShields) {
		
		this.players = players;
		List<Player> lowestList = new ArrayList<Player>();
		String name = event.getName();

		
		
	    Model.log("Event: " + event.getName(), "eventBehaviour " ,"play");
		// Chivalrous Deed
		if (name == "Chivalrous Deed") {
			// Make sure references are used
			List<Player> lowLowestList = new ArrayList<Player>();
			lowestList.add(players.get(0));
			 Model.log("Finding lowest ranked player(s) in session", "eventBehaviour" ,"play");
			// Find lowest ranked player(s) in session
			for (int i = 1; i < players.size(); i++) {
				// If the current player is of a lower rank than the one in the list
				/*
				 * Champion Knight Knight Squire
				 */
				
				Model.log("Comparing players", "eventBehaviour" ,"play");
				System.out.println(lowestList.get(0).rank.compareTo(players.get(i).rank));
				if (lowestList.get(0).rank.compareTo(players.get(i).rank) > 1) {
					System.out.println("lowestList cleared!");
					System.out.println(players.get(i).getName() + " added to lowestList!");
					Model.log("Clearing list of higher ranked players.", "eventBehaviour" ,"play");
					// Clear array of higher ranked players, add the lower ranked player
					lowestList.clear();
					lowestList.add(players.get(i));
					
					Model.log(players.get(i).getName() + " was added as a poorer player", "eventBehaviour" ,"play");
				}
				// If the current player is of the same rank than the one in the list
				else if (lowestList.get(0).rank.compareTo(players.get(i).rank) == 0) {
					System.out.println(players.get(i).getName() + " added to lowestList!");
					lowestList.add(players.get(i));
					
					Model.log(players.get(i).getName() + " was added as same rank as current player compared.", "eventBehaviour" ,"play");
				}
			}

			// Compare players with the lowest rank against shield count
			lowLowestList.add(lowestList.get(0));
			for (int i = 1; i < lowestList.size(); i++) {
				if (lowLowestList.get(0).getShields() > lowestList.get(i).getShields()) {
					// Clear array of richer players, add poorer player
					
					Model.log("Clearing list of higher ranked players.", "eventBehaviour" ,"play");
					lowLowestList.clear();
					Model.log(lowestList.get(i).getName() + " was added as a poorer player", "eventBehaviour" ,"play");
					lowLowestList.add(lowestList.get(i));
				}
				// If the current player is of the same wealth than the one in the list
				else if (lowLowestList.get(0).getShields() == lowestList.get(i).getShields()) {
					
					Model.log(lowestList.get(i).getName() + " was added as same rank as current player compared.", "eventBehaviour" ,"play");
					lowLowestList.add(lowestList.get(i));
				}
			}

			// Award 3 shields to each player with the lowest rank and shield count
			for (int i = 0; i < lowLowestList.size(); i++) {
				
				
				
				
				System.out.println(lowLowestList.get(i).getName() + " won the event!");
				lowLowestList.get(i).addShields(3);
				Model.log(lowLowestList.get(i).getName() + " won the event. 3 Shields added.", "eventBehaviour" ,"play");
				
			}
			updateAllPlayers();
			setPlayersFinishedTurnToFalse();
			
			
			Model.log("Players are being displayed the result event " + name, "eventBehaviour", "play" );
			
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter, name, "Won event "+ name + " and gained 3 shields",   lowLowestList);
				}
				
			}
				
			counter =1;
			
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
			
			}while(allPlayersFinishedTurn(this.players) == false);

		}
		// Pox
		else if (name == "Pox") {
			// All players except the drawing player loses one shield
			Model.log("All players except " + curr.getName() + " lost a shield", "eventBehaviour" ,"play");
			List<Player> loosers = new ArrayList<Player>();
			for (int i = 0; i < players.size(); i++) {
				System.out.println(players.get(i).getName() + " lost a shield!");
				if (players.get(i) != curr)
					players.get(i).addShields(-1);
					
				
				
				
				loosers.add(players.get(i));
			}
			
			updateAllPlayers();
			setPlayersFinishedTurnToFalse();
			
			
			Model.log("Players are being displayed the result event " + name, "eventBehaviour", "play" );
			
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter,name, "Got the pox and lost one shield", loosers);
				}
				
			}
				
			counter =1;
			
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
			
			}while(allPlayersFinishedTurn(this.players)==false);
			
			
			
			
			
			
			
		}
		// Plague
		else if (name == "Plague") {
			
			Model.log(curr.getName() + " lost 2 shields", "eventBehaviour" ,"play");
			
			System.out.println(curr.getName() + " lost 2 shields!");

			// Drawer loses
			curr.addShields(-2);
			
			List<Player> currentPlayer = new ArrayList<Player>();
			
			currentPlayer.add(curr);
			
			updateAllPlayers();
			setPlayersFinishedTurnToFalse();
			
			
			Model.log("Players are being displayed the result event " + name, "eventBehaviour", "play" );
			
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter,name, "Got the Plague and lost 2 shields", currentPlayer);
				}
				
			}
				
			counter =1;
			
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
			
			}while(allPlayersFinishedTurn(this.players) == false);
			
			
			
			
			
			
			
			
		}
		// King's Recognition
		else if (name == "King's Recognition") {
			System.out.println("There is an additional bonus of 2 shields added to the next quest!");
			Model.log("2 bonus shields added to next quest", "eventBehaviour" ,"play");
			
			bonusShields += 2;
			

			updateAllPlayers();
			setPlayersFinishedTurnToFalse();
			Model.log("Players are being displayed the result event " + name, "eventBehaviour", "play" );
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter,name, "King's Recognition. There is an additional bonus of 2 shields added to the next quest!", players);
				}
				
			}
				
			counter =1;
			
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
			
			}while(allPlayersFinishedTurn(this.players)==false);
			
			
			
			
		}
		// Queen's Favor
		else if (name == "Queen's Favor") {
			lowestList.add(players.get(0));
			Model.log("Finding lowest ranked player(s) in session", "eventBehaviour" ,"play");
			for (int i = 1; i < players.size(); i++) {
				
				Model.log("Comparing players", "eventBehaviour" ,"play");
				if (lowestList.get(0).rank.compareTo(players.get(i).rank) > 1) {
					System.out.println("lowestList cleared!");
					System.out.println(players.get(i).getName() + " added to lowestList!");
					Model.log("Clearing list of higher ranked players.", "eventBehaviour" ,"play");
					// Clear list then add
					lowestList.clear();
					lowestList.add(players.get(i));
					
					Model.log(players.get(i).getName() + " was added as a poorer player", "eventBehaviour" ,"play");
				} else if (lowestList.get(0).rank.compareTo(players.get(i).rank) == 0) {
					System.out.println(players.get(i).getName() + " added to lowestList!");
					
					Model.log(players.get(i).getName() + " was added as same rank as current player compared.", "eventBehaviour" ,"play");
					lowestList.add(players.get(i));
				}
			}

			// Players with the lowest rank immediately draws two Adventure Cards
			for (int i = 0; i < lowestList.size(); i++) {
				System.out.println(lowestList.get(i).getName() + " awarded 2 cards!");
				
				Model.log(lowestList.get(i).getName() + " won the event. Awared 2 cards.", "eventBehaviour" ,"play");
				lowestList.get(i).addCard(deck.dealCard());
				lowestList.get(i).addCard(deck.dealCard());
				
			}
			

			updateAllPlayers();
			setPlayersFinishedTurnToFalse();
		
			Model.log("Players are being displayed the result event " + name, "eventBehaviour", "play" );
			
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter,name, "Won event Queen's Favor and got awarded 2 cards!",   lowestList);
				}
				
			}
				
			counter =1;
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
			}while(allPlayersFinishedTurn(this.players) == false);
			
			
			counter=0;
			setPlayersFinishedTurnToFalse();
			
			 boolean haveToDiscard = !playersDecksAreNotOverMax(lowestList);
			
		
			
			
			
			do {
			
			for (int i = 0; i < lowestList.size(); i++) {
				
				if(lowestList.get(i).deck.getSize()>12){
					
					int amount = lowestList.get(i).deck.getSize() - 12;
					if (lowestList.get(i).hasFinishedTurn) {
						
						lowestList.get(i).decideCardsToDiscard(0, amount);
						
					}else {
					lowestList.get(i).decideCardsToDiscard(counter, amount);
					}
				}
				
				else {
					
					
					lowestList.get(i).hasFinishedTurn = true;
				}
			}
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	counter =1;
			}while(haveToDiscard && playersDecksAreNotOverMax(lowestList) == false && allPlayersFinishedTurn(lowestList) == false);
			
			
			updateAllPlayers();
		}
		
	
		
		
		// Court Called to Camelot **NEED A WAY TO DISCARD ALL ALLIES AND UPDATE UI
		else if (name == "Court Called Camelot") {
			// All allies are discarded from each player's deck, essentially get new empty
			// decks
			Model.log("All allies are discarded", "eventBehaviour" ,"play");
			
			for (int i = 0; i < players.size(); i++) {
				
				if (players.get(i).alliesInPlay.getSize()>1) {
					
					
					Model.log(players.get(i).getName() + " lost his allies.","eventBehaviour" ,"play");
				}
				
				
				for (int j=0; j < players.get(i).alliesInPlay.getSize(); j++) {
					
					deck.addToDiscardPile(players.get(i).alliesInPlay.get(j));
					
				}
				
				
				players.get(i).alliesInPlay = new AllyDeck();
				
			
			}
			
			
			
			
			updateAllPlayers();
			setPlayersFinishedTurnToFalse();
			
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter,name, "Court Called to Camelot, all Allies lost!", players);
				}
				
			}
				
			counter =1;
			
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
			
			}while(allPlayersFinishedTurn(this.players)==false);
			
			
		}
		// King's Call to Arms **NEED DISCARD OF CARDS
		/*
		 * else if (name == "King's Call to Arms") { List<Player> highestList = new
		 * ArrayList<Player>();
		 * 
		 * highestList.add(players.get(0));
		 * 
		 * //Find highest ranked player(s) in session for (int i = 1; i <
		 * players.size(); i++) { if
		 * (highestList.get(0).rank.compareTo(players.get(i).rank) < 0) { //Clear then
		 * add highestList.clear(); highestList.add(players.get(i)); } else if
		 * (highestList.get(0).rank.compareTo(players.get(i).rank) == 0) {
		 * highestList.add(players.get(i)); } }
		 * 
		 * for (int i = 0; i < highestList.size(); i++) { //Force player to discard
		 * Weapon Card, player.weaponCardCount? boolean isDiscarded =
		 * players.get(i).discard("Weapon");
		 * 
		 * //If not possible, discard 2 Foe Cards, player.foeCardCount? if
		 * (!isDiscarded) { players.get(i).discard("Foe");
		 * players.get(i).discard("Foe"); }
		 * 
		 * }
		 * 
		 * }
		 */
		// Prosperity Throughout the Realm
		else if (name == "Prosperity Throughout the Realm") {
			
			Model.log("All players are awared 2 cards.", "eventBehaviour" ,"play");
			// All players must draw 2 Adventure Cards
			for (int i = 0; i < players.size(); i++) {
				System.out.println(players.get(i).getName() + " is awarded 2 cards!");
				Model.log(players.get(i).getName() + " is awared 2 cards.", "eventBehaviour" ,"play");
				players.get(i).addCard(deck.dealCard());
				players.get(i).addCard(deck.dealCard());
			
			}
			
			updateAllPlayers();
			
			
			
			setPlayersFinishedTurnToFalse();
			
			int counter = 0;
			
			do {
			
				
			for (int i=0; i<this.players.size(); i++) {
				
				if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter,name, "Prosperity Throughout the Realm! All awared 2 cards", players);
				}
				
			}
				
			counter =1;
			
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
			
			}while(allPlayersFinishedTurn(this.players)==false);
			
			
			
			counter=0;
			setPlayersFinishedTurnToFalse();
			
			 boolean haveToDiscard = !playersDecksAreNotOverMax(players);
			
		
			
			
			
			do {
			
			for (int i = 0; i < players.size(); i++) {
				
				if (players.get(i).deck.getSize()>12){
					
					int amount = players.get(i).deck.getSize() - 12;
					
					if (players.get(i).hasFinishedTurn) {
						
						players.get(i).decideCardsToDiscard(0, amount);
						
					}
					else {
					players.get(i).decideCardsToDiscard(counter, amount);
					
					
					}
				
				}
				else {
					
				
					players.get(i).hasFinishedTurn = true;
					
				}
			}
			
			
			try {
				Thread.sleep(300);
				//System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	counter =1;
			}while(haveToDiscard &&  (playersDecksAreNotOverMax(players) == false));
			
			
			updateAllPlayers();
		}
		
			
			
			
		}
	}


