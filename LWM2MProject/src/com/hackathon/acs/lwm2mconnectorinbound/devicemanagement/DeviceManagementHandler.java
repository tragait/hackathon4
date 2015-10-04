package com.hackathon.acs.lwm2mconnectorinbound.devicemanagement;

import java.io.IOException;
import java.util.List;

import ch.ethz.inf.vs.californium.coap.DELETERequest;
import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.coap.Option;
import ch.ethz.inf.vs.californium.coap.POSTRequest;
import ch.ethz.inf.vs.californium.coap.PUTRequest;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.coap.registries.MediaTypeRegistry;

import com.google.gson.Gson;
import com.hackathon.acs.lwm2m.common.InvalidClientIdentifierException;
import com.hackathon.acs.lwm2m.common.LWM2MClient;
import com.hackathon.acs.lwm2m.common.LWM2MClientObject;
import com.hackathon.acs.lwm2m.common.LWM2MClientObjectInstance;
import com.hackathon.acs.lwm2m.common.uri.InternalUriIdentifier;
import com.hackathon.acs.lwm2m.common.uri.InvalidUriPathException;
import com.hackathon.acs.lwm2m.common.uri.UriHandler;
import com.hackathon.acs.lwm2m.mediatype.json.JsonData;
import com.hackathon.acs.lwm2m.mediatype.tlv.Lwm2mTLVManagement;
import com.hackathon.acs.lwm2mconnectorinbound.server.LWM2MServer;
import com.hackathon.acs.lwm2mconnectorinbound.utils.IOUtil;
import com.hackathon.lightweightm2m.objects.LWM2MStandardObjectInterface;

public class DeviceManagementHandler {

