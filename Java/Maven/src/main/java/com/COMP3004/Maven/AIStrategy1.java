package com.COMP3004.Maven;

import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.PlayerDeck.WeaponDeck;
import com.COMP3004.Maven.Rank.championKnightCard;
import com.COMP3004.Maven.Rank.knightCard;
import com.COMP3004.Maven.Rank.squireCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.tournamentCard;

public class AIStrategy1 extends AIStrategy {

	public AIStrategy1(AI ai)
	{
		super(ai);
	}
	
	@Override
	void doIParticipate(tournamentCard tourney) {
		//If someone can evolve, shields + 1 = new Rank, then participate
		List<Player> players = Model.players;
		Model.log("Do i participate in tournament", "AIStrategy1", "doIParticipate");
		for(int i = 0; i < players.size(); i++)
		{
			Player p = players.get(i);
			int possibleShields = p.shields + 1 + tourney.getShields();
			if((p.rank == new squireCard() && possibleShields  >= 5)|| (p.rank == new knightCard() && possibleShields  >= 7) || (p.rank == new championKnightCard() && possibleShields  >= 10))
			{
				Model.log("Participate player name is "+player.getName(), "AIStrategy1", "doIParticipate");
				player.isParticipating = true;
				return;
			}
		}
		player.isParticipating = false;
	}

	@Override
	void fight(tournamentCard tourney, int round) {

		Model.log("In fight, tourney: "+tourney.getName()+", rount: "+round, "AIStrategy1", "fight");
		player.cardsCommittedToFight = new PlayerDeck();
		int possibleShields = player.shields + 1 + tourney.getShields();
		if((player.rank == new squireCard() && possibleShields  >= 5)|| (player.rank == new knightCard() && possibleShields  >= 7) || (player.rank == new championKnightCard() && possibleShields  >= 10))
		{
			//Add Ally
			player.cardsCommittedToFight.add(player.deck.getAllies().get(0));
			//Add Amour
			player.cardsCommittedToFight.add(player.deck.getAmours().get(0));
			//Add Strongest Weapon
			player.deck.getWeapons().sortHighest();
			player.cardsCommittedToFight.add(player.deck.getWeapons().get(0));
			return;
		}
		
		//Else, add weapons that have duplicates
		int i = 0;
		WeaponDeck weapons = player.deck.getWeapons();
		while(i < weapons.getSize())
		{
			//If the cards committed don't contain that card already, then add
			if(!player.cardsCommittedToFight.isFound(weapons.get(i).getName()))
			{
				player.cardsCommittedToFight.add(weapons.get(i));
			}
			i++;
		}
		
	}

	@Override
	int questSetup(questCard q, List<adventureCard>[] stages) {

		player.deck.getFoes().sortHighest();
		player.deck.getWeapons().sortHighest();
		
		int numStages = q.getNumStages();
		
		int points = player.deck.getFoes().get(0).getStat(0);
		WeaponDeck weapons = player.deck.getWeapons();
		
		Model.log("Quest setup, weapons: "+weapons.getSize()+", foes points: "+points, "AIStrategy1", "questSetup");
		int i = 0;
		//Last stage
		while(points < 50 && i < weapons.getSize())
		{
			//If the cards in stage don't contain that card already, then add
			if(!player.selectedQuestStages[numStages - 1].isFound(weapons.get(i).getName()))
			{
				player.selectedQuestStages[numStages - 1].add(weapons.get(i));
				points += weapons.get(i).getPoints();
			}
			i++;
		}
		
		//second last stage
		if(player.deck.getTests().getSize() > 0){
			player.selectedQuestStages[numStages - 2].add(player.deck.getTests().get(0));
			Model.log("2nd last stage, player tests", "AIStrategy1", "questSetup");
		}
		else
		{
			Model.log("2nd last stage", "AIStrategy1", "questSetup");
			int nextPoints = player.deck.getFoes().get(1).getStat(0);
			while(nextPoints < points - 10 && i < weapons.getSize())
			{
				//If the cards in stage don't contain that card already, then add
				if(!player.selectedQuestStages[numStages - 1].isFound(weapons.get(i).getName()))
				{
					player.selectedQuestStages[numStages - 2].add(weapons.get(i));
					nextPoints += weapons.get(i).getPoints();
				}
				i++;
			}
			points = nextPoints;
		}
		
		for(i = stages.length - 3; i >= 0; i--)
		{
			int nextPoints = player.deck.getFoes().get(i).getStat(0);
			while(nextPoints < points - 10 && i < weapons.getSize())
			{
				//If the cards in stage don't contain that card already, then add
				if(!stages[i].contains(weapons.get(i)))
				{
					stages[i].add(weapons.get(i));
					nextPoints += weapons.get(i).getPoints();
				}
				i++;
			}
			points = nextPoints;
		}
		return points;
	}

