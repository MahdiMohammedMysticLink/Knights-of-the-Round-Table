using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class knightCard : rankCard
{
    // second level 
    private int level = 2;
    private int points;

    public knightCard(string name, int p) : base(name, "KNIGHT")
    {
        this.points = p;
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

    public int getLevel() { return level; }


}

