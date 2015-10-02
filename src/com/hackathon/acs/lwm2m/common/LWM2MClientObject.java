package com.hackathon.acs.lwm2m.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LWM2MClientObject {
	public int id; 
	private List<LWM2MClientObjectInstance> objectInstances;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void addObjectInstance(LWM2MClientObjectInstance clientObjectInstance)
	{
		if( null == getObjectInstances() )
			setObjectInstances(new ArrayList<LWM2MClientObjectInstance>());
		if(!getObjectInstances().contains(clientObjectInstance))
			getObjectInstances().add(clientObjectInstance);

	}
	
	public void addObjectResource(short objectInstanceId, LWM2MResource resource)
	{
		boolean elementExist = false;
		for(LWM2MClientObjectInstance objectInstance : getObjectInstances())
		{
			if(objectInstance.getId() == objectInstanceId)
			{
				objectInstance.addLWM2MResource(resource);
				elementExist = true;
			}
		}
		if(!elementExist)
		{
			//throw 
		}
	}
	public List<LWM2MClientObjectInstance> getObjectInstances() {
		return objectInstances;
	}
	public void setObjectInstances(List<LWM2MClientObjectInstance> objectInstances) {
		this.objectInstances = objectInstances;
	}
	public void setResourceOptions(short instanceId,
			short resourceId, List<String> inputOptions) throws UnsupportedEncodingException {
		for(LWM2MClientObjectInstance objectInstance : getObjectInstances())
		{
			if(objectInstance.getId() == instanceId)
			{
				List<LWM2MResource> resourceList = objectInstance.getResourceList();
				for(LWM2MResource resource : resourceList)
				{
					if(resource.getResourceId() == resourceId)
					{
						resource.setOptions(inputOptions);
					}
				}
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[Client Object id=" + id + ", Object Instances="
				+ objectInstances + "]";
	}
	
	
}
