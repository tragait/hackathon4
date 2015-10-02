package com.hackathon.acs.lwm2mconnectorinbound.devicemanagement;

import java.awt.TrayIcon.MessageType;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.hackathon.acs.lwm2m.common.LWM2MClient;
import com.hackathon.acs.lwm2mconnectorinbound.server.LWM2MServer;

import ch.ethz.inf.vs.californium.coap.EndpointAddress;
import ch.ethz.inf.vs.californium.coap.Message;
import ch.ethz.inf.vs.californium.dtls.DTLSSession;
import ch.ethz.inf.vs.californium.dtls.ServerHandshaker;


public class DTLSHandShakerImpl  {
	
	public static void main(String[] args){
		
		//create a dtls session
		DTLSSession session = new DTLSSession(true);
		LWM2MClient client = LWM2MServer.getInstance().getClient("clientid");
		InetAddress inetAddress= null;
		try {
			inetAddress = InetAddress.getByName(client.getUriHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int port = Integer.valueOf(client.getUriPort());
		ServerHandshaker serverHandShaker = new ServerHandshaker(new EndpointAddress(inetAddress,port ), session); // endpointaddress, session
		//serverHandShaker.
		Message msg =new Message();
		}
}
