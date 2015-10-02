package com.hackathon.acs.lwm2m.common;
public enum CoAPStatusEnum
{
  NO_ERROR(0),

  CREATED_2_01(65), // CREATED
  DELETED_2_02(66), // DELETED
  VALID_2_03(67), // NOT_MODIFIED
  CHANGED_2_04(68), // CHANGED
  CONTENT_2_05(69), // OK

  BAD_REQUEST_4_00(128), // BAD_REQUEST
  UNAUTHORIZED_4_01(129), // UNAUTHORIZED
  BAD_OPTION_4_02(130), // BAD_OPTION
  FORBIDDEN_4_03(131), // FORBIDDEN
  NOT_FOUND_4_04(132), // NOT_FOUND
  METHOD_NOT_ALLOWED_4_05(133), // METHOD_NOT_ALLOWED
  NOT_ACCEPTABLE_4_06(134), // NOT_ACCEPTABLE
  PRECONDITION_FAILED_4_12(140), // BAD_REQUEST
  REQUEST_ENTITY_TOO_LARGE_4_13(141), // REQUEST_ENTITY_TOO_LARGE
  UNSUPPORTED_MEDIA_TYPE_4_15(143), // UNSUPPORTED_MEDIA_TYPE

  INTERNAL_SERVER_ERROR_5_00(160), // INTERNAL_SERVER_ERROR
  NOT_IMPLEMENTED_5_01(161), // NOT_IMPLEMENTED
  BAD_GATEWAY_5_02(162), // BAD_GATEWAY
  SERVICE_UNAVAILABLE_5_03(163), // SERVICE_UNAVAILABLE
  GATEWAY_TIMEOUT_5_04(164), // GATEWAY_TIMEOUT
  PROXYING_NOT_SUPPORTED_5_05(165); // PROXYING_NOT_SUPPORTED

	private int intValue;
	private static java.util.HashMap<Integer, CoAPStatusEnum> mappings;
	private static java.util.HashMap<Integer, CoAPStatusEnum> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CoAPStatusEnum.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CoAPStatusEnum>();
				}
			}
		}
		return mappings;
	}

	private CoAPStatusEnum(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CoAPStatusEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}