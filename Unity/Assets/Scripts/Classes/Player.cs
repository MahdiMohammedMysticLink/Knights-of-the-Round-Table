using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player{

    public string name, type;
    public int shields;

    public rankCard rank;
    public AdventureDeck deck; //Since the player can only hold Adventure Cards in a personal hand/deck
    public AdventureDeck cardsInPlay;

    public Player(string n, rankCard r, string t)
    {
        name = n;
        rank = r;
        type = t;
        shields = 0;
    }

    public int computePoints()
    {
        //return cardsInPlay.computePoints() + shields;
        return 0;
    }

    public void fightFoe(ref int points, ref List<adventureCard> cardsToPlay)
    {
        points = 0;
        points += this.shields; //Attained from rank


        //Select cards to play, ONLY AMOUR, ALLY , WEAPON
        /* For every card selected
         * card.Add(selectedCard);
         * points += selectedCard.points;
         */
         
        
    }

    public bool discardCard(string type)
    {
        if(type == "WEAPON")
        {
            //Have player discard a weapon from deck
            /* if(player discards a weapon card)
                    return true;
            */
        }
        else if(type == "FOE")
        {
            //Have player discard a foe from deck
            /* if(player discards a foe card)
                    return true;
            */
        }

        return false;
    }


    //Player draws card from Adventure Deck
    public void drawCard(Stack<adventureCard> d)
    {
        //deck.Add(d.Pop());
    }
}
