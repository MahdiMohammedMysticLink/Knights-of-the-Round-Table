using UnityEngine;
using System.Collections;
using System.Collections.Generic;



public class MasterDeck : MonoBehaviour

{

    private int size;



    private List<Card> deck;
    private AdventureDeck adventureDeck;
    private RankDeck rankDeck;
    private StoryDeck storyDeck;






    // constructor

    public MasterDeck()

    {

        size = 0;

    }


    public int getSize()

    {

        return size;

    }



    public FoeDeck getFoes() { return adventureDeck.getFoes(); }
    public AllyDeck getAllies() { return adventureDeck.getAllies(); }
    public WeaponDeck getWeapons() { return adventureDeck.getWeapons();}
    public TestDeck getTests() { return adventureDeck.getTests(); }
    public ArmourDeck getArmour() { return adventureDeck.getArmour(); }
    public SquireDeck getSquires() { return rankDeck.getSquires();}
    public KnightDeck getKnights() { return rankDeck.getKnights();}
    public ChampionKnightDeck getChampionKnights() { return rankDeck.getChampionKnights(); }
    public QuestDeck getQuests() { return storyDeck.getQuests(); }
    public TournamentDeck getTournaments() { return storyDeck.getTournaments(); }
    public EventDeck getEvents() { return storyDeck.getEvents(); }

    public AdventureDeck getAdventures() { return adventureDeck; }
    public RankDeck getRanks() { return rankDeck; }
    public StoryDeck getStories() { return storyDeck; }

    public void add(Card c)
    {

        size++;
        deck.Add(c);

        if (c.getDeck() == "ADVENTURE")
        {

            adventureDeck.add((adventureCard)c);


        }


        if (c.getDeck() == "RANK")
        {
            rankDeck.add((rankCard)c);

        }

        if (c.getDeck() == "STORY")
        {
            storyDeck.add((storyCard)c);

        }






    }





    public bool remove(Card c)
    {

        //if we remove, size is decreased
        size--;




        if (isFound(c.getName()))
        {

            deck.RemoveAt(findIndex(c.getName()));


        }



        if (c.getDeck() == "ADVENTURE")
        {

            return adventureDeck.remove((adventureCard)c);


        }


        if (c.getDeck() == "RANK")
        {
            return rankDeck.remove((rankCard)c);

        }

        if (c.getDeck() == "STORY")
        {
            return storyDeck.remove((storyCard)c);

        }

        



        size++; //if we didn't remove, just return it back to old value
        return false;

    }


    public Card get(int index)

    {

        if (index >= size)

        {



            return null;

        }

        return deck[index];

    }





    public bool isFound(string n)

    {



        for (int i = 0; i < size; i++)

        {

            if (deck[i].getName() == n)

            {

                return true;

            }

        }

        return false;

    }



    public Card find(string n)

    {



        for (int i = 0; i < size; i++)

        {

            if (deck[i].getName() == n)

            {

                return deck[i];

            }

        }

        return null;



    }









    public int findIndex(string n)

    {



        for (int i = 0; i < size; i++)

        {

            if (deck[i].getName() == n)

            {

                return i;

            }



        }

        return -1;



    }



    // Get Random Card

  public Card getRandomCard()

    {

        while (true)
        {

            int index = Random.Range(0, size);


            Card c = get(index);

            if (c.getDeck() == "RANK")
            {

                continue;
            }

            return get(index);
        }
    }

}

