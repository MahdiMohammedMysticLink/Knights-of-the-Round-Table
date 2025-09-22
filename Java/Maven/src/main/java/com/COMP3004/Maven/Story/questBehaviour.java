package com.COMP3004.Maven.Story;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Player;
import com.COMP3004.Maven.Human;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import com.COMP3004.Socket.ObjectArray;
import com.COMP3004.Maven.Adventure.*;
import com.COMP3004.Maven.PlayerDeck.FoeDeck;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.PlayerDeck.TestDeck;

public class questBehaviour implements storyBehaviour, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Player sponsor;
	public questCard quest;
	private List<Player> players;
	
	
	public boolean tristAndQueenInPlay;
	
	public boolean tristInPlay;
	
	public boolean iseultInPlay;

	public PlayerDeck[] questStages;

	public questBehaviour(questCard q) {
		quest = q;
		
		
	}
	
	public void setPlayersFinishedTurnToFalse() {
		
		for (int i=0; i<players.size(); i++) {
			
			players.get(i).hasFinishedTurn = false;
			
		}
		
	}

	private int calculateCardsSpent() {
		
		int total = 0;

		for (int i = 0; i < questStages.length; i++) {

			total += questStages[i].getSize();
		}
		Model.log("Cards Spent by Sponsor " + sponsor.getName() +" :" + total, "questBehaviour", "calculateCardsSpent");
		return total;
	}

	
	
	public void updateAllPlayers() {
		
		for (int i= 0; i<players.size(); i++) {
			
			
			
			players.get(i).updateUI();
			
		}
		
		
		
	}
	
	public boolean playersDecksAreNotOverMax(List<Player> players) {
		
		
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).deck.getSize()> 12) {
				
				return false;
				
			}
			
			
			
		}
		
		return true;
		
		
	}
	
	public boolean allPlayersFinishedTurn(List<Player> players) {
		
		
		
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).hasFinishedTurn == false) {
				
				return false;
				
			}
			
			
			
		}
		
		return true;
		
		
		
	}
	
	
	@Override
	public void play(Player currPlayer, List<Player> players, AdventureDeck deck, int bonusShields) {
		
		
		
		this.players = players;
		
		Model.log("Playing: " + quest.getName(), "questBehaviour", "play" );
		
		
		System.out.println("Begin questBehaviour.play()");
		tristAndQueenInPlay = false;
		tristInPlay = false;
		iseultInPlay = false;
		
		
		Model.log("Checking all players to see if  Sir Tristan and Queen are in play  ", "questBehaviour", "play");
		
		if (isIseultInPlay(players)) {
			Model.log("Observed that Queen Iseult is in play", "questBehaviour", "play");
			iseultInPlay = true;
			
		}
		
		if (isTristanInPlay(players)) {
			Model.log("Observed that Sir Tristan is in play", "questBehaviour", "play");
			tristInPlay= true;
			
		}
		
		
		if (tristInPlay && iseultInPlay) {
			Model.log("Observed that Sir Tristan and Queen Iseult are in play", "questBehaviour", "play");
    		tristAndQueenInPlay = true;
    	}
		
		
        int numStages = quest.getNumStages();
        sponsor=null;
        selectSponsor(players);
        
        //Was a sponsor chosen?
        if (sponsor != null)
        {
        	
        	
        	System.out.println("Sponsor chosen!");
            //List<adventureCard>[] stages = new List[numStages];
            int cardsSpent = 0; //Assume no cards spent

            //Sponsor sets up the quest
            questStages = sponsor.selectedQuestStages;
            cardsSpent = calculateCardsSpent();
            
            

            //Determine which players are playing
            List<Player> participants = new ArrayList<Player>();
            selectParticipants(participants, players, deck);

            //Allow players to play stages
            playQuest(participants, deck);

            //Award successful players with shields
            for (int i = 0; i < participants.size(); i++)
            {
            	
            	participants.get(i).wonQuest = true;
            	
            	Model.log(participants.get(i).getName() + " has won quest " + quest.getName() + " with " + participants.get(i).deckSize() + " cards left over.", "questBehaviour", "play");
            	
            	if (participants.get(i).amourInPlay != null) {
            	
            		
            		
            	participants.get(i).amourInPlay = null;
            	
            	
            	
            	Model.log(participants.get(i).getName() + " discarded their Amour in play", "questBehaviour", "play");
            	deck.addToDiscardPile(new amourCard());
            	
            	
            	}
            	System.out.println(participants.get(i).getName() + " is awarded " + (numStages + bonusShields) + " shields for winning!");
                participants.get(i).addShields(numStages + bonusShields);
            }
            
            Model.log(sponsor.getName() + " discarding sponsored cards in" + quest.getName(), "questBehaviour", "play");
            sponsor.discardSponsoredCards();
            
            
            
            
            
            
            Model.log(sponsor.getName() + " is awarded " + (cardsSpent + numStages) + " cards for sponsoring", "questBehaviour", "play");
            System.out.println(sponsor.getName() + " is awarded " + (cardsSpent + numStages) + " cards for sponsoring!");
            //Sponsor draws (cards spent + numStages) Adventure Cards
            for (int i = 0; i < (cardsSpent + numStages); i++)
            {
                sponsor.addCard(deck.dealCard());
                
                
                
              
            }
            
            
            	
            
            	
            while(sponsor.deck.getSize()>12) {
            		
            	 Model.log(sponsor.getName() + " has to discard cards", "questBehaviour", "play");
            		
            		int amountToDiscard = sponsor.deck.getSize()-12;
                	
            		
            		sponsor.decideCardsToDiscard(amountToDiscard);
            		
            		
            		
            		
            		//sponsor.updateUI();
            }
            updateAllPlayers();
            //Reset bonusShield count
       
            
            
            if (participants.size()>0) {
          
            	Model.log("Players are being shown winners of the quest and shields they received.", "questBehaviour", "play");
            		
            		updateAllPlayers();
        			setPlayersFinishedTurnToFalse();
        			
        			int counter = 0;
        			
        			do {
        			
        				
        			for (int i=0; i<this.players.size(); i++) {
        				
        				if (this.players.get(i).hasFinishedTurn == false) {
        				this.players.get(i).display(counter, quest.getName(), ("Won quest: " + quest.getName() + " and gained" + (numStages + bonusShields) + " shields"), participants );
        				}
        				
        			}
        				
        			counter =1;
        			
        			
        			
        			try {
        				Thread.sleep(300);
        				//System.out.println("weeee");
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        	    	
        			
        			
        			}while(allPlayersFinishedTurn(this.players) == false);

        		}
            		
            		
            	
            	
            bonusShields = 0;
            	
            }
		
	

        
        else {
        	
        	Model.log("Sponsor has not been chosen", "questBehaviour", "play");
        	System.out.println("Sponsor NOT chosen!");
        	
        	
        }

        //Quest End
        
        Model.log("Quest behaviour is complete", "questBehaviour", "play");
        System.out.println("Exit questBehaviour.play()");

    }
	
	private boolean questSetupProperly() {
		//note some things are checked in UI, and won't need to be checked here.
		int tests = 0;
		int previousPoints = 0;
		int nextPoints = 0;
		Model.log("Seeing if quest is setup properly", "questBehaviour", "questSetupProperly");
		
		
		Model.log("The quest setup is:", "questBehaviour", "questSetupProperly");
		
		
		for (int i=0; i<questStages.length; i++) {
			
			Model.log("Stage 1:", "questBehaviour", "questSetupProperly");
			
			questStages[i].printCards();
			
		}
		
		if (questStages.length != quest.getNumStages()) {
			
			
			return false;
		}
		
		for (int i=0; i<questStages.length; i++) {
			
			
			
			
		
			Model.log("Checking if stage " + (i+1) + " is empty.", "questBehaviour", "questSetupProperly");
		if (questStages[i].getSize()<1)	{
			
			Model.log("Stage " + (i+1) + " is empty, quest stages not set up properly", "questBehaviour", "questSetupProperly");
			return false;
		}
			
		if (questStages[i].hasType("TEST")) {
			
			Model.log("Counting number of tests, currently: " + tests, "questBehaviour", "questSetupProperly");
			
			
			tests++;
		}
		
		Model.log("Checking if stage " + (i+1) + "has neither Foe or Test", "questBehaviour", "questSetupProperly");
		
		if (!questStages[i].hasType("FOE") && (!questStages[i].hasType("TEST"))) {
			Model.log("Stage " + (i+1) + " has neither Foe nor Test, quest stages not setup properly", "questBehaviour", "questSetupProperly");
			return false;
		}
		
		
		Model.log("Checking if stage " + (i+1) + "has both Foe and a Test", "questBehaviour", "questSetupProperly");
		
		if (questStages[i].hasType("FOE")  && questStages[i].hasType("TEST")) {
			Model.log("Stage" + (i+1) + " has both a Foe and a Test, quest stages not setup properly", "questBehaviour", "questSetupProperly");
			return false;
		}
		
		Model.log("Checking if stage " + (i+1) + "has both Foe and a Test", "questBehaviour", "questSetupProperly");
		if (questStages[i].hasType("WEAPON")  && questStages[i].hasType("TEST")) {
			Model.log("Stage " + (i+1) + " has both a Weapon and a Test, quest stages not setup properly", "questBehaviour", "questSetupProperly");
			return false;
		}
		
		Model.log("Checking if stage " + (i+1) + "has a Weapon but not  a Foe", "questBehaviour", "questSetupProperly");

		if (questStages[i].hasType("WEAPON")  && !questStages[i].hasType("FOE")) {
			Model.log("Stage " + (i+1) + " has a Weapon but not  a Foe, quest stages not setup properly", "questBehaviour", "questSetupProperly");
			return false;
		}
		
	
		
		}
		
		Model.log("Checking if more then one test ", "questBehaviour", "questSetupProperly");
		if (tests>1) {
			Model.log("More then one test: " + tests + ", quest stages not setup properly", "questBehaviour", "questSetupProperly");
			return false;
		}
		
		
		Model.log("Checking Ascending order. ", "questBehaviour", "questSetupProperly");
		for(int i=0, j=1; i<questStages.length-1; i++, j++) {
			
	    
		if (questStages[i].hasType("FOE")){	
		previousPoints = questStages[i].calculateQuest(quest.getSpecialFoe());
		
		
		Model.log("Previous Stage ("+ (i+1)+") points: " + previousPoints, "questBehaviour", "questSetupProperly");
		
		}
		
		if (questStages[j].hasType("FOE")){	
			nextPoints =  questStages[j].calculateQuest(quest.getSpecialFoe());
			
			Model.log("Next Stage ("+ (i+1)+") points: " + previousPoints, "questBehaviour", "questSetupProperly");
		}
		
		
		
		
		
		
		if (questStages[j].hasType("FOE") && nextPoints <= previousPoints) 
		{
				
			Model.log("Next stage with foe doesn't have more points that previous stage with foe, quest stages not setup properly", "questBehaviour", "questSetupProperly");
			
			return false;
			
		}
	}
		
	Model.log("Quest stages setup properly, acknowledging setup.", "questBehaviour", "questSetupProperly");
		
	return true;
	}
	
	
	
	

	
	

	private void selectSponsor(List<Player> players)
    {
    	System.out.println("Begin questBehaviour.selectSponsor()");
        for (int i = 0; i < players.size(); i++)
        {
            //If player i agrees to sponsor, they become the sponsor.
        	players.get(i).wonQuest = false;
        	int counter = 0;
        	boolean stagesSetupProperly = false;
        	while (!stagesSetupProperly) {
        		
        		
        		if (counter>0) {
        			
        			Model.log( players.get(i).getName() + " is being prompted again ("+ counter + " time(s) asked again) to setup quests properly or decline sponsoring.",  "questBehavior" , "selectSponsor");
        			
        		}else {
        		Model.log("Selecting a sponsor (can be none)", "questBehaviour", "selectSponsor");
        		
        		}
        		
        		
        		
        	
        		setPlayersFinishedTurnToFalse();
     
        		
        		players.get(i).doISponsor(quest);
        		
        		System.out.println("was the value of isSponsoring saved?");
        	    System.out.println(players.get(i).isSponsoring); 
        	
   
        	
				if (players.get(i).isSponsoring)
				{
					
					counter++;
					Model.log(players.get(i).getName() + " has sponsored " + quest.getName(), "questBehaviour", "selectSponsor");
					
				
					System.out.println(players.get(i).getName() + " agreed to sponsor!");
					//cleanUpExtraFoes(players.get(i));
				
					
					questStages = players.get(i).selectedQuestStages;
				    
				    stagesSetupProperly = questSetupProperly();
					
					sponsor = players.get(i);
				   
				    
				}
				
				else {
					
					sponsor = null;
					stagesSetupProperly = true;
				}
        	}
        	
        	if (players.get(i).isSponsoring) {
        		
        		Model.log(players.get(i).getName() + " has successfully sponsored quest and set up stages  ("+ counter + " time(s) asked)",  "questBehavior" , "selectSponsor");
        		
        		
        		updateAllPlayers();
        		
        		
        		
        		

                List<Player> onePlayer = new ArrayList<Player>();
        		
        		
        		onePlayer.add(sponsor);
        		
            	
            	updateAllPlayers();
        		setPlayersFinishedTurnToFalse();
        		
        		counter = 0;
        		
        		Model.log("Updating all players there is  a sponsor. (the UI follows it up with stats of all players)", "questBehaviour", "selectSponsor");
        		
        		do {
        		
        		
        			
        		for (int j=0; j<this.players.size(); j++) {
        			
        			
        			
        	
        		
        				
        			
        			if (this.players.get(j).hasFinishedTurn == false) {
        			this.players.get(j).display(counter, quest.getName(), (sponsor.getName() + "has sponsored " + quest.getName()), onePlayer);
        			}
        			
        		}
        			
        		counter =1;
        		
        		
        		
        		try {
        			Thread.sleep(300);
        			//System.out.println("weeee");
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            	
        		
        		
        		}while(allPlayersFinishedTurn(this.players) == false);

        		
        		
        		
        		
        	//	players.get(i).updateUI();
        		break;
        	}else {
        		
        		
        		
        		 List<Player> onePlayer = new ArrayList<Player>();
        			
        			
        			onePlayer.add(players.get(i));
        			
        	    	
        	    	updateAllPlayers();
        			setPlayersFinishedTurnToFalse();
        			
        			counter = 0;
        			
        			Model.log("Updating all players " +players.get(i).getName() + "is not sponsoirng", "questBehaviour", "selectSponsor");
        			
        			do {
        			
        			
        				
        			for (int j=0; j<this.players.size(); j++) {
        				
        				
        				
        		
        			
        					
        				
        				if (this.players.get(j).hasFinishedTurn == false) {
        				this.players.get(j).display(counter, quest.getName(), (players.get(i).getName() + "has decline to sponsor " + quest.getName()), onePlayer);
        				}
        				
        			}
        				
        			counter =1;
        			
        			
        			
        			try {
        				Thread.sleep(300);
        				//System.out.println("weeee");
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        	    	
        			
        			
        			}while(allPlayersFinishedTurn(this.players) == false);

        		
        		
        	}
		
        }
        
        
    	
        
        
        System.out.println("Exit questBehaviour.selectSponsor()");
    }
	
	
	private boolean isTristanInPlay(List<Player> players) {
		
		Model.log("Checking if  Sir Tristan is in play with a set of players", "questBehaviour" , "isTristanInPlay");
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).alliesInPlay.isFound("Sir Tristan")){
				
				Model.log(players.get(i).getName() + " has ally Sir Tristan in play", "questBehaviour" , "isTristanInPlay");
				return true;
				
			}
			
		Model.log("Sir Tristan is in not play with the (perhaps limited) players checked.", "questBehaviour" , "isTristanInPlay");
		}
		return false;
	}
	
	
	private boolean isIseultInPlay(List<Player> players) {
		
		Model.log("Checking if Queen Iseult is in play with a set of players", "questBehaviour" , "isTristanInPlay");
		for (int i=0; i<players.size(); i++) {
			
			
			if(players.get(i).alliesInPlay.isFound("Queen Iseult")) {
				Model.log(players.get(i).getName() + " has ally Queen Iseult in play", "questBehaviour" , "isIseultInPlay");
				return true;
			
			}
		}
		
		Model.log("Queen Iseult is in not play with the (perhaps limited) players checked.", "questBehaviour" , "isTristanInPlay");
		return false;
	}
	
	


    private void selectParticipants(List<Player> participants, List<Player> players, AdventureDeck deck)
    {
    	System.out.println("Begin questBehaviour.selectParticipants()");
    	
    
    	setPlayersFinishedTurnToFalse();
    	
    	

    	int counter = 0;
    	
        for (int i = 0; i < players.size(); i++)
        {
        	players.get(i).wonQuest = false;
        	
        	if (players.get(i).isSponsoring) {
        		
        		
        		players.get(i).hasFinishedTurn = true;
        		
        	}
        	//Everyone except the sponsor should be asked to participate
            if (!players.get(i).isSponsoring)
            {
            	//If a player decides to participate, add them to the list of participants
            	Model.log("Checking if " + players.get(i).getName() + " will participate in " + quest.getName(), "questBehaviour", "selectParticipants");
            	players.get(i).doIParticipate(questStages, quest);
            	String participate = "";
            	if (players.get(i).isParticipating) {
            		
            		
            		
            		Model.log(players.get(i).getName() + " was rewarded a card and is participating", "questBehaviour", "selectParticipants");
            		
            		players.get(i).addCard(deck.dealCard());
					
					participate = " is participating in ";
				}else {
					
					participate =" is not participating in ";
					
				}
            	
				List<Player> onePlayer = new ArrayList<Player>();
				
				
				onePlayer.add(players.get(i));
				
            	
            	updateAllPlayers();
    			setPlayersFinishedTurnToFalse();
    			
    			counter = 0;
    			
    			Model.log("Updating all players that " + players.get(i).getName() + participate + quest.getName(), "questBehaviour", "selectParticipants");
    			
    			do {
    			
    			
    				
    			for (int j=0; j<this.players.size(); j++) {
    				
    				
    				
    		
    			
    					
    				
    				if (this.players.get(j).hasFinishedTurn == false) {
    				this.players.get(j).display(counter, quest.getName(), (players.get(i).getName() + participate + quest.getName()), onePlayer);
    				}
    				
    			}
    				
    			counter =1;
    			
    			
    			
    			try {
    				Thread.sleep(300);
    				//System.out.println("weeee");
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    	
    			
    			
    			}while(allPlayersFinishedTurn(this.players) == false);

    		}
            	
            	
            	
          
       
        }
        
        
  

        
     
        
    	
    	
    	
      for (int i = 0; i < players.size(); i++) {
        	
        	
        	if (players.get(i).isParticipating) {
        		
        		
        		participants.add(players.get(i));
        	}
        	
        	
        }
    	
    	
    	
    	counter = 0;
    	
    	
    	setPlayersFinishedTurnToFalse();
    	
    	
    	Model.log("Checking to see if participants cards are over maximum", "questBehaviour", "selectParticipants");
    	
 
        boolean haveToDiscard = !playersDecksAreNotOverMax(participants);
        
    	do {
        
    	 for (int i=0; i <participants.size(); i++) {
        
        	if(participants.get(i).deck.getSize() > 12) {
        		
        		
        		
        		
        		if (participants.get(i).hasFinishedTurn) {
        			
        			participants.get(i).decideCardsToDiscard(0, 1);	
        			
        		}
        		
        		else {
        		
        		participants.get(i).decideCardsToDiscard(counter, 1);
        		}
        		
        	}else {
        		
        		
        		
        		participants.get(i).hasFinishedTurn = true;
        	}
        	
        
        }
        
        
        counter=1;
        

    	try {
			Thread.sleep(300);
			//System.out.println("weeee");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    	}while((haveToDiscard)&& (playersDecksAreNotOverMax(participants) == false));
        
        System.out.println("Exit questBehaviour.selectParticipants()");
    }

	 private void playQuest(List<Player> participants, AdventureDeck deck)
	    {
	    	
	        int[] playerPoints = new int[participants.size()];
	    	
	        
	        
	        
	        
	        for (int i=0; i<questStages.length; i++) {
	        
	        
	        FoeDeck foe = new FoeDeck();
	        TestDeck test = new TestDeck();
	    	
	     
            	
            	
            
	        	
	        	
            	
	        
	        	foe = questStages[i].getFoes();
	        	test = questStages[i].getTests();
	        	
	        	
	        	PlayerDeck[] cardsAgainstFoes = new PlayerDeck[participants.size()];
	        	
	        	if (foe.getSize() > 0)
	
	            {
	
	              int foePoints = questStages[i].calculateQuest(quest.getSpecialFoe());
	              
	              int counter = 0;  
	              
	              setPlayersFinishedTurnToFalse();
	              
	              do {
	              
	            	
	                //Each player engages the foe
	                for (int j = 0; j < participants.size(); j++)
	                {
	                	
	                	
	                	if (participants.get(j).hasFinishedTurn == false) {
	                	participants.get(j).fight(counter, questStages[i], (i+1));
	                	
	                	}
	                
	                	
	                	
	            		if (participants.get(j).hasFinishedTurn == true) {
	                
	                	if (isIseultInPlay(players)) {
	            			Model.log("Observed that Queen Iseult is in play", "questBehaviour", "playQuest");
	            			iseultInPlay = true;
	            			
	            		}
	            		
	            		if (isTristanInPlay(players)) {
	            			Model.log("Observed that Sir Tristan is in play", "questBehaviour", "playQuest");
	            			tristInPlay= true;
	            			
	            		}
	            		
	            		
	            		if (tristInPlay && iseultInPlay) {
	            			Model.log("Observed that Sir Tristan and Queen Iseult are in play", "questBehaviour", "playQuest");
	                		tristAndQueenInPlay = true;
	                	}
	            		
	            		
	                	playerPoints[j] = (participants.get(j).calculatePointsForQuest(quest.getName(), tristAndQueenInPlay));
	                	
	                	
	               
	                	cardsAgainstFoes[j] = participants.get(j).allCardsUsedAgainstAFoe();
	                	
	                	
	                	Model.log(participants.get(j).getName() + " used the following cards against Foes:", "questBehaviour", "playQuest");
	                	
	                	cardsAgainstFoes[j].printCards();
	                	
	                	
	                	Model.log("At Stage :" + (i+1) +" which consists of:", "questBehaviour", "playQuest");
	                	
	                	questStages[i].printCards();
	                	
	                	participants.get(j).removeCommittedCards();
	                	
	                	
	                	Model.log("Total Points of player: " + playerPoints[j], "questBehaviour", "playQuest");
	                	Model.log("Total Points of Stage:" + foePoints, "questBehaviour", "playQuest");
	                	
	          
	            		}
	                
	                	
	                	
	                	
	                }
	                
	                
	           
	                
	               
	                counter=1;
	                
	                
	             	
                	try {
    					Thread.sleep(300);
    					//System.out.println("weeee");
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                
	                
	                	
	                }while(allPlayersFinishedTurn(participants) == false);
	                
	                
	              
	              	updateAllPlayers(); 	
	              
	              
	                
	                for (int j = 0; j < participants.size(); j++)
	                {
	               
	                	counter = 0;
	                	
	                	setPlayersFinishedTurnToFalse();

	                	do {	
	                		
	                		
	                
	                		
	                	

	                	
	                	if (playerPoints[j]< foePoints) {
	                		
	                		
	                		String display = participants.get(j).getName()+ " lost with " + playerPoints[j];
	                		
	                		
	                		for (int k=0; k<players.size(); k++) {
	                		
	                	
	                		if (players.get(k).hasFinishedTurn == false) {
	                		players.get(k).displayEndOfStage(counter, questStages[i], cardsAgainstFoes[j], display);
	                		}
	                		}
	                	}
	                	else {
	                		
	                		String display = participants.get(j).getName() + " won with " + playerPoints[j];
	                		
	                		
	                		for (int k=0; k<players.size(); k++) {
	                		
	                			if (players.get(k).hasFinishedTurn == false) {
	    	                		players.get(k).displayEndOfStage(counter, questStages[i], cardsAgainstFoes[j], display);
	    	                	}
	                	
	                		
	                		}
	            
	                	
	                	}
	                	
	                	counter=1;
	                	
	                	
	            		try {
        					Thread.sleep(300);
        					//System.out.println("weeee");
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
	                	
	                	}while(!allPlayersFinishedTurn(players));
	                	
	                	
	                }
	                	
	                	//Discard all cards in play by participants since they've already committed to fight
	                	
	         
	              
	                
	                
	                
	          
	                
	                for (int j = 0; j<participants.size(); j++) {
	                
	                
	                	
	                    //If player fails
	                    if (playerPoints[j] < foePoints)
	                    {
	                    	
	                    	Model.log(participants.get(j).getName() + " has lost to a foe. Foe Points: " + foePoints + " player points:" + playerPoints[j] , "questBehaviour", "playQuest");
	                    	
	                    	participants.get(j).amourInPlay = null;
	                    	
	                    	Model.log(participants.get(j).getName() + " discarded their Amour in play", "questBehaviour", "playQuest");
	                    	deck.addToDiscardPile(new amourCard());
	                    	
	                    	
	                    	Model.log(participants.get(j).getName() + " is being removed", "questBehaviour", "playQuest");
	                        participants.remove(j);
	                        
	                       
	                  
	                        
	                        
	                    }
	                    
	                    
	                }
	                
	                setPlayersFinishedTurnToFalse();
	                counter = 0;
	             
	               
	                for (int j = 0; j<participants.size(); j++) {
	                    	
	                    	
	                	if (playerPoints[j]>= foePoints) {
	                
	                		Model.log(participants.get(j).getName() + " has won to a foe. Foe Points: " + foePoints + " player points:" + playerPoints[j] , "questBehaviour", "playQuest");
	                        //Successful participant draws one Adventure Card
	                        
	                		Model.log(participants.get(j).getName() + "is being rewarded a card", "questBehaviour", "playQuest");
	                    		participants.get(j).addCard(deck.dealCard());
	                    	
	                        
	                    	
	                    	
	                    	
	                    	
	                    	
	                	}  	
	                   
	                }	
	                boolean haveToDiscard = !playersDecksAreNotOverMax(participants);
	                	
	                	
	               do { 	
	                	
	                for (int j = 0; j<participants.size(); j++) {	
	                	
	                	
	                	
	                	if (participants.get(j).deck.getSize()>12) {
	                    	   
	                    	   
	                    	   
	                        	
	                    	   	if (participants.get(j).hasFinishedTurn) {
	                    	   		
	                    	   		participants.get(j).decideCardsToDiscard(0, 1);
	                    	   		
	                    	   	}
	                    	   	else {
	                        	participants.get(j).decideCardsToDiscard(counter, 1);
	                    	   	}
	                        	
	                        }else{
	                        	
	                        	participants.get(j).hasFinishedTurn = true;
	                        }
	                       
	                        
	                        updateAllPlayers();

		                //	participants.get(j).updateUI();
	                        //Display a card has been drawn and added
	                   
	                
	                	
	                	
	                	
	                	
	                }
	               counter =1;     
	                    
	                    try {
        					Thread.sleep(300);
        					//System.out.println("weeee");
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
	                  
	            
	                }while(haveToDiscard && playersDecksAreNotOverMax(participants) == false);
	 
	            }  
	     
	                
	     
			else if (test.getSize() > 0) {
				// Sponsor displays test card to all
				testCard t = test.get(0);

				// All participants bid cards
			

				// Continue until there is only one player left standing
			
				int highestBid = t.getMinBid();
				
				if (t.getName() == "Test of the Questing Beast") {
					
					
					if (quest.getName() == "Search for the Questing Beast") {
						
						
						highestBid = 4;
					}
					else {
						
						
						highestBid =1;
						
					}
					
					
				}
				Model.log(t.getName() + " in quest: " + quest.getName() + " starting bid: " + highestBid, "questBehaviour", "playQuest");
				
				int winnerCardsSpent = 0;
				
				Player winner =null;
				
				
			
				
				// Ally/Amour cards can reduce the amount necessary to bid
				// thisBid += Ally/Amour.bidValue
				
				int counter = 0;
				
			
				int round = 1;
				
				if (participants.size() == 1) {
					
					
					int[] biddersAmount = new int[1];
					
					if (highestBid <3) {
					highestBid =3;
					}
					
					Model.log(participants.get(0).getName() + "is bidding alone.", "questBehaviour", "playQuest");
					
					Model.log(t.getName() + " in quest: " + quest.getName() + " starting bid: " + highestBid, "questBehaviour", "playQuest");
					
					 int bidsFromCardsInPlay= 0;
					 
					 
					 
					 
					 participants.get(0).playAllyCardsForBid(t.getName());
					 
					 if (isIseultInPlay(players)) {
							Model.log("Observed that Queen Iseult is in play", "questBehaviour", "playQuest");
							iseultInPlay = true;
							
						}
						
						if (isTristanInPlay(players)) {
							Model.log("Observed that Sir Tristan is in play", "questBehaviour", "playQuest");
							tristInPlay= true;
							
						}
						
						
						if (tristInPlay && iseultInPlay) {
							Model.log("Observed that Sir Tristan and Queen Iseult are in play", "questBehaviour", "playQuest");
				    		tristAndQueenInPlay = true;
				    	}
					 
					 bidsFromCardsInPlay = participants.get(0).alliesInPlay.calculateSpecialBids(quest.getName(), tristAndQueenInPlay);
					
					
					 
					 
					 
					 
					 
					if (participants.get(0).amourInPlay != null) {
						
						Model.log("1 Bid point from Amour in play", "questBehaviour", "playQuest");
						
						bidsFromCardsInPlay++;
						
					}
					
					
					
					
					participants.get(0).bid(t.getName(), bidsFromCardsInPlay, biddersAmount, highestBid, 1);
					
					
					
					

					updateAllPlayers();
	        		
	        		
	        		
	        		

	                List<Player> onePlayer = new ArrayList<Player>();
	        		
	        		
	        		onePlayer.add(participants.get(0));
	        		
	            	
	            	updateAllPlayers();
	        		setPlayersFinishedTurnToFalse();
	        		
	        		counter = 0;
	        		
	        		Model.log("Updating all players " + participants.get(0) + " bid (free bids not included): " +  participants.get(0).bid + "with free Bids: " + bidsFromCardsInPlay , "questBehaviour", "playQuest");
	        		
	        		do {
	        		
	        		
	        			
	        		for (int k=0; k<this.players.size(); k++) {
	        			
	        			
	        			
	        	
	        		
	        				
	        			
	        			if (this.players.get(k).hasFinishedTurn == false) {
	        			this.players.get(k).display(counter, t.getName(), ( participants.get(0).getName() + "has bid :" + participants.get(0).bid + "with free Bids: " + bidsFromCardsInPlay), onePlayer);
	        			}
	        			
	        		}
	        			
	        		counter =1;
	        		
	        		
	        		
	        		try {
	        			Thread.sleep(300);
	        			//System.out.println("weeee");
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	
	        		
	        		
	        		}while(allPlayersFinishedTurn(this.players) == false);
					
					
					
					if (bidsFromCardsInPlay + participants.get(0).bid >= highestBid) {
						Model.log(participants.get(0).getName() + "is the solo bid winner "+ t.getName() + " in quest: " + quest.getName() + "with " + participants.get(0).bid+ "active bids, and " + bidsFromCardsInPlay +" free bids", "questBehaviour", "playQuest");
						
						
						
						
						
						
						winnerCardsSpent = participants.get(0).bid - bidsFromCardsInPlay;
						winner = participants.get(0);
						highestBid = bidsFromCardsInPlay +biddersAmount[0];
						
					}else {
						
						
						if (participants.get(0).amourInPlay != null) {
							
							
							participants.get(0).amourInPlay = null;
							Model.log(participants.get(0).getName() + " discarded their Amour in play", "questBehaviour", "play");
							
							participants.get(0).adventureDeck.addToDiscardPile(new amourCard());
							
							
						}
						Model.log(participants.get(0).getName() + "has lost the bid for "+ t.getName() + " in quest: " + quest.getName() + "with " + participants.get(0).bid + "active bids, and " + bidsFromCardsInPlay +" free bids", "questBehaviour", "playQuest");
						participants.remove(0);
					}
					
				}
				int j = 0;
				while(participants.size() > 1)  {
					
					
					
					// participants announce how many cards they bid
						
					
						 if (j>=participants.size()) {
							 
							 round++;
							 j = 0;
						 }
						
						 int bidsFromCardsInPlay= 0;
						 
						 int[] biddersAmount = new int[1];
						 
						 
						 participants.get(j).playAllyCardsForBid(t.getName());
						 
						 
						 if (isIseultInPlay(players)) {
								Model.log("Observed that Queen Iseult is in play", "questBehaviour", "playQuest");
								iseultInPlay = true;
								
							}
							
							if (isTristanInPlay(players)) {
								Model.log("Observed that Sir Tristan is in play", "questBehaviour", "playQuest");
								tristInPlay= true;
								
							}
							
							
							if (tristInPlay && iseultInPlay) {
								Model.log("Observed that Sir Tristan and Queen Iseult are in play", "questBehaviour", "playQuest");
					    		tristAndQueenInPlay = true;
					    	}
						 
						 
						 bidsFromCardsInPlay = participants.get(j).alliesInPlay.calculateSpecialBids(quest.getName(), tristAndQueenInPlay);
						
						
						if (participants.get(j).amourInPlay != null) {
							Model.log("1 Bid point from Amour in play", "questBehaviour", "playQuest");
							
							bidsFromCardsInPlay++;
							
						}
						
						
						
						
						participants.get(j).bid(t.getName(), bidsFromCardsInPlay, biddersAmount, highestBid, 1);
						
					
						
						updateAllPlayers();
		        		
		        		
		        		
		        		

		                List<Player> onePlayer = new ArrayList<Player>();
		        		
		        		
		        		onePlayer.add(participants.get(j));
		        		
		            	
		            	updateAllPlayers();
		        		setPlayersFinishedTurnToFalse();
		        		
		        		counter = 0;
		        		
		        		Model.log("Updating all players " + participants.get(j) + " bid (free bids not included): " +  participants.get(j).bid + "with free Bids: " + bidsFromCardsInPlay , "questBehaviour", "playQuest");
		        		
		        		do {
		        		
		        		
		        			
		        		for (int k=0; k<this.players.size(); k++) {
		        			
		        			
		        			
		        	
		        		
		        				
		        			
		        			if (this.players.get(k).hasFinishedTurn == false) {
		        			this.players.get(k).display(counter, t.getName(), ( participants.get(j).getName() + "has bid :" + participants.get(j).bid + "with free Bids: " + bidsFromCardsInPlay), onePlayer);
		        			}
		        			
		        		}
		        			
		        		counter =1;
		        		
		        		
		        		
		        		try {
		        			Thread.sleep(300);
		        			//System.out.println("weeee");
		        		} catch (InterruptedException e) {
		        			// TODO Auto-generated catch block
		        			e.printStackTrace();
		        		}
		            	
		        		
		        		
		        		}while(allPlayersFinishedTurn(this.players) == false);

		        		
						
						
						
						
						if ((counter == 0 && bidsFromCardsInPlay + participants.get(j).bid >= highestBid) || (bidsFromCardsInPlay + participants.get(j).bid > highestBid)) {
							
							counter++;
							winnerCardsSpent = participants.get(j).bid;
							winner = participants.get(j);
							highestBid = bidsFromCardsInPlay + participants.get(j).bid;
									
							Model.log(participants.get(j).getName() + "is current bid winner "+ t.getName() + " in quest: " + quest.getName() + "with " + participants.get(j).bid + " active bids, and " + bidsFromCardsInPlay +" free bids", "questBehaviour", "playQuest");
							
						}else {
							
							Model.log(participants.get(j).getName() + "has lost the bid for "+ t.getName() + " in quest: " + quest.getName() + "with " + participants.get(j).bid + " active bids, and " + bidsFromCardsInPlay +" free bids", "questBehaviour", "playQuest");
							
							if (participants.get(j).amourInPlay != null) {
								
								Model.log(participants.get(j).getName() + " discarded their Amour in play", "questBehaviour", "playQuest");
								participants.get(j).amourInPlay = null;
								
								deck.addToDiscardPile(new amourCard());
								
							
							}
							
							
							
							participants.remove(j);
							
							j--;
							
						}
					
						
						j++;
					}
					
	
					// The participant's bid number has to be higher than the previous
					// participant's, otherwise pass
					// if(thisBid > highestBid) highestBid = thisBid; else bidders.remove(index);

					// Move onto the next person in the sequence
					
				

				
				
				// The case where there was only 1 participant for the test
		
	
				
			
				
				if (winner != null && participants.size()<1) {
				
				participants.add(winner);
				}else if (winner == null && participants.size() == 1) {
					
					
						
						 int bidsFromCardsInPlay= 0;
						 
						 int[] biddersAmount = new int[1];
						 
						 
						 participants.get(0).playAllyCardsForBid(t.getName());
						 
						 if (isIseultInPlay(players)) {
								Model.log("Observed that Queen Iseult is in play", "questBehaviour", "playQuest");
								iseultInPlay = true;
								
							}
							
							if (isTristanInPlay(players)) {
								Model.log("Observed that Sir Tristan is in play", "questBehaviour", "playQuest");
								tristInPlay= true;
								
							}
							
							
							if (tristInPlay && iseultInPlay) {
								Model.log("Observed that Sir Tristan and Queen Iseult are in play", "questBehaviour", "playQuest");
					    		tristAndQueenInPlay = true;
					    	}
						 
						 bidsFromCardsInPlay = participants.get(0).alliesInPlay.calculateSpecialBids(quest.getName(), tristAndQueenInPlay);
						
						
						if (participants.get(0).amourInPlay != null) {
							
							Model.log("1 Bid point from Amour in play", "questBehaviour", "playQuest");
							
							bidsFromCardsInPlay++;
							
						}
						
						
						
						
						
						
						
						
						
						
						participants.get(0).bid(t.getName(), bidsFromCardsInPlay, biddersAmount, highestBid, 2);
						
						
						
						

		                List<Player> onePlayer = new ArrayList<Player>();
		        		
		        		
		        		onePlayer.add(participants.get(0));
		        		
		            	
		            	updateAllPlayers();
		        		setPlayersFinishedTurnToFalse();
		        		
		        		counter = 0;
		        		
		        		Model.log("Updating all players " + participants.get(0) + " bid (free bids not included): " +  participants.get(0).bid + "with free Bids: " + bidsFromCardsInPlay , "questBehaviour", "playQuest");
		        		
		        		do {
		        		
		        		
		        			
		        		for (int k=0; k<this.players.size(); k++) {
		        			
		        			
		        			
		        	
		        		
		        				
		        			
		        			if (this.players.get(k).hasFinishedTurn == false) {
		        			this.players.get(k).display(counter, t.getName(), ( participants.get(0).getName() + "has bid :" + participants.get(0).bid + "with free Bids: " + bidsFromCardsInPlay), onePlayer);
		        			}
		        			
		        		}
		        			
		        		counter =1;
		        		
		        		
		        		
		        		try {
		        			Thread.sleep(300);
		        			//System.out.println("weeee");
		        		} catch (InterruptedException e) {
		        			// TODO Auto-generated catch block
		        			e.printStackTrace();
		        		}
		            	
		        		
		        		
		        		}while(allPlayersFinishedTurn(this.players) == false);
						
						
						
						
						if (bidsFromCardsInPlay + participants.get(0).bid > highestBid) {
							Model.log(participants.get(0).getName() + "is current bid winner "+ t.getName() + " in quest: " + quest.getName() + "with " + participants.get(0).bid + "active bids, and " + bidsFromCardsInPlay +" free bids", "questBehaviour", "playQuest");
							winnerCardsSpent = participants.get(0).bid;
							winner = participants.get(0);
							highestBid = bidsFromCardsInPlay +participants.get(0).bid;
							
						}else {
							
							
							if (participants.get(0).amourInPlay != null) {
								
								
								participants.get(0).amourInPlay = null;
								
								Model.log(participants.get(i).getName() + " discarded their Amour in play", "questBehaviour", "playQuest");
								
								participants.get(0).adventureDeck.addToDiscardPile(new amourCard());
								
								
								
							}
							
							
							Model.log(participants.get(0).getName() + "has lost the bid for "+ t.getName() + " in quest: " + quest.getName() + "with " + participants.get(0).bid + "active bids, and " + bidsFromCardsInPlay +" free bids", "questBehaviour", "playQuest");
							
							participants.remove(0);
						}
					
					
					
					
				}
				
		
					// Winner discards the number of cards they bid, get awarded 1 card from
					// AdventureDeck
				if (participants.size()>0 && winner != null) {
						
						Model.log(winner.getName() + "has won the bid for "+ t.getName() + " in quest: " + quest.getName(), "questBehaviour", "playQuest");
						
						
						
						
						
						
						
						
						
						
						
				
						
						int originalSize = winner.deck.getSize();
						
						if (winnerCardsSpent < 0) {
							
							winnerCardsSpent = 0;
							
						}
						
						
						int neededSize = originalSize  - winnerCardsSpent;
						
					
						
						
						
						
						
						Model.log(winner.getName() + " has to discard: " + winnerCardsSpent  , "questBehaviour", "playQuest");
								
						
						
						while(winner.deck.getSize() > neededSize) {
							

							int amountToDiscard = winner.deck.getSize() - neededSize;
							
							Model.log(winner.getName() + "has to discard: " + amountToDiscard  , "questBehaviour", "playQuest");
							
							
							winner.discardAfterWinning(round, amountToDiscard);
							
						
							
						}
						
						
						
					
			
					winner.addCard(deck.dealCard());
				
					
					Model.log(winner.getName() + "is dealt a card" , "questBehaviour", "playQuest");
					
					while(winner.deck.getSize() > 12) {
						
						int amountToDiscard = winner.deck.getSize() - 12;
						
						Model.log(winner.getName() + "has to discard: " + amountToDiscard  , "questBehaviour", "playQuest");
						
						
						winner.decideCardsToDiscard(amountToDiscard);
						
						
						
						
						
					}

					
					
					List<Player> onePlayer = new ArrayList<Player>();
	        		
	        		
	        		onePlayer.add(winner);
	        		
	            	
	            	updateAllPlayers();
	        		setPlayersFinishedTurnToFalse();
	        		
	        		counter = 0;
	        		
	        		Model.log("Updating all players " + winner + " has won the Test: " + t.getName() , "questBehaviour", "playQuest");
	        		
	        		do {
	        		
	        		
	        			
	        		for (int k=0; k<this.players.size(); k++) {
	        			
	        			
	        			
	        	
	        		
	        				
	        			
	        			if (this.players.get(k).hasFinishedTurn == false) {
	        			this.players.get(k).display(counter, t.getName(), ( winner.getName() + " has won the Test: " + t.getName() ), onePlayer);
	        			}
	        			
	        		}
	        			
	        		counter =1;
	        		
	        		
	        		
	        		try {
	        			Thread.sleep(300);
	        			//System.out.println("weeee");
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	
	        		
	        		
	        		}while(allPlayersFinishedTurn(this.players) == false);
					
					
						
					
					
					
				updateAllPlayers();
                    
                    
                    
                    
                    
				}else {
					
					Model.log("No bidders for "+ t.getName() + " in quest: " + quest.getName(), "questBehaviour", "playQuest");
					
				}
				
				
	        }
	
		}
			

} 
}
