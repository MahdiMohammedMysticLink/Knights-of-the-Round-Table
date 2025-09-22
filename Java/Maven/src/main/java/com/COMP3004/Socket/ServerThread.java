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

import com.COMP3004.Maven.Human;

//code taken and adapted from https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
public class ServerThread extends Thread{  

    String line=null;
   
    public ObjectArray dataToSend = null;
    public ObjectArray dataRecieved = null;
   
    InputStream is = null;
    ObjectInputStream ois = null;
    OutputStream os = null;
    ObjectOutputStream oos = null;
    
    
    
    int playerNumber;
    //BufferedReader  is = null;
    //PrintWriter os=null;
    Socket s=null;

    public ServerThread(Socket s, int playerNumber){
        this.s=s;
        this.playerNumber = playerNumber;
    }
    
    public void sendData(ObjectArray data) {
    	dataToSend = data;
    	dataRecieved = null;
    }
    
    

    
    
    
    public ObjectArray retrieveData() {
    	if (dataRecieved != null) {
    		
    		ObjectArray temp = dataRecieved;
    		dataRecieved = null ;
    		return temp;
    	}else {
    		return null;
    	}
    }

    public void run() {
    try{
    	
    	
		os = s.getOutputStream();
		oos = new ObjectOutputStream(os);
		//oos.flush();
        is = s.getInputStream();
        
        ois = new ObjectInputStream(is);
      
    }catch(IOException e){
        System.out.println("IO error in server thread");
    }

    try {
        while(true){
        	//if (messageToSend.equals("QUIT"))
        		//break;
        	try {
        		Thread.sleep(300);
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
            if (dataRecieved != null) { 
            	
            	
            	//do nothing
            }else if ( dataToSend != null) {
            		
           

            		oos.reset();
            		oos.writeObject(dataToSend);
          
            		//oos.flush();
            		//os.flush();
            		
            		dataToSend = null;
            		
            		dataRecieved = (ObjectArray) (ois.readObject());
                
            	
            		
            		
            	}
            
            
            
        }
    }
            
            catch (Exception e) {

        line=this.getName(); //reused String line for getting thread name
        System.out.println("IO Error/ Client "+line+" terminated abruptly");
    }

            	
         

    finally{    
    try{
        System.out.println("Connection Closing..");
        if (is!=null){
            is.close(); 
            System.out.println(" Socket Input Stream Closed");
        }

        if(os!=null){
            os.close();
            System.out.println("Socket Out Closed");
        }
        if (s!=null){
        s.close();
        System.out.println("Socket Closed");
        }

        }
    catch(IOException ie){
        System.out.println("Socket Close Error");
    }
    }//end finally
    }
}