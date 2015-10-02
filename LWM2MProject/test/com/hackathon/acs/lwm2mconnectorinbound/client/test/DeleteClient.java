package com.hackathon.acs.lwm2mconnectorinbound.client.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import ch.ethz.inf.vs.californium.coap.DELETERequest;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;

public class DeleteClient {
	/*
	 * Application entry point.
	 * 
	 */	
	public static void main(String args[]) {
		
		URI uri = null; // URI parameter of the request
		args = new String[10];
		args[0] = "rd/af00i4kmd5";
		
		if (args.length > 0) {
			
			// input URI from command line arguments
			try {
				uri = new URI(args[0]);
			} catch (URISyntaxException e) {
				System.err.println("Invalid URI: " + e.getMessage());
				System.exit(-1);
			}
		
			// create new request
			Request request = new DELETERequest();
			// specify URI of target endpoint
			request.setURI(uri);
			// enable response queue for blocking I/O
			request.enableResponseQueue(true);
			
			// execute the request
			try {
				request.execute();
			} catch (IOException e) {
				System.err.println("Failed to execute request: " + e.getMessage());
				System.exit(-1);
			}
			
			// receive response
			try {
				Response response = request.receiveResponse();
				
				if (response != null) {
					// response received, output a pretty-print
					response.prettyPrint();
				} else {
					System.out.println("No response received.");
				}
				
			} catch (InterruptedException e) {
				System.err.println("Receiving of response interrupted: " + e.getMessage());
				System.exit(-1);
			}
			
		} 
	}
}
