package com.COMP3004.Maven.Story;

import java.io.Serializable;

public class tournamentCard extends storyCard implements Serializable {

	private static final long serialVersionUID = 1L;
	// Properties
	int bonusShields;

	// Constructor
	public tournamentCard(String name, int bS) {
		super("TOURNAMENT", name);
		bonusShields = bS;
		behaviour = new tournamentBehaviour(this);
	}

	public int getShields() {
		return bonusShields;
	}

	@Override
	public String toString() {
		return "tournamentCard [bonusShields=" + bonusShields + "]";
	}
	
}
