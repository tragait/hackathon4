package com.hackathon.lightweightm2m.objects;

import java.util.HashMap;
import java.util.Map;

import com.hackathon.acs.lwm2m.common.LWM2MResource;
import com.hackathon.acs.lwm2m.common.LWM2MResourceDataTypes;

public abstract class LWM2MStandardObjectInterface {

	Map<LWM2MResource, Object> resourceValueMap = new HashMap<LWM2MResource, Object>();

	public Map<LWM2MResource, Object> getResourcesAndTheirValues() {
		return resourceValueMap;
	}

	public Object getValueForResourceId(int id, Integer instanceId) {
		LWM2MResource dummyLwm2mResource = new LWM2MResource(id);
		if(null != instanceId)
		{
			dummyLwm2mResource.setResourceInstanceId(instanceId.intValue());
		}

		return resourceValueMap.get(new LWM2MResource(id));
		
	}

	public LWM2MResourceDataTypes getResourceDataTypeForResourceId(int id, Integer instanceId) {
		LWM2MResource dummyLwm2mResource = new LWM2MResource(id);
		if(null != instanceId)
		{
			dummyLwm2mResource.setResourceInstanceId(instanceId.intValue());
		}
		for (LWM2MResource resource :resourceValueMap.keySet())
		{
			if(resource.equals(dummyLwm2mResource))
			{
				return resource.getDatatype();
			}
		}
		return null;
	}

	public void setResourceValue(int id, Integer instanceId, Object value) {
		LWM2MResource dummyLwm2mResource = new LWM2MResource(id);
		if(null != instanceId)
		{
			dummyLwm2mResource.setResourceInstanceId(instanceId.intValue());
		}
		for (LWM2MResource resource :resourceValueMap.keySet())
		{
			if(resource.equals(dummyLwm2mResource))
			{
				resourceValueMap.put(resource, value);
				return;
			}
		}
		
	}
	
	public abstract int getObjectId();
}
