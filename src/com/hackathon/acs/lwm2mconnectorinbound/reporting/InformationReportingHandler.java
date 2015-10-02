package com.hackathon.acs.lwm2mconnectorinbound.reporting;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.coap.Message.messageType;
import ch.ethz.inf.vs.californium.coap.Option;
import ch.ethz.inf.vs.californium.coap.PUTRequest;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.coap.ResponseHandler;
import ch.ethz.inf.vs.californium.coap.TokenManager;
import ch.ethz.inf.vs.californium.coap.registries.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.registries.OptionNumberRegistry;

import com.hackathon.acs.lwm2m.common.InvalidClientIdentifierException;
import com.hackathon.acs.lwm2m.common.LWM2MClient;
import com.hackathon.acs.lwm2m.common.uri.InternalUriIdentifier;
import com.hackathon.acs.lwm2m.common.uri.InvalidUriPathException;
import com.hackathon.acs.lwm2m.common.uri.UriHandler;
import com.hackathon.acs.lwm2mconnectorinbound.server.LWM2MServer;
import com.hackathon.acs.lwm2mconnectorinbound.utils.RandomStringGenerator;


public class InformationReportingHandler implements ResponseHandler{

	public void handleObservationRequestToClient(String clientId, String uriPath) throws InvalidClientIdentifierException, InvalidUriPathException, IOException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null == client)
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}

		LWM2MObservationEntry observationEntry= new LWM2MObservationEntry();

		
		InternalUriIdentifier uriIdentifier = UriHandler.createInternalUri(uriPath);
		
		observationEntry.setId(new RandomStringGenerator(10).nextString());
		observationEntry.setRequestStatus(LWM2MObservationRequestStatus.PENDING);
		observationEntry.setUriIdentifier(uriIdentifier);
		
		byte[] acquiredToken = TokenManager.getInstance().acquireToken(false);
		int intValueOfAquiredToken = new BigInteger(1, acquiredToken).intValue();
		LWM2MServer.getInstance().getObserverTokenClientIdMapping().put(intValueOfAquiredToken, clientId);
		client.getObserveRegistry().put(intValueOfAquiredToken, observationEntry);
		Request request = new GETRequest();
		request.setURI(client.getUri());
		request.setObserve();
		request.setUriPath(uriPath);
		request.setToken(acquiredToken);
		request.registerResponseHandler(this);
		request.execute();
		request.prettyPrint();

	}
	
	public void handleObservationRequestWithWriteAttrToClient(String clientId, String uriPath) throws InvalidClientIdentifierException, InvalidUriPathException, IOException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null == client)
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}

		LWM2MObservationEntry observationEntry= new LWM2MObservationEntry();

		
		InternalUriIdentifier uriIdentifier = UriHandler.createInternalUri(uriPath);
		
		observationEntry.setId(new RandomStringGenerator(10).nextString());
		observationEntry.setRequestStatus(LWM2MObservationRequestStatus.PENDING);
		observationEntry.setUriIdentifier(uriIdentifier);
		uriPath = uriPath+ "?pmin=10&pmax=120";///{Object ID}/{Object Instance ID}/{Resource ID}?pmin={minimum period}&pmax={maximum period}
									//&gt={greater than}&lt={less than}&st={step}
									//&cancel

		byte[] acquiredToken = TokenManager.getInstance().acquireToken(false);
		int intValueOfAquiredToken = new BigInteger(1, acquiredToken).intValue();
		LWM2MServer.getInstance().getObserverTokenClientIdMapping().put(intValueOfAquiredToken, clientId);
		client.getObserveRegistry().put(intValueOfAquiredToken, observationEntry);
		Request request = new PUTRequest();
		request.setURI(client.getUri());
		request.setObserve();
		request.setUriPath(uriPath);
		request.setToken(acquiredToken);
		request.registerResponseHandler(this);
		request.execute();
		request.prettyPrint();

	}
	
	public void cancelObservation(String clientId, String uriPath) throws InvalidClientIdentifierException, InvalidUriPathException, IOException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null == client)
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}

		LWM2MObservationEntry observationEntry= new LWM2MObservationEntry();

		
		InternalUriIdentifier uriIdentifier = UriHandler.createInternalUri(uriPath);
		
		observationEntry.setUriIdentifier(uriIdentifier);
		
		Map<Integer, LWM2MObservationEntry> observeRegistry = client.getObserveRegistry();
		for(Integer token: observeRegistry.keySet())
		{
			System.out.println("CANCELTOKEN: "+ token);
			if(observeRegistry.get(token).equals(observationEntry))
			{
				LWM2MObservationEntry entry = observeRegistry.get(token);
				entry.setCancellationRequested(true);
				System.out.println("CANCELLED THE REQUEST");
				
				break;
			}
		}
	}

	private void handleObservationRequestResponse(Response response)
	{
		int code;
	    if (response == null)
	    {
	        code = CodeRegistry.RESP_SERVICE_UNAVAILABLE;
	    }
	    else if (response.getCode() == CodeRegistry.RESP_CONTENT
	         && !response.hasOption(OptionNumberRegistry.OBSERVE))
	    {
	        code = CodeRegistry.RESP_METHOD_NOT_ALLOWED;
	    }
	    else
	    {
	        code = response.getCode();
	    }

	    if (code != CodeRegistry.RESP_CONTENT)
	    {
	    	removeObserve(response.getRequest());
	    }
	    else
	    {
			response.prettyPrint();
	    	Request request = response.getRequest();
	    	byte[] tokens = request.getToken();
	    	int intValueOfAquiredToken = new BigInteger(1, tokens).intValue();
	    	String clientId = LWM2MServer.getInstance().getClientIdForToken(intValueOfAquiredToken);
	    	
	    	
		    if(null != clientId)
	    	{
		    	LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		    	LWM2MObservationEntry observationEntry = client.getObserveRegistry().get(intValueOfAquiredToken);
		    	if(null != observationEntry)
		    	{
		    		
		    		
			    		if(observationEntry.isCancellationRequested())
			    		{
			    			
			    		//	request.reject();
			    			client.getObserveRegistry().remove(intValueOfAquiredToken);
			    			LWM2MServer.getInstance().getObserverTokenClientIdMapping().remove(intValueOfAquiredToken);
			    			//return;
			    			
			    		
			    			
			    			Request request1=new  PUTRequest();
							request1.setType(messageType.RST);
							
							request1.setMID(response.getMID());
							request1.setURI(client.getUri());
						//	byte[] acquiredToken = TokenManager.getInstance().acquireToken(false);
							request1.setToken(tokens);
							String uriPath = null;
							if(null != client.getResourcePath())
			    			{
			    				
			    				uriPath = client.getResourcePath() + uriPath;
			    				request1.setUriPath(uriPath);
			    			}
							request1.registerResponseHandler(this);
							
							System.out.println("READY TO SEND RESET TO CLIENT");
							try {
								request1.execute();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("NOT ABLE TO RESET REQUEST");
							}
							response.reject();
							request.reject();
							request1.prettyPrint();
							return;
			    		}
		    		Option option = request.getFirstOption(OptionNumberRegistry.OBSERVE);
		    		int count = option.getIntValue();

		    		observationEntry.setRequestStatus(LWM2MObservationRequestStatus.REGISTERED);
		    		observationEntry.getDataValue().put(count, request.getPayload());
		    	}
	    	}
	    }
	}

	public void handleObservationNotification(Request request)
	{
		
    	byte[] tokens = request.getToken();
    	int intValueOfAquiredToken = new BigInteger(1, tokens).intValue();
    	String clientId = LWM2MServer.getInstance().getClientIdForToken(intValueOfAquiredToken);
    	if(null != clientId)
    	{
    		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
    		if(null == client)
    		{
    			return;
    		}
		    Option option = request.getFirstOption(OptionNumberRegistry.OBSERVE);
		    int count = option.getIntValue();
	    	LWM2MObservationEntry observationEntry= client.getObserveRegistry().get(tokens);;
	    	if(null != observationEntry)
	    	{
	    		observationEntry.getDataValue().put(count, request.getPayload());
	    		if(observationEntry.isCancellationRequested())
	    		{
	    			request.reject();
	    			client.getObserveRegistry().remove(intValueOfAquiredToken);
	    			LWM2MServer.getInstance().getObserverTokenClientIdMapping().remove(intValueOfAquiredToken);
	    			//return;
	    			
	    			/*request = new PUTRequest(){
	    				@Override
	    				protected void handleResponse(Response response) {
	    					response.prettyPrint();
	    				}
	    			};
	    			request.setURI(client.getUri());
	    			String uriPath = null;
	    			if(null != client.getResourcePath())
	    			{
	    				
	    				uriPath = client.getResourcePath() + uriPath+"&cancel";
	    			}
	    			
	    			
	    			request.setUriPath(uriPath);
	    			//request.setPayload(data);
	    			try {
						request.execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			*/
	    			
	    			Request request1=new  PUTRequest();
					request1.setType(messageType.RST);
					request1.setURI(client.getUri());
				//	byte[] acquiredToken = TokenManager.getInstance().acquireToken(false);
					request1.setToken(request.getToken());
					String uriPath = null;
					if(null != client.getResourcePath())
	    			{
	    				
	    				uriPath = client.getResourcePath() + uriPath;
	    			}
					request1.setUriPath(uriPath);
					request1.registerResponseHandler(this);
					
					System.out.println("READY TO SEND RESET TO CLIENT");
					try {
						request1.execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("NOT ABLE TO RESET REQUEST");
					}
					request1.prettyPrint();
					return;
	    		}
	    		if(request.isConfirmable())
	    		{
	    			request.accept();
	    		}
	    		if(request.isNonConfirmable())
	    		{
	    			request.accept();
	    		}
	    	}
    	}
    	else
    	{
    		request.reject();
    	}
 	}

	private void removeObserve(Request request)
	{
    	byte[] tokens = request.getToken();
    	int intValueOfAquiredToken = new BigInteger(1, tokens).intValue();
    	String clientId = LWM2MServer.getInstance().getClientIdForToken(intValueOfAquiredToken);
    	LWM2MServer.getInstance().getObserverTokenClientIdMapping().remove(intValueOfAquiredToken);
    	if(null != clientId)
    	{
	    	LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
	    	client.getObserveRegistry().remove(intValueOfAquiredToken);
    	}
	}
	@Override
	public void handleResponse(Response response) {
		handleObservationRequestResponse(response);
	}

}
