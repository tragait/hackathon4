package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public class LWM2MServerAccessSecurity extends LWM2MStandardObjectInterface{
	public static final int OBJECT_ID = 0;
	
	public LWM2MServerAccessSecurity(){
		
		LWM2MResource resource = new LWM2MResource(0);
		resource.setName("LWM2M Server URI");
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1);
		resource.setName("Bootstrap Server");
		resource.setDatatype(LWM2MResourceDataTypes.BooleanValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2);
		resource.setName("Security Mode");
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMinValue(0);
		resource.setMaxValue(3);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3);
		resource.setName("Public Key or Identity");
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4);
		resource.setName("Server Public Key or Identity");
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5);
		resource.setName("Secret Key");
		resource.setDatatype(LWM2MResourceDataTypes.ByteValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(6);
		resource.setName("Short Server ID");
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMinValue(1);
		resource.setMaxValue(65535);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(7);
		resource.setName("ClientHoldOffTime");
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
