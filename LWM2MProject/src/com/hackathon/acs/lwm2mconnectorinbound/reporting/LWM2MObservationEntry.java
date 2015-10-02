package com.hackathon.acs.lwm2mconnectorinbound.reporting;

import java.util.HashMap;
import java.util.Map;

import com.hackathon.acs.lwm2m.common.uri.InternalUriIdentifier;

public class LWM2MObservationEntry {

	private String id;
	private LWM2MObservationRequestStatus requestStatus;
	private InternalUriIdentifier uriIdentifier;
	private Map<Integer, byte[]> dataValue = new HashMap<Integer, byte[]>();
	private boolean isCancellationRequested = false;
	
	public String  getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LWM2MObservationRequestStatus getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(LWM2MObservationRequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}
	public InternalUriIdentifier getUriIdentifier() {
		return uriIdentifier;
	}
	public void setUriIdentifier(InternalUriIdentifier uriIdentifier) {
		this.uriIdentifier = uriIdentifier;
	}
	public Map<Integer, byte[]> getDataValue() {
		return dataValue;
	}
	public void setDataValue(Map<Integer, byte[]> dataValue) {
		this.dataValue = dataValue;
	}
	public boolean isCancellationRequested() {
		return isCancellationRequested;
	}
	public void setCancellationRequested(boolean isCancellationRequested) {
		this.isCancellationRequested = isCancellationRequested;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((uriIdentifier == null) ? 0 : uriIdentifier.hashCode());
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
		LWM2MObservationEntry other = (LWM2MObservationEntry) obj;
		if (uriIdentifier == null) {
			if (other.uriIdentifier != null)
				return false;
		} else if (!uriIdentifier.equals(other.uriIdentifier))
			return false;
		return true;
	}
	
}
