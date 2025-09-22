using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class foeCard : adventureCard {

    private int[] stats = new int[2];

    //Constructor
    public foeCard(string name, int s1, int s2 = 0) : base(name, "FOE")
    {
        //If foe only has one stat
        if (s2 == 0)
            s2 = s1;

        stats[0] = s1;
        stats[1] = s2;
    }



    public int[] getStats()
    {

        return stats;
    }



    public int[] setStats(int s1, int s2)
    {


        stats[0] = s1;
        stats[1] = s2;

        return stats;

    }


    public int[] addStats(int s1, int s2)
    {

        stats[0] += s1;
        stats[1] += s2;


        return stats;
    }


    public int[] substractStats(int s1, int s2)
    {

        stats[0] -= s1;
        stats[1] -= s2;

        return stats;


    }
 

}
