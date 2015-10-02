package com.hackathon.acs.lwm2mconnectorinbound.server;

import ch.ethz.inf.vs.californium.coap.Request;

public interface LWM2MReceiver {
	public void packetReceived(Request packet);
}
