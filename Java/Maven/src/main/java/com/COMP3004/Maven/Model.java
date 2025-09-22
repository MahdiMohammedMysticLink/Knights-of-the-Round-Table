package com.COMP3004.Maven;

import com.COMP3004.Maven.Story.eventCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;
import com.COMP3004.Maven.Adventure.amourCard;
import com.COMP3004.Maven.Rank.*;
import com.COMP3004.Maven.TableDeck.*;
import com.COMP3004.Socket.GameClient;
import com.COMP3004.Socket.GameServer;
import com.COMP3004.Socket.ObjectArray;
import com.COMP3004.Socket.ServerThread;

import javafx.application.Platform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
	// The model has players, cards, some reference to UI for Observer pattern,
	// current Story card
	public static List<Player> players;

	static boolean isServer;
	public static String messageToSend;
	GameClient client;
	static GameServer server;
	public static int playerNumber; //each client will be initialized with a player number
	
	AdventureDeck AD;
	StoryDeck SD;
	public storyCard story;
	public Player current;
    File file = new File("log.txt");
    static FileWriter writer;
    static PrintWriter PrinterWriter; 
	int bonusShields = 0;
	int scen;
	boolean gameWon;
	UI modelUI; 

	

		
	
	
	int currentIndex;

	public List<Player> winners;
	
	/**
	 * 
	 * @param logtext
	 * @param className
	 * @param functionName
	 */
	public static void log(String logtext, String className, String functionName) {
		
	    try(BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true))) {
	    	
		    	bw.write(className + "\n" + functionName + "\n" + logtext + "\n");
		    	bw.newLine();
		    	bw.flush();
		    	
	    }catch (IOException e) {
	        e.printStackTrace();
	    } 
	}

	// To generate specific scenarios defined in Adventure/StoryDeck classes
	public Model(UI ui, int scen) {
		file = new File("log.txt");
	    writer = null;
	    try {
	        writer = new FileWriter(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	        //break; this should be called in case opening the file doesn't open, end the game immediately
	    } finally {
	        //if (writer != null) try { writer.close(); } catch (IOException ignore) {}
	    }
	    System.out.printf("File is located at %s%n", file.getAbsolutePath());

		gameWon = false;
		winners = new ArrayList<Player>();
		players = new ArrayList<Player>();
		this.scen = scen;

		
		initDecks(scen);
		initPlayers(ui, scen);
		
	}

	public Model(UI ui)

	{
		modelUI = ui;
		file = new File("log.txt");
		//PrintWriter = new PrintWriter(, true);
	    writer = null;
	    try {
	        writer = new FileWriter(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	        //break; this should be called in case opening the file doesn't open, end the game immediately
	    } finally {
	        //if (writer != null) try { writer.close(); } catch (IOException ignore) {}
	    }
	    System.out.printf("File is located at %s%n", file.getAbsolutePath());

		
		gameWon = false;
		winners = new ArrayList<Player>();
		players = new ArrayList<Player>();
		
		initDecks();
		initPlayers(ui);
	

	}

	public void initPlayers(UI ui) {
		System.out.println("InitPlayers");
		//gameSetupInfo [0] is the number of human players
		//gameSetupInfo [1] is the number of AI players
		//gameSetupInfo [2] determines if the game is offline, a server, or a client
		// gameSetupInfo[2]: 0 = offline 1 = server 2 = client
		//gameSetupInfo [3] is the port number
		//IP is the string of the IPaddress, it will only be set if in client mode
		int[] gameSetupInfo = {-1, -1, -1, -1};
		String[] IP = {""};
		// System.out.println(gameSetupInfo[0] + " humans are playing!");

		
		Platform.runLater(new Runnable() {
			public void run() {

				ui.startScreen(gameSetupInfo, IP);

			}
		});

		while (gameSetupInfo[0] == -1 | gameSetupInfo[1] == -1) {
			try {
				Thread.sleep(1000);
				System.out.println(" humans are playing!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println(IP[0]);
		
		
		if(gameSetupInfo[2] == 0) {
			//play offline game
		}else if(gameSetupInfo[2] == 1){
			server = new GameServer(gameSetupInfo[3], gameSetupInfo[0]);
			isServer = true;
			//for(int i = 0; i < gameSetupInfo[0]; i++) {
			//	server.Servers[i].sendMessage("you are player number " + i);
			//}
			System.out.println("done calling connect server function!");
		}else if (gameSetupInfo[2] == 2) {
			try {
				client = new GameClient(gameSetupInfo[3], IP[0], ui);
				client.connect();
				listenToServer();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isServer = false;
			System.out.println("done calling connect client function!");
		}
		
		
		int numHuman = gameSetupInfo[0];
		int numAI = gameSetupInfo[1];
		
		log(numHuman + " human players are playing", "Model", "initPlayers");
		log(numAI + " AI players are playing", "Model", "initPlayers");
		

		for (int i = 1; i <= numHuman; i++) {
			String name = "Player " + i;
			// Prompt for player's name, assign it to name
			// Create human player, add human player to players list
			players.add(new Human(name, new squireCard(), AD, i-1));
		}

		// Create the remaining AI players
		for (int i = 1; i <= numAI; i++) {
			String name = "Player " + i + "(AI)";
		}

		// Shuffle players so that their order is random
		Collections.shuffle(players);
		ui.passInPlayers(players);
		current = players.get(0);
	}

	public void initDecks() {
		// Initialize decks according to scenario given
		AD = new AdventureDeck();
		SD = new StoryDeck();
	}

	public void initDecks(int scen) {
		// Initialize decks according to scenario given
		
		AD = new AdventureDeck();
		AD.shuffleCards();
		
		SD = new StoryDeck(scen);
		SD.shuffleCards();
	}

	public void getNextPlayer() {

		// Removes and assigns the player in the first position as current player
		current = players.remove(0);

		// Inserts the current player at the end of the list to maintain order
		players.add(current);

	}
	
  public void initPlayers(UI ui, int scen) {
	  
	  
	  
	  for (int i=1; i<=4; i++) {
		  
		  
		  String name = "Player " + i;
			// Prompt for player's name, assign it to name
			// Create human player, add human player to players list
		players.add(new Human(name, new squireCard(), AD, i-1));
		 
	  }
	//  ui.passInPlayers(players);
	  current = players.get(0);
  }
  
  
  public void play(int scen) {
	  
	  //server.sendOutputToClient("test!!!!");
	  
	  
	  if (scen == 1) {
		  
		 
		
		  
		  players.get(0).addCard(AD.find("Boar"));
		  
		  AD.remove(AD.find("Boar"));
		  players.get(0).addCard(AD.find("Saxons"));
		  AD.remove(AD.find("Saxons"));
		  players.get(0).addCard(AD.find("Sword"));
		  AD.remove(AD.find("Sword"));
		  players.get(0).addCard(AD.find("Dagger"));
		  AD.remove(AD.find("Dagger"));
		  
		  
		
		  players.get(1).addCard(AD.find("Dragon"));
		  
		  AD.remove(AD.find("Dragon"));
		  players.get(1).addCard(AD.find("Black Knight"));
		  AD.remove(AD.find("Black Knight"));
		  players.get(1).addCard(AD.find("Lance"));
		  AD.remove(AD.find("Lance"));
		  
		
		  
		  
		
		  AD.remove(AD.find("Horse"));
		  players.get(2).addCard(AD.find("AMOUR"));
		  AD.remove(AD.find("AMOUR"));
		  
	
		  
		  
		  players.get(3).addCard(AD.find("Dagger"));
		  AD.remove(AD.find("Dagger"));
		  players.get(3).addCard(AD.find("Battle-ax"));
		  AD.remove(AD.find("Battle-ax"));
		  
		  
		  players.get(3).addCard(AD.find("Giant"));
		  AD.remove(AD.find("Giant"));
		  
		  
		  
		  while(players.get(0).deck.getSize()<12) {
			  
			  
			  players.get(0).addCard(AD.dealCard());
		  }
		  
		  
		  
		  while(players.get(1).deck.getSize()<12) {
			  
			  
			  
			  players.get(1).addCard(AD.dealCard());
			  
		  }
		  
		  
		  while(players.get(2).deck.getSize()<12) {
			  
			  
			  players.get(2).addCard(AD.dealCard());
			  
			  
			  
			  
		  }
		  
		  
		  while(players.get(3).deck.getSize()<12) {
			  
			  
			  players.get(3).addCard(AD.dealCard());
			  
			  
			  
			  
		  }
	  }
	  
	  
	  
	  
	  
if (scen >= 2) {
		  
		 
		
		  
		
		  
		  players.get(0).addCard(AD.find("Sir Tristan"));
		  AD.remove(AD.find("Sir Tristan"));
		  players.get(0).addCard(AD.find("King Pellinore"));
		  AD.remove(AD.find("King Pellinore"));
		  players.get(0).addCard(AD.find("Dagger"));
		  AD.remove(AD.find("Dagger"));
		  
		  
	
		  
		  players.get(1).addCard(AD.find("Queen Iseult"));
		  
		  AD.remove(AD.find("Queen Iseult"));
		  players.get(1).addCard(AD.find("Black Knight"));
		  AD.remove(AD.find("Black Knight"));
		  players.get(1).addCard(AD.find("AMOUR"));
		  AD.remove(AD.find("AMOUR"));
		  
		
		  
		  
		
		  players.get(2).addCard(AD.find("Test of the Questing Beast"));
		  AD.remove(AD.find("Test of the Questing Beast"));
		  players.get(2).addCard(AD.find("Thieves"));
		  AD.remove(AD.find("Thieves"));
		  
		  
		  players.get(2).addCard(AD.find("Green Knight"));
		  AD.remove(AD.find("Green Knight"));
		  
		  
		  players.get(2).addCard(AD.find("Saxon Knight"));
		  AD.remove(AD.find("Saxon Knight"));
		  
		  

		  players.get(2).addCard(AD.find("Giant"));
		  AD.remove(AD.find("Giant"));
		  
		
		  
		  players.get(3).addCard(AD.find("King Arthur"));
		  AD.remove(AD.find("King Arthur"));
		  players.get(3).addCard(AD.find("Sir Lancelot"));
		  AD.remove(AD.find("Sir Lancelot"));
		  
		  
		  players.get(3).addCard(AD.find("Thieves"));
		  AD.remove(AD.find("Thieves"));
		  
		  
		  while(players.get(3).deck.getSize()<12) {
			  
			  
			  players.get(3).addCard(AD.dealCard());
			  
			  
			  
			  
		  }
		  
		  while(players.get(2).deck.getSize()<12) {
			  
			  
			  players.get(2).addCard(AD.dealCard());
			  
			  
			  
			  
		  }
		  
		  while(players.get(1).deck.getSize()<12) {
			  
			  
			  
			  players.get(1).addCard(AD.dealCard());
			  
		  }
		  
	  while(players.get(0).deck.getSize()<12) {
			  
			  
			  players.get(0).addCard(AD.dealCard());
		  }
		  
	  }
	  
	  
	  
		int counter =0;
	  while (gameWon == false) {
		  
		  
		 
			// Card is automatically drawn from StoryDeck
			
		  	if (counter == 0) {
		  		
		  		if (scen == 1) {
		  			story = new questCard("Boar Hunt", 2, "Boar");
		  		}
		  		if (scen == 2) {
		  			
		  			story = new questCard("Search for the Questing Beast", 4, "NULL");
		  			
		  		}
		  		if (scen == 3) {
		  			
		  			story = new tournamentCard("Camelot", 3);
		  			
		  		}
		  		
		  		SD.remove(story);
		  	}
		  	else if (counter == 1) {
		  		
		  		if (scen == 1) {
		  		story = new eventCard("Prosperity Throughout the Realm");
		  		}
		  		
		  		if (scen >=2) {
		  			
		  		story = new questCard("Defend the Queen's Honor", 4, "");
		  			
		  		}
		  		SD.remove(story);
		  	}
		  	
		  	else if (counter == 2) {
		  		
		  		
		  		story = new eventCard("Chivalrous Deed");
		  		SD.remove(story);
		  	}
		  	if (counter>=3) {
		  	
		  	story = SD.draw();
		  	
		  	}
			counter++;
			/*
			 * Story card is played: involves all players including current adventureDeck
			 * for drawing cards in quests and events like "Prosperity Throughout the Realm"
			 * bonusShields for quests or events
			 */

			System.out.println("Story: " + story.getName());
			Model.log("Story: " + story.getName(), "Model", "play");
			displayStory();
			story.play(current, players, AD, bonusShields);
			
			
			
			getNextPlayer();
			resetPlayerAttributes();
	  
		
	  
	  
	  
	  for (int i = 0; i < players.size(); i++) {

				if (players.get(i).wonGame) {

				
						gameWon = true;
						winners.add(players.get(i));

					}

				}

			

			// Current player changes to the next in list
		
		getNextPlayer();
		resetPlayerAttributes();
	  }

		// Break only if a player has won
		// Display who won game

		System.out.println("Players who won");

		for (int i = 0; i < winners.size(); i++) {

			System.out.println(winners.get(i).getName());

		}

	  
	  
	  
	  
  }
  
  
  
  public void setPlayersFinishedTurnToFalse() {
		
		for (int i=0; i<players.size(); i++) {
			
			players.get(i).hasFinishedTurn = false;
			
		}
		
	}

	public void updateAllPlayers() {
		
		
		Model.log("All players are being updated information about players before next Story", "Model", "updateAllPlayers");
		
		for (int i= 0; i<players.size(); i++) {
			
			
			
			players.get(i).updateUI();
			
		}
		
		
		
	}
	

	
	public boolean allPlayersFinishedTurn() {
		
		
		
		for (int i=0; i<players.size(); i++) {
			
			
			if (players.get(i).hasFinishedTurn == false) {
				
				return false;
				
			}
			
			
			
		}
		
		return true;
		
		
		
	}
  
  
	
	public void displayStory() {
		Model.log("Story: " + story.getName() + " to be displayed to all players.", "Model", "displayStory");
		
		int counter = 0;
		
		do {
			
			
			for (int i=0; i<players.size(); i++) {
			
				if (players.get(i).hasFinishedTurn == false) {
				players.get(i).displayStory(counter, story);
				}
				
			}
			
			
			

			counter =1;	
			
			try {
			Thread.sleep(300);
			}
			catch(InterruptedException e){
				
				e.printStackTrace();
				
			}
			
			
		}while(allPlayersFinishedTurn() == false);
		
		
		setPlayersFinishedTurnToFalse();
		
	}


	public void play() {
		// while(true)
		
		for (int i = 0; i < players.size(); i++) {
			
			log("adding cards for " + players.get(i).getName(), "Model", "play");
			
			for (int j = 0; j < 12; j++) {

				players.get(i).addCard(AD.dealCard());

			}
		
		}
		
		
		
		//try { writer.close(); } catch (IOException ignore) {}  //going to need to call this once the game is done
		//keeping it here for now to demonstrate functionality

		while (gameWon == false) {
			// Card is automatically drawn from StoryDeck
			story = SD.draw();

		
			
			/*
			 * Story card is played: involves all players including current adventureDeck
			 * for drawing cards in quests and events like "Prosperity Throughout the Realm"
			 * bonusShields for quests or events
			 */

			System.out.println("Story:" + story.getName());
			Model.log("Story: " + story.getName(), "Model", "play");
			
			
			updateAllPlayers();
			
			displayStory();
			
			resetPlayerAttributes();

			
			
			story.play(current, players, AD, bonusShields);

			for (int i = 0; i < players.size(); i++) {

				if (players.get(i).wonGame) {

				

						winners.add(players.get(i));

					}

				}

			

			// Current player changes to the next in list
			
			getNextPlayer();
			resetPlayerAttributes();
		}

		// Break only if a player has won
		// Display who won game

		Model.log("Player(s) who won:", "Model", "play");

		for (int i = 0; i < winners.size(); i++) {

			Model.log(winners.get(i).getName(), "Model", "play");

		}

	}
	
	public void listenToServer(){
		System.out.println("inside the listenToServer function");
		ObjectArray temp;
		while (true) {
			
			
			
			temp = client.recieveData();
			//process string into UI function call
			//instance of UI inside this class
			//figure out some way to wait for user response (different for each UI call most likely)
			//make return message, with info on player moves
			//temp = returnMessage
			
			System.out.println("about to sendData");
			//System.out.println(((Human)temp.get(1)).isSponsoring);
			client.sendData(temp);
		}
	}
	
	
	
	
	public void resetPlayerAttributes() {
		
		
		Model.log("Reseting some of the attributes of all players to false ", "Model", "resetPlayerAttributes");
		
		
		
		for (int i=0; i< players.size(); i++) {
			
			//to deal with edge case of ending on a test
			
			if (players.get(i).amourInPlay != null) {
				
				players.get(i).amourInPlay = null;
				
				Model.log(players.get(i).getName() + " discarded their Amour in play",  "Model", "resetPlayerAttributes");
				
				AD.addToDiscardPile(new amourCard());
			}
			
			players.get(i).setAllAttributesToFalse();
		}
	
	
	}
	
	
	

}