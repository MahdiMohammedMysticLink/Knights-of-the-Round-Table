package com.COMP3004.Maven;

import java.io.Serializable;

import com.COMP3004.Maven.Rank.rankCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

public class AI extends Player implements Serializable{
	
	AIStrategy strat;
	private static final long serialVersionUID = 1L;
	
	
	public AI(String n, rankCard r, AdventureDeck AD, int stratNum) {
		super(n, r, AD);
		
		hasFinishedTurn = true;
		behaviour = new aiBehaviour(this);
		if(stratNum == 1)
		{
			strat = new AIStrategy1(this);
		}
		else
		{
			strat = new AIStrategy2(this);
		}
	}

	public AI() {
		// TODO Auto-generated constructor stub
	}


}
