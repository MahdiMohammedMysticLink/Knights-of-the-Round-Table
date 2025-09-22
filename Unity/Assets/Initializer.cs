using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Initializer : MonoBehaviour
{


    //This class is meant to initialize all cards in the entityArray class to make use of our game plan for onClick

    public GameObject card;
    public Sprite excalibur;
    public Sprite lance;
    public Sprite battleax;
    public Sprite sword;
    public Sprite horse;
    public Sprite dagger;

    public EntityArray universalArray;

    public weaponCard[] weapons; //might be useful not sure

    private int count;

    private int weaponStartingIndex;

    private int weaponEndingIndex;




    //put starting and ending indexes for each type in universal array for all types

    public int getWeaponStartingIndex()
    {

        return weaponStartingIndex;
    }

    public int getWeaponEndingIndex()
    {


        return weaponEndingIndex;
    }

    public Initializer()
    {

        count = 0;
        // make all potential entities
        int current = 0;

        weaponStartingIndex = 0;
        weaponEndingIndex = 0;
        createNWeaponCards(2, ref current, "Excalibur", excalibur, 30);
        createNWeaponCards(6, ref current, "Lance", lance, 20);
        createNWeaponCards(8, ref current, "Battle-Ax", battleax, 15);
        createNWeaponCards(16, ref current, "Sword", sword, 10);
        createNWeaponCards(11, ref current, "Horse", horse, 10);
        createNWeaponCards(6, ref current, "Dagger", dagger, 5);



    }


    void createNWeaponCards(int n, ref int m, string nameWithoutIndex, Sprite sprite, int points)
    {
        for (int i = 0; i < n; i++)
        {
            string nameWithIndex = string.Concat(nameWithoutIndex, count);
            weaponCard weapon = new weaponCard(nameWithIndex, points);
            weapon.obj = Instantiate(card, new Vector3(5, (float)((m + i) * 0.1), 0), transform.rotation);
            weapon.obj.GetComponent<SpriteRenderer>().sprite = sprite;
            weapons[weaponEndingIndex] = weapon;
            weaponEndingIndex++;
            universalArray.add(weapon);
            count++;


            //Can have a global list; AdventureDeckList.Add(weapon);
        }

        m += n;
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