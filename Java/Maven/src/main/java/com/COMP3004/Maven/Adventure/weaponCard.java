package com.COMP3004.Maven.Adventure;

import java.io.Serializable;

public class weaponCard extends adventureCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Properties
	private int points;

	// Constructor
	public weaponCard(String name, int p) {
		super("WEAPON", name);
		points = p;
		// TODO Auto-generated constructor stub
	}

	public int getPoints() {
		return points;
	}

	
	  public int compareTo(weaponCard c) { return (this.points-c.getPoints()); }
	  
	  
	 
}
