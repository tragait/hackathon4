package com.hackathon.acs.lwm2mconnectorinbound.registration;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ch.ethz.inf.vs.californium.coap.DELETERequest;
import ch.ethz.inf.vs.californium.coap.GETRequest;
import ch.ethz.inf.vs.californium.coap.POSTRequest;
import ch.ethz.inf.vs.californium.coap.PUTRequest;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.coap.registries.CodeRegistry;
import ch.ethz.inf.vs.californium.endpoint.resources.LocalResource;

import com.hackathon.acs.lwm2m.common.Constants;
import com.hackathon.acs.lwm2m.common.LWM2MClient;
import com.hackathon.acs.lwm2m.common.LWM2MClientObject;
import com.hackathon.acs.lwm2m.common.LWM2MClientObjectInstance;
import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.uri.InternalUriIdentifier;
import com.hackathon.acs.lwm2m.common.uri.UriHandler;
import com.hackathon.acs.lwm2mconnectorinbound.server.LWM2MServer;
import com.hackathon.acs.lwm2mconnectorinbound.utils.RandomStringGenerator;

public class RegistrationHandler extends LocalResource{

	public final int MAX_LOCATION_LENGTH = 10;

	public final String  URI_REGISTRATION_SEGMENT="rd";

	private RandomStringGenerator randomString  = new RandomStringGenerator(MAX_LOCATION_LENGTH);

	private final String resourcePathLocator = "(?i).*oma.lwm2m.*";;

	public RegistrationHandler() {
		super("rd");
	}

	@Override
	public void performGET(GETRequest request) {
		// create new response
		request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
	};
	
	@Override
	public void performDELETE(DELETERequest request) {
		InternalUriIdentifier uriIdentifier = UriHandler.createInternalUri(request.getUriPath());
		if((uriIdentifier.flag & Constants.LWM2M_URI_MASK_ID) != Constants.OBJECT_ID_URI)
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		LWM2MClient clientP = null;
		clientP = getClientBylocationPath(uriIdentifier.getId());
		if (clientP != null)
		{
			LWM2MServer.getInstance().removeClient(uriIdentifier.getId());
			Response response = new Response(CodeRegistry.RESP_CHANGED);
			response.setCode(CodeRegistry.RESP_DELETED);
			request.respond(response);
		}
		else
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
	};

