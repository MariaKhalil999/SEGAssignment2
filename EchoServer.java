// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import ocsf.server.*;
import common.ChatIF;
import java.io.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @author Maria Khalil (student number: 300242332)
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Exercise 2b
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the server.
   */
  //ChatIF serverUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }
  
  
  //Exercise 2b
  /**
   * Constructs an instance of the echo server
   * 
   * @param port The port number to connect on.
   * @param serverUI The interface type variable.
   */
//  public EchoServer(int port, ChatIF serverUI) {
//	  super(port);
//	  this.serverUI = serverUI;
//  }
//  
  

  
  //Instance methods ************************************************
  
  //Exercise 2b
  
//  public void handleMessageFromServerUI(String message) {
//	  
//  }
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	String msgString = msg.toString();
	
	if(msgString.startsWith("#login")) {
		String loginID = msgString.split(" ")[1];
		System.out.println("Message received: " + msg + " from " + client.getInfo("loginID")); //should be null after first message
		this.sendToAllClients(loginID + " has logged on.");
		System.out.println(loginID + " has logged on.");
		//this.sendToAllClients("Client " + client.getInfo("loginID") + " > " + msg);
		//this.sendToAllClients("Client " + (String)client.getInfo("loginID") + " > " + msg);
		client.setInfo("loginID", loginID);
		
		return;
	}
	  
    System.out.println("Message received: " + msg + " from " + client.getInfo("loginID"));
    
    //loginID is null right now, should be the actual ID in exercise 3
    //this.sendToAllClients("Message received: " + msg + " from " + client.getInfo("loginID"));
    this.sendToAllClients("Client " + client.getInfo("loginID") + " > " + msg); //original was this.sendToAllClients(msg)
  }
  
  
  //Exercise 1c (the two methods below)
  
  /**
   * Implements the hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A new client has connected to the server.");
  }
  
  /**
   * Implements the hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("Client is disconnected.");
  }
  
  /**
   * Implements the hook method called each time an exception is thrown in a
   * ConnectionToClient thread.
   * The method may be overridden by subclasses but should remains
   * synchronized.
   *
   * @param client the client that raised the exception.
   * @param Throwable the exception thrown.
   */
  @Override
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  System.out.println("Client is disconnected.");
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  /**
   * Hook method called when the server is clased.
   * The default implementation does nothing. This method may be
   * overriden by subclasses. When the server is closed while still
   * listening, serverStopped() will also be called.
   */
  protected void serverClosed() {
	  System.out.println("Server is closed.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
