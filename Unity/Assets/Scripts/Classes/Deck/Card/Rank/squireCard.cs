using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class squireCard : rankCard
{
    // first level 
    int level = 1;
    int points;

    public squireCard(string name, int p) : base(name, "SQUIRE")
    {
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
