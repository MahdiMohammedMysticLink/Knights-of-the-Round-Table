package com.COMP3004.Maven;

import java.io.Serializable;

public abstract class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Properties
	protected String name, type, deck;

	// Constructor
	public Card(String d, String t, String n) {
		name = n;
		type = t;
		deck = d;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDeck() {
		return deck;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + ", type=" + type + ", deck=" + deck + "]";
	}
	
}