	@Override
	public void performPOST(POSTRequest request) {
		InternalUriIdentifier uriIdentifier = UriHandler.createInternalUri(request.getUriPath());
		List<LWM2MClientObject> objects;
		LWM2MClient clientP = null;
		String locationString = new String(new char[Constants.MAX_LOCATION_LENGTH]);
		
		if((uriIdentifier.flag & Constants.LWM2M_URI_MASK_ID) != 0)
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		Map<String, String> queryParametersNameValuePair = null;
		try {
			queryParametersNameValuePair = getQueryParametersNameValuePair(request.getUriQuery());
		} catch (UnsupportedEncodingException e) {
			return;
		}
		
		String endpointName =queryParametersNameValuePair.get("ep");
		if (endpointName == null)
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		String payloadString = request.getPayloadString();
		String resourcePath = getResourcePathIfExist(payloadString);
		int JsonFormat=0;
		String json = getJSONFormatifExist(payloadString);
		if(null != json)
			JsonFormat = Integer.parseInt(json);
		if(null != resourcePath)
			payloadString = modifyPayloadStringToRemoveResourcePath(payloadString, resourcePath);
		
		try {
			objects = decodeRegisterRequestPayload(payloadString, request.getPayloadString().length());
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		if (objects.isEmpty())
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		clientP = getClientByName(endpointName);
		if(clientP != null)
		{
			clientP.resetAllData();
		}
		else
		{
			clientP = new LWM2MClient();
			clientP.setInternalID(getNewIdForClient());
		}
		clientP.setName(endpointName);
		clientP.setObjectList(objects);
		clientP.setUriHost(request.getPeerAddress().getAddress().toString().split("/")[1]);
		clientP.setUriPort(Integer.toString(request.getPeerAddress().getPort()));
		clientP.setQueryStringParameters(queryParametersNameValuePair);
		clientP.setJsonFormat(JsonFormat);
		if(null != resourcePath)
		{
			clientP.setResourcePath(resourcePath);
		}
		Response response = new Response(CodeRegistry.RESP_CREATED);
		locationString = getLocationString(clientP.getInternalID());

		addClientToLWM2MServer(clientP);
		
		response.setLocationPath(locationString);
		request.respond(response);
	};
	
	@Override
	public void performPUT(PUTRequest request) {
		InternalUriIdentifier uriIdentifier = UriHandler.createInternalUri(request.getUriPath());
		if((uriIdentifier.flag & Constants.LWM2M_URI_MASK_ID) != Constants.OBJECT_ID_URI)
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		LWM2MClient clientP = null;
		clientP = getClientBylocationPath(uriIdentifier.getId());
		if (clientP != null)
		{
			try {
				clientP.updateRegistrationParameterOnUpdate(getQueryParametersNameValuePair(request.getUriQuery()));
			} catch (UnsupportedEncodingException e) {
			}
			if( null!= request.getPayloadString() && !request.getPayloadString().equals(""))
			{
				String payloadString = request.getPayloadString();
				String resourcePath = getResourcePathIfExist(payloadString);
				String json = getJSONFormatifExist(payloadString);
				int JsonFormat=0;
				if(null != json)
					JsonFormat = Integer.parseInt(json);
				if(null != resourcePath)
					payloadString = modifyPayloadStringToRemoveResourcePath(payloadString, resourcePath);
				
				List<LWM2MClientObject> objects;
				try {
					objects = decodeRegisterRequestPayload(payloadString, request.getPayloadString().length());
				} catch (NumberFormatException | UnsupportedEncodingException e) {
					request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
					return;
				}
				if (objects.isEmpty())
				{
					request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
					return;
				}
				clientP.setObjectList(objects);
				clientP.setUriHost(request.getPeerAddress().getAddress().toString().split("/")[1]);
				clientP.setUriPort(Integer.toString(request.getPeerAddress().getPort()));
				clientP.setJsonFormat(JsonFormat);
				if(null != resourcePath)
				{
					clientP.setResourcePath(resourcePath);
				}
			}
		}
		else
		{
			request.respondAndSend(CodeRegistry.RESP_BAD_REQUEST);
			return;
		}
		Response response = new Response(CodeRegistry.RESP_CHANGED);
		response.setCode(CodeRegistry.RESP_CHANGED);
		request.respond(response);

	};
	
	
	private LWM2MClient getClientBylocationPath(String id) {
		return LWM2MServer.getInstance().getClient(id);
	}

	private void addClientToLWM2MServer(LWM2MClient client)
	{
		LWM2MServer.getInstance().addLWM2MClient(client);
	}

	private LWM2MClient getClientByName(String name) {
		Map<String, LWM2MClient> clientList = LWM2MServer.getInstance().getClientList();
		for(String id: clientList.keySet())
		{
			if(clientList.get(id).getName().equals(name))
			{
				return clientList.get(id);
			}
		}
		return null;
	}
	
	private String getNewIdForClient() {
		return randomString.nextString();
	}
	
	private Map<String, String> getQueryParametersNameValuePair(String  queryString) throws UnsupportedEncodingException
	{
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		if(null == queryString || queryString == "")
		{
			return query_pairs;
		}
		if(queryString.charAt(0)== '?')
		{
			queryString = queryString.substring(1, queryString.length());
		}
	    String[] queryArray = queryString.split("&");
		for(String query : queryArray)
		{
			
	        int idx = query.indexOf("=");
	        query_pairs.put(URLDecoder.decode(query.substring(0, idx), "UTF-8"), URLDecoder.decode(query.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}

	private List<LWM2MClientObject> decodeRegisterRequestPayload(String payloadString, int maxLength) throws NumberFormatException, UnsupportedEncodingException
	{
		boolean objectExistInList= false;
		String objId = null;
		String objInstanceId = null;
		String resourceId = null;
		List<LWM2MClientObject> clientObjects = new ArrayList<LWM2MClientObject>();
		List<String> inputOptions = new ArrayList<String>();
		String[] objectInfoPairs = payloadString.split(",");
		
		if(!payloadString.contains("<"))
		{
			clientObjects = decodePayloadWithoutAngularBraces(payloadString, maxLength);
			return clientObjects;
		}
		
		for (String objectInfo : objectInfoPairs) {
			if(objectInfo.matches(resourcePathLocator))
			{
				continue;
			}
			String[] objectDataWithOptionalValuesArray = objectInfo.split(";");
			LWM2MClientObject clientObject = null;

			for(String input: objectDataWithOptionalValuesArray)
			{
				input.replaceAll("\\s","");
				input = input.trim();


				if(input.contains("<"))
				{
					int idIdx = 0;
			    	objectExistInList = false;


			    	if(input.charAt(1) == '/')
			    	{
			    		idIdx = input.indexOf("/");
			    	}
			        
			    	int nxtIdx = input.indexOf("/",idIdx+1);

			        if(idIdx != nxtIdx && nxtIdx !=-1)
			        	objId = input.substring(idIdx+1, nxtIdx);
			        else
			        	objId = input.substring(idIdx+1, input.length()-1);
			        
			        for(LWM2MClientObject object : clientObjects)
			        {
			        	if(String.valueOf(object.getId()).equals(objId))
			        	{
			        		clientObject = object;
			        		objectExistInList = true;
			        	}
			        }
			        if(!objectExistInList){
			        	clientObject = new LWM2MClientObject();
			        	clientObject.setId(Integer.parseInt(objId));
			        }
			        
			        if(idIdx != nxtIdx && nxtIdx !=-1)
			        {
			        	int finalIdx = input.indexOf("/",nxtIdx+1);
			        	
			        	if(nxtIdx != finalIdx && finalIdx!= -1)
			        	{
				        	objInstanceId = input.substring(nxtIdx+1, finalIdx);
				        	clientObject.addObjectInstance(new LWM2MClientObjectInstance(Short.parseShort(objInstanceId)));

				        	resourceId = input.substring(finalIdx+1, input.length()-1);
			        		clientObject.addObjectResource(Short.parseShort(objInstanceId), new LWM2MResource(Short.parseShort(resourceId)));
			        	}
			        	else
			        	{
			        		objInstanceId = input.substring(nxtIdx+1, input.length()-1);
			        		clientObject.addObjectInstance(new LWM2MClientObjectInstance(Short.parseShort(objInstanceId)));
			        	}
			        }
			        if(!clientObjects.contains(clientObject))
			        	clientObjects.add(clientObject);
				}
				else
				{
					inputOptions.add(input);
				}
			}
			if(!inputOptions.isEmpty())
			//clientObject.setResourceOptions(Short.parseShort(objInstanceId), Short.parseShort(resourceId), inputOptions);
			inputOptions.clear();
		}

		 return clientObjects;
	}

	private List<LWM2MClientObject> decodePayloadWithoutAngularBraces(String payloadString,
			int maxLength) {
		String[] pairs = payloadString.split(",");
		boolean objectExistInList= false;
		String objId = null;
		String objInstanceId = null;
		List<LWM2MClientObject> clientObjects = new ArrayList<LWM2MClientObject>();
	    
		for (String pair : pairs) {
			if(pair.matches(resourcePathLocator))
			{
				continue;
			}
	    	objectExistInList = false;
	    	pair.replaceAll("\\s","");
	    	pair = pair.trim();
	    	LWM2MClientObject clientObject;
	    	clientObject = new LWM2MClientObject();

	    	int idxOf = pair.indexOf("/");
	        if(idxOf == -1)
	        {
	        	objId = pair;
	        }
	        else
	        {
	        	objId = pair.substring(0, idxOf);
	        }
       
	        for(LWM2MClientObject object : clientObjects)
	        {
	        	if(String.valueOf(object.getId()).equals(objId))
	        	{
	        		clientObject = object;
	        		objectExistInList = true;
	        	}
	        }

	        if(!objectExistInList)
	        clientObject.setId(Integer.parseInt(objId));

	        if(idxOf != -1 && idxOf != pair.length()-1)
	        {
	        	objInstanceId = pair.substring(idxOf+1, pair.length());
	        	clientObject.addObjectInstance(new LWM2MClientObjectInstance(Short.parseShort(objInstanceId)));;
	        }
	        if(!clientObjects.contains(clientObject))
	        	clientObjects.add(clientObject);
	    };
	    return clientObjects;
		
	}

	private String getResourcePathIfExist(String payloadString)
	{
		String resourcePath = null;
		
		if(payloadString.matches(resourcePathLocator))
		{
			String[] pairs = payloadString.split(",");
			for(String value: pairs)
			{
				if(value.matches(resourcePathLocator))
				{
					 resourcePath= value.substring(value.indexOf("/"), value.lastIndexOf(">"));
				}
			}
		}
		return resourcePath;
		
	}
	
	private String modifyPayloadStringToRemoveResourcePath(String payloadString, String resourcePath)
	{
		return payloadString.replaceAll(resourcePath, "");
	}
	
	private String getJSONFormatifExist(String payloadString){
		String resourcePath = null;
		
		if(payloadString.matches(resourcePathLocator))
		{
			String[] pairs = payloadString.split(",");
			for(String value: pairs)
			{
				if(value.matches(resourcePathLocator))
				{
					 resourcePath= value.substring(value.lastIndexOf("=")+1, value.length());
				}
			}
		}
		return resourcePath;
	}
	private String getLocationString(String id)
	{
		return "/" +URI_REGISTRATION_SEGMENT+"/"+ id;
	}
	

}
