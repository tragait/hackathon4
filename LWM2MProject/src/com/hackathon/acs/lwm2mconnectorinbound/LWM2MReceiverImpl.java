package com.hackathon.acs.lwm2mconnectorinbound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.registries.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.registries.OptionNumberRegistry;
import ch.ethz.inf.vs.californium.endpoint.resources.LocalResource;
import ch.ethz.inf.vs.californium.util.Properties;

import com.hackathon.acs.lwm2m.common.Constants;
import com.hackathon.acs.lwm2m.common.uri.InternalUriIdentifier;
import com.hackathon.acs.lwm2m.common.uri.UriHandler;
import com.hackathon.acs.lwm2mconnectorinbound.bootstrap.BootstrapHandlerResource;
import com.hackathon.acs.lwm2mconnectorinbound.registration.RegistrationHandler;
import com.hackathon.acs.lwm2mconnectorinbound.reporting.InformationReportingHandler;
import com.hackathon.acs.lwm2mconnectorinbound.server.LWM2MReceiver;

public class LWM2MReceiverImpl implements LWM2MReceiver{
	

	private static final int THREAD_POOL_SIZE = Properties.std.getInt("THREAD_POOL_SIZE");;
	private final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	
	Map<String, LocalResource> rootResource = new HashMap<String, LocalResource>();
	
	
	public LWM2MReceiverImpl() {
		rootResource.put("BOOTSTRAP", new BootstrapHandlerResource());
		rootResource.put("REGISTRATION", new RegistrationHandler());
	}

	@Override
	public void packetReceived(Request packet) {
		System.out.println("[" + this.getClass().getName() + "] recieved request.");
        packet.prettyPrint();
        if (packet.isProxyUriSet()) {
			packet.respondAndSend(CodeRegistry.RESP_PROXYING_NOT_SUPPORTED);
		} 
        else
        execute(packet);
	}

	public void execute(final Request request) {

		// check if request exists
		if (request != null) {
			
			if(request.getCode() == CodeRegistry.RESP_CHANGED && request.hasOption(OptionNumberRegistry.OBSERVE))
			{
				InformationReportingHandler reportingHandler = new InformationReportingHandler();
				reportingHandler.handleObservationNotification(request);
				return;
			}

			final LocalResource resource = getResource(request);
			// check if resource available
			if (resource != null) {

				request.setResource(resource);

				System.out.println(String.format("Dispatching execution: %s", request.getUriPath()));

				threadPool.submit(new Runnable() {

					@Override
					public void run() {
						// invoke request handler of the resource
						try {
							request.dispatch(resource);
						} catch (Exception e) {
							System.out.println(String.format("Resource handler %s crashed: %s", resource.getClass().getSimpleName(), e.getMessage()));
							request.respondAndSend(CodeRegistry.RESP_INTERNAL_SERVER_ERROR);
							return;
						}
						// check if resource did generate a response
						if (request.getResponse() != null) {

						}
						
						// send response from this thread
						request.sendResponse();
					}
				});

			} else {
				// resource does not exist
				System.out.println(String.format("Cannot find resource: %s", request.getUriPath()));

				request.respondAndSend(CodeRegistry.RESP_NOT_FOUND);
			}
			
		}
	}
	


	public LocalResource getResource(Request request) {
		LocalResource resource = null;
		InternalUriIdentifier uriIdentifier;
		try
		{
			uriIdentifier = UriHandler.createInternalUri(request.getUriPath());
		}
		catch(NumberFormatException e)
		{
			return null;
		}
		if( null == uriIdentifier)
		{
			return null;
		}
		if((uriIdentifier.flag & Constants.LWM2M_URI_MASK_TYPE) == Constants.BOOTSTRAP_URI)
		{
			resource = rootResource.get("BOOTSTRAP");
		}
		else if((uriIdentifier.flag & Constants.LWM2M_URI_MASK_TYPE) == Constants.REGISTRATION_URI)
		{
			resource = rootResource.get("REGISTRATION");
		}
		return resource;
	}
}
