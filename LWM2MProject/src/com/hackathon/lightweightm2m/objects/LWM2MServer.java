package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class LWM2MServer extends LWM2MStandardObjectInterface{
	public static final int OBJECT_ID = 1;
	
	public LWM2MServer() {
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("LWM2M Server URI");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1); 
		resource.setName("Lifetime");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("Security Mode");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("Public Key or Identity");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4);
		resource.setName("Secret Key");
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5);
		resource.setName("Short Server ID");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(6);
		resource.setName("Default Minimum Period");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
	
		resource = new LWM2MResource(7);
		resource.setName("Default Maximum Period");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(8);
		resource.setName("Disable");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(9);
		resource.setName("Disable Timeout");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(10);
		resource.setName("Notification Storing When Disabled or Offline");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.BooleanValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(11);
		resource.setName("Binding Preference");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMinValue(0);
		resource.setMaxValue(3);
		resourceValueMap.put(resource, null);
		
		
		resource = new LWM2MResource(12);
		resource.setName("Registration Update Trigger");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
