package com.hackathon.acs.lwm2m.common;

import java.util.Map;

import com.hackathon.lightweightm2m.objects.LWM2MStandardObjectInterface;

public class LWM2MSpecDefinedObjectDictonary {

	Map<String,  LWM2MStandardObjectInterface>  standard ; 
	private static final LWM2MSpecDefinedObjectDictonary Instance = new LWM2MSpecDefinedObjectDictonary();
	
	private LWM2MSpecDefinedObjectDictonary()
	{
		
	}
	
	public static LWM2MSpecDefinedObjectDictonary getInstance()
	{
		return Instance;
	}
}
