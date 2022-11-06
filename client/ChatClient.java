// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Maria Khalil (student number: 300242332)
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }
  
  
  
  //Exercise 2a (the two methods below)

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
//	if(message.charAt(0) == '#') {
//		try {
//			handleCommand(message);
//		}
//		catch(IOException e){
//			System.out.println(e);
//		}
//	}
	  
    try
    {
      if(message.startsWith("#")) {
    	  handleCommand(message);
      }
      else {
    	  sendToServer(message); 
      }
      
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method handles commands that the user of the client types, in order to 
   * perform special functions.
   * 
   * @throws IOException
   */
  public void handleCommand(String message) throws IOException{ //Exercise 2a
	  
	  String[] splitCommand = message.split(" ", 2);
	  String command = splitCommand[0];
	  
	  if(command.equals("#quit")) {
		  quit();
	  }
	  else if(command.equals("#logoff")) {//we don't want to do system.exit(0), we want to close
		  closeConnection();
	  }
	  else if(command.equals("#sethost")) {
		  if(!isConnected()) {
			  setHost(splitCommand[1]);
			  //String host = splitCommand[1];
		  }
		  else {
			  clientUI.display("Please log off in order to set host.");
		  }
	  }
	  else if(command.equals("#setport")) {
		  if(!isConnected()) {
			  setPort(Integer.parseInt(splitCommand[1]));
			  //String port = splitCommand[1];
		  }
		  else {
			  clientUI.display("Please log off in order to set port.");
		  }
	  }
	  else if(command.equals("#login")) {
		  if(!isConnected()) {
			  try {
				  openConnection();
			  }
			  catch(IOException e) {
				  clientUI.display("Couldn't login.");
			  }
		  }
		  else {
			  clientUI.display("Unable to login when already logged in.");
			  //throw new IOException("Unable to login when already logged in.");
		  }
	  }
	  else if(command.equals("#gethost")) {
		  clientUI.display("The host is " + getHost());
	  }
	  else if(command.equals("#getport")) {
		  clientUI.display("The port is " + getPort());
	  }
	  else {
		  throw new IOException("Enter a command");
	  }
		  
	  
	
	  
//	  String[] splitCommand = message.split(" ", 2);
//	  String command = splitCommand[0];
	  
	  //if(command.).
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  /**
	 * Implements the hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception The exception raised.
	 */
  @Override
  protected void connectionException(Exception e) { //Exercise 1a
	  clientUI.display("The server is shut down.");
	  quit(); //or system.exit(0)
  }
  
  /**
	 * Implements the hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
  @Override
  protected void connectionClosed() { //Exercise 1a
	  clientUI.display(getHost() + " " + getPort() + " Connection is closed");
  }
}
//End of ChatClient class
