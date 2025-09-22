using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RankDeck : MonoBehaviour
{

    private int size;
    private List<rankCard> deck;
    private SquireDeck squires;
    private KnightDeck knights;
    private ChampionKnightDeck championKnights;





    //write the same thing for all types in adventure deck.....


    public RankDeck()
    {

        size = 0;


    }

    public int getSize()
    {

        return size;
    }


    public SquireDeck getSquires()
    {

        return squires;

    }

    public KnightDeck getKnights()
    {

        return knights;
    }

    public ChampionKnightDeck getChampionKnights()
    {

        return championKnights;

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



    public rankCard find(string n)
    {

        for (int i = 0; i < size; i++)
        {
            if (deck[i].getName() == n)
                return deck[i];


        }
        return null;



    }


    public rankCard get(int index)
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




    public void add(rankCard c)
    {

        size++;
        deck.Add(c);

        if (c.getRankType() == "SQUIRE")
        {


            squires.add((squireCard)c);

        }

        if (c.getRankType() == "KNIGHT")
        {

            knights.add((knightCard)c);

        }

        if (c.getRankType() == "CHAMPIONKNIGHT")
        {
            championKnights.add((championKnightCard)c);

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
