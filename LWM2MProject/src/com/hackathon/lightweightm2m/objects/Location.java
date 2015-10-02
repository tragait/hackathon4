package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class Location extends LWM2MStandardObjectInterface{

public static final int OBJECT_ID = 6;
	
	public Location() {
		
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("Latitude");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.FloatValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1);
		resource.setName("Longitude");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.FloatValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("Altitude");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.FloatValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("Uncertainity");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.FloatValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4);
		resource.setName("Velocity");
		resource.setReadOperationAllowed(true);
//		resource.setDatatype(LWM2MResourceDataTypes.FloatValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5);
		resource.setName("Timestamp");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
