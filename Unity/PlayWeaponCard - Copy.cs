using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayWeaponCard : PlayAdventureCard{

	private WeaponCard weapon;

	public PlayWeaponCard(){

		weapon = (WeaponCard)adventureCard;
	}

	// Use this for initialization
	void Start () {

		weapon.choose(player);


	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
