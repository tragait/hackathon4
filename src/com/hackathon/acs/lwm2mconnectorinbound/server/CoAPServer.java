package com.hackathon.acs.lwm2mconnectorinbound.server;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.endpoint.ServerEndpoint;
import ch.ethz.inf.vs.californium.util.Log;









import com.hackathon.acs.lwm2m.common.InvalidClientIdentifierException;
import com.hackathon.acs.lwm2m.common.LWM2MClient;
import com.hackathon.acs.lwm2m.common.uri.InvalidUriPathException;
import com.hackathon.acs.lwm2mconnectorinbound.LWM2MReceiverImpl;
import com.hackathon.acs.lwm2mconnectorinbound.devicemanagement.DeviceManagementHandler;
import com.hackathon.acs.lwm2mconnectorinbound.reporting.InformationReportingHandler;

public class CoAPServer extends ServerEndpoint{

	private static LWM2MReceiver reciever;
	volatile static boolean stopServer = false;
	private static ServerEndpoint endpoint;

	public CoAPServer() throws SocketException {
	}

	public static void main(String[] args) {
		try {
	        Log.setLevel(Level.INFO);
	    	Log.init();
			endpoint = new CoAPServer();
			endpoint.start();
			System.out.println("Server Listening on port "+ endpoint.getPort() );
		} catch (SocketException e) {
			System.err.println("Failed to initialize the server:" +e.getMessage());
		}
		reciever = new LWM2MReceiverImpl();
		takeUserInput();
	}
	@Override
	public void handleRequest(Request request) {
		reciever.packetReceived(request);
	}
	
