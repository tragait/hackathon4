package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class AccessControl extends LWM2MStandardObjectInterface{

public static final short OBJECT_ID = 2;
	
	
	public AccessControl() {
		
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("Object ID");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1);
		resource.setName("Object Instance ID");
		resource.setReadOperationAllowed(true);
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("ACL");
		resource.setReadOperationAllowed(true);
		resource.setReadOperationAllowed(true);
		resource.setMultipleInstancesAllowed(true); 
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("Access Control Owner");
		resource.setReadOperationAllowed(true);
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
	}


	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
