package com.COMP3004.Maven.Story;

import java.io.Serializable;

public class questCard extends storyCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Properties
	int numStages;
	String specialFoesKeyword;
	// Can use keyword to see if any Foe is special to the quest
	// ie.Foe.name.Contains(specialKeyword)

	// Constructor
	public questCard(String name, int n, String k) {
		super("QUEST", name);
		numStages = n;
		specialFoesKeyword = k;
		behaviour = new questBehaviour(this);
	}

	public int getNumStages() {
		return numStages;
	}

	public String getSpecialFoe() {
		return specialFoesKeyword;
	}

	@Override
	public String toString() {
		return "questCard [numStages=" + numStages + ", specialFoesKeyword=" + specialFoesKeyword + "]";
	}
	
}
