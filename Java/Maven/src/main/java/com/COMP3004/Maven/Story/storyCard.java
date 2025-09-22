package com.COMP3004.Maven.Story;

import java.io.Serializable;
import java.util.List;

import com.COMP3004.Maven.Card;
import com.COMP3004.Maven.Player;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

public abstract class storyCard extends Card implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	
	storyBehaviour behaviour;

	// Constructor
	public storyCard(String type, String name) {
		super("STORY", type, name);
	}

	public void play(Player current, List<Player> players, AdventureDeck aD, int bonusShields) {
		behaviour.play(current, players, aD, bonusShields);
	}
}
