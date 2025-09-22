package com.COMP3004.Maven;

import com.COMP3004.Maven.Rank.championKnightCard;
import com.COMP3004.Maven.Rank.knightCard;
import com.COMP3004.Maven.Rank.rankCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.Adventure.amourCard;
import com.COMP3004.Maven.PlayerDeck.AllyDeck;
import com.COMP3004.Maven.PlayerDeck.AmourDeck;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.PlayerDeck.WeaponDeck;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Properties
	public PlayerDeck deck; // Player contains a deck they can play
	public rankCard rank; // Player holds a rank
	public String name; // Player's name
	public int shields, points; // Number of shields and points collected
	public playerBehaviour behaviour;

	public boolean isSponsoring;
	
	public boolean isParticipating;

	public boolean wonQuest;
	
	public boolean hasFinishedTurn;
	
	public int bid;

	
	private int highestShieldsGotten;
	public boolean wonGame;

	public amourCard amourInPlay;
	public PlayerDeck[] selectedQuestStages;
	public PlayerDeck cardsInPlay;
	public PlayerDeck cardsCommittedToFight;
	
	public PlayerDeck cardsToDiscard;
	
	public PlayerDeck allCardsUSedAgainstAFoe;
	
	public AdventureDeck adventureDeck;

	public AllyDeck alliesInPlay;

	// Constructor
	public Player(String n, rankCard r, AdventureDeck AD) {
		name = n;
		rank = r;
		deck = new PlayerDeck();
		shields= 0;
		highestShieldsGotten = 0;
		isSponsoring = false;
		isParticipating = false;
		hasFinishedTurn = false;
		alliesInPlay = new AllyDeck();
		amourInPlay = null ;
		cardsCommittedToFight = new PlayerDeck();
		wonQuest = false;
		wonGame = false;
		bid = 0;
		cardsToDiscard = new PlayerDeck();
		this.adventureDeck = AD;
		// TODO Auto-generated constructor stub
	}

	public Player() {
	}
	
	
	public rankCard getRank() {return rank;}
	
	
	public void updateUI() {
		
		
		behaviour.updateUI();
		
	}
	public void discardAfterWinning(int round, int amount) {
		
		
		behaviour.discardAfterWinning(round, amount, this);
		
		
		for (int i=0; i<cardsToDiscard.getSize(); i++) {
			
			
			adventureDeck.addToDiscardPile(cardsToDiscard.get(i));
		}
		
	}
	
	
	
	public void setAllAttributesToFalse() {
		
		
		isSponsoring = false;
		isParticipating = false;
		wonQuest = false;
		
		
		
	}
	

	
	
	public PlayerDeck allCardsUsedAgainstAFoe() {
		
		PlayerDeck cards = new PlayerDeck();
		
		
		WeaponDeck weapons = cardsCommittedToFight.getWeapons();
		
		
		
		for (int i = 0; i<weapons.getSize(); i++) {
			
			
			
			cards.add(weapons.get(i));
			
		}
		
		for (int i= 0; i<alliesInPlay.getSize(); i++) {
			
		
			cards.add(alliesInPlay.get(i));
			
		}
		
		if (amourInPlay != null) {
			
			
			cards.add(new amourCard());
		}
		
		return cards;
		
	}
	
	public int calculatePointsForQuest(String questName, boolean tristAndQueenInPlay) {
		
		
		int total = rank.getPoints();
		
		
		
		
		if (amourInPlay != null) {
			
			total+=10;
		}
		
	
		total+= cardsCommittedToFight.calculateWeapons();
		
		total+= alliesInPlay.calculateSpecialFeaturePoints(questName, tristAndQueenInPlay);
		
		return total;
	}
	
	public void discardSponsoredCards() {
		
		Model.log(name + " is discarding Sponsored cards.", "Player", "discardSponsoredCards");
		
		for (int i = 0; i < selectedQuestStages.length; i++) {

			
			
			for (int j = 0; j < selectedQuestStages[i].getSize(); j++) {
				
				
				
				deck.remove(selectedQuestStages[i].get(j));
				adventureDeck.addToDiscardPile(selectedQuestStages[i].get(j));
			}
		}

		
		
	}
	
	
	
	public void removeCommittedCardsForRoundTwo(boolean lost) {
		
		
	
	
		
		for (int i = 0; i < cardsCommittedToFight.getSize(); i++) {

			removeCard(cardsCommittedToFight.get(i));
			
		}
		
		
		WeaponDeck weapons= cardsCommittedToFight.getWeapons();
		
		for (int i=0; i< weapons.getSize(); i++) {
		Model.log(name + " is discarding weapon cards committed to round 2.", "Player", "removeComittedCards");

		adventureDeck.addToDiscardPile(weapons.get(i));
		
		}
		
	
	
		
		
	}
	
	
	public void removeCommittedCardsForRoundOne(boolean lost) {
		
		
		
		
		for (int i = 0; i < cardsCommittedToFight.getSize(); i++) {

			removeCard(cardsCommittedToFight.get(i));
			
		
		}
		
	
		WeaponDeck weaponsToDiscard = cardsCommittedToFight.getWeapons();
		
		if (lost) {
			
		Model.log(name + " is discarding cards committed weapon cards to round 1 of tournament.", "Player", "removeComittedCards");	
		for (int i=0; i< weaponsToDiscard.getSize(); i++) {
		
		adventureDeck.addToDiscardPile(weaponsToDiscard.get(i));

		}
		
		
		}
		
		
		
	}
		
		
	

	public void removeCommittedCards() {
		
		
		Model.log(name + " is discarding cards committed to Fight.", "Player", "removeComittedCards");

		for (int i = 0; i < cardsCommittedToFight.getSize(); i++) {

			removeCard(cardsCommittedToFight.get(i));
			
		}
		WeaponDeck weaponsToDiscard = cardsCommittedToFight.getWeapons();
		
		
		for (int i=0; i< weaponsToDiscard.getSize(); i++) {
		
		adventureDeck.addToDiscardPile(weaponsToDiscard.get(i));
		
		}
	}
	
	public void decideCardsToDiscard(int amount) {
		
		
		Model.log(name + " is deciding which cards to discard. Must discard " + amount, "Player", "decideCardsToDiscard");
		
		behaviour.decideCardsToDiscard(amount, this);
		
		
		for (int i=0; i<cardsToDiscard.getSize(); i++) {
			
			
			adventureDeck.addToDiscardPile(cardsToDiscard.get(i));
		}
	}

	
	public void decideCardsToDiscard(int counter, int amount) {
		
		hasFinishedTurn = false;
		Model.log(name + " is deciding which cards to discard. Must discard " + amount, "Player", "decideCardsToDiscard");
		
		
		
		
		behaviour.decideCardsToDiscard(counter, amount, this);
		
		
		if (hasFinishedTurn == true) {
			
			
			for (int i=0; i<cardsToDiscard.getSize(); i++) {
				
				
				adventureDeck.addToDiscardPile(cardsToDiscard.get(i));
			}
		}
	}
	
	public int calculatePossibleQuestPoints(String questFoeName) {
		return deck.calculateQuest(questFoeName);
	}

	public int getShields() {
		return shields;
	}

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}

	public int deckSize() {
		return deck.getSize();
	}

	public void addShields(int n) {
		
		int previous = shields;
		
		shields += n;
		
		if (shields<0) {
			
			
			shields = 0;
		}
		
		if (shields> highestShieldsGotten) {
			
			
			highestShieldsGotten = shields;
		}
		
		if (shields>=highestShieldsGotten) {
			Model.log(name + " has been awared " + n + " shields", "Player", "addShields");
		}else {
			
			Model.log(name + " has lost " + (-n) + " shields", "Player", "addShields");
		}
		
		if (shields>=highestShieldsGotten)  {
		
		if (shields>= 10) {
			Model.log(name + " has won game", "Player", "addShields");
			wonGame = true;
			
			
		}
		if (shields>=7 && previous<7) {
		   Model.log(name + " is promotted to Champion Knight", "Player", "addShields");
		   rank = new championKnightCard();	
			
		}
		
		if (shields>=7 && previous>=7) {
			   Model.log(name + " is still a Champion Knight", "Player", "addShields");
			   rank = new championKnightCard();	
				
			}
		else if (shields>=5  && previous<5) {
			
			 Model.log(name + " is promotted to a  Knight", "Player", "addShields");
			
			rank = new knightCard();
		}
		
		
		else if (shields>=5  && previous>=5) {
			
			 Model.log(name + " is still a  Knight", "Player", "addShields");
			
			rank = new knightCard();
		}
		
		else {
			
			Model.log(name + " is still a  Squire", "Player", "addShields");
			
		}
		}
		Model.log(name + "has now" + shields + " shields", "Player", "addShields");
		Model.log(name + "previous shields: " + previous , "Player", "addShields");
	}

	public void addCard(adventureCard c) {
		deck.add(c);
	    Model.log("added a card for " + name + ": " + c.getName(), "Player", "addcard");
	}

	public void removeCard(adventureCard c) {
		deck.remove(c);
		
		 Model.log("removed a card for" + name + ": " + c.getName(), "Player", "removeCard");
	}

	public void fight(int counter, PlayerDeck enemies, int stage) {

		
		if (counter == 0) {
		cardsCommittedToFight = new PlayerDeck();
		Model.log(name + " is fighting a foe at stage: " + stage, "Player", "fight");
		}
		
		behaviour.fight(counter, enemies, stage, this);
	
		if (hasFinishedTurn == true) {
		AllyDeck allies = cardsCommittedToFight.getAllies();
		AmourDeck tempAmour = cardsCommittedToFight.getAmours();

		for (int i = 0; i < allies.getSize(); i++) {

			
			Model.log(name + " has put ally " + allies.get(i).getName() +" in play", "Player", "fight");
			
			alliesInPlay.add(allies.get(i));
		}

		if (tempAmour.getSize() > 0) {
			Model.log(name + " has put amour in play", "Player", "fight");
			amourInPlay = new amourCard();

		}

		}

	}

	public int questSetup(questCard q, List<adventureCard>[] stages) {
		return behaviour.questSetup(q, stages);
	}

	public void doISponsor(questCard quest) {
		
		
		Model.log(name + " is choosing whether or not sponsor.", "Player", "doISponsor");
		
		behaviour.doISponsor(quest, this);

		

	}

	public void doIParticipate(int counter, PlayerDeck[] questStages, questCard quest) {
		
		
		Model.log(name + " is choosing whether or not to participate for " + quest.getName(), "Player", "doIParticipate");
		
		behaviour.doIParticipate(counter, questStages, quest, this);
	}

	
	
	public void display(int counter, String eventName, String event, List<Player> players) {
		
		Model.log("Event/Tournament results is being displayed", "Player", "display");
		
		
		behaviour.display(counter, eventName, event, players, this);
		
	}
	public void displayEndOfStage(int counter, PlayerDeck stage, PlayerDeck cardsAgainstFoes, String result) {
		Model.log(name + " is seeing results at the end of stage.", "Player", "displayEndOfStage");
		behaviour.seeCardsEndOfStage(counter, stage, cardsAgainstFoes, result, this);
	}

	public void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round) {
		
		Model.log(name + " is bidding.", "Player", "bid");
	
		behaviour.bid(card, bidsFromCardsInPlay, biddersAmount, highestBid, round, this);
		
		
		Model.log(name + " has bid: " + biddersAmount[0] + " with " + bidsFromCardsInPlay +" free bids", "Player", "bid");
		
	}

	public void playAllyCardsForBid(String test) {
		
		Model.log(name + " has choice of selecting an ally or amour Card for bids.", "Player", "playAllyCardsForBid");
		
		behaviour.playAllyCardsForBid(test, this);
		// TODO Auto-generated method stub
		
	}

	public void doIParticipate(int counter, tournamentCard tourney) {
		// TODO Auto-generated method stub
		behaviour.doIParticipate(counter, tourney, this);
	}

	public void fight(int counter, tournamentCard tourney, int round) {
		// TODO Auto-generated method stub
		Model.log(name + "is fighting in" + tourney.getName(), "Player", "fight");
		if (round==1 && counter == 0) {
			
			
			cardsCommittedToFight = new PlayerDeck();
		
		}
	
	
		behaviour.fight(counter, tourney, round, this);
		

		if (hasFinishedTurn) {
		AllyDeck allies = cardsCommittedToFight.getAllies();
		AmourDeck tempAmour = cardsCommittedToFight.getAmours();

		for (int i = 0; i < allies.getSize(); i++) {
			removeCard(allies.get(i));
		
			Model.log(name + " has put ally " + allies.get(i).getName() +" in play", "Player", "fight");
			if (!alliesInPlay.isFound(allies.get(i).getName())) {
			alliesInPlay.add(allies.get(i));
			
			}
			
		
		}
		
		if (tempAmour.getSize() > 0) {
			
			
			if (round == 1) {
			Model.log(name + " has put an amour in play", "Player", "fight");
			amourInPlay = new amourCard();
			}
			
			else {
			Model.log(name + " has an amour in play", "Player", "fight");
			amourInPlay = new amourCard();
				
			}
		}
		
		}
	}

	public void removeCommittedCardsForTourney(int round, boolean lost) {
		// TODO Auto-generated method stub
		if (round == 1) {
			
		
			
			removeCommittedCardsForRoundOne(lost);
			
		}
		if (round == 2) {
			
			removeCommittedCardsForRoundTwo(lost);
			
		}
		
		
		
	}

	public void display(int counter, List<Player> winners, int[] playerPoints) {
		// TODO Auto-generated method stub
		behaviour.display(counter, winners, playerPoints, this);
	}

	public void displayStory(int counter, storyCard story) {
		// TODO Auto-generated method stub
		
		if (counter==0) {
			
			
			Model.log(this.name + "is being displayed the Story card and will be updated with stats of players.", "Player", "displayStory");
			
		}
		
		behaviour.displayStory(counter, story, this);
	}
	
	@Override
	public String toString() {
		return "Player [deck=" + deck + ", rank=" + rank + ", name=" + name + ", shields=" + shields + ", points="
				+ points + ", behaviour=" + behaviour + ", isSponsoring=" + isSponsoring + ", isParticipating="
				+ isParticipating + ", wonQuest=" + wonQuest + ", hasFinishedTurn=" + hasFinishedTurn + ", bid=" + bid
				+ ", highestShieldsGotten=" + highestShieldsGotten + ", wonGame=" + wonGame + ", amourInPlay="
				+ amourInPlay + ", selectedQuestStages=" + Arrays.toString(selectedQuestStages) + ", cardsInPlay="
				+ cardsInPlay + ", cardsCommittedToFight=" + cardsCommittedToFight + ", adventureDeck=" + adventureDeck
				+ ", alliesInPlay=" + alliesInPlay + "]";
	}

	public void doIParticipate(PlayerDeck[] questStages, questCard quest) {
		// TODO Auto-generated method stub
		behaviour.doIParticipate(questStages, quest, this);
	}
	
	
	public void doIParticipate(tournamentCard tourney) {
		// TODO Auto-generated method stub
		behaviour.doIParticipate(tourney, this);
	}


}
