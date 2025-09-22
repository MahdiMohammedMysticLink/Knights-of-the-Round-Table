package com.COMP3004.Maven;

import com.COMP3004.Maven.Adventure.*;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;

import java.util.List;

public interface playerBehaviour {

	public void fight(int counter, PlayerDeck enemies, int stage, Player player);

	//public void decideCardsToDiscard(int amount);
	// General
	public void doIParticipate(int counter, PlayerDeck[] questStages, questCard quest, Player player);

	
	public void doIParticipate(int counter, tournamentCard tourney, Player player);

	// Specific to Quests
	public void doISponsor(questCard quest, Player player);

	public int questSetup(questCard q, List<adventureCard>[] stages);

	public int nextBid();

	public void discardAfterWinning(int round, int amount, Player p);

	public void seeCardsEndOfStage(int counter, PlayerDeck stage, PlayerDeck cardsAgainstFoes, String result, Player player);

	
	public void display(int counter, String eventName, String event, List<Player> players, Player p);
	
	public void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round, Player p);


	public void playAllyCardsForBid(String test, Player player);

	

	public void fight(int counter, tournamentCard tourney, int round, Player p);

	public void display(int counter, List<Player> winners, int[] playerPoints, Player p);

	public void decideCardsToDiscard(int counter, int amount, Player player);

	public void decideCardsToDiscard(int amount, Player player);

	public void updateUI();

	public void displayStory(int counter, storyCard story, Player p);

	public void doIParticipate(PlayerDeck[] questStages, questCard quest, Player p);

	public void doIParticipate(tournamentCard tourney, Player player);
}
