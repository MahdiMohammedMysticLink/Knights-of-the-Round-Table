package com.COMP3004.Maven.Story;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Player;
//import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.Adventure.amourCard;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

public class tournamentBehaviour implements storyBehaviour, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	tournamentCard tourney;
	
	
	public boolean tristAndQueenInPlay;
	
	public boolean tristInPlay;
	
	public boolean iseultInPlay;
	
	private List<Player> players;


	public tournamentBehaviour(tournamentCard t) {
		tourney = t;
	}
	
	
public void setPlayersFinishedTurnToFalse() {
		
	
	
	 Model.log("Setting Players hasFinishedTurn to false", "tournamentBehaviour", "setPlayersFinishedTurnToFalse");
	
		for (int i=0; i<players.size(); i++) {
			
			
			Model.log(players.get(i).getName() + " hasFinishedTurn set to false", "tournamentBehaviour", "setPlayersFinishedTurnToFalse");
		
			players.get(i).hasFinishedTurn = false;
			
		}
		
	}

public void setPlayersFinishedTurnToFalse(List<Player> players) {
	
	for (int i=0; i<players.size(); i++) {
		
		players.get(i).hasFinishedTurn = false;
		
	}
	
}
	
	
	public void updateAllPlayers() {
		
		Model.log("Updating all players with information about all players", "tournamentBehaviour", "updateAllPlayers");
		
		for (int i= 0; i<players.size(); i++) {
			
			
			Model.log(players.get(i).getName()  +" is updated information about other players.", "tournamentBehaviour", "updateAllPlayers" );
			
			players.get(i).updateUI();
			
		}
		
		
		
	}
	
	
	public boolean playersDecksAreNotOverMax(List<Player> players, int requiredAmount) {
		
		
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).deck.getSize()> requiredAmount) {
				
				return false;
				
			}
			
			
			
		}
		
		return true;
		
		
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
	
	private boolean isTristanInPlay(List<Player> players) {
		
		Model.log("Checking if  Sir Tristan is in play with a set of players", "tournamentBehaviour" , "isTristanInPlay");
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).alliesInPlay.isFound("Sir Tristan")){
				
				Model.log(players.get(i).getName() + " has ally Sir Tristan in play", "tournamentBehaviour" , "isTristanInPlay");
				return true;
				
			}
			
		Model.log("Sir Tristan is in not play with the (perhaps limited) players checked.", "tournamentBehaviour" , "isTristanInPlay");
		}
		return false;
	}
	
	
	private boolean isIseultInPlay(List<Player> players) {
		
		Model.log("Checking if Queen Iseult is in play with a set of players", "tournamentBehaviour" , "isTristanInPlay");
		for (int i=0; i<players.size(); i++) {
			
			
			if(players.get(i).alliesInPlay.isFound("Queen Iseult")) {
				Model.log(players.get(i).getName() + " has ally Queen Iseult in play", "tournamentBehaviour" , "isIseultInPlay");
				return true;
			
			}
		}
		
		Model.log("Queen Iseult is in not play with the (perhaps limited) players checked.", "tournamentBehaviour" , "isTristanInPlay");
		return false;
	}
	
	
	
	

	public void play(Player curr, List<Player> players, AdventureDeck deck, int bonusShields) {
		
		this.players = players;
		
		
		
		iseultInPlay = false;
		
		tristInPlay = false;
		
		tristAndQueenInPlay = false;
		
		
		if (isIseultInPlay(players)) {
			Model.log("Observed that Queen Iseult is in play", "tournamentBehaviour", "play");
			iseultInPlay = true;
			
		}
		
		if (isTristanInPlay(players)) {
			Model.log("Observed that Sir Tristan is in play", "tournamentBehaviour", "play");
			tristInPlay= true;
			
		}
		
		
		if (tristInPlay && iseultInPlay) {
			Model.log("Observed that Sir Tristan and Queen Iseult are in play", "tournamentBehaviour", "play");
    			tristAndQueenInPlay = true;
    		}
		
	
		
		
		@SuppressWarnings("unused")
		int round =0;
		List<Player> participants = new ArrayList<Player>();
		
		
		selectParticipants(participants, players, deck);
		
		System.out.println("participants size:" + participants.size());
		
		
		Model.log("Participants size:" + participants.size(), "tournamentBehaviour", "play" );
		
		
		
		int participantsSize = participants.size(); // Original number

		if (participantsSize == 1) {
			
			
			
			 
				
				
				updateAllPlayers();
			
			
			
			// Award player for participating
			participants.get(0).addShields(1 + tourney.getShields());
			Model.log("Shields: "+participants.get(0).shields+" awarded to "+participants.get(0).getName(), "tournamentBehaviour", "play");
		} else if (participantsSize > 1) {
			// Each player draws from the adventure deck
			
			
			
			int counter=0;
			setPlayersFinishedTurnToFalse();
			Model.log("Checking if participants decks are over limit (12)", "tournamentBehaviour", "play");
			 boolean haveToDiscard = !playersDecksAreNotOverMax(participants);
			
			do {
			
			for (int i = 0; i < participants.size(); i++) {
				
				if(participants.get(i).deck.getSize()>12){
					
					int amount = participants.get(i).deck.getSize() - 12;
					if (participants.get(i).hasFinishedTurn) {
						
						participants.get(i).decideCardsToDiscard(0, amount);
						
					}else {
					participants.get(i).decideCardsToDiscard(counter, amount);
					}
				}
				
				else {
					
					
					participants.get(i).hasFinishedTurn = true;
				}
			}
			
			counter =1;
			
			try {
				Thread.sleep(300);
				//Model.log("Thread sleep for 300 mili seconds", "tournamentBehaviour", "play");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Model.log(e.getMessage(), "tournamentBehaviour", "play");
			}
	    	
			}while(haveToDiscard &&  (playersDecksAreNotOverMax(participants) == false));
			
			
			updateAllPlayers();
		
		
			
			
			System.out.println("Got out of discarding cards");
			
			
			//Participants play in a tournament
			playTourney(participants);
		
			// Winning participants
			if (participants.size() == 1) {
				
				
				
			    while(participants.get(0).deck.getSize()>12) {
            		
	            	 Model.log(participants.get(0).getName() + " has to discard cards", "questBehaviour", "play");
	            		
	            		int amountToDiscard = participants.get(0).deck.getSize()-12;
	                	
	            		
	            		participants.get(0).decideCardsToDiscard(amountToDiscard);
	            		
	            		
	            		
	            		
	            }
	            updateAllPlayers();
				
				participants.get(0).addShields(participantsSize + tourney.getShields());
				Model.log(participants.get(0).getName() + " won the tournament!" , "tournamentBehaviour", "play");
				
				if (participants.get(0).amourInPlay != null) {
					
					deck.addToDiscardPile(new amourCard());
				
					participants.get(0).amourInPlay = null;
					
					
				}
				//participants.get(0).removeCommittedCardsForTourney(round, false);
				updateAllPlayers();
			}

				// Winner receives (# of participants) + (tournament's shields)
		
				//If there is a tie, all winners discard weapons in play
				//Engage a second round
			if (participants.size() > 1) {
					// Winner receives (# of original participants) + (tournament's shields) *** UNCLEAR
					
					
					
			
					for (int i = 0; i < participants.size(); i++) {
						// All participants that tied are awarded shields
						participants.get(i).addShields(participantsSize + tourney.getShields());
						Model.log(participants.get(i).getName() + " won the tournament!" , "tournamentBehaviour", "play");
						
						if (participants.get(i).amourInPlay != null) {
							
							deck.addToDiscardPile(new amourCard());
						
							participants.get(i).amourInPlay = null;
							
							
						}
						//participants.get(i).removeCommittedCardsForTourney(round, false);
						
					}
					
				updateAllPlayers();
			}
	
		}

		// End game

	}

	private void playTourney(List<Player> participants) {
		
		List<Player> winners = new ArrayList<Player>();
		int maxPoints = 0;
		int round = 1;
		Model.log("Total participants playing a tournament: "+participants.size(), "tournamentBehaviour", "playTourney");
		
		System.out.println("In playTourney)");
		
		int[] playerPoints = new int[participants.size()];
		// Each participant decides what cards to play
		
		setPlayersFinishedTurnToFalse();
		int counter = 0;
		
		
		
		Model.log("Participants are choosing cards to fight.", "tournamentBehaviour", "playTourney");
		
		
		do {
		
			for (int i = 0; i < participants.size(); i++) {

				if (participants.get(i).hasFinishedTurn == false) {
					participants.get(i).fight(counter, tourney, round);
	
				}
		
		}
			
			counter=1;
			
			try {
				Thread.sleep(300);
				//Model.log("Thread sleep for 300 mili-seconds", "tournamentBehaviour", "playTourney");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Model.log(e.getMessage(), "tournamentBehaviour", "playTourney");
			}
			
			
		}while(allPlayersFinishedTurn(participants)==false);
		
		
		
		
		for (int i=0; i<participants.size(); i++) {
			
			
			
			if (isTristanInPlay(this.players)) {
				
				
				tristInPlay = true;
				
			}
			
			if (isIseultInPlay(this.players)) {
				
				
				iseultInPlay = true;
				
			}
			
			if (tristInPlay && iseultInPlay) {
				
				
				tristAndQueenInPlay = true;
				
			}
		
			
			
			
			
			
			playerPoints[i] = participants.get(i).calculatePointsForQuest(tourney.getName(), tristAndQueenInPlay);
		
			
			
			if (participants.get(i).amourInPlay != null) {
				
			
				
			
				playerPoints[i]+=10;
			}
			
			PlayerDeck cardsUsedInFight = participants.get(i).allCardsUsedAgainstAFoe();
	
			
			
			Model.log(participants.get(i).getName() + " has " + playerPoints[i] + "with the following cards", "tournamentBehaviour", "playTourney");
			
			cardsUsedInFight.printCards();
			
			if (playerPoints[i] > maxPoints) {
				
				if (winners!=null) {
				
					
					winners.clear();
				}
				
				maxPoints = playerPoints[i];
				
				winners.add(participants.get(i));
				
				
				
				
				participants.get(i).removeCommittedCardsForRoundOne(false);
				
				
			}else if (playerPoints[i] == maxPoints) {
				
				
				winners.add(participants.get(i));
				participants.get(i).removeCommittedCardsForRoundOne(false);
				
				
				
			}else {
				
				
				Model.log(participants.get(i).getName() + " has lost tournament in first round", "tournamentBehaviour", "playTourney");
				if (participants.get(i).amourInPlay != null) {
					
					participants.get(i).adventureDeck.addToDiscardPile(new amourCard());
				
					participants.get(i).amourInPlay = null;
					
					
				}
				
				participants.get(i).removeCommittedCardsForRoundOne(true);
			}
			
			//Players are allowed to use  Ally, Weapon and Amours to increase BP
			//Can't play duplicate weapons and more than 1 Amour
			//Existing Allies before tournament don't contribute to BP
			//Not playing any cards is allowed, as they could just use rank
			
			//Use condition below to determine the player with the greatest BP
			/*
			 * int playersPoints = participants.get(i).fight(null);
			 * 
			 * if(playersPoints > maxPoints) { winners.clear(); maxPoints = playersPoints;
			 * winners.add(participants.get(i)); } else if(participants.get(i).fight(null)
			 * == maxPoints) { winners.add(participants.get(i)); }
			 * 
			 */
		}
		
		
		
		updateAllPlayers();
		
		
		
		setPlayersFinishedTurnToFalse();
		
		counter = 0;
		
		do {
		
			
		for (int i=0; i<this.players.size(); i++) {
			
			if (this.players.get(i).hasFinishedTurn == false) {
			this.players.get(i).display(counter, tourney.getName(), "Won first round.", winners);
			}
			
		}
			
		counter =1;
		
		
		
		try {
			Thread.sleep(300);
			//Model.log("Thread sleep for 300 mili-seconds", "tournamentBehaviour", "playTourney");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Model.log(e.getMessage(), "tournamentBehaviour", "playTourney");
		}
    	
		
		
		}while(allPlayersFinishedTurn(this.players)==false);
		
		
		
		setPlayersFinishedTurnToFalse();
		
		
		counter = 0;
		
		do {
		
			
		for (int i=0; i<this.players.size(); i++) {
			
			if (this.players.get(i).hasFinishedTurn == false) {
			this.players.get(i).display(counter, participants, playerPoints);
			}
			
		}
			
		counter =1;
		
		
		
		try {
			Thread.sleep(300);
			//System.out.println("weeee");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Model.log(e.getMessage(), "tournamentBehaviour", "playTourney");
		}
    	
		
		
		}while(allPlayersFinishedTurn(this.players) == false);
		
		
		
		
		
		participants = winners;
		
		playerPoints = new int[participants.size()];
		
		winners = new ArrayList<Player>();
		
		
		round = 2;
			 
		if (participants.size() == 1) {
				
				
				winners = participants;
				
				
		}
			
			
		else {
			
	
		counter = 0;
		
		
		setPlayersFinishedTurnToFalse();
	
		
		do {		
		for (int i = 0; i < participants.size(); i++) {
			
			

			
			
	
			
			if (participants.get(i).hasFinishedTurn == false)
			participants.get(i).fight(counter, tourney, round);
			
			
			//check before and after
			
		}
		
		
		counter =1;
		
		
		
		try {
			Thread.sleep(300);
			//System.out.println("weeee");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Model.log(e.getMessage(), "tournamentBehaviour", "playTourney");
		}
    	
		
		
		
		
	}while(allPlayersFinishedTurn(participants)==false);
		
		for (int i = 0; i < participants.size(); i++) 
		
		{
		
			if (isTristanInPlay(players)) {
				
				
				tristInPlay = true;
				
			}
			
			if (isIseultInPlay(players)) {
				
				
				iseultInPlay = true;
				
			}
			
			if (tristInPlay && iseultInPlay) {
				
				
				tristAndQueenInPlay = true;
				
			}	
			
			playerPoints[i] = participants.get(i).calculatePointsForQuest(tourney.getName(), tristAndQueenInPlay);
			
		
			
			if (playerPoints[i] > maxPoints) {
				
				if (winners!=null) {
				
					
					winners.clear();
				}
				
				maxPoints = playerPoints[i];
				
				winners.add(participants.get(i));
				
				
			}else if (playerPoints[i] == maxPoints) {
				
				
				winners.add(participants.get(i));
				participants.get(i).removeCommittedCardsForRoundTwo(false);
				
				
				
			}else {
				
				
				Model.log(participants.get(i).getName() + " has lost tournament in second round", "tournamentBehaviour", "playTourney");
				
				if (participants.get(i).amourInPlay != null) {
					
					participants.get(i).adventureDeck.addToDiscardPile(new amourCard());
				
					participants.get(i).amourInPlay = null;
					
					
				}
				
				participants.get(i).removeCommittedCardsForRoundOne(true);
				
				
			}
			
		
		}
		
		updateAllPlayers();
		

		setPlayersFinishedTurnToFalse();
		
		counter = 0;
		
		do {
		
			
		for (int i=0; i<this.players.size(); i++) {
			
			if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter, tourney.getName(), "Won second round.", winners);
			}
			
		}
			
		counter =1;
		
		
		
		try {
			Thread.sleep(300);
			//System.out.println("weeee");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Model.log(e.getMessage(), "tournamentBehaviour", "playTourney");
		}
    	
		
		
		}while(allPlayersFinishedTurn(this.players)==false);

		
		
			//Players are allowed to use  Ally, Weapon and Amours to increase BP
			//Can't play duplicate weapons and more than 1 Amour
			//Existing Allies before tournament don't contribute to BP
			//Not playing any cards is allowed, as they could just use rank
			
			//Use condition below to determine the player with the greatest BP
			/*
			 * int playersPoints = participants.get(i).fight(null);
			 * 
			 * if(playersPoints > maxPoints) { winners.clear(); maxPoints = playersPoints;
			 * winners.add(participants.get(i)); } else if(participants.get(i).fight(null)
			 * == maxPoints) { winners.add(participants.get(i)); }
			 * 
			 */
	
		
		// Discard all cards currently in play, return winners
		counter = 0;
		setPlayersFinishedTurnToFalse();
		do {
		
			
		for (int i=0; i<this.players.size(); i++) {
			
			if (this.players.get(i).hasFinishedTurn == false) {
				this.players.get(i).display(counter, participants, playerPoints);
			}
			
		}
			
		counter =1;
		
		
		
		try {
			Thread.sleep(300);
			//System.out.println("weeee");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Model.log(e.getMessage(), "tournamentBehaviour", "playTourney");
		}
    	
		
		
		}while(allPlayersFinishedTurn(this.players)==false);
		
		
		
		
		
		
		
	}

		participants = winners;
		
		
	}

	private void selectParticipants(List<Player> participants, List<Player> players, AdventureDeck deck) {
		
		
		
		setPlayersFinishedTurnToFalse();
		
		
		Model.log("Asking players if they want to participate in tournament: " + tourney.getName(), "tournamentBehaviour", "selectParticipants");
		
		
		for (int i = 0; i<players.size(); i++) {
			
		Model.log("Checking if " + players.get(i).getName() + " will participate in " + tourney.getName(), "tournamentBehaviour", "selectParticipants");
			
		
		setPlayersFinishedTurnToFalse();
		
		
		players.get(i).doIParticipate(tourney);
    	String participate = "";
    	if (players.get(i).isParticipating) {
    		
    		
    		
    		Model.log(players.get(i).getName() + " was rewarded a card and is participating", "tournametBehaviour", "selectParticipants");
			
    		players.get(i).addCard(deck.dealCard());
			participate = " is participating in ";
		}else {
			
			participate =" is not participating in ";
			
		}
    	
		List<Player> onePlayer = new ArrayList<Player>();
		
		
		onePlayer.add(players.get(i));
		
    	
    	updateAllPlayers();
		setPlayersFinishedTurnToFalse();
		
		int counter = 0;
		Model.log("Updating all players that " + players.get(i).getName() + participate + tourney.getName(), "tournamentBehaviour", "selectParticipants");

		do{
		
		
			
		for (int j=0; j<this.players.size(); j++) {
			
			
			
	
		
				
			
			if (this.players.get(j).hasFinishedTurn == false) {
			this.players.get(j).display(counter, tourney.getName(), (players.get(i).getName() + participate + tourney.getName()), onePlayer);
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

		
		for (int i = 0; i < players.size(); i++) {
			
			if (players.get(i).isParticipating) {
				
				Model.log(players.get(i).getName() + "is participating", "tournamentBehaviour", "selectParticipants");
				
				participants.add(players.get(i));
			}
		}
		Model.log("Total participants:"+participants.size(), "tournamentBehaviour", "selectParticipants");
		
		
		
		
		
		
	}
}
