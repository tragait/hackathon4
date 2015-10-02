package com.hackathon.acs.lwm2m.common.uri;
public class InternalUriIdentifier
{
	public byte flag; // indicates the type of URI and which segments are set
	private String id;
	private String objectInstanceId;
	private String resourceId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getObjectInstanceId() {
		return objectInstanceId;
	}
	public void setObjectInstanceId(String objectInstanceId) {
		this.objectInstanceId = objectInstanceId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((objectInstanceId == null) ? 0 : objectInstanceId.hashCode());
		result = prime * result
				+ ((resourceId == null) ? 0 : resourceId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InternalUriIdentifier other = (InternalUriIdentifier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (objectInstanceId == null) {
			if (other.objectInstanceId != null)
				return false;
		} else if (!objectInstanceId.equals(other.objectInstanceId))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		return true;
	}
}