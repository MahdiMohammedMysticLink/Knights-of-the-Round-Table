using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Card: Entity{

    protected string deck;


    //Constructor
    public Card(string n = "", string d = "", playBehaviour p = null):base(n, "CARD", p)
    {
        deck = d;
    }



    public string getDeck()
    {

        return deck;
    }



}

