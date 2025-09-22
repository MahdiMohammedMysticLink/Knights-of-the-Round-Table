package com.COMP3004.Maven;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import com.COMP3004.Socket.ObjectArray;

import javafx.application.Platform;

public class humanBehaviour implements playerBehaviour, Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private Human human;

	private ObjectArray objects;
	private ObjectArray objectsTwo;

	public humanBehaviour(Player p) {
		human = (Human) p;

	}

	public void fight(int counter, PlayerDeck enemies, int stage, Player humanPlayer) {

		
	
		human.cardsCommittedToFight = new PlayerDeck();

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.amourInPlay = humanPlayer.amourInPlay;

		human.hasFinishedTurn = false;

		if (counter == 0) {
			
			
			
			
			objects = new ObjectArray();

			objects.add("pickCardsToFightFoe");

			objects.add(human);
			objects.add(enemies);
			objects.add(Model.players); // going to change it later so players isn't static
			objects.add(stage);
			
			Model.log("sending data to client for " + humanPlayer.getName(), "humanBehaviour", "fight");
			
			human.getServerThread().sendData(objects);
		}



		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		}

		else {
			
			

			Model.log("Retrieved data from client for " + humanPlayer.getName(), "humanBehaviour", "fight");
			
			
			human = (Human) (objectsTwo.get(1));

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;

			humanPlayer.cardsCommittedToFight = human.cardsCommittedToFight;
			humanPlayer.deck = human.deck;

			humanPlayer.alliesInPlay = human.alliesInPlay;

			humanPlayer.amourInPlay = human.amourInPlay;
			
			

		}

	}

	public void updateUI() {

		objects = new ObjectArray();

		objects.add("passInPlayers");

		objects.add(human);
		objects.add("Nothing2");
		objects.add(Model.players);
		
		
		Model.log("sending updated information of players to" + human.getName(), "humanBehaviour", "updateUI");

		human.getServerThread().sendData(objects);

		objectsTwo = human.getServerThread().retrieveData();

	}

	public int questSetup(questCard q, List<adventureCard>[] stages) {
		/*
		 * System.out.println(human.name + " has entered questSetup()"); //How much
		 * cards does the sponsor start off with? int cardsSpent = human.deck.getSize();
		 * 
		 * //Set up in terms of stages for (int i = 0; i < stages.length; i++) {
		 * //Display which stage# you are setting up
		 * human.ui.alertUser("Setting up stage " + i);
		 * 
		 * boolean isFinished = false; while (!isFinished) { adventureCard c = null;
		 * 
		 * 
		 * List<adventureCard> allowableCards = new ArrayList<adventureCard>(); //Ask
		 * sponsor if they want to use a Foe or Test Card if(human.ui.foeOrTest())//If
		 * true, the player wants to use a foe { System.out.println(human.name +
		 * " wants to use a Foe card"); allowableCards = human.deck.getFoes().toList();
		 * } else { System.out.println(human.name + " wants to use a Test card");
		 * allowableCards = human.deck.getTests().toList(); }
		 * 
		 * //Have player select card from the set of allowed cards selectCard(c,
		 * allowableCards);
		 * 
		 * int pointsLastRound = 0; //Check what kind of card was selected if
		 * (c.getType() == "Foe") { int points = 0; stages[i].add(c);
		 * 
		 * boolean finishedSel = false; while (!finishedSel) { //Display only sponsor's
		 * Weapon cards allowableCards = human.deck.getWeapons().toList(); selectCard(c,
		 * allowableCards);
		 * 
		 * //Have player select a weapon card, suppose c becomes this card if (c.name ==
		 * "Battle Axe" && stages[i].contains(c)) { //Display to user can't play more
		 * than one Battle Axe
		 * human.ui.alertUser("You cannot play more than one Battle Axe"); } else {
		 * //Add weapon card to stage stages[i].add(c); points =
		 * q.calculateFoePoints(stages[i]); }
		 * 
		 * if (points > pointsLastRound) { //Prompt to player if they are done setting
		 * up this round finishedSel = human.ui.areYouDone(); }
		 * 
		 * }
		 * 
		 * pointsLastRound = points; } else if (c.getType() == "Test") {
		 * stages[i].add(c); } } }
		 * 
		 * cardsSpent -= human.deck.getSize(); //Computed difference gives cards spent
		 * for setup System.out.println(human.name + " has exited questSetup()"); return
		 * cardsSpent;
		 */

		return 0;
	}

	public void updateCardsPlayed(List<adventureCard> cards, int player) {
		;
	}

	public void decideCardsToDiscard(int counter, int amount, Player humanPlayer) {
		
		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.amourInPlay = humanPlayer.amourInPlay;

		// humanPlayer.hasFinishedTurn = false;

		human.hasFinishedTurn = false;

		if (counter == 0) {
			objects = new ObjectArray();

			objects.add("discardCards");

			objects.add(human);
			objects.add(amount);
			objects.add(Model.players); // going to change it later so players isn't static
			
			
			Model.log("sending data to client for " + humanPlayer.getName(), "humanBehaviour", "decideCardsToDiscard");
			human.getServerThread().sendData(objects);
		}

		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		}

		else {
			
			
			Model.log("Retrieved data from client for " + humanPlayer.getName(), "humanBehaviour", "decideCardsToDiscard");
			human = (Human) (objectsTwo.get(1));

			humanPlayer.deck = human.deck;

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
			
			

		}

	}

	public void decideCardsToDiscard(int amount, Player humanPlayer) {

	
		human.cardsToDiscard = new PlayerDeck();
		human.deck = humanPlayer.deck;
		
		
		
		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.amourInPlay = humanPlayer.amourInPlay;

		human.hasFinishedTurn = false;

		objects = new ObjectArray();

		objects.add("discardCards");

		objects.add(human);
		objects.add(amount);
		objects.add(Model.players); // going to change it later so players isn't static
		Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "decideCardsToDiscard");
		human.getServerThread().sendData(objects);

		// objects = null;

		objectsTwo = null;

		while (objectsTwo == null || ((Human) objectsTwo.get(1)).hasFinishedTurn == false) {
			objectsTwo = human.getServerThread().retrieveData();
			try {
				Thread.sleep(300);
				// System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "decideCardsToDiscard");
		human = (Human) (objectsTwo.get(1));

		humanPlayer.deck = human.deck;
		
		humanPlayer.cardsToDiscard = human.cardsToDiscard;
		

	}
	
	
	
	public void doIParticipate(tournamentCard tourney, Player humanPlayer) {

		human.isParticipating = false;
		human.hasFinishedTurn = false;

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		

			objects = new ObjectArray();
			
			objects.add("doIParticipateTournament");

			objects.add(human);
			objects.add(tourney);
			objects.add(Model.players); // going to change it later so players isn't static
			Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			human.getServerThread().sendData(objects);


		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		while (objectsTwo == null || ((Human) objectsTwo.get(1)).hasFinishedTurn == false) {
			objectsTwo = human.getServerThread().retrieveData();
			try {
				Thread.sleep(300);
				// System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
			// System.out.println(human.isSponsoring);
			Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			human = (Human) (objectsTwo.get(1));

			humanPlayer.isParticipating = human.isParticipating;

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
			
		}




	public void doIParticipate(int counter, tournamentCard tourney, Player humanPlayer) {

		human.isParticipating = false;
		human.hasFinishedTurn = false;

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		if (counter == 0) {

			objects = new ObjectArray();
			
			objects.add("doIParticipateTournament");

			objects.add(human);
			objects.add(tourney);
			objects.add(Model.players); // going to change it later so players isn't static
			Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			human.getServerThread().sendData(objects);

		}

		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		} else {
			// System.out.println(human.isSponsoring);
			Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			human = (Human) (objectsTwo.get(1));

			humanPlayer.isParticipating = human.isParticipating;

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
			
		}

	}

	// General
	public void doIParticipate(int counter, PlayerDeck[] questStages, questCard quest, Player humanPlayer) {

		human.isParticipating = false;
		human.hasFinishedTurn = false;

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.selectedQuestStages = questStages;

		if (counter == 0) {

			objects = new ObjectArray();

			objects.add("doIParticipate");

			objects.add(human);
			objects.add(quest);
			objects.add(Model.players); // going to change it later so players isn't static
			Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			human.getServerThread().sendData(objects);

		}

		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		} else {
			// System.out.println(human.isSponsoring);

			human = (Human) (objectsTwo.get(1));
			Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			humanPlayer.isParticipating = human.isParticipating;

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
		}
	}

	
	
	
	
	public void doIParticipate(PlayerDeck[] questStages, questCard quest, Player humanPlayer) {

		human.isParticipating = false;
		human.hasFinishedTurn = false;

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.selectedQuestStages = questStages;

	

			objects = new ObjectArray();

			objects.add("doIParticipate");

			objects.add(human);
			objects.add(quest);
			objects.add(Model.players); // going to change it later so players isn't static
			Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			human.getServerThread().sendData(objects);


			objectsTwo = null;
		
			while (objectsTwo == null || ((Human) objectsTwo.get(1)).hasFinishedTurn == false) {
				objectsTwo = human.getServerThread().retrieveData();
				try {
					Thread.sleep(300);
					// System.out.println("weeee");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		


	
			human = (Human)(objectsTwo.get(1));
			Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			humanPlayer.isParticipating = human.isParticipating;

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
		}


	
	
	
	
	// Specific to sponsoring
	public int nextBid() {
		return 0;
	}

	public void discardAfterWinning(int round, int amount, Player humanPlayer) {

		decideCardsToDiscard(amount, humanPlayer);

	}

	public void doISponsor(questCard quest, Player humanPlayer) {

		human.hasFinishedTurn = false;
		human.isSponsoring = false;
		
		

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		objects = new ObjectArray();

		objects.add("doISponsor");

		objects.add(human);
		objects.add(quest);
		objects.add(Model.players); // going to change it later so players isn't static
		Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "doISponsor");
		human.getServerThread().sendData(objects);

		// objects = null;

		objectsTwo = null;

		while (objectsTwo == null || ((Human) objectsTwo.get(1)).hasFinishedTurn == false) {
			objectsTwo = human.getServerThread().retrieveData();
			try {
				Thread.sleep(300);
				// System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		
		Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
		System.out.println(human.isSponsoring);

		// have to make a tempHuman every time. don't need to pass in the player every
		// time now
		// Human tempHuman;

		// tempHuman = (Human)(objectsTwo.get(1));
		// humanPlayer = (Player)(objects.get(1)); //not sure why this doesn't work
		// human.isSponsoring = tempHuman.isSponsoring;

		// human.selectedQuestStages = tempHuman.selectedQuestStages;

		// human.deck = tempHuman.deck;

		human = (Human) (objectsTwo.get(1));
		// humanPlayer = (Player)(objects.get(1)); //not sure why this doesn't work
		humanPlayer.isSponsoring = human.isSponsoring;

		humanPlayer.selectedQuestStages = human.selectedQuestStages;

		humanPlayer.deck = human.deck;
		
		

		System.out.println("about to do this check:");
		System.out.println(human.isSponsoring);

	}

	public void display(int counter, String eventName, String event, List<Player> players, Player humanPlayer) {

		human.hasFinishedTurn = false;

		if (counter == 0) {

			objects = new ObjectArray();
			objects.add("displayEvent");
			objects.add(human);
			objects.add(eventName);
			objects.add(Model.players);
			objects.add(event);
			objects.add(players);
			Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "display");
			human.getServerThread().sendData(objects);
		}

		// objects = null;

		objectsTwo = null;

		
		
		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		} else {
			human = (Human) (objectsTwo.get(1));
			Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "doIParticipate");
			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;

		}

	}

	public void selectCard(adventureCard card, List<adventureCard> allowable) {
		String cardName = null;
		// human.ui.selectCard(cardName, convertToString(allowable));
		card = human.deck.find(cardName);
	}

	public List<String> convertToString(List<adventureCard> deck) {
		List<String> newList = new ArrayList<String>();

		for (int i = 0; i < deck.size(); i++) {
			newList.add(deck.get(i).getName());
		}

		return newList;
	}

	public void seeCardsEndOfStage(int counter, PlayerDeck stage, PlayerDeck cardsUsedAgainstFoes, String result,
			Player humanPlayer) {

		human.hasFinishedTurn = false;

		humanPlayer.hasFinishedTurn = false;

		if (counter == 0) {

			objects = new ObjectArray();

			objects.add("endOfStage");

			objects.add(human);
			objects.add(stage);
			objects.add(Model.players); // going to change it later so players isn't static
			objects.add(cardsUsedAgainstFoes);
			objects.add(result);
			Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "seeCardsEndOfStage");
			human.getServerThread().sendData(objects);
		}

		// objects = null;

		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		} else {
			human = ((Human) objectsTwo.get(1));
			Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "seeCardsEndOfStage");
			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;

		}

		// System.out.println(human.isSponsoring);

		// humanPlayer = (Player)(objects.get(1)); //not sure why this doesn't work

	}

	public void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round,
			Player humanPlayer) {

		int tempBidsFromCardsInPlay = bidsFromCardsInPlay;

		String tempCard = card;

		int[] tempBiddersAmount = biddersAmount;

		human.bid = 0;

		int tempHighestBid = highestBid;

		// human.cardsCommittedToFight = new PlayerDeck();

		// human.deck = humanPlayer.deck;

		// human.alliesInPlay = humanPlayer.alliesInPlay;

		// human.amourInPlay = humanPlayer.amourInPlay;

		human.hasFinishedTurn = false;

		objects = new ObjectArray();

		objects.add("bid");

		objects.add(human);
		objects.add(tempCard);
		objects.add(Model.players);
		objects.add(tempBidsFromCardsInPlay);
		objects.add(tempBiddersAmount);
		objects.add(tempHighestBid);
		Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "bid");
		human.getServerThread().sendData(objects);

		// objects = null;

		objectsTwo = null;

		while (objectsTwo == null || ((Human) objectsTwo.get(1)).hasFinishedTurn == false) {
			objectsTwo = human.getServerThread().retrieveData();
			try {
				Thread.sleep(300);
				// System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "bid");
		human = (Human) (objectsTwo.get(1));

		// humanPlayer.cardsCommittedToFight = human.cardsCommittedToFight;
		// humanPlayer.deck = human.deck;

		// humanPlayer.alliesInPlay = human.alliesInPlay;

		// humanPlayer.amourInPlay = human.amourInPlay;
		tempBiddersAmount = (int[]) (objects.get(5));
		biddersAmount = tempBiddersAmount;
		
		humanPlayer.bid = human.bid;

	}

	public void playAllyCardsForBid(String test, Player humanPlayer) {
		// TODO Auto-generated method stub
		
		
		human.hasFinishedTurn = false;

		human.cardsCommittedToFight = new PlayerDeck();

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.amourInPlay = humanPlayer.amourInPlay;

		objects = new ObjectArray();

		objects.add("playAllyCardsForBid");

		objects.add(human);
		objects.add(test);
		objects.add(Model.players);
		Model.log("sending data to client for " + humanPlayer.getName(),"humanBehaviour", "playAllyCardsForBid");
		human.getServerThread().sendData(objects);

		// objects = null;

		objectsTwo = null;

		while (objectsTwo == null || ((Human) objectsTwo.get(1)).hasFinishedTurn == false) {
			objectsTwo = human.getServerThread().retrieveData();
			try {
				Thread.sleep(300);
				// System.out.println("weeee");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		human = (Human) (objectsTwo.get(1));
		

		Model.log("Retrieved data from client for " + humanPlayer.getName(),"humanBehaviour", "playAllyCardsForBid");

		humanPlayer.cardsCommittedToFight = human.cardsCommittedToFight;
		humanPlayer.deck = human.deck;

		humanPlayer.alliesInPlay = human.alliesInPlay;

		humanPlayer.amourInPlay = human.amourInPlay;
		
		

	}

	@Override
	public void fight(int counter, tournamentCard tourney, int round, Player humanPlayer) {

		
		
		
		human.hasFinishedTurn = false;

		human.cardsCommittedToFight = humanPlayer.cardsCommittedToFight;

		human.deck = humanPlayer.deck;

		human.alliesInPlay = humanPlayer.alliesInPlay;

		human.amourInPlay = humanPlayer.amourInPlay;

		if (counter == 0) {

			objects = new ObjectArray();
			
			objects.add("pickCardsForTournament");

			objects.add(human);

			objects.add(tourney.getName());

			objects.add(Model.players);

			objects.add(round);
			Model.log("sending data to client for " + humanPlayer.getName(), "humanBehaviour", "fight");
			human.getServerThread().sendData(objects);

		}

		objectsTwo = null;
		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		}

		else {

			Model.log("Retrieving data from client for " + humanPlayer.getName(), "humanBehaviour", "fight");
			human = (Human) (objectsTwo.get(1));

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
			humanPlayer.cardsCommittedToFight = human.cardsCommittedToFight;
			humanPlayer.deck = human.deck;

			humanPlayer.alliesInPlay = human.alliesInPlay;

			humanPlayer.amourInPlay = human.amourInPlay;
			
		}

	}

	@Override
	public void display(int counter, List<Player> winners, int[] playerPoints, Player humanPlayer) {
		// TODO Auto-generated method stub

		human.hasFinishedTurn = false;

		if (counter == 0) {

			objects = new ObjectArray();

			objects.add("displayTournament");

			objects.add(human);
			objects.add(winners);
			objects.add(Model.players);

			objects.add(playerPoints);

			Model.log("sending data to client for " + humanPlayer.getName(), "humanBehaviour", "display");

			human.getServerThread().sendData(objects);

		}

		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();

		if (objectsTwo == null) {

			humanPlayer.hasFinishedTurn = false;

		} else {

			human = (Human) (objectsTwo.get(1));

			Model.log("Retrieved data for client for " + humanPlayer.getName(), "humanBehaviour", "display");
			humanPlayer.cardsCommittedToFight = human.cardsCommittedToFight;
			humanPlayer.deck = human.deck;

			humanPlayer.alliesInPlay = human.alliesInPlay;

			humanPlayer.amourInPlay = human.amourInPlay;

			humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
			
		}

	}
	
	@Override
	public void displayStory(int counter, storyCard story, Player humanPlayer) {
		// TODO Auto-generated method stub
		human.hasFinishedTurn = false;
		
		if (counter ==0) {
			
			objects = new ObjectArray();
			
			objects.add("displayStory");
			
			objects.add(human);
			objects.add(story);
			objects.add(Model.players);
			
			Model.log("Sending data to client for " + humanPlayer.getName(), "humanBehaviour", "displayStory");

			
			human.getServerThread().sendData(objects);
			
			
		}
		
		
		objectsTwo = null;

		objectsTwo = human.getServerThread().retrieveData();
			
		
		
		

		if (objectsTwo == null) {
			

			humanPlayer.hasFinishedTurn = false;
			
		}
		else {
		
		
		
		
		human = (Human)(objectsTwo.get(1));
		
	
		
		

		Model.log("Retrieved data for client for " + humanPlayer.getName(), "humanBehaviour", "displayStory");
		
		humanPlayer.hasFinishedTurn = human.hasFinishedTurn;
		
		}
		
		
		
		
		
	}


}
