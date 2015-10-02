package com.hackathon.acs.lwm2mconnectorinbound.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.hackathon.acs.lwm2m.common.LWM2MClient;
import com.hackathon.lightweightm2m.objects.AccessControl;
import com.hackathon.lightweightm2m.objects.ConnectivityMonitoring;
import com.hackathon.lightweightm2m.objects.ConnectivityStatistics;
import com.hackathon.lightweightm2m.objects.Device;
import com.hackathon.lightweightm2m.objects.Firmware;
import com.hackathon.lightweightm2m.objects.LWM2MServerAccessSecurity;
import com.hackathon.lightweightm2m.objects.Location;

public class LWM2MServer {

	private static LWM2MServer INSTANCE= new LWM2MServer();
	
	private Map<String, LWM2MClient> clientList = new HashMap<String, LWM2MClient>();
	
	private Map<Integer, String> observerTokenClientIdMapping = new HashMap<Integer, String>();
	
	private Map<Integer, Object> protocolSpecifiedObjectMapping = new HashMap<Integer, Object>();
	
	private Map<String, Timer> clientLifetimeSchedulers = new HashMap<String, Timer>();
	
	private LWM2MServer()
	{
		populateProtocolSpecifiedObjectMapping();
	}
	
	public static LWM2MServer getInstance()
	{
		return INSTANCE;
	}

	public Map<String, LWM2MClient> getClientList() {
		return clientList;
	}

	public void setClientList(Map<String, LWM2MClient> clientList) {
		this.clientList = clientList;
	}
	

	public LWM2MClient getClient(String clientIdentifier)
	{
		return clientList.get(clientIdentifier);
	}
	
	public void addLWM2MClient(final LWM2MClient client)
	{
		clientList.put(client.getInternalID(), client);

		Timer timer = new Timer();
		timer.schedule( 
				new TimerTask() {
					@Override
					public void run() {
						removeLWM2MClientAtLifetimeExpiry(client.getInternalID());
					}
				}, 
				client.getLifetime() 
				);
		clientLifetimeSchedulers.put(client.getInternalID(), timer);
	}

	public void removeClient(String id) {
		clientList.remove(id);
	}
	@Override
	public String toString() {
		return "LWM2MServer [clientList=" + clientList + "]";
	}

	public Map<Integer, String> getObserverTokenClientIdMapping() {
		return observerTokenClientIdMapping;
	}

	public void setObserverTokenClientIdMapping(
			Map<Integer, String> observerTokenClientIdMapping) {
		this.observerTokenClientIdMapping = observerTokenClientIdMapping;
	}

	public String getClientIdForToken(Integer tokens) {
		return observerTokenClientIdMapping.get(tokens);
		
	}

	public Map<Integer, Object> getProtocolSpecifiedObjectMapping() {
		return protocolSpecifiedObjectMapping;
	}

	public void setProtocolSpecifiedObjectMapping(
			Map<Integer, Object> protocolSpecifiedObjectMapping) {
		this.protocolSpecifiedObjectMapping = protocolSpecifiedObjectMapping;
	}
	
	private void populateProtocolSpecifiedObjectMapping()
	{
		protocolSpecifiedObjectMapping.put(2, AccessControl.class);
		protocolSpecifiedObjectMapping.put(4, ConnectivityMonitoring.class);
		protocolSpecifiedObjectMapping.put(7, ConnectivityStatistics.class);
		protocolSpecifiedObjectMapping.put(3, Device.class);
		protocolSpecifiedObjectMapping.put(5, Firmware.class);
		protocolSpecifiedObjectMapping.put(6, Location.class);
		protocolSpecifiedObjectMapping.put(1, com.hackathon.lightweightm2m.objects.LWM2MServer.class);
		protocolSpecifiedObjectMapping.put(0, LWM2MServerAccessSecurity.class);
	}
	
	private void removeLWM2MClientAtLifetimeExpiry(String internalID)
	{
		//removeClient(internalID);
		clientLifetimeSchedulers.get(internalID).cancel();
		clientLifetimeSchedulers.remove(internalID);
	}
}
