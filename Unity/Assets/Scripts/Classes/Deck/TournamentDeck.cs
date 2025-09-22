using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TournamentDeck : MonoBehaviour
{

    private int size;

    private List<tournamentCard> deck;

    // Use this for initialization


    public TournamentDeck()
    {

        size = 0;

    }



    public int getSize()
    {

        return size;
    }

    public void add(tournamentCard c)
    {

        deck.Add(c);
        size++;

    }



    public tournamentCard get(int index)
    {


        if (index >= size)
        {

            return null;
        }
        return deck[index];
    }

    public bool remove(string n)
    {
        size--;

        if (isFound(n))
        {




            deck.RemoveAt(findIndex(n));

            return true;


        }

        size++;


        return false;


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


    public tournamentCard find(string n)
    {

        for (int i = 0; i < size; i++)
        {
            if (deck[i].getName() == n)
                return deck[i];


        }
        return null;

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


    void Start()
    {

    }
    // Update is called once per frame
    void Update()
    {

    }
}
