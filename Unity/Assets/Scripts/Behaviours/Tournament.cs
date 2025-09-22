using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


class Tournament : Play
{
    public int numberOfPlayers;
    public string name;

    // Constructor 1
    public Tournament(string name)
    {
        this.name = name;
    }

    // Constructor 2
    public Tournament(string name, ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields)
    {
        this.name = name;
    }

    public override void play()
    {
        // still to implement 
        throw new NotImplementedException();
    }
}

