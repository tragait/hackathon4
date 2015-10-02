package com.hackathon.acs.lwm2m.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LWM2MResource {

	String name;
	int resourceId;
	int resourceInstanceId;
	boolean isReadOperationAllowed = false;
	boolean isWriteOperationAllowed = false;
	boolean isExecuteOperationAllowed = false;
	boolean isMultipleInstancesAllowed = false;
	LWM2MResourceDataTypes datatype;
	int minValue;
	int maxValue;
	private Map<String, String> options = new HashMap<String, String>();

	public LWM2MResource(int resourceId) {
		this.resourceId = resourceId;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the resourceId
	 */
	public int getResourceId() {
		return resourceId;
	}


	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}


	/**
	 * @return the resourceInstanceId
	 */
	public int getResourceInstanceId() {
		return resourceInstanceId;
	}


	/**
	 * @param resourceInstanceId the resourceInstanceId to set
	 */
	public void setResourceInstanceId(int resourceInstanceId) {
		this.resourceInstanceId = resourceInstanceId;
	}


	/**
	 * @return the isReadOperationAllowed
	 */
	public boolean isReadOperationAllowed() {
		return isReadOperationAllowed;
	}


	/**
	 * @param isReadOperationAllowed the isReadOperationAllowed to set
	 */
	public void setReadOperationAllowed(boolean isReadOperationAllowed) {
		this.isReadOperationAllowed = isReadOperationAllowed;
	}


	/**
	 * @return the isWriteOperationAllowed
	 */
	public boolean isWriteOperationAllowed() {
		return isWriteOperationAllowed;
	}


	/**
	 * @param isWriteOperationAllowed the isWriteOperationAllowed to set
	 */
	public void setWriteOperationAllowed(boolean isWriteOperationAllowed) {
		this.isWriteOperationAllowed = isWriteOperationAllowed;
	}


	/**
	 * @return the isExecuteOperationAllowed
	 */
	public boolean isExecuteOperationAllowed() {
		return isExecuteOperationAllowed;
	}


	/**
	 * @param isExecuteOperationAllowed the isExecuteOperationAllowed to set
	 */
	public void setExecuteOperationAllowed(boolean isExecuteOperationAllowed) {
		this.isExecuteOperationAllowed = isExecuteOperationAllowed;
	}


	/**
	 * @return the isMultipleInstancesAllowed
	 */
	public boolean isMultipleInstancesAllowed() {
		return isMultipleInstancesAllowed;
	}


	/**
	 * @param isMultipleInstancesAllowed the isMultipleInstancesAllowed to set
	 */
	public void setMultipleInstancesAllowed(boolean isMultipleInstancesAllowed) {
		this.isMultipleInstancesAllowed = isMultipleInstancesAllowed;
	}


	/**
	 * @return the datatype
	 */
	public LWM2MResourceDataTypes getDatatype() {
		return datatype;
	}


	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(LWM2MResourceDataTypes datatype) {
		this.datatype = datatype;
	}


	/**
	 * @return the minValue
	 */
	public int getMinValue() {
		return minValue;
	}


	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}


	/**
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}


	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + resourceId;
		result = prime * result + resourceInstanceId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LWM2MResource other = (LWM2MResource) obj;
		if (resourceId != other.resourceId)
			return false;
		if (resourceInstanceId != other.resourceInstanceId)
			return false;
		return true;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(List<String> inputOptions) throws UnsupportedEncodingException {
		options = getResourceOptionsNameValuePair(inputOptions);
	}
	
	private Map<String, String> getResourceOptionsNameValuePair(List<String> inputOptions) throws UnsupportedEncodingException
	{
		Map<String, String> optionsNameValueMapping = new LinkedHashMap<String, String>();
		if(null == inputOptions || inputOptions.isEmpty())
		{
			return optionsNameValueMapping;
		}
		for(String option : inputOptions)
		{
			
	        int idx = option.indexOf("=");
	        optionsNameValueMapping.put(URLDecoder.decode(option.substring(0, idx), "UTF-8"), URLDecoder.decode(option.substring(idx + 1), "UTF-8"));
		}
		return optionsNameValueMapping;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[resourceId=" + resourceId
				+ ", resource Instance Id=" + resourceInstanceId + ", datatype="
				+ datatype + ", options=" + options + "]";
	}


}