	public void performReadOperation(String clientId, String... uriPaths) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if( null == client)
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + " is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
		for(String uriPath : uriPaths)
		{
			if(null != client)
			{
				InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);
				if(null == identifier.getId())
				{
					throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
				}
				GETRequest request = new GETRequest(){
					@Override
					protected void handleResponse(Response response) {
						System.out.println(response.toString());
						response.prettyPrint();
						int mediaType = response.getContentType();
						switch(mediaType)
						{
						case 1541:  // MediaTypeRegistry.TEXT_PLAIN:
							System.out.println("Content type: text/plain(UTF 8)");
							System.out.println("Payload Content:");
							System.out.println(response.getPayloadString());
							break;
						case 1542:     //90:
/*							InternalUriIdentifier identifier = UriHandler.createInternalUri(response.getRequest().getUriPath());
							Class className = (Class<LWM2MStandardObjectInterface>) LWM2MServer.getInstance().getProtocolSpecifiedObjectMapping().get(Integer.parseInt(identifier.getId()));
							if( null != className)
							{
								try {
									LWM2MStandardObjectInterface object = (LWM2MStandardObjectInterface) className.newInstance();
								} catch (InstantiationException
										| IllegalAccessException e) {
									e.printStackTrace();
								}
							}
*/							System.out.println("Content type: tlv");
							System.out.println("Payload Content:");
							Lwm2mTLVManagement.decodeTLV(response.getPayload());
							break;
						case 1543:    //MediaTypeRegistry.APPLICATION_JSON:
							System.out.println("Content type: Json");
							System.out.println("Payload Content:");
							System.out.println(response.getPayloadString());
							Gson gson = new Gson();
							JsonData data = gson.fromJson(response.getPayloadString(), JsonData.class);
							System.out.println("Recieved values:" + data);
							break;
						}
					}
				};
				request.setURI(client.getUri());
				if(null != client.getResourcePath())
				{
					uriPath = client.getResourcePath() + uriPath;
				}
				request.setUriPath(uriPath);
				request.execute();
		}
		}

	}
	
	
	public void performReadOperationForTLV(String clientId, String... uriPaths) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if( null == client)
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + " is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
		for(String uriPath : uriPaths)
		{
			if(null != client)
			{
				InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);
				if(null == identifier.getId())
				{
					throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
				}
				GETRequest request = new GETRequest(){
					@Override
					protected void handleResponse(Response response) {
						System.out.println(response.toString());
						response.prettyPrint();
						int mediaType = response.getContentType();
						switch(mediaType)
						{
						case MediaTypeRegistry.TEXT_PLAIN:
							System.out.println("Content type: text/plain(UTF 8)");
							System.out.println("Payload Content:");
							System.out.println(response.getPayloadString());
							break;
						
						case MediaTypeRegistry.APPLICATION_JSON:
							System.out.println("Content type: Json");
							System.out.println("Payload Content:");
							System.out.println(response.getPayloadString());
							Gson gson = new Gson();
							JsonData data = gson.fromJson(response.getPayloadString(), JsonData.class);
							System.out.println("Recieved values:" + data);
							break;
						case 1542:
							/*							InternalUriIdentifier identifier = UriHandler.createInternalUri(response.getRequest().getUriPath());
														Class className = (Class<LWM2MStandardObjectInterface>) LWM2MServer.getInstance().getProtocolSpecifiedObjectMapping().get(Integer.parseInt(identifier.getId()));
														if( null != className)
														{
															try {
																LWM2MStandardObjectInterface object = (LWM2MStandardObjectInterface) className.newInstance();
															} catch (InstantiationException
																	| IllegalAccessException e) {
																e.printStackTrace();
															}
														}
							*/							System.out.println("Content type: tlv");
														System.out.println("Payload Content:");
														Lwm2mTLVManagement.decodeTLV(response.getPayload());
														break;
						}
					}
				};
				request.setURI(client.getUri());
				if(null != client.getResourcePath())
				{
					uriPath = client.getResourcePath() + uriPath;
				}
				request.setUriPath(uriPath);
				Option option = new Option(17);  //initially it was 17, changed to 16
				option.setIntValue(1542);
				
				request.setOption(option);
				request.execute();
		}
		}

	}
	
	public void performReadOperationForJSON(String clientId, String... uriPaths) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if( null == client)
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + " is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
		for(String uriPath : uriPaths)
		{
			if(null != client)
			{
				InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);
				if(null == identifier.getId())
				{
					throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
				}
				GETRequest request = new GETRequest(){
					@Override
					protected void handleResponse(Response response) {
						System.out.println(response.toString());
						response.prettyPrint();
						int mediaType = response.getContentType();
						switch(mediaType)
						{
						case MediaTypeRegistry.TEXT_PLAIN:
							System.out.println("Content type: text/plain(UTF 8)");
							System.out.println("Payload Content:");
							System.out.println(response.getPayloadString());
							break;
						case 90:
/*							InternalUriIdentifier identifier = UriHandler.createInternalUri(response.getRequest().getUriPath());
							Class className = (Class<LWM2MStandardObjectInterface>) LWM2MServer.getInstance().getProtocolSpecifiedObjectMapping().get(Integer.parseInt(identifier.getId()));
							if( null != className)
							{
								try {
									LWM2MStandardObjectInterface object = (LWM2MStandardObjectInterface) className.newInstance();
								} catch (InstantiationException
										| IllegalAccessException e) {
									e.printStackTrace();
								}
							}
*/							System.out.println("Content type: tlv");
							System.out.println("Payload Content:");
							Lwm2mTLVManagement.decodeTLV(response.getPayload());
							break;
						case 1543:
							System.out.println("Content type: Json");
							System.out.println("Payload Content:");
							System.out.println(response.getPayloadString());
							Gson gson = new Gson();
							JsonData data = gson.fromJson(response.getPayloadString(), JsonData.class);
							System.out.println("Recieved values:" + data);
							break;
						}
					}
				};
				request.setURI(client.getUri());
				if(null != client.getResourcePath())
				{
					uriPath = client.getResourcePath() + uriPath;
				}
				request.setUriPath(uriPath);
				Option option = new Option(17);
				option.setIntValue(1543);
				request.setOption(option);
				request.execute();
		}
		}

	}
	
	
	public void performWriteOperation(String clientId, String uriPath, byte[] data) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null != client)
		{
			InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);	
			
			
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
	
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
			if(null == identifier.getObjectInstanceId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Instance Id is not specified");
			}
			
			PUTRequest request = new PUTRequest(){
				@Override
				protected void handleResponse(Response response) {
					System.out.println(response.toString());
					response.prettyPrint();
				}
			};
			request.setURI(client.getUri());
			if(null != client.getResourcePath())
			{
				
				uriPath = client.getResourcePath() + uriPath;
			}
			
			
			request.setUriPath(uriPath);
			request.setPayload(data);
			request.execute();
		}
		else
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
	}
	
	public void performWriteAttributeOperation(String clientId, String uriPath) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null != client)
		{
			InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);	
			
			
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
	
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
			if(null == identifier.getObjectInstanceId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Instance Id is not specified");
			}
			
			PUTRequest request = new PUTRequest(){
				@Override
				protected void handleResponse(Response response) {
					System.out.println(response.toString());
					response.prettyPrint();
				}
			};
			request.setURI(client.getUri());
			if(null != client.getResourcePath())
			{
				String pathToWO = "?pmin=10&pmax=120";
				uriPath = client.getResourcePath() + uriPath+ pathToWO;
			}else{
				String pathToWO = "?pmin=10&pmax=120";
				uriPath =  uriPath+ pathToWO;
			}
			Option option = new Option(15);  //write attribute start here
			option.setOptionNumber(80);
			
			request.setOption(option);
			request.setUriPath(uriPath);
			request.setObserve();
		//	request.setPayload(data);
			request.execute();
		}
		else
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
	}
	
	public void performWriteOperationForFirmawareUpdate(String clientId, String uriPath, String path) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null != client)
		{
			InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);		
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
	
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
			if(null == identifier.getObjectInstanceId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Instance Id is not specified");
			}
			
			PUTRequest request = new PUTRequest(){
				@Override
				protected void handleResponse(Response response) {
					System.out.println(response.toString());
					response.prettyPrint();
				}
			};
			request.setURI(client.getUri());
			if(null != client.getResourcePath())
			{
				uriPath = client.getResourcePath() + uriPath;
			}
			request.setUriPath(uriPath);
			byte[] data = IOUtil.readFile(path);
			request.setPayload(data);
			request.execute();
		}
		else
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
	}

	
	public void performCreateOperation(String clientId, String uriPath, byte[] payload) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null != client)
		{
			InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
	
			POSTRequest request = new POSTRequest(){
				@Override
				protected void handleResponse(Response response) {
					response.prettyPrint();
					System.out.println(response.toString());
				}
			};
			request.setURI(client.getUri());
			if(null != client.getResourcePath())
			{
				uriPath = client.getResourcePath() + uriPath;
			}
			request.setUriPath(uriPath);
			request.setPayload(payload);
			request.execute();
		}
		else
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
	}

	public void performExecuteOperation(String clientId, String uriPath) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null != client)
		{
			InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
			if(null == identifier.getObjectInstanceId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Instance Id is not specified");
			}
			if(null == identifier.getResourceId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Resource Id is not specified");
			}
	
			POSTRequest request = new POSTRequest(){
				@Override
				protected void handleResponse(Response response) {
					System.out.println(response.toString());
					response.prettyPrint();
				}
			};
			request.setURI(client.getUri());
			if(null != client.getResourcePath())
			{
				uriPath = client.getResourcePath() + uriPath;
			}

			request.setUriPath(uriPath);
			request.execute();
		}
		else
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
	}
	
	public void performDeleteOperation(String clientId, String uriPath) throws IOException, InvalidUriPathException, InvalidClientIdentifierException
	{
		LWM2MClient client = LWM2MServer.getInstance().getClient(clientId);
		if(null != client)
		{
			InternalUriIdentifier identifier = UriHandler.createInternalUri(uriPath);
			if(null == identifier.getId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Id is not specified");
			}
			if(null == identifier.getObjectInstanceId())
			{
				throw new InvalidUriPathException("Error while parsing uripath: Mandatory Object Instance Id is not specified");
			}
	
			DELETERequest request = new DELETERequest(){
				@Override
				protected void handleResponse(Response response) {
					System.out.println(response.toString());
					response.prettyPrint();
				}
			};
			request.setURI(client.getUri());
			request.setUriPath(uriPath);
			request.execute();
		}
		else
		{
			throw new InvalidClientIdentifierException("The client id "+clientId + "is invalid. Please provide a valid client id. Please refer help i.e. -h for list of clients registered with the system");
		}
	}
	private void validateURIbeforeRequestingClient(LWM2MClient client, InternalUriIdentifier identifier) throws InvalidUriPathException
	{
		List<LWM2MClientObject> objectList;
		objectList = client.getObjectList();
		if(null == objectList)
		{
			throw new InvalidUriPathException("Error while parsing uripath: Objects are available to the server for access");
		}
		boolean elementExist = false;

		if(null != identifier.getId())
		{
			for(LWM2MClientObject clientObject : objectList)
			{
				if(clientObject.getId() == Integer.parseInt(identifier.getId()))
				{
					elementExist = true;
					break;
				}
			}
			if(!elementExist)
			throw new InvalidUriPathException("Error while parsing uripath: Either object do not exist on client or not available to the server for access");
			else
				elementExist = false;
		}
		if(null != identifier.getObjectInstanceId())
		{
			for(LWM2MClientObject clientObject : objectList)
			{
				List<LWM2MClientObjectInstance> instanceList = clientObject.getObjectInstances();
				for(LWM2MClientObjectInstance instance : instanceList)
				{
					if(instance.getId() == Integer.parseInt(identifier.getObjectInstanceId()))
					{
						elementExist = true;
						break;
					}
				}
			}
			if(!elementExist)
			throw new InvalidUriPathException("Error while parsing uripath: Either object instance id do not exist on client or not available to the server for access");
			else
				elementExist = false;
		}
		if(null != identifier.getResourceId())
		{
			throw new InvalidUriPathException("Error while parsing uripath: Mandatory Resource Id is not specified");
		}
	}
}
