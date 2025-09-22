using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class amourCard : adventureCard {

    int points, bonus;
    string bonusType; //Could be a bid or something else

    //Constructor
    public amourCard(string name, int p, int b, string bT) : base(name, "ARMOUR")
    {
        points = p;
        bonus = b;
        bonusType = bT;
    }


    public int getPoints() { return points; }


    public int setPoints(int p)
    {

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


    public int getBonus() { return bonus; }

    public string getBonusType() { return bonusType; }
}


