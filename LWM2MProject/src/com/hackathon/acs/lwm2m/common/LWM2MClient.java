package com.hackathon.acs.lwm2m.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hackathon.acs.lwm2m.common.queue.mode.BindingMode;
import com.hackathon.acs.lwm2mconnectorinbound.reporting.LWM2MObservationEntry;

public class LWM2MClient
{
	private String internalID;
	private String uriHost;
	private String uriPort;

	private String endpointName;
	private String version;
	
	//default lifetime of client in milliseconds i.e. 86400 seconds or 24hrs
	private int lifetime=86400000;
	
	private BindingMode binding;
	private String msisdn;
	
	private List<LWM2MClientObject> objectList;
	private Map<Integer, LWM2MObservationEntry> observeRegistry= new HashMap<Integer, LWM2MObservationEntry>();
	
	private String resourcePath = null;
	private int jsonFormat = 0;
	
	public String getInternalID() {
		return internalID;
	}
	public void setInternalID(String id) {
		this.internalID = id;
	}
	public String getName() {
		return endpointName;
	}
	public void setName(String name) {
		this.endpointName = name;
	}
	public List<LWM2MClientObject> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<LWM2MClientObject> objectList) {
		this.objectList = objectList;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getResourcePath() {
		return resourcePath;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	public int getJsonFormat() {
		return jsonFormat;
	}
	public void setJsonFormat(int jsonFormat) {
		this.jsonFormat = jsonFormat;
	}
	public URI getUri() {
		return getCompleteUri();
	}
	public String getUriHost() {
		return uriHost;
	}
	public void setUriHost(String uriHost) {
		this.uriHost = uriHost;
	}
	public String getUriPort() {
		return uriPort;
	}
	public void setUriPort(String uriPort) {
		this.uriPort = uriPort;
	}
	public Map<Integer, LWM2MObservationEntry> getObserveRegistry() {
		return observeRegistry;
	}
	public void setObserveRegistry(Map<Integer, LWM2MObservationEntry> observeRegistry) {
		this.observeRegistry = observeRegistry;
	}
	public int getLifetime() {
		return lifetime;
	}
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	public BindingMode getBinding() {
		return binding;
	}
	public void setBinding(BindingMode binding) {
		this.binding = binding;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public void resetAllData() {
		endpointName = null;
		uriHost = null;
		uriPort = null;
		objectList = null;
		
	}
	
	public void setQueryStringParameters(Map<String, String> queryStringParameters) {
		if(null != queryStringParameters.get(Constants.CLIENT_LIFETIME))
		{
			lifetime = Integer.parseInt(queryStringParameters.get(Constants.CLIENT_LIFETIME));
		}
		if(null != queryStringParameters.get(Constants.CLIENT_VERSION))
		{
			version = queryStringParameters.get(Constants.CLIENT_VERSION);
		}
		if(null != queryStringParameters.get(Constants.CLIENT_BINDING_MODE))
		{
			switch(queryStringParameters.get(Constants.CLIENT_BINDING_MODE))
			{
			case "U":
				binding = BindingMode.U;
				break;
			case "UQ":
				binding = BindingMode.UQ;
				break;
			case "S":
				binding = BindingMode.S;
				break;
			case "SQ":
				binding = BindingMode.SQ;
				break;
			case "US":
				binding = BindingMode.US;
				break;
			case "UQS":
				binding = BindingMode.UQS;
				break;
			}
		}
		if(null != queryStringParameters.get(Constants.CLIENT_SMS_MSISDN))
		{
			msisdn = queryStringParameters.get(Constants.CLIENT_SMS_MSISDN);
		}
	}

	public void updateRegistrationParameterOnUpdate(Map<String, String> queryStringParameters)
	{
		if(null != queryStringParameters.get(Constants.CLIENT_LIFETIME))
		{
			lifetime = Integer.parseInt(queryStringParameters.get(Constants.CLIENT_LIFETIME));
		}
		if(null != queryStringParameters.get(Constants.CLIENT_VERSION))
		{
			version = queryStringParameters.get(Constants.CLIENT_VERSION);
		}
		if(null != queryStringParameters.get(Constants.CLIENT_BINDING_MODE))
		{
			switch(queryStringParameters.get(Constants.CLIENT_BINDING_MODE))
			{
			case "U":
				binding = BindingMode.U;
				break;
			case "UQ":
				binding = BindingMode.UQ;
				break;
			case "S":
				binding = BindingMode.S;
				break;
			case "SQ":
				binding = BindingMode.SQ;
				break;
			case "US":
				binding = BindingMode.US;
				break;
			case "UQS":
				binding = BindingMode.UQS;
				break;
			}
		}
		if(null != queryStringParameters.get(Constants.CLIENT_SMS_MSISDN))
		{
			msisdn = queryStringParameters.get(Constants.CLIENT_SMS_MSISDN);
		}
	}

	private URI getCompleteUri() {
		StringBuilder builder = new StringBuilder();
		builder.append("coap://");
		builder.append(getUriHost());
		builder.append(":" + getUriPort());
		
		URI ret = null;
		
		try {
			ret = new URI(builder.toString());
		} catch (URISyntaxException e) {
			System.err.println(String.format("Cannot assemble Message URI."));
		}

		return ret;
	}

	@Override
	public String toString() {
		return "LWM2M Client Details [internalID=" + internalID + ", endpointName="
				+ endpointName + ", uriHost=" + uriHost + ", uriPort="
				+ uriPort + ", objectList=" + objectList + "]";
	}

}