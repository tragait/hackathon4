package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class Firmware extends LWM2MStandardObjectInterface{

public static final int OBJECT_ID = 5;
	
	public Firmware() {
		
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("Package");
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1);
		resource.setName("Package URI");
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("Update");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("State");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMinValue(1);
		resource.setMaxValue(3);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4);
		resource.setName("UpdateSupportedObjects");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.BooleanValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5);
		resource.setName("UpdateResult");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
