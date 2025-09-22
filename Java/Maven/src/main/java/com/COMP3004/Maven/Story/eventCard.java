package com.COMP3004.Maven.Story;

import java.io.Serializable;

public class eventCard extends storyCard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// Constructor
	public eventCard(String name) {
		super("EVENT", name);
		behaviour = new eventBehaviour(this);
		// TODO Auto-generated constructor stub
	}

}
