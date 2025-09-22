package com.COMP3004.Maven.Adventure;

import java.io.Serializable;

public class testCard extends adventureCard implements Comparable<adventureCard>, Serializable{

	// Properties
	private final int minBid;
	private static final long serialVersionUID = 1L;
	// Constructor
	public testCard(String name, int b) {
		super("TEST", name);
		minBid = b;
		// TODO Auto-generated constructor stub
	}

	public testCard(String name) {
		super("TEST", name);
		minBid = 1;
	}

	public final int getMinBid() {

		return minBid;

	}

	/*
	 * @Override public int compareTo(adventureCard c) {
	 * 
	 * 
	 * testCard other = (testCard)c;
	 * 
	 * return (this.minBid-other.getMinBid());
	 * 
	 * 
	 * }
	 * 
	 */

}
