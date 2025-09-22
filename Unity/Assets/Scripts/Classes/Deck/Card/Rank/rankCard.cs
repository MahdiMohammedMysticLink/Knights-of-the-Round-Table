using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class rankCard : Card
{

    protected string rankType;

    public rankCard(string n, string rankType) : base(n, "RANKCARD")
    {

        this.rankType = rankType;

    }

    public string getRankType()
    {
        return rankType;

    }
}

