package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class ConnectivityMonitoring extends LWM2MStandardObjectInterface{

public static final int OBJECT_ID = 4;
	
	public ConnectivityMonitoring() {
		
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("Network Bearer");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1);
		resource.setName("Available Network Bearer");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMultipleInstancesAllowed(true); 
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("Radio signal strength");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("Link Quality");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4);
		resource.setName("IP Address");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resource.setMultipleInstancesAllowed(true); 
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5);
		resource.setName("Parent IP Address");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(6);
		resource.setName("Link Utilization");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMinValue(0);
		resource.setMaxValue(100);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(7);
		resource.setName("APN");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resource.setMultipleInstancesAllowed(true); 
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(8);
		resource.setName("Cell ID");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(9);
		resource.setName("SMNC");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(10);
		resource.setName("SMCC");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
