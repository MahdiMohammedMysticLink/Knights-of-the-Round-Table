using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;



public class Utilities : MonoBehaviour {



    public int parseNameIntoIndex(string name)
    {

       
        string index = string.Empty;
        int value = -1;

        for (int i = 0; i < name.Length; i++)
        {
            if (char.IsDigit(name[i]))
                index += name[i];
        }

        if (index.Length > 0)
            value = int.Parse(index);

        return value;

    }

    public string parseNumberOut(string name)
    {


        string nameWithoutIndex = string.Empty;

        for (int i = 0; i < name.Length; i++)
        {
            if (!char.IsDigit(name[i]))
                nameWithoutIndex += name[i];
        }

        return nameWithoutIndex;

    }



    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
