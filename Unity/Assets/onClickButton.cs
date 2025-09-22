using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class onClickButton : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    public void buttonClick()
    {
        //getting object name
        string name = EventSystem.current.currentSelectedGameObject.name;
        Debug.Log("this button was clicked! yay!");
        Debug.Log(name);
    } 
}
