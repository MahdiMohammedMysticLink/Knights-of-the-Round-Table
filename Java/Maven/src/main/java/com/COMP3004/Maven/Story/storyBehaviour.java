package com.COMP3004.Maven.Story;

import com.COMP3004.Maven.Player;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import java.util.List;

public interface storyBehaviour {
	public void play(Player curr, List<Player> players, AdventureDeck deck, int bonusShields);
	
	
	
}
