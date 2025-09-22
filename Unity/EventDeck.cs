using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EventDeck : MonoBehaviour
{

    private int size;

    private List<eventCard> deck;

    // Use this for initialization


    public EventDeck()
    {

        size = 0;

    }



    public int getSize()
    {

        return size;
    }

    public void add(eventCard c)
    {

        deck.Add(c);
        size++;

    }



    public eventCard get(int index)
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


    public eventCard find(string n)
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
