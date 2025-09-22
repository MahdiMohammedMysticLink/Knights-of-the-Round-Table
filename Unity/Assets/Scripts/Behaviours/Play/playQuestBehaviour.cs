using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class playQuestBehaviour : playStoryBehaviour
{

    Sponsor sponsor;

    public playQuestBehaviour(questCard q) : base(q)
    { sponsor = q.sponsor; }

    public override void play(ref Player[] players, ref Player currPlayer, ref Stack<adventureCard> deck, ref int bonusShields)
    {
        questCard quest = (questCard)card;

        int numStages = quest.numStages;

        selectSponsor(ref quest.sponsor, ref players);

        //Was a sponsor chosen?
        if (sponsor != null)
        {

            if (sponsor != currPlayer && sponsor.type == "Human")
            {
                //Display to screen that sponsor needs to setup Quest

            }

            List<adventureCard>[] stages = new List<adventureCard>[numStages];
            int cardsSpent = 0; //Assume no cards spent

            //Sponsor sets up the quest
            sponsor.questSetup(quest, ref stages, ref cardsSpent);

            //Determine which players are playing
            List<Player> participants = new List<Player>();
            selectParticipants(ref participants, ref players);

            //Allow players to play stages
            playQuest(ref participants, stages, ref deck);

            //Award successful players with shields
            for (int i = 0; i < participants.Count; i++)
            {
                participants[i].shields += numStages + bonusShields;
            }

            //Sponsor draws (cards spent + numStages) Adventure Cards
            for (int i = 0; i < (cardsSpent + numStages); i++)
            {
                sponsor.drawCard(deck);
            }

            //Reset bonusShield count
            bonusShields = 0;

        }

        //Quest End
    }

    void selectSponsor(ref Sponsor sponsor, ref Player[] players)
    {
        for (int i = 0; i < players.Length; i++)
        {
            string ans = "yes";//Prompt the players if they want to sponsor quest

            if (ans == "yes")
            {
                sponsor = new Sponsor(players[i]);
                break;
            }
        }
    }

    void selectParticipants(ref List<Player> participants, ref Player[] players)
    {
        for (int i = 0; i < players.Length; i++)
        {
            if (players[i] != sponsor)
            {
                /* //Prompt user if they want to play
                 if (yes)
                     participants.Add(players[i])*/

            }
        }
    }

    void playQuest(ref List<Player> participants, List<adventureCard>[] stages, ref Stack<adventureCard> deck)
    {
        for (int i = 0; i < stages.Length; i++)
        {
            if (stages[i][0].getAdventureType() == "FOE")
            {
                int[] playerPoints = new int[participants.Count];
                List<adventureCard>[] playerCards = new List<adventureCard>[participants.Count];

                //Each player engages the foe
                for (int j = 0; j < participants.Count; j++)
                {
                    participants[j].fightFoe(ref playerPoints[j], ref playerCards[j]);
                }

                //All cards are flipped over / revealed
                // Flip weapon cards, if any, for foe


                //Initialize and foe's battle points
                int foePoints = 0;
                ((questCard)this.card).calculateFoePoints(stages[i], ref foePoints);

                //See if participants pass or fail
                for (int j = 0; j < participants.Count; j++)
                {
                    //Pseudo, if player fails
                    if (playerPoints[j] < foePoints)
                    {
                        participants.Remove(participants[j]);
                    }
                    else
                    {
                        //Successful participant draws one Adventure Card
                        participants[j].drawCard(deck);
                    }
                }
                //Discard all cards in play
            }
            else if (stages[i][0].getAdventureType() == "TEST")
            {
                //Engage test instructions
            }

        }
    }
}


