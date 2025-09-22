using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


class Quest 
{

        public int numberOfPlayers;
        public string name;

        // Constructor 1
        public Quest(string name)
        {
            this.name = name;
        }

        // Constructor 2
        public Quest(string name, ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields)
        {
            this.name = name;
        }

        public void play()
        {
            // still to implement 
            throw new NotImplementedException();
        }
 
}

