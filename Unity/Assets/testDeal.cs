using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class testDeal : MonoBehaviour {
    GameObject card;

    // Use this for initialization
    void Start () {
        

		for(int i = 0; i < 3; i++)
        {
            allyCard pellinore = new allyCard("King Pellinore", 10, 4, "BID");
            pellinore.obj = Instantiate(card);
            print("Pellinore Created.");
        }
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
