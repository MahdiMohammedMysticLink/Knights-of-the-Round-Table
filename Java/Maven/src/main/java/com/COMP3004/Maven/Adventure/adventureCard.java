package com.COMP3004.Maven.Adventure;

import java.io.Serializable;

import com.COMP3004.Maven.Card;

public class adventureCard extends Card implements Comparable<adventureCard>, Serializable {
//this may not have to be serializable, trying to fix foeCard
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor
	public adventureCard(String type, String name) {
		super("ADVENTURE", type, name);
	}

	// Compare by name
	@Override
	public int compareTo(adventureCard c) {
		return name.compareTo(c.getName());
	}

}
