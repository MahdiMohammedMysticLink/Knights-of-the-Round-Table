package com.COMP3004.Maven;

import java.util.List;

import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.Rank.championKnightCard;
import com.COMP3004.Maven.Rank.knightCard;
import com.COMP3004.Maven.Rank.squireCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.tournamentCard;

public abstract class AIStrategy {
	
	AI player;
	
	public AIStrategy(AI ai)
	{
		player = ai;
	}

	abstract void doIParticipate(tournamentCard tourney);
	abstract void fight(tournamentCard tourney, int round);
	abstract int questSetup(questCard q, List<adventureCard>[] stages);
	abstract void doIParticipate(PlayerDeck[] questStages, questCard quest);
	abstract void fight(PlayerDeck enemies, int stage);
	abstract void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round);
	abstract List<adventureCard> discardAfterWinning(int round);
	
	public void doISponsor(questCard quest, List<Player> players){
		Model.log("In sponser", "AIStrategy", "doISponsor");
		for(int i = 0; i < players.size(); i++)
		{
			Player p = players.get(i);
			int possibleShields = p.shields + quest.getNumStages();
			if((p.rank == new squireCard() && possibleShields  >= 5)|| (p.rank == new knightCard() && possibleShields  >= 7) || (p.rank == new championKnightCard() && possibleShields  >= 10))
			{
				player.isParticipating = true;
			}
		}
		
		if(player.deck.getFoes().getSize() > (quest.getNumStages() - 1) && player.deck.getTests().getSize() > 0)
		{
			Model.log("Player is sponsoring ", "AIStrategy", "doISponsor");
			player.isSponsoring = true;
			player.questSetup(quest, null);
		}
		else
		{
			player.isSponsoring = false;
			Model.log("Player is not sponsoring ", "AIStrategy", "doISponsor");
		}
	}
}
