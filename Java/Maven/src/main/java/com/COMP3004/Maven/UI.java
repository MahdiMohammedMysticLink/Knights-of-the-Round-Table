package com.COMP3004.Maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;

import com.COMP3004.Maven.Adventure.allyCard;
import com.COMP3004.Maven.Adventure.amourCard;
import com.COMP3004.Maven.PlayerDeck.AllyDeck;
import com.COMP3004.Maven.PlayerDeck.FoeDeck;
import com.COMP3004.Maven.PlayerDeck.PlayerDeck;
import com.COMP3004.Maven.PlayerDeck.WeaponDeck;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.storyCard;
import com.COMP3004.Maven.Story.tournamentCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import com.COMP3004.Socket.ObjectArray;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UI extends Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static UI UI = null;
	public static int height = 700;
	public static int width = 1365;
	public static int cardWidth = 150;
	public static int cardHeight = 210;
	public static int newCardWidth = 120;
	public static int newCardHeight = 120;
	public static boolean playingTourney = false;
	public static boolean questInPlay = false;
	public static boolean fightingFoe = false;
	public static boolean discardingCards = false;
	public static boolean choosingAlliesForBid = false;
	public static int numStages = 3;
	public static Timer timer;
	public static int amountToDiscard = 0;
	public static int amountDiscarded=0;
	public static List<Player> playersInGame;
	
	
	//public static Human player;
	
	Utility utility;

	AdventureDeck AD;
	Pane canvas = new Pane();
	Stage dummyStage = new Stage();
	Label gameInfo = new Label();
	String gameState = "Game State";
	Label cardInfo1 = new Label();
	Label cardInfo2 = new Label();
	Label cardInfo3 = new Label();
	Label cardInfo4 = new Label();
	
	Map<String,String> cardInfoMap = new HashMap<String,String>() {/**
		 * 
		 */
	

	{
	    put("King Arthur", "ALLY:King Arthur\n+10\n2 Bids");
	    put("AMOUR", "AMOUR\n+10\n1 Bid");
	    put("King Pellinore", "ALLY:King Pellinore\n+10\n4 Bids on the Search for\nthe Questing Beast Quest");
	    put("Merlin", "ALLY:Merlin\nPlayer may preview any one stage per Quest");
	    put("Queen Guinevere", "ALLY:Queen Guinevere\n3 Bids");
	    put("Queen Iseult", "ALLY:Queen Iseult\n2 Bids\n4 Bids when Tristan is in play");
	    put("Sir Galahad", "ALLY:Sir Galahad\n+15");
	    put("Sir Gawain", "ALLY:Sir Gawain\n+10\n+20 on the Test of the\nGreen Knight Quest");
	    put("Sir Lancelot", "ALLY:Sir Lancelot\n+15\n+25 when on the Quest\nto Defend the Queen's Honor");
	    put("Sir Percival", "ALLY:Sir Percival\n+5\n+20 on the Search for the Holy Grail Quest");
	    put("Sir Tristan", "ALLY:Sir Tristan\n+10\n+20 when Queen Iseult is in play");
	    put("Black Knight", "FOE:Black Knight\n25/30");
	    put("Boar", "FOE:Boar\n5/15");
	    put("Dragon", "FOE:Dragon\n50/70");
	    put("Evil Knight", "FOE:Evil Knight\n20/30");
	    put("Giant", "FOE:Giant\n40");
	    put("Green Knight", "FOE:Green Knight\n25/40");
	    put("Mordred", "FOE:Mordred\n30\nUse as Foe or discard at any time to\nremove any player's Ally from play");
	    put("Robber Knight", "FOE:Robber Knight\n15");
	    put("Saxon Knight", "FOE:Saxon Knight\n15/25");
	    put("Saxons", "FOE:Saxons\n10/20");
	    put("Thieves", "FOE:Thieves\n5");
	    put("Battle-ax", "WEAPON:Battle-ax\n+15");
	    put("Dagger", "WEAPON:Dagger\n+5");
	    put("Excalibur", "WEAPON:Excalibur\n+30");
	    put("Horse", "WEAPON:Horse\n+10");
	    put("Lance", "WEAPON:Lance\n+20");
	    put("Sword", "WEAPON:Sword\n+10");
	    put("Test of Morgan Le Fey", "Test of Morgan Le Fey\nMinimum 3 Bid");
	    put("Test of Temptation", "Test of Temptation");
	    put("Test of the Questing Beast", "Test of the Questing Beast\nMinimum 4 Bid on the\nSearch for the Questing\nBeast Quest");
	    put("Test of Valor", "Test of Valor");
	}};

	
	

	//boolean amourAlreadySelectedByFighter;

	// TO DO: fix bug where removing cards from sponsor gives gaps in cards
	// ArrayList<ArrayList<ImageView>> CardsForStages = new
	// ArrayList<ArrayList<ImageView>>();
	
	String styleSheetPath = "file:///" + new File("stylesheet.css").getAbsolutePath().replace("\\", "/");
	String styleSheetPath2 = "file:///" + new File("stylesheet2.css").getAbsolutePath().replace("\\", "/");
	
	ArrayList<Boolean> Selected = new ArrayList<Boolean>();
	int[] QuestSelected;
	ArrayList<ImageView> SelectForFoes = new ArrayList<ImageView>();

	HashMap<String, Image> ImageMap = new HashMap<String, Image>();
	Timer time;

	int currStagePicked = 1;
	
	Font globalFont;

	public void loadCards() {

		globalFont = Font.loadFont("file:///" + new File("DUKEPLUS.TTF").getAbsolutePath().replace("\\", "/"), 20);
        //inside of stylesheet.css, specify font-family as the name of the font. open the font file and 
	    //the name of the font should be on the first line
	    //also, manually set each scene to have the stylesheet applied to it
	    
		AD = new AdventureDeck();
		utility = new Utility();
		File cardsDir = new File("src/resources/newCards");

		File[] cardsFile = cardsDir.listFiles();

		System.out.println(cardsFile.length);

		for (File cardFile : cardsFile) {
			try {
				System.out.println(cardFile.getName().substring(2, cardFile.getName().length() - 4));
				ImageMap.put(cardFile.getName().substring(2, cardFile.getName().length() - 4),
						new Image(new FileInputStream(cardFile.getPath())));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static UI waitForUI() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
		return UI;
	}

	public static void setUI(UI UI0) {
		UI = UI0;
		latch.countDown();
	}

	public UI() {
		setUI(this);
	}

	public void selectCard(String card, List<String> allowable) {}
	
	public void start(Stage primaryStage) throws Exception {}

	// Made by Ryan: used to prompt Human players if they want to be the sponsor of
	// the quest
	public boolean doISponsor() {
		Alert question = new Alert(Alert.AlertType.CONFIRMATION);
		question.setTitle("SPONSOR REQUEST");
		question.setHeaderText("Do you want to sponsor this quest?");
		boolean response = (question.showAndWait().get() == ButtonType.OK);
		question.close();
		return response;
	}
	
	
	
	
	public void doIParticipateTournament(ObjectArray objects) {
		
		canvas = new Pane();
		
	
		List<String> cardsInHandList = (utility.playerDeckCards(((Human)objects.get(1)).deck));
		
		tournamentCard tourney = (tournamentCard)objects.get(2);
		
	
		
		
		AllyDeck allies = ((Human)objects.get(1)).alliesInPlay;

		amourCard amourCard = ((Human)objects.get(1)).amourInPlay;

		PlayerDeck alliesAndAmour = new PlayerDeck();

		for (int i = 0; i < allies.getSize(); i++) {

			alliesAndAmour.add(allies.get(i));

		}

		if (amourCard != null) {

			alliesAndAmour.add(amourCard);

		}

		List<String> cardsInPlayList = utility.playerDeckCards(alliesAndAmour);

		String[] cardsInHand;
		String[] cardsInPlay;

		if (((Human)objects.get(1)).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}

		if (alliesAndAmour.getSize() != 0) {
			cardsInPlay = new String[alliesAndAmour.getSize()];
			cardsInPlayList.toArray(cardsInPlay);

		} else {

			cardsInPlay = null;

		}
		
		
		
		ImageView imgView;
		
		imgView = new ImageView();
		imgView.setImage(ImageMap.get(tourney.getName()));
		imgView.relocate(0, 45);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(tourney.getName());
		
		donePlayerTurn(((Human)(objects.get(1))), imgView);

		canvas.getChildren().addAll(imgView);
		
		
		gameInfo = new Label(((Human)(objects.get(1))).getName());
		gameInfo.relocate(width / 2 - 170, 10);
		canvas.getChildren().addAll(gameInfo);

		gameInfo = new Label("Participate");
		gameInfo.relocate(width / 2 - 80, 10);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("player is participating!");

			((Human)(objects.get(1))).isParticipating = true;
			
			//nextPlayersTurn(player);
		});

		gameInfo = new Label("Don't Participate");
		gameInfo.relocate(width / 2 + 35, 10);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("player is not participating!");

			((Human)(objects.get(1))).isParticipating = false;
			// player.isSponsoring = false;
			// nextPlayersTurn(player);
		});
		
		renderCurrentPlayersCards(((Human)(objects.get(1))), cardsInPlay, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		

	}
	
	
	
	

	public void doIParticipate(ObjectArray objects) {

		
		
		questCard quest = (questCard)objects.get(2);
		PlayerDeck[] questStages = ((Human)objects.get(1)).selectedQuestStages;
		canvas = new Pane();

		List<String>[] stages = utility.questStages(questStages);

		ImageView imgView;

		for (int i = 0; i < stages.length; i++) {
			System.out.println("stage" + (i + 1));
			for (int j = 0; j < stages[i].size(); j++) {
				System.out.println(stages[i].get(j));
				imgView = new ImageView();
				imgView.setImage(ImageMap.get("Face Down Adventure Card"));
				imgView.relocate(cardWidth + (width - cardWidth) / numStages * i + (10 * j), 45 + (10 * j));
				imgView.setFitWidth(cardWidth);
				imgView.setFitHeight(cardHeight);
				imgView.setPreserveRatio(true);
				imgView.setId(stages[i].get(j));

				canvas.getChildren().addAll(imgView);
			}
		}

		List<String> cardsInHandList = (utility.playerDeckCards(((Human)objects.get(1)).deck));

		// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
		// Lancelot"};
		// List<String> cardsInPlayList = (utility.playerDeckCards(player.cardsInPlay));

		AllyDeck allies = ((Human)objects.get(1)).alliesInPlay;

		amourCard amourCard = ((Human)objects.get(1)).amourInPlay;

		PlayerDeck alliesAndAmour = new PlayerDeck();

		for (int i = 0; i < allies.getSize(); i++) {

			alliesAndAmour.add(allies.get(i));

		}

		if (amourCard != null) {

			alliesAndAmour.add(amourCard);

		}

		List<String> cardsInPlayList = utility.playerDeckCards(alliesAndAmour);

		String[] cardsInHand;
		String[] cardsInPlay;

		if (((Human)objects.get(1)).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}

		if (alliesAndAmour.getSize() != 0) {
			cardsInPlay = new String[alliesAndAmour.getSize()];
			cardsInPlayList.toArray(cardsInPlay);

		} else {

			cardsInPlay = null;

		}

		// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
		// Lancelot"};

		imgView = new ImageView();
		imgView.setImage(ImageMap.get(quest.getName()));
		imgView.relocate(0, 45);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(quest.getName());

		donePlayerTurn(((Human)objects.get(1)), imgView);

		canvas.getChildren().addAll(imgView);
		
		gameInfo = new Label(((Human)objects.get(1)).getName());
		gameInfo.relocate(width / 2 - 170, 10);
		canvas.getChildren().addAll(gameInfo);

		gameInfo = new Label("Participate");
		gameInfo.relocate(width / 2 - 80, 10);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("player is participating!");

			((Human)objects.get(1)).isParticipating = true;
			// player.isSponsoring = false;
			// nextPlayersTurn(player);
		});

		gameInfo = new Label("Don't Participate");
		gameInfo.relocate(width / 2 + 35, 10);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("player is not participating!");

			((Human)objects.get(1)).isParticipating = false;
			// player.isSponsoring = false;
			//nextPlayersTurn((Human)objects.get(1));
		});

		renderCurrentPlayersCards(((Human)objects.get(1)), cardsInPlay, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();

		// show cards in stages but faced down so they know how much cards each stage
		// has like it would be in real life...........

		// suggest making a function when getting to merlin

	}
	
	
	

	
	
	
	
	public void updatePlayerInGame(Player player) {
		
		
		for (int i=0; i<playersInGame.size();i++) {
			
			
			if (playersInGame.get(i).getName() == player.getName()) {
				
				
				playersInGame.set(i, player);
				
			}
				
				
			
		}
		
	}
	
	
	public void passInPlayers(List<Player> players) {
		
		
		
		playersInGame = new ArrayList<Player>();
		
		playersInGame.addAll(players);
		
		
		
	}
	
	public void passInPlayers(ObjectArray objects) {
		
		
		
		playersInGame = new ArrayList<Player>();
		
		playersInGame.addAll((ArrayList<Player>)objects.get(3));
		
		
		
	}
	
	public void displayPlayersInfo() {
		
		
	
		
		
		
		for (int i=0; i<playersInGame.size(); i++) {
			
			
			String rankCard = playersInGame.get(i).getRank().getName();
			
			
			
			
			
			gameInfo = new Label(playersInGame.get(i).getName() +":  Rank: " + rankCard );
			gameInfo.relocate( (230*i)+100,100);
			canvas.getChildren().addAll(gameInfo);
			
			
			gameInfo = new Label("Points: " + (playersInGame.get(i).getRank()).getPoints() +"  Shields: " + playersInGame.get(i).getShields());
			gameInfo.relocate((230*i)+100, 140);
			canvas.getChildren().addAll(gameInfo);
			
			
			
			
			
			gameInfo = new Label("Amount of cards: " + (playersInGame.get(i).deck.getSize()));
			gameInfo.relocate((230*i)+100, 180 );
			canvas.getChildren().addAll(gameInfo);
			ImageView imgView;
			// renderPlayerInfo(players);

			imgView = new ImageView();
			imgView.setImage(ImageMap.get(rankCard));
			imgView.relocate((230*i)+100 , 375);
			imgView.setFitWidth(cardWidth);
			imgView.setFitHeight(cardHeight);
			imgView.setPreserveRatio(true);
			imgView.setId(rankCard);
			
			
			canvas.getChildren().addAll(imgView);
		}
		
		
		
	}
	
	
	
	
	
	public void discardCards(ObjectArray objects) {
		

		canvas = new Pane();
		int requiredAmount = (int)(objects.get(2));
		List<String> cardsInHandList = (utility.playerDeckCards(((Human)objects.get(1)).deck));
		String[] cardsInHand;
		String[] allies;
		if (((Human)objects.get(1)).alliesInPlay.getSize()>0) {
			
			
			allies = (utility.allyCards(((Human)objects.get(1)).alliesInPlay));
		}
		else {
			
			allies = null;
		}
		
		if (((Human)objects.get(1)).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}
	
		
		gameInfo = new Label(((Human)objects.get(1)).getName() +" must discard "+ requiredAmount);
		gameInfo.relocate(width / 2 - 35, 10);
		canvas.getChildren().addAll(gameInfo);
		
		amountToDiscard =requiredAmount;
		ImageView imgView;
		// renderPlayerInfo(players);

		imgView = new ImageView();
		imgView.setImage(ImageMap.get("Face Down Adventure Card"));
		imgView.relocate(0, 45);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId("Face Down Adventure Card");
		
	
		// render weapons face down

		donePlayerTurn(((Human)objects.get(1)), imgView);

	
		amountDiscarded = 0;
		discardingCards = true;
		canvas.getChildren().addAll(imgView);

		renderCurrentPlayersCards(((Human)objects.get(1)), allies, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
		
	}
	

	// Made by Ryan: used to prompt Human players if they want to participate in
	// event
	public void alertUser(String message) {
		Alert question = new Alert(Alert.AlertType.INFORMATION);
		question.setTitle("ALERT");
		question.setHeaderText(message);
	}

	// Made by Ryan: used to prompt Human players if they want to participate in
	// event
	public boolean foeOrTest() {
		Alert question = new Alert(Alert.AlertType.CONFIRMATION);
		question.setTitle("FOE OR TEST");
		question.setHeaderText("Do you want this stage to use a foe? Selecting 'Cancel' will use a test.");
		boolean response = (question.showAndWait().get() == ButtonType.OK);
		question.close();
		return response;
	}

	// Made by Ryan: asks the player if they are finished selecting n cards
	public boolean areYouDone() {
		Alert question = new Alert(Alert.AlertType.CONFIRMATION);
		question.setTitle("FINISHED?");
		question.setHeaderText("Are you done selecting cards?");
		boolean response = (question.showAndWait().get() == ButtonType.OK);
		question.close();
		return response;
	}
	
	public void playerSelectionLabels(int numPlayers, int[] amount, int height, boolean AI) {
		Label label;
		if (AI) {
			if(numPlayers == 1) {
				label = new Label(numPlayers + " AI  Player");
			}else {
				label = new Label(numPlayers + " AI  Players");
			}
		}else {
		    label = new Label(numPlayers + " Players");
		}
		label.relocate(width/2 - 20, height);
		label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(AI) {
			    amount[1] = numPlayers;
			}else {
				amount[0] = numPlayers;
				if (amount[0] == 4) {
					amount[1] = 0;
				}else {
					selectAIScreen(amount);
				}
			}
		});
		canvas.getChildren().addAll(label);
	}
	
	public void startScreen(int[] amount, String[] IP) {
		canvas = new Pane();
		
		Label label;

		label = new Label("Play online (host a game)");
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		label.relocate(width/2 - (fontLoader.computeStringWidth(label.getText(), globalFont))/2, height/3);
		label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			amount[2] = 1;
		    selectServerScreen(amount);
			//amount[3] = 1983;
			//selectPlayerScreen(amount);
			//IP[0] = "localhost";
		});
		canvas.getChildren().addAll(label);

		label = new Label("Play online (join a game)");
		label.relocate(width/2 - (fontLoader.computeStringWidth(label.getText(), globalFont))/2, 2 * height/3);
		label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			amount[2] = 2;
		    selectClientScreen(amount, IP);
			//amount[3] = 1983;
			//IP[0] = "localhost";
			//amount[0] = 2;
			//amount[1] = 0;
		});
		canvas.getChildren().addAll(label);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath2);
		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
	}

	public void selectServerScreen(int[] amount) {
		canvas = new Pane();
		Label label;
		TextField portNumber = new TextField();
		portNumber.relocate(width/2 + 30, height/2 + 8);
		
		label = new Label("Enter in a port number:");
		label.relocate(width/2 - 160, height/2);
		canvas.getChildren().addAll(label, portNumber);
		
		label = new Label("submit");
		label.relocate(width/2 - 50, height/2 + 30);
		label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			amount[3] = Integer.parseInt(portNumber.getText());
			System.out.println("inside of UI function selecter server" + portNumber.getText());
			selectPlayerScreen(amount);
		});
		canvas.getChildren().addAll(label);
		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath2);
		dummyStage.setScene(scene);
	}
	
	public void selectClientScreen(int[] amount, String[] IPAddress) {
		canvas = new Pane();
		Label label;
		TextField portNumber = new TextField();
		portNumber.relocate(width/2 + 30, height/2 + 8);
		TextField IP = new TextField();
		IP.relocate(width/2 + 30, height/2 - 22);
		canvas.getChildren().addAll(portNumber, IP);
		
		label = new Label("Enter in an IP address:");
		label.relocate(width/2 - 160, height/2 - 30);
		canvas.getChildren().addAll(label);
		
		label = new Label("Enter in a port number:");
		label.relocate(width/2 - 160, height/2);
		canvas.getChildren().addAll(label);
		
		label = new Label("submit");
		label.relocate(width/2 - 50, height/2 + 30);
		label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			amount[3] = Integer.parseInt(portNumber.getText());
			System.out.println("inside of UI function selecter client" + portNumber.getText());
			System.out.println("inside of UI function selecter client" + IP.getText());
			IPAddress[0] = IP.getText();
			amount[0] = 2;
			amount[1] = 0;
		});
		canvas.getChildren().addAll(label);
		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath2);
		dummyStage.setScene(scene);
	}

	public void selectPlayerScreen(int[] amount) {
		canvas = new Pane();
		
		for (int i = 2; i <= 4; i++) {
			playerSelectionLabels(i, amount, (i-2) * 100 + 10, false);
		}

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath2);
		dummyStage.setScene(scene);
	}
	
	public void selectAIScreen(int[] amount) {
		canvas = new Pane();
		
		for (int i = 0; i <= 4 - amount[0]; i++) {
			playerSelectionLabels(i, amount, i * 100 + 10, true);
		}

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath2);
		dummyStage.setScene(scene);
	}

	public boolean getSponsor() {
		return questInPlay;
	}

	public boolean setSponsorToFalse() {
		questInPlay = false;
		return questInPlay;
	}

	// TO DO: create separate function for each type of card, to facilitate
	// functionality
	public void cardSelect(Human player, ImageView imgView) {
		
		
		
		int selectedIndex = Selected.size();
		int originalX = (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinX();
		int originalY = (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinY();
		Selected.add(false);
		imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			gameInfo.setText(imgView.getId());
			if (questInPlay) {

				if (Selected.get(selectedIndex)) {
					
					
					
					if (currStagePicked > player.selectedQuestStages.length) 
					{

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Stage not available");
						reminder.setHeaderText("You can only select a stage the Quest allows ");
						reminder.showAndWait().get();
						reminder.close();

					}
					else if (player.selectedQuestStages[currStagePicked - 1].isFound(imgView.getId()) == false) 
					{
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("You have wrong stage selected for the card you are trying to deselect");
						reminder.setHeaderText("Please select right stage for card you want unselect");
						reminder.showAndWait().get();
						reminder.close();
						
					}
					
					else {
					
					imgView.relocate(originalX, originalY);
					Selected.set(selectedIndex, false);		
							
					(player.selectedQuestStages[currStagePicked - 1]).remove(AD.find(imgView.getId()));
					QuestSelected[currStagePicked - 1]--;
					System.out.println("unselected card for quest" + imgView.getId());
					}
				
		
				} else {

					if (currStagePicked > player.selectedQuestStages.length) {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Stage not available");
						reminder.setHeaderText("You can only select a stage the Quest allows ");
						reminder.showAndWait().get();
						reminder.close();


					}

					
					else if (AD.find(imgView.getId()).getType() == "FOE" && player.selectedQuestStages[currStagePicked-1].hasType("FOE")) {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("More then one Foe selected");
						reminder.setHeaderText("You can't pick more then one foe per stage!");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick more then one foe");

					} else if (AD.find(imgView.getId()).getType() == "ALLY"
							|| AD.find(imgView.getId()).getType() == "AMOUR") {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Ally or Amour selected");
						reminder.setHeaderText("You can't pick an ally or amour");
						reminder.showAndWait().get();
						reminder.close();

					}

					else if ((player.selectedQuestStages[currStagePicked - 1]).isFound(imgView.getId())) {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Weapon Already Selected");
						reminder.setHeaderText("You can't pick more then one weapon!");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick more then one weapon");

					} else {

						imgView.relocate(
								cardWidth + (width - cardWidth) / numStages * (currStagePicked - 1)
										+ 10 * QuestSelected[currStagePicked - 1],
								45 + 10 * QuestSelected[currStagePicked - 1]);
						Selected.set(selectedIndex, true);
						QuestSelected[currStagePicked - 1]++;
						(player.selectedQuestStages[currStagePicked - 1]).add(AD.find(imgView.getId()));

						System.out.println("selected card for quest " + imgView.getId());

					}
				}
			}

			if (fightingFoe) {
				if (Selected.get(selectedIndex)) {
					imgView.relocate(originalX, originalY);
					Selected.set(selectedIndex, false);
					SelectForFoes.remove(imgView);
					renderCardsPickedForFoe();
					player.cardsCommittedToFight.remove(AD.find(imgView.getId()));
					System.out.println("unselected card for quest " + imgView.getId());

				}else {
					
					
					if (AD.find(imgView.getId()).getType() == "AMOUR") {


						if (player.amourInPlay != null || player.cardsCommittedToFight.hasType("AMOUR") ){

							Alert reminder = new Alert(Alert.AlertType.WARNING);
							reminder.setTitle("amour is already in play");
							reminder.setHeaderText("You can't have more then one amour in play");
							reminder.showAndWait().get();
							reminder.close();

							System.out.println("Trying to pick more then one amour!");

						} else {

							

							Selected.set(selectedIndex, true);
							SelectForFoes.add(imgView);
							renderCardsPickedForFoe();

							player.cardsCommittedToFight.add(AD.find(imgView.getId()));

						}
					} else if (AD.find(imgView.getId()).getType() == "FOE") {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("A Foe selected");
						reminder.setHeaderText("You can't pick a foe");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick a foe");

					}

					else if (AD.find(imgView.getId()).getType() == "TEST") {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("A test selected");
						reminder.setHeaderText("You can't pick a test");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick a test");

					} else if (player.cardsCommittedToFight.isFound(imgView.getId())) {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Duplicate Selected");
						reminder.setHeaderText("You can't pick duplicates");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick duplicates");

					} else {

						Selected.set(selectedIndex, true);
						SelectForFoes.add(imgView);
						renderCardsPickedForFoe();

						player.cardsCommittedToFight.add(AD.find(imgView.getId()));

						System.out.println("selected card for quest " + imgView.getId());
					}
				}
			}if (discardingCards) {
				
				
				
				
				
				if (Selected.get(selectedIndex)) {
					imgView.relocate(originalX, originalY);
					Selected.set(selectedIndex, false);
					SelectForFoes.remove(imgView);
					renderCardsPickedForFoe();
					player.addCard(AD.find(imgView.getId()));
					
					player.cardsToDiscard.remove(AD.find(imgView.getId())); 
					System.out.println("unselected card for discarding " + imgView.getId());
				
					
					amountDiscarded--;

				}else {
					
					
					if (amountDiscarded >= amountToDiscard) {
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Maximum amount to discard reached");
						reminder.setHeaderText("You cannot discard more cards");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't discard more cards!");
						
					}
					else {
					Selected.set(selectedIndex, true);
					SelectForFoes.add(imgView);
					renderCardsPickedForFoe();

					player.deck.remove(AD.find(imgView.getId()));
					player.cardsToDiscard.add(AD.find(imgView.getId())); 
					System.out.println("selected card for discarding" + imgView.getId());
					amountDiscarded++;
					}
				}
				
				
				
				
				
				
				
			}
			
			if (choosingAlliesForBid) {
				
				
				
				
				
				
				if (Selected.get(selectedIndex)) {
					imgView.relocate(originalX, originalY);
					Selected.set(selectedIndex, false);
					SelectForFoes.remove(imgView);
					renderCardsPickedForFoe();
					player.addCard(AD.find(imgView.getId()));
					
					if(AD.find(imgView.getId()).getType() == "ALLY") {
						player.alliesInPlay.remove((imgView.getId()));
					}
					else {
						
						player.amourInPlay = null;
						
					}
					
					
					
					
					System.out.println("unselected card for discarding" + imgView.getId());
					

				}else {
					if (AD.find(imgView.getId()).getType() == "AMOUR"  && player.amourInPlay != null) {
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Amour already in play");
						reminder.setHeaderText("Can't pick more then one Amour");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick more then one Amour!");
						
					}
					
					else if (AD.find(imgView.getId()).getType() != "ALLY"  && AD.find(imgView.getId()).getType() != "AMOUR") {
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Not an Ally or an Amour");
						reminder.setHeaderText("Must select an Ally or an Amour");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Must select only Allies or an Amour!");
						
					}
					else {
						
						
						
					Selected.set(selectedIndex, true);
					SelectForFoes.add(imgView);
					renderCardsPickedForFoe();
					
					if(AD.find(imgView.getId()).getType() == "ALLY") {
						player.alliesInPlay.add((allyCard)AD.find(imgView.getId()));
					}
					else {
						
						player.amourInPlay = new amourCard();
						
					}
					
					player.removeCard(AD.find(imgView.getId()));

					System.out.println("selected card for helping to bid " + imgView.getId());
				
					}
				}
				
				
				
				
				
			}
			if (playingTourney) {
				
				if (Selected.get(selectedIndex)) {
					imgView.relocate(originalX, originalY);
					Selected.set(selectedIndex, false);
					SelectForFoes.remove(imgView);
					renderCardsPickedForFoe();
			
					player.cardsCommittedToFight.remove(AD.find(imgView.getId()));
					System.out.println("unselected card for tournament" + imgView.getId());
					

				}else {
					
					
					if (AD.find(imgView.getId()).getType() == "TEST") {
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Test picked");
						reminder.setHeaderText("Can't pick a test");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick a test!");
						
						
					}else if(AD.find(imgView.getId()).getType() == "AMOUR"  && (player.cardsCommittedToFight.hasType("AMOUR") || player.amourInPlay !=null)) {
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Amour already in play");
						reminder.setHeaderText("Can't pick more then one Amour");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick more then one Amour!");
						
					}else if(AD.find(imgView.getId()).getType() == "FOE") {
						
						
						
						
						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Foe picked");
						reminder.setHeaderText("Can't pick a foe");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick a foe!");
						
						
						
						
					}
					else if (player.cardsCommittedToFight.isFound(imgView.getId())) {

						Alert reminder = new Alert(Alert.AlertType.WARNING);
						reminder.setTitle("Duplicate Selected");
						reminder.setHeaderText("You can't pick duplicates");
						reminder.showAndWait().get();
						reminder.close();

						System.out.println("Can't pick duplicates");

					}else if (AD.find(imgView.getId()).getName() == "Queen Iseult") {

					
						
						allyCard ally = (allyCard)AD.find(imgView.getId());
	
							
							
							
							if (player.cardsCommittedToFight.isFound("Sir Tristan") || player.alliesInPlay.isFound("Sir Tristan")) {
								
								Selected.set(selectedIndex, true);
								SelectForFoes.add(imgView);
								renderCardsPickedForFoe();
								
								player.cardsCommittedToFight.add(ally);
								
								
							} 
							else {
								

								Alert reminder = new Alert(Alert.AlertType.WARNING);
								reminder.setTitle("Sir Tristan must be an Ally or in play to select Queen Iseult");
							
								reminder.setHeaderText("You can select allies but they must have points to use (exception Queen Iseult)");
								reminder.showAndWait().get();
								reminder.close();

								System.out.println("Can only select allies with points");
								
								
							}
							
							
							
					} else if ( AD.find(imgView.getId()).getType()  == "ALLY") {
							
							
			allyCard ally = (allyCard)AD.find(imgView.getId());
			
			
				if (ally.getData1Type() == "POINTS") {
				
					Selected.set(selectedIndex, true);
					SelectForFoes.add(imgView);
					renderCardsPickedForFoe();
				
					player.cardsCommittedToFight.add(ally);
					
				
			} 
				else  {
				

				Alert reminder = new Alert(Alert.AlertType.WARNING);
				reminder.setTitle("Allies must have points to play");
			
				reminder.setHeaderText("You can select allies but they must have points to use (exception Queen Iseult)");
				reminder.showAndWait().get();
				reminder.close();

				System.out.println("Can only select allies with points");
				
				
				}
			
			
			
			
			
							
							
		} 	else {
							
			
			
							
							Selected.set(selectedIndex, true);
							SelectForFoes.add(imgView);
							renderCardsPickedForFoe();
							
							player.cardsCommittedToFight.add(AD.find(imgView.getId()));
							
							
							
							
						}
						
						
				
						
						
					}
					
					
					
					
					
					
					
		
				
				
			}
			
			
			

		});

	}


	public void donePlayerTurn(Human player, ImageView imgView) {

		imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			nextPlayersTurn(player);
		});
	}

	public void renderCardsPickedForFoe() {
		for (int i = 0; i < SelectForFoes.size(); i++) {
			SelectForFoes.get(i).relocate(((width - 50 - cardWidth) / SelectForFoes.size() * i)
					+ (width - 50 - cardWidth) / SelectForFoes.size() / 2 - cardWidth / 2 + 25 + cardWidth, 45);
		}
	}

	public void endOfStage(ObjectArray objects)
	{
		// String result: battle points of foe, battle points of yourself, and whether
		// or not you won

		
		
		
		
		canvas = new Pane();

		

		
		
		PlayerDeck stage = (PlayerDeck)objects.get(2); 
		
		
		PlayerDeck cardsUsedAgainstFoes = (PlayerDeck)objects.get(4);
		
		String result = (String)objects.get(5);
		
		
		gameInfo = new Label(result);
		gameInfo.relocate(width / 2 - 50, 20);
		canvas.getChildren().addAll(gameInfo);
		
		

		gameInfo = new Label("display next player's results");
		gameInfo.relocate(width / 2 - 50, 510);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			((Human)objects.get(1)).hasFinishedTurn = true;

		});

		List<String> foeWithWeapons = utility.playerDeckCards(stage);

		ImageView imgView;

		for (int i = 0; i < foeWithWeapons.size(); i++) {
			System.out.println("stage" + (i + 1));
			imgView = new ImageView();
			imgView.setImage(ImageMap.get(foeWithWeapons.get(i)));
			imgView.relocate(((width - 50) / foeWithWeapons.size() * i) + (width - 50) / foeWithWeapons.size() / 2
					- cardWidth / 2 + 25, 185);
			imgView.setFitWidth(cardWidth);
			imgView.setFitHeight(cardHeight);
			imgView.setPreserveRatio(true);
			imgView.setId(foeWithWeapons.get(i));

			canvas.getChildren().addAll(imgView);
		}

		
		List<String> cardsPlayed = (utility.playerDeckCards(cardsUsedAgainstFoes));
		
		String[] cardsPlayedList = new String[cardsPlayed.size()];

		cardsPlayed.toArray(cardsPlayedList);

		
		
		displayCardsAcrossScreen(cardsPlayedList, 385);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();

	}
	
	
	public void pickCardsForTournament(ObjectArray objects) {
		
		String tourney = (String)(objects.get(2));
		int round = (int)(objects.get(4));
		
		canvas = new Pane();
		
		gameInfo = new Label("Round: " + round + " "+ ((Human)(objects.get(1))).getName() + " is picking cards for " + tourney);
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		gameInfo.relocate(width/2 - (fontLoader.computeStringWidth(gameInfo.getText(), globalFont))/2, 10);
		canvas.getChildren().addAll(gameInfo);
		
		
		List<String> cardsInHandList = (utility.playerDeckCards(((Human)(objects.get(1))).deck));

		// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
		// Lancelot"};
		// List<String> cardsInPlayList = (utility.playerDeckCards(player.cardsInPlay));

		AllyDeck allies = ((Human)(objects.get(1))).alliesInPlay;

		amourCard amourCard = ((Human)(objects.get(1))).amourInPlay;

		PlayerDeck alliesAndAmour = new PlayerDeck();

		for (int i = 0; i < allies.getSize(); i++) {

			alliesAndAmour.add(allies.get(i));

		}

		if (amourCard != null) {

			alliesAndAmour.add(amourCard);

		}

		List<String> cardsInPlayList = utility.playerDeckCards(alliesAndAmour);

		String[] cardsInHand;
		String[] cardsInPlay;

		if (((Human)(objects.get(1))).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}

		if (alliesAndAmour.getSize() != 0) {
			cardsInPlay = new String[alliesAndAmour.getSize()];
			cardsInPlayList.toArray(cardsInPlay);

		} else {

			cardsInPlay = null;

		}
		
		gameInfo = new Label("Game state / card info");
		gameInfo.relocate(-500, 10);
		canvas.getChildren().addAll(gameInfo);

		// renderPlayerInfo(players);
		
		ImageView imgView;
		imgView = new ImageView();
		imgView.setImage(ImageMap.get(tourney));
		imgView.relocate(0, 45);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(tourney);

		// render weapons face down

		donePlayerTurn(((Human)(objects.get(1))), imgView);

		canvas.getChildren().addAll(imgView);
		
		
		playingTourney = true;

		renderCurrentPlayersCards(((Human)(objects.get(1))), cardsInPlay, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
	}
	
	
	

	public void pickCardsToFightFoe(ObjectArray objects) {

		// just need to render weapons face down
		
		
		
		PlayerDeck enemies = (PlayerDeck)objects.get(2);
				
		int stage =  (int)objects.get(4);
		
		canvas = new Pane();
		
		gameInfo = new Label(((Human)objects.get(1)).getName() + " is Fighting a Foe at " + stage);
		gameInfo.relocate(width / 2 - 50, 20);
		canvas.getChildren().addAll(gameInfo);


		List<String> cardsInHandList = (utility.playerDeckCards(((Human)objects.get(1)).deck));

		// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
		// Lancelot"};
		// List<String> cardsInPlayList = (utility.playerDeckCards(player.cardsInPlay));

		AllyDeck allies = ((Human)objects.get(1)).alliesInPlay;

		amourCard amourCard = ((Human)objects.get(1)).amourInPlay;

		PlayerDeck alliesAndAmour = new PlayerDeck();

		for (int i = 0; i < allies.getSize(); i++) {

			alliesAndAmour.add(allies.get(i));

		}

		if (amourCard != null) {

			alliesAndAmour.add(amourCard);

		}

		List<String> cardsInPlayList = utility.playerDeckCards(alliesAndAmour);

		String[] cardsInHand;
		String[] cardsInPlay;

		if (((Human)objects.get(1)).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}

		if (alliesAndAmour.getSize() != 0) {
			cardsInPlay = new String[alliesAndAmour.getSize()];
			cardsInPlayList.toArray(cardsInPlay);

		} else {

			cardsInPlay = null;

		}

		FoeDeck foes = enemies.getFoes();

		String foeName = foes.get(0).getName();

		WeaponDeck weapons = enemies.getWeapons();

		String[] weaponNames = new String[weapons.getSize()];
		for (int i = 0; i < weapons.getSize(); i++) {

			weaponNames[i] = weapons.get(i).getName();

		}

		String[] weaponsAndFoe = new String[weapons.getSize() + 1];

		weaponsAndFoe[0] = foeName;

		for (int i = 1; i <= weaponNames.length; i++) {

			weaponsAndFoe[i] = weaponNames[i - 1];

		}
		ImageView imgView;

		for (int i = 0; i < weaponsAndFoe.length; i++) {

			imgView = new ImageView();
			imgView.setImage(ImageMap.get("Face Down Adventure Card"));
			imgView.relocate(cardWidth + (width - cardWidth) / numStages * (10 * i), 45 + (10 * i));
			imgView.setFitWidth(cardWidth);
			imgView.setFitHeight(cardHeight);
			imgView.setPreserveRatio(true);
			imgView.setId(weaponsAndFoe[i]);

			canvas.getChildren().addAll(imgView);

		}

		fightingFoe = true;

		gameInfo = new Label("Game state / card info");
		gameInfo.relocate(width / 2 - cardWidth / 2, 10);
		canvas.getChildren().addAll(gameInfo);

		// renderPlayerInfo(players);

		imgView = new ImageView();
		imgView.setImage(ImageMap.get("Face Down Adventure Card"));
		imgView.relocate(0, 45);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(foeName);

		// render weapons face down

		donePlayerTurn(((Human)objects.get(1)), imgView);

		canvas.getChildren().addAll(imgView);

		renderCurrentPlayersCards(((Human)objects.get(1)), cardsInPlay, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
	}

	public void nextPlayersTurn(Human player) {

		canvas = new Pane();

		currStagePicked = 1;
		
		
		displayPlayersInfo();

		gameInfo = new Label("Click here for next turn!");
		gameInfo.relocate(width / 2, height-70);
		canvas.getChildren().addAll(gameInfo);
		choosingAlliesForBid = false;
		fightingFoe = false;
		questInPlay = false;
		discardingCards = false;
		//amourAlreadySelectedByFighter = false;
		amountDiscarded =0;
		playingTourney = false;
		
		

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			player.hasFinishedTurn = true;
			
			// dummyStage.close();
		});

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);
		dummyStage.setScene(scene);
	}

	public void doISponsor(ObjectArray objects) {


		questCard quest = (questCard)objects.get(2);
		
		
		List<String> cardsInHandList = (utility.playerDeckCards(((Human)objects.get(1)).deck));

		// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
		// Lancelot"};
		// List<String> cardsInPlayList = (utility.playerDeckCards(player.cardsInPlay));

		AllyDeck allies = ((Human)objects.get(1)).alliesInPlay;

		amourCard amourCard = ((Human)objects.get(1)).amourInPlay;

		PlayerDeck alliesAndAmour = new PlayerDeck();

		for (int i = 0; i < allies.getSize(); i++) {

			alliesAndAmour.add(allies.get(i));

		}

		if (amourCard != null) {

			alliesAndAmour.add(amourCard);

		}

		List<String> cardsInPlayList = utility.playerDeckCards(alliesAndAmour);

		String[] cardsInHand;
		String[] cardsInPlay;

		if (((Human)objects.get(1)).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}

		if (alliesAndAmour.getSize() != 0) {
			cardsInPlay = new String[alliesAndAmour.getSize()];
			cardsInPlayList.toArray(cardsInPlay);

		} else {

			cardsInPlay = null;

		}

		String questName = quest.getName();

		int stages = quest.getNumStages();

		canvas = new Pane();
		
		System.out.println(stages);

		numStages = stages;

		QuestSelected = new int[stages];
		
		((Human)objects.get(1)).selectedQuestStages = new PlayerDeck[stages];

		for (int i = 0; i < numStages; i++) {
			QuestSelected[i] = 0;
			
			((Human)objects.get(1)).selectedQuestStages[i] = new PlayerDeck();

		}
	    
		gameInfo = new Label(((Human)objects.get(1)).getName());
		gameInfo.relocate(width / 2 - cardWidth / 2, 10);
		canvas.getChildren().addAll(gameInfo);

		// renderPlayerInfo(players);

		ImageView imgView = new ImageView();
		imgView.setImage(ImageMap.get(questName));
		imgView.relocate(width / 2 - cardWidth / 2, 45);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(questName);

		donePlayerTurn(((Human)objects.get(1)), imgView);

		gameInfo = new Label("Sponsor the Quest");
		gameInfo.relocate(width / 2 + cardWidth / 2, 50);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			questInPlay = true;
			App.nextTurn = true;
			((Human)objects.get(1)).isSponsoring = true;
			System.out.println("selected the sponsor button inside the UI");
			imgView.relocate(0, 45);
		});

		gameInfo = new Label("Don't sponsor the Quest");
		gameInfo.relocate(width / 2 + cardWidth / 2, 80);
		canvas.getChildren().addAll(gameInfo);

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			((Human)objects.get(1)).isSponsoring = false;

			nextPlayersTurn(((Human)objects.get(1)));
		});

		canvas.getChildren().addAll(imgView);

		renderCurrentPlayersCards((Human)(objects.get(1)), cardsInPlay, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);
		
		scene.addEventFilter(KeyEvent.ANY, keyEvent -> {
			switch (keyEvent.getCode()) {
			case DIGIT1:
				currStagePicked = 1;
				break;
			case DIGIT2:
				currStagePicked = 2;
				break;
			case DIGIT3:
				currStagePicked = 3;
				break;
			case DIGIT4:
				currStagePicked = 4;
				break;
			case DIGIT5:
				currStagePicked = 5;
				break;
			case DIGIT6:
				currStagePicked = 6;
				break;
			case DIGIT7:
				currStagePicked = 7;
				break;
			case DIGIT8:
				currStagePicked = 8;
				break;
			case DIGIT9:
				currStagePicked = 9;
				break;
			default:
				break;
			}
		});
		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();

	}

	public void renderPlayerInfo(String[] players) {
		String[] firstHalfOfPlayers = Arrays.copyOfRange(players, 0, (int)(Math.floor(players.length / 2)));
		String[] secondHalfOfPlayers = Arrays.copyOfRange(players, (int)(Math.floor(players.length / 2)), players.length);
		System.out.println(firstHalfOfPlayers.length);
		System.out.println(secondHalfOfPlayers.length);

		for (int i = 0; i < firstHalfOfPlayers.length; i++) {
			Label label = new Label(firstHalfOfPlayers[i]);
			label.relocate(width / 2 / (firstHalfOfPlayers.length + 1) * (i + 1), 10);
			canvas.getChildren().addAll(label);
		}

		for (int i = 0; i < secondHalfOfPlayers.length; i++) {
			Label label = new Label(secondHalfOfPlayers[i]);
			label.relocate(width / 2 + width / 2 / (secondHalfOfPlayers.length + 1) * (i + 1), 10);
			canvas.getChildren().addAll(label);
		}
	}
	
	
	public void displayTournament(ObjectArray objects) {
		
		
		
		List<Player> players = (ArrayList<Player>)(objects.get(2)); 
		int[] playersPoints = (int[])(objects.get(4));
		// TODO Auto-generated method stub
		
		canvas = new Pane();
		
		gameInfo = new Label("Cards used in Round");
		gameInfo.relocate(width / 2 + 200, 20);
		canvas.getChildren().addAll(gameInfo);
		
		
		
		for (int i=0; i< players.size(); i++) {
			
			PlayerDeck cardsUsed = players.get(i).allCardsUsedAgainstAFoe();
			
			
			List<String> cardsToDisplay = (utility.playerDeckCards(cardsUsed));

			// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
			// Lancelot"};
			// List<String> cardsInPlayList = (utility.playerDeckCards(player.cardsInPlay));

			String[] cardsToShow;
			
			if (cardsUsed.getSize()>0) {
			
				cardsToShow = new String[cardsUsed.getSize()];
				
				cardsToDisplay.toArray(cardsToShow);
				
			}else {
				
				
				cardsToShow = null;
			}
			
			displayTournamentCards(players.get(i), playersPoints[i], cardsToShow, i*200+50);
			
			
			
		}
		
		
		gameInfo = new Label("Done seeing result");
		gameInfo.relocate(width / 2 - 50, 20);
		canvas.getChildren().addAll(gameInfo);
		
		
	

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			nextPlayersTurn(((Human)(objects.get(1))));

		});

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);
		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
		
	}
	
	
	
	public void displayStory(ObjectArray objects) {
		
		storyCard story = (storyCard)(objects.get(2));
		

		
		canvas = new Pane();
		
		gameInfo = new Label(story.getType() + ": " + story.getName());
		gameInfo.relocate(width / 2 - 50, 20);
		canvas.getChildren().addAll(gameInfo);
		
		
		List<String> cardsInHandList = (utility.playerDeckCards(((Human)(objects.get(1))).deck));

		// String[] cardsInPlay = {"King Arthur", "Sir Percival", "Sir Galahad", "Sir
		// Lancelot"};
		// List<String> cardsInPlayList = (utility.playerDeckCards(player.cardsInPlay));

		AllyDeck allies = ((Human)(objects.get(1))).alliesInPlay;

		amourCard amourCard = ((Human)(objects.get(1))).amourInPlay;

		PlayerDeck alliesAndAmour = new PlayerDeck();

		for (int i = 0; i < allies.getSize(); i++) {

			alliesAndAmour.add(allies.get(i));

		}

		if (amourCard != null) {

			alliesAndAmour.add(amourCard);

		}

		List<String> cardsInPlayList = utility.playerDeckCards(alliesAndAmour);

		String[] cardsInHand;
		String[] cardsInPlay;

		if (((Human)(objects.get(1))).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}

		if (alliesAndAmour.getSize() != 0) {
			cardsInPlay = new String[alliesAndAmour.getSize()];
			cardsInPlayList.toArray(cardsInPlay);

		} else {

			cardsInPlay = null;

		}
		
	
	

		// renderPlayerInfo(players);
		
		ImageView imgView;
		imgView = new ImageView();
		imgView.setImage(ImageMap.get(story.getName()));
		imgView.relocate(width/2, 100);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(story.getName());

		// render weapons face down

		donePlayerTurn(((Human)(objects.get(1))), imgView);

		canvas.getChildren().addAll(imgView);
		
		
		//playingTourney = true;

		renderCurrentPlayersCards(((Human)(objects.get(1))), cardsInPlay, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
		
		
		
	}
	
	public void displayEvent(ObjectArray objects) {
		
		
		String eventName = (String)(objects.get(2));
		String information = (String)(objects.get(4));
		List<Player> players = (List<Player>)(objects.get(5));

		canvas = new Pane();

		gameInfo = new Label(information);
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		gameInfo.relocate(width/2 - (fontLoader.computeStringWidth(gameInfo.getText(), globalFont))/2, 100);
		canvas.getChildren().addAll(gameInfo);

	
		
		String[] playerNames = utility.playerNames(players);
		
		
		
		renderPlayerInfo(playerNames);
		
		
		ImageView imgView;
		imgView = new ImageView();
		imgView.setImage(ImageMap.get(eventName));
		imgView.relocate(width/2-cardWidth/2 , 200);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(eventName);

		canvas.getChildren().addAll(imgView);
		
		
	

		gameInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			nextPlayersTurn((Human)(objects.get(1)));

		});

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);
		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
		
	}
	public void renderCurrentPlayersCards(Human player, String[] cardsInPlay, String[] cardsInHand) {
		ImageView imgView = new ImageView();

		if (cardsInPlay != null)
			displayCardsAcrossScreen(cardsInPlay, 285);
		/*
		 * for(int i = 0; i < cardsInPlay.length; i++) { imgView = new ImageView();
		 * imgView.setImage(ImageMap.get(cardsInPlay[i])); imgView.relocate(((width -
		 * 50)/cardsInPlay.length * i) + (width - 50)/cardsInPlay.length/2 - cardWidth/2
		 * + 25, 285); imgView.setFitWidth(cardWidth); imgView.setFitHeight(cardHeight);
		 * imgView.setPreserveRatio(true); imgView.setId(cardsInPlay[i]);
		 * 
		 * canvas.getChildren().addAll(imgView); }
		 */

		if (cardsInHand != null) {
			for (int i = 0; i < cardsInHand.length; i++) {
				imgView = new ImageView();
				imgView.setImage(ImageMap.get(cardsInHand[i]));
				imgView.relocate(((width - 100) / cardsInHand.length * i) + (width - 100) / cardsInHand.length / 2
						- newCardWidth / 2 + 50, 485);
				imgView.setFitWidth(newCardWidth);
				imgView.setFitHeight(newCardHeight);
				//imgView.setPreserveRatio(true);
				imgView.setId(cardsInHand[i]);
				
				cardInfoDisplay(imgView);
				cardSelect(player, imgView);

				canvas.getChildren().addAll(imgView);
			}
		}
	}
	
	public void cardInfoDisplay(ImageView imgView) { //can't create a list out of labels...
		imgView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
			FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
			String list[] = cardInfoMap.get(imgView.getId()).split("[\\n]+");
			cardInfo1 = new Label("");
			cardInfo2 = new Label("");
			cardInfo3 = new Label("");
			cardInfo4 = new Label("");
			for (int i = 0; i < list.length; i ++) {
				if (i == 0) {
					cardInfo1 = new Label(list[i]);
					cardInfo1.relocate((int) imgView.localToScene(imgView.getBoundsInLocal()).getMinX() + (newCardWidth - fontLoader.computeStringWidth(cardInfo1.getText(), globalFont))/2, (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinY() + 120);
					canvas.getChildren().addAll(cardInfo1);
				}else if(i == 1) {
					cardInfo2 = new Label(list[i]);
					cardInfo2.relocate((int) imgView.localToScene(imgView.getBoundsInLocal()).getMinX() + (newCardWidth - fontLoader.computeStringWidth(cardInfo2.getText(), globalFont))/2, (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinY() + 140);
					canvas.getChildren().addAll(cardInfo2);
				}else if(i == 2) {
					cardInfo3 = new Label(list[i]);
					cardInfo3.relocate((int) imgView.localToScene(imgView.getBoundsInLocal()).getMinX() + (newCardWidth - fontLoader.computeStringWidth(cardInfo3.getText(), globalFont))/2, (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinY() + 160);
					canvas.getChildren().addAll(cardInfo3);
				}else if(i == 3) {
					cardInfo4 = new Label(list[i]);
					cardInfo4.relocate((int) imgView.localToScene(imgView.getBoundsInLocal()).getMinX() + (newCardWidth - fontLoader.computeStringWidth(cardInfo4.getText(), globalFont))/2, (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinY() + 180);
					canvas.getChildren().addAll(cardInfo4);
				}
				System.out.println(list[i]);
			}
			//cardInfo = new Label(cardInfoMap.get(imgView.getId()));
			//cardInfo = new Label(imgView.getId());
			//imgView.setStyle("-fx-border-width: 10px;\r\n" + 
			//		"-fx-border-style: solid;\r\n" + 
			//		"-fx-border-color: black;");
			//cardInfo.setPrefWidth(newCardWidth + 50);
			//cardInfo.setMinWidth(newCardWidth + 50);
		    //FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
			//cardInfo.relocate((int) imgView.localToScene(imgView.getBoundsInLocal()).getMinX() + (newCardWidth - fontLoader.computeStringWidth(cardInfo.getText(), globalFont))/2, (int) imgView.localToScene(imgView.getBoundsInLocal()).getMinY() + 130);
			//canvas.getChildren().addAll(cardInfo);
		});
		
		
		imgView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
			canvas.getChildren().remove(cardInfo1);
			canvas.getChildren().remove(cardInfo2);
			canvas.getChildren().remove(cardInfo3);
			canvas.getChildren().remove(cardInfo4);
		});
		
	}

	
	public void displayTournamentCards(Player participant, int points, String[] cards, int height) {
		

		gameInfo = new Label(participant.getName());
		gameInfo.relocate(10, height-15);
		canvas.getChildren().addAll(gameInfo);

		if (cards != null) {
		
		ImageView imgView = new ImageView();
		for (int i = 0; i < cards.length; i++) {
			imgView = new ImageView();
			imgView.setImage(ImageMap.get(cards[i]));
			imgView.relocate(((width - 100) / cards.length * i) + (width - 100) / cards.length / 2 - newCardWidth / 2 + 50,
					height);
			imgView.setFitWidth(newCardWidth);
			imgView.setFitHeight(newCardHeight);
			//imgView.setPreserveRatio(true);
			imgView.setId(cards[i]);
			cardInfoDisplay(imgView);

			canvas.getChildren().addAll(imgView);
		}
		
		}
	}
	
	
	
	public void displayCardsAcrossScreen(String[] cards, int height) {
		ImageView imgView = new ImageView();
		for (int i = 0; i < cards.length; i++) {
			imgView = new ImageView();
			imgView.setImage(ImageMap.get(cards[i]));
			imgView.relocate(((width - 100) / cards.length * i) + (width - 100) / cards.length / 2 - newCardWidth / 2 + 50,
					height);
			imgView.setFitWidth(newCardWidth);
			imgView.setFitHeight(newCardHeight);
			//imgView.setPreserveRatio(true);
			imgView.setId(cards[i]);
			cardInfoDisplay(imgView);
			
			canvas.getChildren().addAll(imgView);
		}
	}

	public void bid(ObjectArray objects) {
		
		
		String card = (String)(objects.get(2));
		int bidsFromCardsInPlay = (int)(objects.get(4));
		
		int highestBid = (int)(objects.get(6));
		
		canvas = new Pane();
		ImageView imgView;
		imgView = new ImageView();
		imgView.setImage(ImageMap.get(card));
		imgView.relocate(50, 100);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(card);
		
		canvas.getChildren().addAll(imgView);
		
		
		gameInfo = new Label(((Human)objects.get(1)).getName()+" Free bids: " + bidsFromCardsInPlay);

		
		gameInfo.relocate(width / 2 - 50, 20);
		canvas.getChildren().addAll(gameInfo);
		
		
		
		gameInfo = new Label("Highest Bid: " + highestBid);

		
		gameInfo.relocate(width / 2 - 50, 50);
		canvas.getChildren().addAll(gameInfo);
		
			
		gameInfo = new Label("Bid below get's added on top of free bids");

		
		gameInfo.relocate(width / 2 - 50, 80);
		canvas.getChildren().addAll(gameInfo);
		
			
		for (int i = 0; i <= ((Human)objects.get(1)).deck.getSize(); i++) {
				playerBidLabel(((Human)objects.get(1)), i, ((int[])(objects.get(5))), (i * 35) + 105);
			}

		
		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);
		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
			
			
		}

		
		
	public void playerBidLabel(Human player, int numCards, int[] bids, int height) {
		Label label;
		
		 label = new Label("Bid " + numCards);
		
		label.relocate(width/2 - 20, height);
		label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			
	
			
		player.bid = numCards;
		
		nextPlayersTurn(player);
		});
		canvas.getChildren().addAll(label);
	}


	
	

	public void playAllyCardsForBid(ObjectArray objects) {
	
		
		String test = (String)(objects.get(2));
		
		
		
		
		canvas = new Pane();
		
		
	
		

		
		
		List<String> cardsInHandList = (utility.playerDeckCards(((Human)(objects.get(1))).deck));
		String[] cardsInHand;
		String[] allies;
		String[] alliesAndAmour;
		int size= 0;
		if (((Human)(objects.get(1))).alliesInPlay.getSize()>0) {
			
			
			allies = (utility.allyCards(((Human)(objects.get(1))).alliesInPlay));
			
			size = allies.length;
		}
		else {
			
			allies = null;
		}
		
		
		if (((Human)(objects.get(1))).amourInPlay != null) {
			
			
			size++;
			
			
		}
		
		if (size!=0) {
			
			
			
			alliesAndAmour = new String[size];
			
			
		}
		
		else {
			
			
			alliesAndAmour = null;
		}
		
		
		
		if (allies !=null) {
			
			
			for (int i = 0; i<allies.length; i++) {
				
				
				alliesAndAmour[i] = allies[i];
			}
			
			
		}
		
		if (((Human)(objects.get(1))).amourInPlay!=null) {
			
			
			
			
			alliesAndAmour[size-1] = "AMOUR";
			
			
		}
		
		
		
		
		
		if (((Human)(objects.get(1))).deck.getSize() != 0) {

			cardsInHand = new String[cardsInHandList.size()];

			cardsInHandList.toArray(cardsInHand);
		} else {

			cardsInHand = null;
		}
	
		
		
		
	
		// renderPlayerInfo(players);
	
		ImageView imgView;
		imgView = new ImageView();
		imgView.setImage(ImageMap.get(test));
		imgView.relocate(50, 100);
		imgView.setFitWidth(cardWidth);
		imgView.setFitHeight(cardHeight);
		imgView.setPreserveRatio(true);
		imgView.setId(test);
		
		choosingAlliesForBid = true;
	

		donePlayerTurn(((Human)(objects.get(1))), imgView);

	
		canvas.getChildren().addAll(imgView);

		renderCurrentPlayersCards(((Human)(objects.get(1))), alliesAndAmour, cardsInHand);

		Scene scene = new Scene(canvas, width, height);
		scene.getStylesheets().add(styleSheetPath);

		dummyStage.setScene(scene);
		dummyStage.setTitle("Quests Of The Round Table");
		dummyStage.show();
		
		
		
		
		
	}

	public static void main(String[] args) {

		Application.launch(args);
	}



}
