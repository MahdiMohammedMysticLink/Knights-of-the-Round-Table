using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public abstract class storyCard : Card{

	
	
    protected string storyCardType;	
    //Constructor
	public storyCard(string name, string storyCardType) : base(name, "STORYCARD")
    {
	
	this.storyCardType = storyCardType;
	
	}

   public string getStoryType(){return storyCardType;}	
	
}
