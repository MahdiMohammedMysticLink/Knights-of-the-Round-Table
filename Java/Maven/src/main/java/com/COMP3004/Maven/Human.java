package com.COMP3004.Maven;

import com.COMP3004.Maven.Rank.rankCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import com.COMP3004.Socket.GameServer;
import com.COMP3004.Socket.ServerThread;

import java.io.Serializable;

public class Human extends Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int serverThread;


	
	/**
	 * Constructor with Server Thread
	 * @param n
	 * @param r
	 * @param u
	 * @param AD
	 * @param server
	 */
	
	//changed some things to static in GameServer. Might have to do it this way, GameServer may not be serializable
	public ServerThread getServerThread() {
		return GameServer.getServerThread(serverThread);
	}
	
	
	
	public Human(String n, rankCard r, AdventureDeck AD, int server) {
		super(n, r, AD);
		serverThread = server;
		behaviour = new humanBehaviour(this);
		hasFinishedTurn = false;
	}
	
	/**
	 * Constructor (without server thread
	 * @param n
	 * @param r
	 * @param u
	 * @param AD
	 */
/*	public Human(String n, rankCard r, UI u, AdventureDeck AD) {
		super(n, r, AD);
		behaviour = new humanBehaviour(this);
		ui = u;
		hasFinishedTurn = false;
	}
*/
	
	
	
}
