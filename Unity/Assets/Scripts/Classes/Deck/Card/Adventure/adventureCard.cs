using System.Collections;
using System.Collections.Generic;
using UnityEngine;
public class adventureCard : Card{

    protected string adventureType;
    //Constructor
    public adventureCard(string name, string adventureType): base(name, "ADVENTURE")
    {

        this.adventureType = adventureType;


    }


    public string getAdventureType()
    {

        return adventureType;

    }
}
