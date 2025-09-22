package com.COMP3004.Maven;

import java.io.Serializable;
import java.util.List;

import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;

public class aiBehaviour implements playerBehaviour, Serializable {
	private static final long serialVersionUID = 1L;
	protected int numberOfPlayers;
	protected AI player;
	
	protected List<Player> players;

	public aiBehaviour(AI p) {
		player = p;

		
		this.players = Model.players;
		


		

	}
	
	
	// General
	
	//WHAT METHODS HUMAN AND AI SHOULD HAVE IN COMMON
	public void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round) {
		player.strat.bid(card, bidsFromCardsInPlay, biddersAmount, highestBid, round);
	}
	
	public void discardAfterWinning(int round, int amount, Player p) {
		
		player.strat.discardAfterWinning(round);
	}
	
	public void doIParticipate(PlayerDeck[] questStages, questCard quest, Player p) {
		
		player.strat.doIParticipate(questStages, quest);
	}
	
	public void doIParticipate(int counter, PlayerDeck[] questStages, questCard quest, Player p) {
		if (counter==0)
		player.strat.doIParticipate(questStages, quest);
	}
	
	public void doIParticipate(int counter, tournamentCard tourney, Player p) {
		if (counter==0)
		player.strat.doIParticipate(tourney);
	}
	
	
	public void doIParticipate(tournamentCard tourney, Player p) {
	
		player.strat.doIParticipate(tourney);
	}
	
	public void doISponsor(questCard quest, Player p) {
		player.strat.doISponsor(quest, players);
		
		
	}
	
	public void fight(int counter, PlayerDeck enemies, int stage, Player p) {
		
		
		if (counter==0)
		player.strat.fight(enemies, stage);
	}
	
	public void fight(int counter, tournamentCard tourney, int round, Player p) {
		
		if (counter == 0) 
		player.strat.fight(tourney, round);
		

	}
	
	public int questSetup(questCard q, List<adventureCard>[] stages) {
		player.strat.questSetup(q, stages);
		return 0;
	}

	// Specific to sponsoring

	public int nextBid() {
		return 0;
	}

	public void decideCardsToDiscard(int amount) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void seeCardsEndOfStage(int counter, PlayerDeck stage, PlayerDeck cardsAgainstFoes, String result, Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(int counter, String eventName, String event, List<Player> players, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playAllyCardsForBid(String test, Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(int counter, List<Player> winners, int[] playerPoints, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decideCardsToDiscard(int counter, int amount, Player player) {
		// TODO Auto-generated method stub
		if(counter == 0)
		{
			decideCardsToDiscard(amount, player);
		}
	}


	@Override
	public void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round, Player p) {
		// TODO Auto-generated method stub
	
		player.strat.bid(card, bidsFromCardsInPlay, biddersAmount, highestBid, round);
	}


	@Override
	public void decideCardsToDiscard(int amount, Player player) {
		// TODO Auto-generated method stub
		for(int i = 0; i < amount; i++)
		{
			player.deck.remove(player.deck.get(0));
		}
	}


	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		
		this.players = Model.players;
		
	}


	@Override
	public void displayStory(int counter, storyCard story, Player p) {
		// TODO Auto-generated method stub
		
	}


	


}