	@Override
	void doIParticipate(PlayerDeck[] questStages, questCard quest)
	{
		int count = 0; 
		for(int i = 0; i < player.deck.getFoes().getSize(); i++)
		{
			if(player.deck.getFoes().get(i).getStat(0) < 20)
			{
				count++;
			}
			if(count == 2 && (player.deck.getWeapons().getSize() + player.deck.getAllies().getSize()) > (2 * quest.getNumStages()))
			{
				player.isParticipating = true;
				return;
			}
		}
		player.isParticipating = false;	
	}

	@Override
	void fight(PlayerDeck enemies, int stage){

		int points = 0;
		//Fighting foes
		if(stage >= 3)
		{
			int i = 0;
			player.deck.getWeapons().sortHighest();
			WeaponDeck weapons = player.deck.getWeapons();
			while(i < weapons.getSize() && points < enemies.calculatePoints())
			{
				//If the cards committed don't contain that card already, then add
				if(!player.cardsCommittedToFight.isFound(weapons.get(i).getName()))
				{
					player.cardsCommittedToFight.add(weapons.get(i));
					points += weapons.get(i).getPoints();
				}
				i++;
			}
		}
		else
		{
			if(player.deck.getAllies().getSize() > 1)
			{
				player.cardsCommittedToFight.add(player.deck.getAllies().get(0));
				player.cardsCommittedToFight.add(player.deck.getAllies().get(1));
			}
			else if(player.deck.getAllies().getSize() > 0)
			{
				player.cardsCommittedToFight.add(player.deck.getAllies().get(0));
			}
			
			player.deck.getWeapons().sortLowest();
			WeaponDeck weapons = player.deck.getWeapons();
			int i = 0;
			while(player.cardsCommittedToFight.getSize() < 2)
			{
				//If the cards committed don't contain that card already, then add
				if(!player.cardsCommittedToFight.isFound(weapons.get(i).getName()))
				{
					player.cardsCommittedToFight.add(weapons.get(i));
					points += weapons.get(i).getPoints();
				}
				i++;
			}
		}
		
	}
	@Override
	void bid(String card, int bidsFromCardsInPlay, int[] biddersAmount, int highestBid, int round) {
		//Bid only once
		if(player.deckSize() > highestBid + 1)
			biddersAmount[0] = highestBid + 1;
		else 
			biddersAmount[0] = 0;
	}

	@Override
	List<adventureCard> discardAfterWinning(int round) {
		//Discard foes < 20 points
		player.deck.getFoes().sortLowest();
		List<adventureCard> discardPile = new ArrayList<adventureCard>();
		for(int i = 0; i < player.deck.getFoes().getSize(); i++)
		{
			if(player.deck.getFoes().get(i).getStat(0) < 20)
			{
				//Discard card
				discardPile.add(player.deck.getFoes().get(i));
				player.deck.remove(player.deck.getFoes().get(i));
				Model.log("Discard foes < 20 points ", "AIStrategy1", "discardAfterWinning");
			}
		}
		
		return discardPile;
	}

}
