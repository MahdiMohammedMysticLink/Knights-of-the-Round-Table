using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Sponsor : PlayTurn {

	private Quest quest;
	private Card cardsChosen[];


	public Sponsor (Card quest)
	{
		quest = (Quest)quest;

	}

	// Use this for initialization
	void Start () {

		cardsChosen[] = quest.playerChooses(player) 
		quest.initialize(cardsChosen);

	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
