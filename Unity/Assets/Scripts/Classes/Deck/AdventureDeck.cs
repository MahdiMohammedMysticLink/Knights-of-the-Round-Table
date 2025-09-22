using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AdventureDeck : MonoBehaviour
{

    private int size;
    private List<adventureCard> deck;
    private FoeDeck foes;
    private WeaponDeck weapons;
    private AllyDeck allies;
    private TestDeck tests;
    private ArmourDeck armour;

    


    //write the same thing for all types in adventure deck.....


    public AdventureDeck()
    {

        size = 0;


    }

    public int getSize()
    {

        return size;
    }


    public FoeDeck getFoes()
    {

        return foes;

    }

    public WeaponDeck getWeapons()
    {

        return weapons;
    }

    public ArmourDeck getArmour()
    {

        return armour;

    }

    public TestDeck getTests()
    {

        return tests;

    }

    public AllyDeck getAllies()
    {


        return allies;

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



    public adventureCard find(string n)
    {

        for (int i = 0; i < size; i++)
        {
            if (deck[i].getName() == n)
                return deck[i];


        }
        return null;



    }


    public adventureCard get(int index)
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




    public void add(adventureCard c)
    {

        size++;
        deck.Add(c);

        if (c.getAdventureType() == "FOE")
        {

             
            foes.add((foeCard)c);
         
        }

        if (c.getAdventureType() == "WEAPON")
        {
            
            weapons.add((weaponCard)c);
           
        }

        if (c.getAdventureType() == "ALLY")
        {
            allies.add((allyCard)c);
            
        }


        if (c.getAdventureType() == "TEST")
        {
            tests.add((testCard)c);
           
        }


        if (c.getAdventureType() == "ARMOUR")
        {
            armour.add((amourCard)c);
           
        }







    }

    
  


    public bool remove(adventureCard c)
    {

        //if we remove, size is decreased
        size--;




        if (isFound(c.getName()))
        {

            deck.RemoveAt(findIndex(c.getName()));


        }



        if (c.getAdventureType() == "FOE"  )
        {

            return foes.remove(c.getName());

           
        }


        if (c.getAdventureType() == "WEAPON")
        {
            return weapons.remove(c.getName());

        }

        if (c.getAdventureType() == "ALLY")
        {
            return allies.remove(c.getName());

        }


        if (c.getAdventureType() == "TEST")
        {
            return tests.remove(c.getName());

        }


        if (c.getAdventureType() == "ARMOUR")
        {
            return armour.remove(c.getName());

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
