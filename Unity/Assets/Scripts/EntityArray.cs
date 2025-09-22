using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EntityArray : MonoBehaviour
{
   
    private Entity[] entities;
    private Utilities utilities;
   
    private int size;

    public int getSize()
    {

        return size;
    }


    public EntityArray()
    {

        size = 0;


    }


    public void add(Entity entity)
    {

        entities[size] = entity;
        size++;

    }


    public void enableEntity(string n)
    {

        get(n).enable();

    }

    public void showEntity(string n)
    {

        get(n).appear();


    }

    public void hideEntity(string n)
    {

        get(n).disappear();
    }

    public int getIndex(string n)
    {


        return utilities.parseNameIntoIndex(n);
    }


    public Entity get(string n)
    {

        int index = utilities.parseNameIntoIndex(n);

        if (index >= size)
        {

            return null;

        }
        else
        {
            return entities[index];
        }
    }

    public string getNameWithoutIndex(int index)
    {
        string n;
        if (index >= size)
        {

            n = "nothing";
        }
        else {
             n = utilities.parseNumberOut(entities[index].getName());
        }
        return n;
    }


    public string getNameWithIndex(int index)
    {
        if (index >= size)
        {

            return "nothing";
        }

        return entities[index].getName();
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
