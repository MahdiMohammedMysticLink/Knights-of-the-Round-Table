/*package com.COMP3004.Maven;

import org.junit.Test;

import com.COMP3004.Maven.Rank.squireCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

import javafx.application.Platform;

public class HumanSponsorSelection {

	@Test
	public void testDoIsponsor() {
		
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(UI.class);
            }
        }.start();
        final UI ui = UI.waitForUI();
        
    	AdventureDeck aD = new AdventureDeck();

        //if a class is accessing the UI it needs this
        //need to wrap every call to the UI in this 'runlater' statement
        Platform.runLater(new Runnable(){
			public void run() {		
				Player bob = new Human("Bob", new squireCard(), ui, aD);
				assert(bob.doISponsor());
			}
        	});
		
		
		
	}

}*/
