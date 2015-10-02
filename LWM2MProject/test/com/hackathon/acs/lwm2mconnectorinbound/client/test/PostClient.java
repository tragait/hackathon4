package com.hackathon.acs.lwm2mconnectorinbound.client.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import ch.ethz.inf.vs.californium.coap.POSTRequest;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;

public class PostClient {
	/*
	 * Application entry point.
	 * 
	 */	
	public static void main(String args[]) {
		
		URI uri = null; // URI parameter of the request
		args = new String[10];
		args[0] = "rd";
		
		if (args.length > 0) {
			
			// input URI from command line arguments
			try {
				uri = new URI(args[0]);
			} catch (URISyntaxException e) {
				System.err.println("Invalid URI: " + e.getMessage());
				System.exit(-1);
			}
		
			// create new request
			Request request = new POSTRequest();
			request.setUriQuery("ep=ggtest&lt=1680000&sms=13542&lwm2m=1&b=1");
			request.setPayload("</3/0/0>;ct=\"0\",</3/0/4>;ct=\"0\",</3/0/16>;ct=\"0\",</3/0/3>;ct=\"0\",</3/0/2>;ct=\"0\",</3/0/1>;ct=\"0\",</3/0/7>;obs;ct=\"0\",</3/0/6>;ct=\"0\"");
			//request.setPayload("<3/0/0>,<3/0/1>,<3/0/2>,<3/0/3>,<3/1/6>,<3/1/7>,<3/1/8>,<3/1/9>,<3/2/6>,<3/2/7>,<4/0/0>,<4/0/1>,<4/0/2>");
			//request.setPayload("</3/0/0>;if=\"\";rt=\"\";ct=\"0\",</3/0/4>;if=\"\";rt=\"\";ct=\"0\",</3/0/16>;if=\"\";rt=\"\";ct=\"0\",</3/0/3>;if=\"\";rt=\"\";ct=\"0\"");
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
			
		} else {
			// display help
			System.out.println("Californium (Cf) GET Client");
			System.out.println("(c) 2012, Institute for Pervasive Computing, ETH Zurich");
			System.out.println();
			System.out.println("Usage: " + GETClient.class.getSimpleName() + " URI");
			System.out.println("  URI: The CoAP URI of the remote resource to GET");
		}
	}

}
