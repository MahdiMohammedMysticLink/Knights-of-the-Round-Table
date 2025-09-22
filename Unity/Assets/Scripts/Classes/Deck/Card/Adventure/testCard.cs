using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class testCard : adventureCard {

    private int minBid;

    //Constructor
    public testCard(string name, int b) : base(name, "TEST")
    {
        minBid = b;
    }


    public int getMinBid()
    {
        return minBid;

    }
}
