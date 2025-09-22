using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Sponsor : Player {


    public Sponsor(Player p) : base(p.name, p.rank, p.type) {
        deck = p.deck;
    }

    public void questSetup(questCard q, ref List<adventureCard>[] stages, ref int cardsSpent)
    {
        //How much cards does the sponsor start off with?
        cardsSpent = this.deck.getSize();

        //Set up in terms of stages
        for (int i = 0; i < stages.Length; i++)
        {
            //Display which stage# you are setting up

            bool isFinished = false;
            while (!isFinished)
            {
                adventureCard c = null;
                //Display in center screen only For and Test Card in player's possession

                //Have player select card

                int pointsLastRound = 0;
                //Check what kind of card was selected
                if (c.getType() == "Foe")
                {
                    foeCard f = (foeCard)(c);
                    int points = 0;
                    stages[i].Add(c);

                    bool finishedSel = false;
                    while (!finishedSel)
                    {
                        //Display only sponsor's Weapon cards
                        //Have player select a weapon card, suppose c becomes this card
                        if (c.name == "Battle Axe" && stages[i].Contains(c))
                        {
                            //Display to user can't play more than one Battle Axe
                        }
                        else
                        {
                            //Add weapon card to stage
                            stages[i].Add(c);
                            q.calculateFoePoints(stages[i], ref points);
                        }

                        if (points > pointsLastRound)
                        {
                            //Prompt to player if is done setting up this round
                            finishedSel = true;//if yes
                        }

                    }
                }
                else if (c.getType() == "Test")
                {
                    stages[i].Add(c);
                }
            }
        }

        cardsSpent -= this.deck.getSize(); //Computed difference gives cards spent for setup
    }

}
