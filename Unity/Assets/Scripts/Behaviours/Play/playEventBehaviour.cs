using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class playEventBehaviour : playStoryBehaviour
{

    public playEventBehaviour(eventCard e) : base(e)
    {}

    public override void play(ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields)
    {
        List<Player> lowestList = new List<Player>();
        string name = card.name;

        //Chivalrous Deed
        if (name == "Chivalrous Deed")
        {
            //Make sure references are used
            List<Player> lowLowestList = new List<Player>();

            lowestList.Add(players[0]);

            //Find lowest ranked player(s) in session
            for (int i = 1; i < players.Length; i++)
            {
                //If the current player is of a lower rank than the one in the list
                /*  Champion Knight
                 *  Knight
                 *  Squire
                 */
                /*if (lowestList[0].rank.CompareTo(players[i].rank) > 1)
                {
                    //Clear array of higher ranked players, add the lower ranked player
                    lowestList.Clear();
                    lowestList.Add(players[i]);
                }
                //If the current player is of the same rank than the one in the list
                else if (lowestList[0].rank == players[i].rank)
                {
                    lowestList.Add(players[i]);
                }*/
            }

            //Compare players with the lowest rank against shield count
            lowLowestList.Add(lowestList[0]);
            for (int i = 1; i < lowestList.Count; i++)
            {
                if (lowLowestList[0].shields > lowestList[i].shields)
                {
                    //Clear array of richer players, add poorer player
                    lowLowestList.Clear();
                    lowLowestList.Add(lowestList[i]);
                }
                //If the current player is of the same wealth than the one in the list
                else if (lowLowestList[0].shields == lowestList[i].shields)
                {
                    lowLowestList.Add(lowestList[i]);
                }
            }

            //Award 3 shields to each player with the lowest rank and shield count
            for (int i = 0; i < lowLowestList.Count; i++)
            {
                lowLowestList[i].shields += 3;
            }

        }
        /*//Pox
        else if (name == "Pox")
        {
            //All players except the drawing player loses one shield
            for (int i = 0; i < players.Length; i++)
            {
                if (players[i] != currPlayer)
                    players[i].shields--;

                if (players[i].shields < 0)
                    players[i].shields = 0;
            }

        }
        //Plague
        else if (name == "Plague")
        {
            //Drawer loses two shields if possible
            currPlayer.shields--;
            currPlayer.shields--;

            if (currPlayer.shields < 0)
                players[i].shields = 0;

        }
        //King's Recognition
        else if (name == "King's Recognition")
        {
            bonusShields += 2;
        }
        //Queen's Favor
        else if (name == "Queen's Favor")
        {
            lowestList.Add(players[0]);
            //Find lowest ranked player(s) in session
            for (int i = 1; i < players.length; i++)
            {
                if (lowestList[0].rank.CompareTo(players[i].rank) > 1)
                {
                    //Clear list then add
                    lowestList.Clear();
                    lowestList.Add(players[i]);
                }
                else if (lowestList[0].rank == players[i].rank)
                {
                    lowestList.Add(players[i]);
                }
            }

            //Players with the lowest rank immediately draws two Adventure Cards
            for (int i = 0; i < lowestList.Count; i++)
            {
                lowestList[i].drawCard();
                lowestList[i].drawCard();
            }

        }
        //Court Called to Camelot
        else if (name == "Court Called to Camelot")
        {
            //All allies are discarded, let's say there's a global collection of allies in play
            for (int i = 0; i < allies.length; i++)
            {
                allies[i].discard();
            }

        }
        //King's Call to Arms
        else if (name == "King's Call to Arms")
        {
            List<Player> highestList;

            highestList.Add(players[0]);

            //Find highest ranked player(s) in session
            for (int i = 1; i < players.length; i++)
            {
                if (highestList[0].rank < players[i].rank)
                {
                    //Clear then add
                    highestList.Clear();
                    highestList.Add(players[i]);
                }
                else if (highestList[0].rank == players[i].rank)
                {
                    highestList.Add(players[i]);
                }
            }

            for (int i = 0; i < highestList.length; i++)
            {
                //Force player to discard Weapon Card, player.weaponCardCount?
                bool isDiscarded = players[i].discard("Weapon");

                //If not possible, discard 2 Foe Cards, player.foeCardCount?
                if (!isDiscarded)
                {
                    players[i].discard("Foe");
                    players[i].discard("Foe");
                }

            }

        }*/
        //Prosperity Throughout the Realm
        else if (name == "Prosperity Throughout the Realm")
        {
            //All players must draw 2 Adventure Cards
            for (int i = 0; i < players.Length; i++)
            {
                players[i].drawCard(deck);
                players[i].drawCard(deck);
            }

        }
    }
}
