package com.COMP3004.Maven.Rank;

import java.io.Serializable;

import com.COMP3004.Maven.Card;

public class rankCard extends Card implements Comparable<rankCard>, Serializable{

	
	private static final long serialVersionUID = 1L;
	
	protected int value;

	public rankCard(String n, int v) {
		super("RANK", "RANK", n);
		value = v;
		// TODO Auto-generated constructor stub
	}

	public int compareTo(rankCard c) {
		// Comparing 'this' relative to card c
		return name.compareTo(c.name);
	}
	
	
	public int getPoints() {return value;}

}