	private static void takeUserInput()
	{
		//  prompt the user to enter his command
		System.out.println("Enter your command here. For help enter \"-h\".");
		while(!stopServer)
		{

		    // get their input
		    Scanner scanner = new Scanner(System.in);

		    String input = scanner.nextLine();
		    try
		    {
		    	// test the String
		    	if (input.trim().equals(""))
		    	{
		    		continue;
		    	}
		    	else
		    	{
		    		if(input.trim().equals("-h"))
		    		{
		    			printHelp();
		    		}
		    		else if(input.trim().equals("-l"))
		    		{
		    			Map<String, LWM2MClient> clientList = LWM2MServer.getInstance().getClientList();
		    			for(String clientId: LWM2MServer.getInstance().getClientList().keySet())
		    			{
		    				System.out.println("Available clients are:");
		    				System.out.println(clientList.get(clientId).toString());
		    				
		    			}
		    		}
		    		else if(input.trim().contains("-r"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3)
		    			{
		    				System.err.println("Invalid input for read operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performReadOperation(arguments[1],Arrays.copyOfRange(arguments, 2, arguments.length));
		    			}
		    		}
		    		else if(input.trim().contains("-t"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3)
		    			{
		    				System.err.println("Invalid input for read operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performReadOperationForTLV(arguments[1],Arrays.copyOfRange(arguments, 2, arguments.length));
		    			}
		    		}
		    		else if(input.trim().contains("-j"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3)
		    			{
		    				System.err.println("Invalid input for read operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performReadOperationForJSON(arguments[1],Arrays.copyOfRange(arguments, 2, arguments.length));
		    			}
		    		}
		    		else if(input.trim().contains("-w"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length!= 4)
		    			{
		    				System.err.println("Invalid input for write operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performWriteOperation(arguments[1], arguments[2], arguments[3].getBytes());
		    			}

		    		}
		    		else if(input.trim().contains("-x"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3 || arguments.length>3)
		    			{
		    				System.err.println("Invalid input for observe operation");
		    			}
		    			else
		    			{
		    				InformationReportingHandler reportingHandler = new InformationReportingHandler();
		    				//reportingHandler.handleObservationRequestToClient(arguments[1], arguments[2]);
		    				reportingHandler.handleObservationRequestWithWriteAttrToClient(arguments[1], arguments[2]);
		    			}


		    		}
		    		else if(input.trim().contains("-f"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length!= 4)
		    			{
		    				System.err.println("Invalid input for write operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performWriteOperationForFirmawareUpdate(arguments[1], arguments[2], arguments[3]);
		    			}
		    		}
		    		else if(input.trim().contains("-e"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length!= 3)
		    			{
		    				System.err.println("Invalid input for execute operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performExecuteOperation(arguments[1], arguments[2]);
		    			}

		    		}
		    		else if(input.trim().contains("-d"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length!= 3)
		    			{
		    				System.err.println("Invalid input for delete operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performDeleteOperation(arguments[1], arguments[2]);
		    			}

		    		}
		    		else if(input.trim().contains("-o"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3 || arguments.length>3)
		    			{
		    				System.err.println("Invalid input for observe operation");
		    			}
		    			else
		    			{
		    				InformationReportingHandler reportingHandler = new InformationReportingHandler();
		    				reportingHandler.handleObservationRequestToClient(arguments[1], arguments[2]);
		    			//	reportingHandler.handleObservationRequestWithWriteAttrToClient(arguments[1], arguments[2]);
		    			}

		    		}
		    		else if(input.trim().contains("-c"))
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3 || arguments.length>3)
		    			{
		    				System.err.println("Invalid input for cancel observe operation");
		    			}
		    			else
		    			{
		    				InformationReportingHandler reportingHandler = new InformationReportingHandler();
		    				reportingHandler.cancelObservation(arguments[1], arguments[2]);
		    			}

		    		}else if(input.trim().contains("-cw"))   // cancel with write parameters
		    		{
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3 || arguments.length>3)
		    			{
		    				System.err.println("Invalid input for cancel observe operation");
		    			}
		    			else
		    			{
		    				InformationReportingHandler reportingHandler = new InformationReportingHandler();
		    				//reportingHandler.cancelObservationWithWriteAttributes(arguments[1], arguments[2]);
		    			}

		    		}else if(input.trim().contains("-b"))  // reboot
		    		{
		    			
		    			//reboot can be achieved by executing /3/4 object // need to test
		    			String[] arguments = input.split(" ");
		    			if(arguments.length!= 2)
		    			{
		    				System.err.println("Invalid input for execute operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performExecuteOperation(arguments[1], "/3/0/4");
		    			}

		    		}else if(input.trim().contains("-q"))  // query firmware version
		    		{
		    			// firmware version can be queried by reading /5/7 
		    			String[] arguments = input.split(" ");
		    			if(arguments.length!=2)
		    			{
		    				System.err.println("Invalid input for read operation");
		    			}
		    			else
		    			{
		    				DeviceManagementHandler deviceManagementHandler = new DeviceManagementHandler();
		    				deviceManagementHandler.performReadOperation(arguments[1],"/3/0/3");
		    			}

		    		}else if(input.trim().contains("-a")){
		    			String[] arguments = input.split(" ");
		    			if(arguments.length<3 || arguments.length>3)
		    			{
		    				System.err.println("Invalid input for observe operation");
		    			}
		    			else
		    			{
		    				InformationReportingHandler reportingHandler = new InformationReportingHandler();
		    				reportingHandler.handleObservationRequestWithWriteAttrToClient(arguments[1], arguments[2]);
		    			}
		    		}

		    	}
		    }
		    catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidUriPathException e) {
				e.printStackTrace();
			} catch (InvalidClientIdentifierException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void printHelp()
	{
		System.out.println("-l:	List registered clients.\n");
		System.out.println("-r:	Read from a client.");
		System.out.println("\te.g. 	-r CLIENT_ID URI1 URI2...");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURIs: 		URIs to read such as /2, /2//2, /2/0/3\n");
		
		System.out.println("-w:	Write to a client." );
		System.out.println("\te.g.	-w CLIEN_ID URI DATA");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURI: 		URI to write such as /2, /2//2, /2/0/3");
		System.out.println("\t\tDATA: 		data to write.\n");
		
		
		System.out.println("-f:	Write firmare version to a client." );
		System.out.println("\te.g.	-w CLIEN_ID URI PATH");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURI: 		URI to write such as /2, /2//2, /2/0/3");
		System.out.println("\t\tPATH: 		Path to the the file containing firmare version data to write.\n");
		
		System.out.println("-e:	Execute a client resource."); 
		System.out.println("\te.g.	 -e CLIENT_ID URI");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURI: 		URI to execute such as /2, /2//2, /2/0/3\n");
		
		System.out.println("-d:	Delete a client Object instance.");
		System.out.println("\te.g.	-d CLIENT_ID URI");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURI: 		URIs to delete such as /2, /2//2, /2/0/3\n");
		
		System.out.println("-o:	observe a client Object, Object Instance or Resource.");
		System.out.println("\te.g.	-o CLIENT_ID URI");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURI: 		URI to observe such as /2, /2//2, /2/0/3\n");
		
		System.out.println("-c:	cancel observation on a client Object, Object Instance or Resource.");
		System.out.println("\te.g.	-c CLIENT_ID URI");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		System.out.println("\t\tURI: 		URI to observe such as /2, /2//2, /2/0/3\n");
		
		System.out.println("-b:	reboot a client.");
		System.out.println("\te.g.	-b CLIENT_ID");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		
		System.out.println("-q:	Query a firmware version for a client.");
		System.out.println("\te.g.	-q CLIENT_ID");
		System.out.println("\t\tWhere");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 
		
		System.out.println("-r CLIENT_ID /3/0/6:	Query power status for a client.");
		System.out.println("\tif it returns 1, fetch battery level by");
		System.out.println("\t\t-r CLIENT_ID /3/0/9");
		System.out.println("\t\tCLIENT_ID: 	client number as returned by command -l"); 

	}
}
