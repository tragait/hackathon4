package com.hackathon.acs.lwm2m.common.uri;

import com.hackathon.acs.lwm2m.common.Constants;

public class UriHandler {
	
	public static  InternalUriIdentifier createInternalUri(String uriPath)
	{
		InternalUriIdentifier uriP= new InternalUriIdentifier();
		if (uriPath == null)
		{
			return null;
		}
		String[] uriPathArray = uriPath.split("/");
		int length = uriPathArray.length;
		if(length <2)
			return null;
		int i = 1;
		String uriString = uriPathArray[i];
		if (Constants.REGISTRATION_URI_SEGMENT_LEN == uriString.length() && Constants.URI_REGISTRATION_SEGMENT.equalsIgnoreCase(uriString))
		{
			uriP.flag |= Constants.REGISTRATION_URI;
			i++;
			if(i>length-1)
				return uriP;
			else
				uriString = uriPathArray[i];
		}
		else if (Constants.BOOTSTRAP_URI_SEGMENT_LEN == uriPath.length() && Constants.URI_BOOTSTRAP_SEGMENT.equalsIgnoreCase(uriPath))
		{
			uriP.flag |= Constants.BOOTSTRAP_URI;
			i++;
			if(i<=length-1)
				return null;
			else
				return uriP;
		}
/*		if (Integer.parseInt(uriString) < 0 || Integer.parseInt(uriString) > Constants.LWM2M_MAX_ID)
		{
			return null;
		}
*/		uriP.setId(uriString);
		uriP.flag |= Constants.OBJECT_ID_URI;
		i++;

		if ((uriP.flag & Constants.LWM2M_URI_MASK_TYPE) == Constants.REGISTRATION_URI)
		{
			if (i<=length-1)
			{
				return null;
			}
			else
			return uriP;
		}
		if(i >= length)
			return uriP;
		
		//read object instance id
		uriString = uriPathArray[i];
		if (Integer.parseInt(uriString) < 0 || Integer.parseInt(uriString) > Constants.LWM2M_MAX_ID)
		{
			return null;
		}
		uriP.setObjectInstanceId(uriString);
		uriP.flag |= Constants.OBJECT_INSTANCE_ID_URI;
		i++;
		
		if(i >= length)
			return uriP;
		
		//read resource id
		uriString = uriPathArray[i];
		if (Integer.parseInt(uriString) < 0 || Integer.parseInt(uriString) > Constants.LWM2M_MAX_ID)
		{
			return null;
		}
		uriP.setResourceId(uriString);
		uriP.flag |= Constants.RESOURCE_ID_URI;
		i++;
		
		return uriP;
	}

		
}	
		
