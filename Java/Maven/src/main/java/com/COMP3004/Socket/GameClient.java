package com.COMP3004.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.COMP3004.Maven.Human;
import com.COMP3004.Maven.Model;
import com.COMP3004.Maven.Player;
import com.COMP3004.Maven.UI;

import javafx.application.Platform;

//code taken and adapted from https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
public class GameClient {

	// initialize logger
	Logger logger = Logger.getLogger("myLogger");
	public UI ui;
	
	
	
	
    public ObjectArray dataToSend = null;
    public ObjectArray dataRecieved = null;
    InputStream is = null;
    ObjectInputStream ois = null;
    OutputStream os = null;
    ObjectOutputStream oos = null;
    
	
    Socket s1=null;
 
    int portNumber;
    String IPAddress;
	
    public GameClient(int portNum, String address, UI ui){
    	portNumber = portNum;
    	IPAddress = address;
   
    	
		this.ui = ui;
 
    
    }
    
    public void sendData(ObjectArray data){ 
	    
    	
    	try {
    		oos.reset();
    		oos.writeObject(data);
    		//oos.flush();
    		
    	}
    	catch (Exception e) {
    		
    		System.out.println("Error occured:" + e.toString());
    		logger.log(Level.WARNING, "Cannot send data on port:" + portNumber);
    	}
	}
    
    public ObjectArray recieveData() {
    	ObjectArray response;
    	
    	
    
	    try{
	    	response = (ObjectArray)(ois.readObject());
	    	System.out.println("waiting for a message from the server");
	    	logger.log(Level.INFO, "Waiting for a message from the server on port:" + portNumber);
	        //System.out.println("I am a client, I got this message: "+response); 
	    	
	    
	    	
	    	dataRecieved = response;
	    	
	    	
	    
	    	
	    	String function = (String)response.get(0);
	    	
	    	Method method = UI.class.getMethod(function, ObjectArray.class);
	    	
	    	if (function == "passInPlayers") {
	    		
	    		ui.passInPlayers(response);
	    		
	    		return response;
	    	}
	    	
	    	else {
	    	
	    	ui.passInPlayers(response);
	    	
	    	
	    	Platform.runLater(new Runnable() {
				public void run() {

					try {
						method.invoke(ui, response);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	

				}
			});

			while (((Human)response.get(1)).hasFinishedTurn == false) {
				try {
					Thread.sleep(300);
					//System.out.println("weeee");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

	    	//ui.passInPlayers(response);
			
	    	System.out.println("finished the turn for that player!");
	    	
	    	}
	    	
	    	
	    	
	    
	    	return response;
	    }
	    catch(Exception e){
	        e.printStackTrace();
	        System.out.println("Socket read Error or server sent null to clear data");
	        logger.log(Level.WARNING, "Socket read Error on port:" + portNumber);
	    	return null;
	    }
    }
    
    public void closeConnection() throws IOException {
    	// close streams
    	ois.close(); 
    	oos.close(); 
    	is.close();
    	os.close();
    	// close socket
    	s1.close();
        System.out.println("Connection Closed");
        logger.log(Level.INFO, "Connection Closed:" + portNumber);
    }
    
    public void connect() throws IOException {

	    try {
	    	// open socket
	        s1=new Socket(IPAddress, portNumber); // You can use static final constant PORT_NUM
	       // br= new BufferedReader(new InputStreamReader(System.in));
	        // open streams
	        is= s1.getInputStream();
	        os= s1.getOutputStream();
	        
	        System.out.println("I am here!");
	        
	        oos = new ObjectOutputStream(os);
	        
	        
	        System.out.println("I am nowwwwww here!");
	        ois = new ObjectInputStream(is);
	        System.out.println("I am now here!");
	        
	        logger.log(Level.INFO, "Connection Established on port:" + portNumber);
	        
	    }
	    catch (Exception e){
	        e.printStackTrace();
	        System.err.print("IO Exception");
	        logger.log(Level.WARNING, "Connection Failed on port:" + portNumber);
	    }
	
	    System.out.println("Connected!");
	}
}
