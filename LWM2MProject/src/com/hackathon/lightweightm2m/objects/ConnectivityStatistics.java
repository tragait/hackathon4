package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class ConnectivityStatistics extends LWM2MStandardObjectInterface{

public static final int OBJECT_ID = 7;
	
	public ConnectivityStatistics() {
		
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("SMS Tx Counter");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1);
		resource.setName("SMS Rx Counter");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("Tx Data");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("Rx Data");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4);
		resource.setName("Max Message Size");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5);
		resource.setName("Average Message Size");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(6);
		resource.setName("StartorReset");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
		
		
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
