using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public abstract class playBehaviour{

    public playBehaviour() { }

    public virtual void play() { }
    public virtual void play(ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields) { }
}
