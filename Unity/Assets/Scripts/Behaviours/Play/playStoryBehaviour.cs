using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public abstract class playStoryBehaviour : playBehaviour
{
    public storyCard card;

    public playStoryBehaviour(storyCard s) { card = s; s.playBehavior = this; }

    //public abstract void play(ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields);
}
