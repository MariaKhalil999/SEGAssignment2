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
		
		    try 
		    {
		      server = new EchoServer(port);
		      
		      
		    } 
		    catch(Exception exception) 
		    {
		      System.out.println("Error: Can't setup connection! Terminating server.");
		      System.exit(1);
		    }
		    
		    // Create scanner object to read from console
		    fromConsole = new Scanner(System.in); 
		  
		
		//server = new EchoServer(port, this);
//		
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
	    	try {
	    		server.listen();
	    	}
	    	catch(Exception e) {
	    		server.setPort(DEFAULT_PORT);
	    	}
	    	
	        message = fromConsole.nextLine();
	        
	        System.out.println("Message from server: " + message);
	        server.sendToAllClients("Message from server: " + message);
	        
	        
	        if(message.startsWith("#")) {
	        	try {
	        		handleCommandFromServer(message);
	        	}
	        	catch(IOException e) {
	        		System.out.println(e);
	        	}
	        
	        }
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
		System.out.println(message);
		//System.out.println("> " + message);
	}
	
	private void handleCommandFromServer(String serverCommand) throws IOException{
		String[] splitCommand = serverCommand.split(" ", 2);
		String command = splitCommand[0];
		
		if(command.equals("#quit")) {
			server.close();
			display("Server quitting.");
			System.exit(0);
		}
		else if(command.equals("#stop")) {
			server.stopListening();
		}
		else if(command.equals("#close")) {
			server.close();
		}
		else if(command.equals("#setport")) {
			if(!server.isListening()) {
				server.setPort(Integer.parseInt(splitCommand[1]));
				display("Server port is now: " + splitCommand[1]);
				  //String host = splitCommand[1];
			  }
			  else {
				  display("Please disconnect in order to set host.");
			  }
		}
		else if(command.equals("#start")) {
			server.listen();
			display("Server started listening.");
		}
		else if(command.equals("#getport")) {
			display("The Port is: " + (Integer.toString(server.getPort())));
		}
		else {
			throw new IOException("Enter a command");
		}
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
	      System.out.println("Default port 5555.");
	    }
	    catch(NumberFormatException ne) {
	    	port = DEFAULT_PORT;
	    	System.out.println("Default port 5555.");
	    }
	    
	    ServerConsole server= new ServerConsole(port);
	    
//	    try {
//	    	chat.server.listen();
//	    }
//	    catch(Exception e) {
//	    	System.out.println("Error");
//	    }
	    //ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
	    server.accept();  //Wait for console data
	  }
	}
	//End of ConsoleChat class


