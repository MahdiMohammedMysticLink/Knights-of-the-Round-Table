using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class championKnightCard : rankCard
{
    // third level 
    private int level;
    private int points;

    public championKnightCard(string name, int p) : base(name, "CHAMPIONKNIGHT")
    {

        this.level = 3;
        this.points = p;
    }

    public int getLevel() { return level; }

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


}
