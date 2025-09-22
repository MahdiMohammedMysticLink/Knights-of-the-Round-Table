using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class questCard : storyCard {

    Utilities utilities;
    public int numStages;
    List<foeCard> specialFoes;
    public Sponsor sponsor;

	public questCard(string name, int n, List<foeCard> foes) : base(name, "QUEST")
    {
        numStages = n;
        specialFoes = foes;
        sponsor = null;
        this.playBehavior = new playQuestBehaviour(this);
    }

    public void calculateFoePoints(List<adventureCard> f, ref int battlePoints)
    {
        foeCard foe = (foeCard)f[0];

        int[] tempStats = foe.getStats();

        if (name == "Defend the Queen's Honor" || name == "Search for the Holy Grail" || specialFoes.Contains(foe))
        {


            //Special foes associated to Quest Card
            battlePoints = tempStats[1];
        }
        battlePoints = tempStats[0];

        //Assuming the other cards played with foe are weapon cards
        for (int j = 1; j < f.Count; j++)
        {
            weaponCard w = (weaponCard)(f[j]);
            //Add Weapon battle points to foes total 
            battlePoints += w.getPoints();
        }
    }




}
