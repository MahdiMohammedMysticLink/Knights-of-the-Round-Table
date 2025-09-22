using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class tournamentCard : storyCard {

    private int bonusShields;

    public tournamentCard(string name, int bS) : base(name, "TOURNAMENT")
    {
        bonusShields = bS;
        this.playBehavior = new playTournamentBehaviour(this);
    }

    public int getBonusShields() { return bonusShields; }
}
