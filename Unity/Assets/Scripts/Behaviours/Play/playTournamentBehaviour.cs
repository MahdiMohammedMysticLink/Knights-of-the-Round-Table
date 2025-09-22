using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


class playTournamentBehaviour : playStoryBehaviour
{

    // Constructor
    public playTournamentBehaviour(tournamentCard t) : base(t)
    {
        this.card = t;
        t.playBehavior = this;
    }

    // Constructor 2
    public override void play(ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields)
    {
    }
}

