using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class weaponCard : adventureCard {

    private int points; //Hit points

    //Constructor
    public weaponCard(string name, int p) : base(name, "WEAPON")
    {
        points = p;
    }


    public int getPoints() { return points; }

    public int setPoints(int p) {

        points = p;

        return points;

    }

    public int addPoints(int p)
    {

        points += p;

        return points;
    }

    public int subtractPoints(int p)
    {

        points -= p;

        return points;

    }
}
