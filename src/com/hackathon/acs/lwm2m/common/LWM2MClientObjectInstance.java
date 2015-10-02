package com.hackathon.acs.lwm2m.common;

import java.util.ArrayList;
import java.util.List;

public class LWM2MClientObjectInstance {

	private short id;
	private List<LWM2MResource> resourceList = new ArrayList<LWM2MResource>();

	public LWM2MClientObjectInstance(short id)
	{
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public short getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(short id) {
		this.id = id;
	}
	/**
	 * @return the resourceList
	 */
	public List<LWM2MResource> getResourceList() {
		return resourceList;
	}
	/**
	 * @param resourceList the resourceList to set
	 */
	public void setResourceList(List<LWM2MResource> resourceList) {
		this.resourceList = resourceList;
	}
	
	public void addLWM2MResource(LWM2MResource resource)
	{
		if( null == resourceList )
	
			resourceList = new ArrayList<LWM2MResource>();
	if(!resourceList.contains(resource))
		resourceList.add(resource);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[object instance id=" + id + ", resourceList="
				+ resourceList + "]";
	}
	
	
}
	
