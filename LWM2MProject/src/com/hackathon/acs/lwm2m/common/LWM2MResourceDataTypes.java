package com.hackathon.acs.lwm2m.common;
public enum LWM2MResourceDataTypes
{
	StringValue,
	IntegerValue,
	FloatValue,
	BooleanValue,
	ByteValue;

	public int getValue()
	{
		return this.ordinal();
	}

	public static LWM2MResourceDataTypes forValue(int value)
	{
		return values()[value];
	}
}