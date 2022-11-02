// Name: Maria Khalil
// Student Number: 300242332

//package ocsf.server;
import java.io.*;
import java.util.Scanner;
import common.*;


public class ServerConsole implements ChatIF{
	final public static int DEFAULT_PORT = 5555;
	
	//Instance variables *************************************************
	  
	
	/**
	 * The instance of the server that created this Console.
	 */
	EchoServer server;
	
	/**
	 * Scanner to read from the console
	 */
	Scanner fromConsole; 
	
	
	//Constructor ****************************************************
	
	/**
	   * Constructs an instance of the ServerConsole UI.
	   *
	   * @param port The port to connect on.
	   */
	public ServerConsole(int port) {
		server = new EchoServer(port, this);
//		try 
//	    {
//	      server = new EchoServer(port, this);
//	      
//	      
//	    } 
//	    catch(IOException exception) 
//	    {
//	      System.out.println("Error: Can't setup connection!"
//	                + " Terminating server.");
//	      System.exit(1);
//	    }
//	    
//	    // Create scanner object to read from console
//	    fromConsole = new Scanner(System.in); 
	}
	
	
	
	//Instance methods ************************************************
	  
	  /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the server's message handler.
	   */
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        server.handleMessageFromServerUI(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }
	
	
	 /**
	   * Implements method that is used to display objects onto
	   * a UI.
	   * 
	   * @param message The message that is displayed.
	   */
	public void display(String message) {
		System.out.println("> " + message);
	}
	
	public static void main(String[] args) 
	  {
	    
	    int port = 0; 


	    try
	    {
	      port = Integer.parseInt(args[0]);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      
	      port = DEFAULT_PORT; //Default port value is used if the port is omitted from command line.
	    }
	    catch(NumberFormatException ne) {
	    	port = DEFAULT_PORT;
	    }
	    
	    ServerConsole chat= new ServerConsole(port);
	    
	    try {
	    	chat.server.listen();
	    }
	    catch(Exception e) {
	    	System.out.println("Error");
	    }
	    //ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
	    chat.accept();  //Wait for console data
	  }
	}
	//End of ConsoleChat class


