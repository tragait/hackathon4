package com.hackathon.acs.lwm2m.common;

public class Constants {
	
	//Registration Interface constants
	public static final String  URI_REGISTRATION_SEGMENT="rd";
	public static final String CLIENT_ENDPOINT = "ep";
	public static final String CLIENT_LIFETIME = "lt";
	public static final String CLIENT_VERSION = "lwm2m";
	public static final String CLIENT_BINDING_MODE = "b";
	public static final String CLIENT_SMS_MSISDN = "sms";
	
	public static final int MAX_LOCATION_LENGTH = 10;
	public static final String QUERY_TEMPLATE ="ep=";
	public static final int QUERY_LENGTH =3;
	public static final char QUERY_DELIMITER ='&';
	public static final int LWM2M_URI_FLAG_DM    = 0x10;
	public static final int REGISTRATION_URI =0x20;
	public static final int BOOTSTRAP_URI =0x40;
	public static final int OBJECT_ID_URI =0x04;
	public static final int OBJECT_INSTANCE_ID_URI =0x02;
	public static final int RESOURCE_ID_URI =0x01;
	public static final long LWM2M_MAX_ID =0xFFFF;
	public static final int LWM2M_URI_MASK_TYPE =0xF0;
	public static final int LWM2M_URI_MASK_ID =0x0F;
	public static final int REGISTRATION_URI_SEGMENT_LEN = 2;
	public static final int BOOTSTRAP_URI_SEGMENT_LEN = 2;
	public static final String URI_BOOTSTRAP_SEGMENT = "bs";

}
