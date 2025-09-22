using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class MasterDeck : MonoBehaviour
{
    private int size;

    private List<Card> deck;

    public int getSize()
    {
        return size;
    }

    // constructor
    public MasterDeck()
    {
        size = 0;
    }


    public void add(Card c)
    {
        deck.Add(c);
        size++;
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
        int index = Random.Range(0, size);
        return get(index);
    }
}
