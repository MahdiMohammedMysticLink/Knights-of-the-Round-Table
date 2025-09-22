package com.COMP3004.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//code taken and adapted from https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
public class GameServer {
	
	Logger logger = Logger.getLogger("myLogger");
	
	public static ServerThread[] Servers;
	
    public static ServerThread getServerThread(int n) {
    	return Servers[n];
    }
	
	public GameServer(int portNumber, int numPlayers){

	Servers = new ServerThread[numPlayers];
	
    Socket s=null;
    ServerSocket ss2=null;
    System.out.println("Server Listening......");
    try{
        ss2 = new ServerSocket(portNumber); // can also use static final PORT_NUM , when defined
        logger.log (Level.INFO, "Server Listening port:" + portNumber);
    }
    catch(IOException e){
	    e.printStackTrace();
	    System.out.println("Server error");
	    logger.log(Level.WARNING, "Cannot connect port:" + portNumber);
    }
    
    int i = 0;
    while(i < numPlayers){
        try{
            s= ss2.accept();
            System.out.println("connection Established");
            logger.log (Level.INFO, "Connection Established port:" + portNumber);
            Servers[i]=new ServerThread(s, i);
            Servers[i].start();
            i++;
        }

    catch(Exception e){
        e.printStackTrace();
        System.out.println("Connection Error");
        logger.log(Level.WARNING, "Connection Error - port:" + portNumber);
    }
    }

}

}