package com.hackathon.lightweightm2m.objects;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;


public class Device extends LWM2MStandardObjectInterface{

	public static final int OBJECT_ID = 3;
	
	public Device(){
		
		LWM2MResource resource = new LWM2MResource(0); 
		resource.setName("Manufacturer");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(1); 
		resource.setName("Model Number");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(2); 
		resource.setName("Serial Number");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(3); 
		resource.setName("Firmware Version");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(4); 
		resource.setName("Reboot");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(5); 
		resource.setName("Factory Reset");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(6); 
		resource.setName("Power Source Status");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMinValue(0);
		resource.setMaxValue(1);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(7); 
		resource.setName("Battery Level");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMultipleInstancesAllowed(true); 
		resource.setMinValue(0);
		resource.setMaxValue(100);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(8); 
		resource.setName("Memory Free");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(9); 
		resource.setName("Error Code");
		resource.setReadOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.IntegerValue);
		resource.setMultipleInstancesAllowed(true); 
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(10); 
		resource.setName("Reset Error Code");
		resource.setExecuteOperationAllowed(true);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(11); 
		resource.setName("Current Time");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.StringValue);
		resourceValueMap.put(resource, null);
		
		resource = new LWM2MResource(12); 
		resource.setName("Time Zone");
		resource.setReadOperationAllowed(true);
		resource.setWriteOperationAllowed(true);
		resource.setDatatype(LWM2MResourceDataTypes.FloatValue);
		resourceValueMap.put(resource, null);
	}

	@Override
	public int getObjectId() {
		return OBJECT_ID;
	}

}
