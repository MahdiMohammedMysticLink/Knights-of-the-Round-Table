package com.COMP3004.Maven.Adventure;

import java.io.Serializable;

public class foeCard extends adventureCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Properties
	int[] stats = new int[2];

	// Normal Foe Constructor
	public foeCard(String name, int s1) {
		super("FOE", name);
		stats[0] = s1;
		stats[1] = s1;
		// TODO Auto-generated constructor stub
	}

	// Special Foe Constructor
	public foeCard(String name, int s1, int s2) {
		super("FOE", name);
		stats[0] = s1;
		stats[1] = s2;
		// TODO Auto-generated constructor stub
	}

	
	  public int compareTo(foeCard c) {return ((this.stats[0])- (c.getStat(0)));}
	  
	 

	public int getStat(int i) {
		return stats[i];
	}

}
