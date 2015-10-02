package com.hackathon.acs.lwm2mconnectorinbound.client.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;

import ch.ethz.inf.vs.californium.coap.DELETERequest;
import ch.ethz.inf.vs.californium.coap.POSTRequest;
import ch.ethz.inf.vs.californium.coap.PUTRequest;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.coap.registries.CodeRegistry;


public class LWM2MServerTest {

	String locationPath;
	
	@Test
	public void testRegistrationReqest()
	{
		URI uri = null;
		try {
			uri = new URI("rd");
		} catch (URISyntaxException e) {
			System.err.println("Invalid URI: " + e.getMessage());
			System.exit(-1);
		}

		// create new request
		Request request = new POSTRequest();
		request.setUriQuery("ep=ggtest&lt=16800&sms=13542&lwm2m=1&b=1");
		request.setPayload("</lwm2m>;rt=\"oma.lwm2m\";ct=100, </lwm2m/1/101>, </lwm2m/1/102>, </lwm2m/2/0>, </lwm2m/2/1>, </lwm2m/2/2>, </lwm2m/3/0>,</lwm2m/4/0>,</lwm2m/5>");
		// specify URI of target endpoint
		request.setURI(uri);
		// enable response queue for blocking I/O
		request.enableResponseQueue(true);

		// execute the request
		try {
			request.execute();
		} catch (IOException e) {
			Assert.fail("Failed to execute request: " + e.getMessage());
		}

		// receive response
		try {
			Response response = request.receiveResponse();

			if (response != null) {
				assertEquals(response.getCode(), CodeRegistry.RESP_CREATED);
				locationPath = response.getLocationPath();
			} else {
				Assert.fail("No response received.");
			}

		} catch (InterruptedException e) {
			System.err.println("Receiving of response interrupted: "
					+ e.getMessage());
		}

	}
	
	@Test
	public void testUpdateReqest()
	{
		URI uri = null;
		try {
			uri = new URI("rd" + "/" + locationPath);
		} catch (URISyntaxException e) {
			System.err.println("Invalid URI: " + e.getMessage());
		}
	
		// create new request
		Request request = new PUTRequest();
		request.setUriQuery("ep=ggtest&lt=16800&sms=13542");
		// specify URI of target endpoint
		request.setURI(uri);
		// enable response queue for blocking I/O
		request.enableResponseQueue(true);
		
		// execute the request
		try {
			request.execute();
		} catch (IOException e) {
			Assert.fail("Failed to execute request: " + e.getMessage());
		}
		
		// receive response
		try {
			Response response = request.receiveResponse();
			
			if (response != null) {
				assertEquals(response.getCode(), CodeRegistry.RESP_CHANGED);
			} else {
				Assert.fail("No response received.");
			}
			
		} catch (InterruptedException e) {
			Assert.fail("Receiving of response interrupted: " + e.getMessage());
		}
	}

	@Test
	public void testDeregisterRequest()
	{
		URI uri = null;
		try {
			uri = new URI("rd" + "/" + locationPath);
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
			Assert.fail("Failed to execute request: " + e.getMessage());
		}
		
		// receive response
		try {
			Response response = request.receiveResponse();
			
			if (response != null) {
				assertEquals(response.getCode(), CodeRegistry. RESP_DELETED);
			} else {
				Assert.fail("No response received.");
			}
			
		} catch (InterruptedException e) {
			Assert.fail("Receiving of response interrupted: " + e.getMessage());
		}
	}
	
}
