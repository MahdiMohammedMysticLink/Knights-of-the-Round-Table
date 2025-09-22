using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StoryDeck : MonoBehaviour
{

    private int size;
    private List<storyCard> deck;
    private QuestDeck quests;
    private EventDeck events;
    private TournamentDeck tournaments;
  




    //write the same thing for all types in adventure deck.....


    public StoryDeck()
    {

        size = 0;


    }

    public int getSize()
    {

        return size;
    }


    public QuestDeck getQuests()
    {

        return quests;

    }

    public EventDeck getEvents()
    {

        return events;
    }

    public TournamentDeck getTournament()
    {

        return tournaments;

    }
    


    public bool isFound(string n)
    {

        for (int i = 0; i < size; i++)
        {
            if (deck[i].getName() == n)
                return true;


        }
        return false;



    }



    public storyCard find(string n)
    {

        for (int i = 0; i < size; i++)
        {
            if (deck[i].getName() == n)
                return deck[i];


        }
        return null;



    }


    public storyCard get(int index)
    {
        if (index >= size)
        {
            return null;
        }

        return deck[index];

    }


    public int findIndex(string n)
    {

        for (int i = 0; i < size; i++)
        {
            if (deck[i].getName() == n)
                return i;


        }
        return -1;

    }




    public void add(storyCard c)
    {

        size++;
        deck.Add(c);

        if (c.getStoryType() == "QUEST")
        {


            quests.add((questCard)c);

        }

        if (c.getStoryType() == "TOURNAMENT")
        {

            tournaments.add((tournamentCard)c);

        }

        if (c.getStoryType() == "EVENT")
        {
           events.add((eventCard)c);

        }
        



    }





    public bool remove(storyCard c)
    {

        //if we remove, size is decreased
        size--;




        if (isFound(c.getName()))
        {

            deck.RemoveAt(findIndex(c.getName()));


        }


        if (c.getStoryType() == "QUEST")
        {
            return quests.remove(c.getName());


        }

        if (c.getStoryType() == "TOURNAMENT")
        {
            return tournaments.remove(c.getName());

        }

        if (c.getStoryType() == "EVENT")
        {

            return events.remove(c.getName());

        }







        size++; //if we didn't remove, just return it back to old value
        return false;

    }

    // Use this for initialization
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

    }
}
