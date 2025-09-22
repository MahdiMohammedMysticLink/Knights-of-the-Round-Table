using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Entity : MonoBehaviour
{
    protected bool playable; //if button is clicked it will do something
    protected bool visible;
    protected string type;
    //protected string name;
    public playBehaviour playBehavior;
    public GameObject obj;
    GameObject newButton;


    public Entity(string name="", string type ="", playBehaviour play = null){
        playable = false;

        visible = false;

        this.name = name;
        this.type = type;
        this.playBehavior = play;

    }    


    public string getType()
    {

        return type;
    }

 

    public string getName()
    {
        return name;

    }


    public void enable()
    {

        playable = true;
    }

    public void disable()
    {
        playable = false;
    }

    public void appear() {

        visible = true;

    }


    public void disappear()
    {

        visible = false;

    }

    public virtual void play()
    {

        playBehavior.play();

        //all behaviours and cards should have this method implemented

        //haven't made them inherit it yet, we have to discuss who is going to have the EntityArray....

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
