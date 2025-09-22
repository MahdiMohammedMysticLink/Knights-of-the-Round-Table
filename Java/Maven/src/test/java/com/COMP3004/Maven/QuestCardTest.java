package com.COMP3004.Maven;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.COMP3004.Maven.Rank.squireCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;

import javafx.application.Platform;

public class QuestCardTest {

	@Test
	
	
	public void test() {
		/*
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(UI.class);
            }
        }.start();
        final UI ui = UI.waitForUI();

        //if a class is accessing the UI it needs this
        //need to wrap every call to the UI in this 'runlater' statement
        Platform.runLater(new Runnable(){
			public void run() {
				
				AdventureDeck aD = new AdventureDeck();
				questCard quest = new questCard("Boar", 10, "boar");
				
				Player bob = new Human("Bob", new squireCard(), aD);
				Player jill = new Human("Jill", new squireCard(), aD);
				Player jack = new Human("Jack", new squireCard(), aD);
				
				List<Player> players = new ArrayList<Player>();
				players.add(bob);
				players.add(jill);
				players.add(jack);
				
			
				
				quest.play(bob, players, aD, 2);
				
				//assertTrue(bob.doISponsor());
			}
        	});
		*
		*/
	}

}
