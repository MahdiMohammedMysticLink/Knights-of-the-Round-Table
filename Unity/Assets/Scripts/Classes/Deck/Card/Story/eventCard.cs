using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class eventCard : storyCard {

	public eventCard(string name) : base(name, "EVENT")
    { this.playBehavior = new playEventBehaviour(this);
    }

}
