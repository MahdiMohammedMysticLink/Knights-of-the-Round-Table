package com.COMP3004.Maven;

import java.util.List;
import java.util.ArrayList;

import com.COMP3004.Maven.PlayerDeck.AllyDeck;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

public class Utility {

	private AdventureDeck AD;

	public Utility() {

		AD = new AdventureDeck();

	}

	public List<String> playerDeckCards(PlayerDeck deck) {
		
		System.out.println("made it here!");

		List<String> cards = new ArrayList<String>();

		System.out.println("made it hereeeeeee!");
		
		for (int i = 0; i < deck.getSize(); i++) {
			System.out.println(i);

			cards.add(deck.get(i).getName());

		}
		Model.log("Player deck cards list: "+cards, "Utility", "playerDeckCards");
		return cards;

	}
	
	
	public String[] allyCards(AllyDeck deck) {

		String[] cards = new String[deck.getSize()];

		for (int i = 0; i < deck.getSize(); i++) {

			cards[i] = (deck.get(i).getName());

		}

		Model.log("Ally cards: "+cards, "Utility", "allyCards");
		return cards;

	}

	public PlayerDeck playerDeckCards(List<String> deck) {

		PlayerDeck cards = new PlayerDeck();

		for (int i = 0; i < deck.size(); i++) {

			cards.add(AD.find(deck.get(i)));

		}
		Model.log("Ally cards: "+cards, "Utility", "playerDeckCards");
		return cards;

	}

	public String[] playerNames(List<Player> playerList) {

		String players[] = new String[playerList.size()];

		for (int i = 0; i < playerList.size(); i++) {

			players[i] = playerList.get(i).getName();

		}

		Model.log("Player names: "+players, "Utility", "playerNames");
		return players;

	}

	public List<String>[] questStages(PlayerDeck stages[]) {

		List<String> stage;
		@SuppressWarnings("unchecked")
		List<String>[] stringStages = new List[stages.length];

		if (stages.length < 0) {

			return null;
		}

		for (int i = 0; i < stages.length; i++) {

			stage = playerDeckCards(stages[i]);
			stringStages[i] = stage;

		}

		Model.log("Quest stages names: "+stringStages, "Utility", "questStages");
		return stringStages;

	}

	public PlayerDeck[] questStages(List<String>[] stringStages) {
		System.out.println("In questStages...");
		PlayerDeck stage = null;
		PlayerDeck stages[] = new PlayerDeck[stringStages.length];

		System.out.println("stages count: "+stringStages.length);
		for (int i = 0; i < stringStages.length; i++) {

			stage = playerDeckCards(stringStages[i]);
			
			stages[i] = stage;

		}
		Model.log("Player Quest stages: "+stages, "Utility", "questStages");
		return stages;

	}

}
