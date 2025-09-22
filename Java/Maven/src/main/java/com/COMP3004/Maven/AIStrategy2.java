package com.COMP3004.Maven;

import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Adventure.adventureCard;
import com.COMP3004.Maven.Adventure.foeCard;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.PlayerDeck.WeaponDeck;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.tournamentCard;

public class AIStrategy2 extends AIStrategy {
	
	public AIStrategy2(AI ai)
	{
		super(ai);
	}

	@Override
	void doIParticipate(tournamentCard tourney) {
		player.isParticipating = true;
	}

	@Override
	void fight(tournamentCard tourney, int round) {

		player.cardsCommittedToFight = new PlayerDeck();
		
		//Only one Amour per Story
		player.cardsCommittedToFight.add(player.deck.getAmours().get(0));
		
		//Add weapons
		int i = 0;
		WeaponDeck weapons = player.deck.getWeapons();
		weapons.sortHighest();
		while(player.cardsCommittedToFight.calculatePoints() < 50 && i < weapons.getSize())
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
		int i = 0;
		//Last stage
		while(points < 40 && i < weapons.getSize())
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
		if(player.deck.getTests().getSize() > 0){player.selectedQuestStages[numStages - 2].add(player.deck.getTests().get(0));}
		else
		{
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
		
		player.deck.getFoes().sortLowest();
		player.deck.getWeapons().sortLowest();
		for(i = 0; i < stages.length - 2; i++)
		{
			int nextPoints = 0;
			if(i == 0)
			{
				points = player.deck.getFoes().get(2 + i).getStat(0);
				stages[i].add(player.deck.getFoes().get(2 + i));
			}
			else
			{
				foeCard foe = null;
				int j = 0;
				while(nextPoints < points)
				{
					foe = player.deck.getFoes().get(2 + i + j);
					nextPoints = foe.getStat(0);
					j++;
				}
				points = nextPoints;
				stages[i].add(player.deck.getFoes().get(2 + i + j));
			}
			
		}
		
		return points;
	}

	@Override
	void doIParticipate(PlayerDeck[] questStages, questCard quest) {
		int count = 0; 
		Model.log("Do i participate in tournament", "AIStrategy2", "doIParticipate");
		for(int i = 0; i < player.deck.getFoes().getSize(); i++)
		{
			if( player.deck.getFoes().get(i).getStat(0) < 20)
			{
				count++;
			}
			if(count == 2 &&  player.deck.getWeapons().calculatePoints() > 100)
			{
				Model.log("Participate player name is "+player.getName(), "AIStrategy2", "doIParticipate");
				 player.isParticipating = true;
				return;
			}
		}
		 player.isParticipating = false;
		
	}

	@Override
	void fight(PlayerDeck enemies, int stage) {
		
		int points = 0;
		Model.log("In fight, stage: "+stage, "AIStrategy2", "fight");
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
					Model.log("cards committed don't contain that card already, then add: "+weapons.get(i).getName(), "AIStrategy2", "fight");
					player.cardsCommittedToFight.add(weapons.get(i));
					points += weapons.get(i).getPoints();
				}
				i++;
			}
		}
		else
		{
			if(player.deck.getAmours().getSize() > 0)	
				player.cardsCommittedToFight.add(player.deck.getAmours().get(0));
			if(player.deck.getAllies().getSize() > 0) 	
				player.cardsCommittedToFight.add(player.deck.getAllies().get(0));
			
			WeaponDeck weapons = player.deck.getWeapons();
			int i = 0;
			while(player.cardsCommittedToFight.calculatePoints() < enemies.calculatePoints())
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
		
		biddersAmount[0] = 0;
		
		if(round == 1)
		{
			List<foeCard> foes = new ArrayList<>();
			//Bid foes < 25
			for(int i = 0; i < player.deck.getFoes().getSize(); i++)
			{
				//Do not include duplicates
				if(player.deck.getFoes().get(i).getStat(0) < 25 && !foes.contains(player.deck.getFoes().get(i)))
				{
					biddersAmount[0]++;
					foes.add(player.deck.getFoes().get(i));
					Model.log("Only include unigue cards", "AIStrategy2", "bid");
				}
			}
		}
		else
		{
			Model.log("Bid foes < 25 including duplicates", "AIStrategy2", "bid");
			//Bid foes < 25 including duplicates
			for(int i = 0; i < player.deck.getFoes().getSize(); i++)
			{
				if(player.deck.getFoes().get(i).getStat(0) < 25)
				{
					biddersAmount[0]++;
				}
			}
		}
	}

	@Override
	List<adventureCard> discardAfterWinning(int round){
		
		List<adventureCard> discardPile = new ArrayList<adventureCard>();
		if(round == 1)
		{
			//Discard foes < 25 points
			player.deck.getFoes().sortLowest();
			for(int i = 0; i < player.deck.getFoes().getSize(); i++)
			{
				if(player.deck.getFoes().get(i).getStat(0) < 25)
				{
					//Discard card
					discardPile.add(player.deck.getFoes().get(i));
					player.deck.remove(player.deck.getFoes().get(i));
					Model.log("Discard foes < 25 points ", "AIStrategy2", "discardAfterWinning");
				}
			}
		}
		else
		{
			//Discard foes from round 1 and all duplicates
		}
		
		return discardPile;
	}

}
