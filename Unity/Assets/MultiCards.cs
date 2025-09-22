using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MultiCards : MonoBehaviour {
    public GameObject card;
    public Sprite excalibur;
    public Sprite lance;
    public Sprite battleax;
    public Sprite sword;
    public Sprite horse;
    public Sprite dagger;

    GameObject newButton1;
    GameObject newButton;


    /**
     * Hey Nick, 
     * 
     * We could use something like this to generate the cards initially and display on-screen.
     * When the game starts up it can use MultiCards.Start() which can call the game's main loop.
     * At that point, once the cards are generated in the model, they can be instantiated from the model,
     * which will cause them to appear in the scene.
     * 
     * Currently it just generates all the Weapon cards, but there is a problem with the formatting. May
     * have to get all images down to the same resolution for them to scale properly.
     * 
     * I haven't figured how Click() events are going to work, however I think it's possible to do it Update()
     * with conditionals saying "If left-clicked, which object was it?"
     * 
     * Or even better, if we can attached the click event to the GameObject..
     * 
     * Anyways let me know what you think.
     * 
     */

    //Suppose this is particular for weaponCards
    void createNCards(int n, ref int m, string name, Sprite sprite, int points)
    {
        for(int i = 0; i < n; i++)
        {
            weaponCard weapon = new weaponCard(name, points);
            weapon.obj = Instantiate(card, new Vector3(5, (float)((m + i) * 0.1), 0), transform.rotation);
            weapon.obj.GetComponent<SpriteRenderer>().sprite = sprite;

            //Can have a global list; AdventureDeckList.Add(weapon);
        }

        m += n;
    }

    void createAdventureDeck()
    {
        int current = 0; //Current number of cards created an displayed for Adventure Deck
        //WEAPONS
        createNCards(2, ref current, "Excalibur", excalibur, 30);
        createNCards(6, ref current, "Lance", lance, 20);
        createNCards(8, ref current, "Battle-Ax", battleax, 15);
        createNCards(16, ref current, "Sword", sword, 10);
        createNCards(11, ref current, "Horse", horse, 10);
        createNCards(6, ref current, "Dagger", dagger, 5);

        /*//FOES

        //Dragon

        //Giant
        for (int i = 0; i < 2; i++)
        {

        }

        //Mordred
        for (int i = 0; i < 4; i++)
        {

        }

        //Green Knight
        for (int i = 0; i < 2; i++)
        {

        }

        //Black Knight
        for (int i = 0; i < 3; i++)
        {

        }

        //Evil Knight
        for (int i = 0; i < 6; i++)
        {

        }

        //Saxon Knight
        for (int i = 0; i < 8; i++)
        {

        }

        //Robber Knight
        for (int i = 0; i < 7; i++)
        {

        }

        //Saxons
        for (int i = 0; i < 5; i++)
        {

        }

        //Boar
        for (int i = 0; i < 4; i++)
        {

        }

        //Thieves
        for (int i = 0; i < 8; i++)
        {

        }*/
    }

	// Use this for initialization
	void Start () {
        /*GameObject card1;
        GameObject card2;
        card1 = Instantiate(card, transform.position, transform.rotation);
        card2 = Instantiate(card, new Vector3(5, 5, 0), transform.rotation);
        card1.transform.position = new Vector3(-3, -3, 0);
        card1.GetComponent<SpriteRenderer>().sprite = aKingPellinore;


        for (int i = 0; i < 3; i++)
        {
            allyCard pellinore = new allyCard("King Pellinore", null, 10, 4, "BID");
            pellinore.obj = Instantiate(card, new Vector3(5, i, 0), transform.rotation);
            pellinore.obj.GetComponent<SpriteRenderer>().sprite = aKingPellinore;
        }*/

        //createAdventureDeck();
        //Finds and assigns the child of the player named "Gun".
        //need to figure out  a way to get rid of this prefab from hierarchy without it bugging out other buttons
        GameObject button;
        GameObject button1;
        button = GameObject.Find("Canvas");
        button1 = button.transform.Find("Button").gameObject;
        button1.transform.position = new Vector3(-300, -300, 0);

        //Here I am showing how to create a button card
        //we can do this dynamically during run time
        //setting the parent to the canvas is necessary for buttons, for some reason
        //also, offsetting the objects position by the canvas position, is also necessary 
        //assigning each prefab a name will be useful for making the buttons do something specific
        //since we can access the name of the button within the buttonClick function I made
        //see onClickButton.cs. we can  use this name to refer to an index in the global
        //card array (or some sort of other struct   that stores the   game  state) so that
        //clicking that particular card holds the right functionality


        
        newButton = Instantiate(card, transform.position, transform.rotation) as GameObject; 
        newButton.transform.SetParent(GameObject.Find("Canvas").transform, false);
        newButton.transform.position = new Vector3(-80 + GameObject.Find("Canvas").transform.position.x, -20 + GameObject.Find("Canvas").transform.position.y, 0);
        newButton.name = "button clone!!! I am a horse!";
        newButton.GetComponent<Image>().sprite = horse;

        
        newButton1 = Instantiate(card, transform.position, transform.rotation) as GameObject;
        newButton1.transform.SetParent(GameObject.Find("Canvas").transform, false);
        newButton1.transform.position = new Vector3(80 + GameObject.Find("Canvas").transform.position.x, 20 + GameObject.Find("Canvas").transform.position.y, 0);
        newButton1.name = "button clone1!!! I am EXCALIBUR!!!";
        newButton1.GetComponent<Image>().sprite = excalibur;

    }



    // Update is called once per frame
    void Update () {
    }
}
