package com.hackathon.acs.lwm2m.mediatype.tlv;

import java.util.Arrays;

import com.hackathon.lightweightm2m.objects.LWM2MStandardObjectInterface;

public class Lwm2mTLVManagement {

	private static LWM2MStandardObjectInterface object;
	/**
	 * Method to decode incoming payload as byte array
	 * 
	 * @param inputPayload
	 */

	public static void decodeTLV(byte[] in)
	{
		int initialLength = 0;
		while(initialLength< in.length)
		{
			initialLength = initialLength + decode(Arrays.copyOfRange(in, initialLength,in.length));
		}
	}
	private static int decode(byte[] in)
	{
		LWM2MTlvTypes type=null;
		int identifierValue;
		int dataLength=0;

		Integer initialDataIndex= 2;
		
		//read type value for first byte
		/**
		 * Identify type of Identifier using bits 6-7.
		 * 00= Object Instance in which case the Value contains one or more Resource TLVs
		 * 01= Resource Instance with Value for use within a multiple Resource TLV
		 * 10= multiple Resource, in which case the Value contains one or more Resource Instance TLVs
		 * 11= Resource with Value
		 */
		switch (in[0] & 0xC0)
		{
		case 0x00:
			type = LWM2MTlvTypes.TLV_OBJECT_INSTANCE;
			break;
		case 0x40:
			type = LWM2MTlvTypes.TLV_RESOURCE_INSTANCE;
			break;
		case 0x80:
			type = LWM2MTlvTypes.TLV_MULTIPLE_INSTANCE;
			break;
		case 0xC0:
			type = LWM2MTlvTypes.TLV_RESSOURCE;
			break;

		}
		//read identifier value
		/**
		 * Identify the Length of the Identifier field using bit 5.
		 * 0=The Identifier field of this TLV is 8 bits long
		 * 1=The Identifier field of this TLV is 16 bits long
		 */
		if ((byte)(in[0] & 0x20) == 0x20)
		{
				initialDataIndex += 1;
				identifierValue = in[1] << 8 + in[2];
		}
		else
		{
			identifierValue = in[1];
		}
		/**
		 * Bit 4-3: Indicates the type of Length.
		 * 00=No length field, the value immediately follows the Identifier field in is of the length indicated by Bits 2-0 of this field
		 * 01 = The Length field is 8-bits and Bits 2-0 MUST be ignored. traverse byte array to length field and retrieve data length
		 * 10 = The Length field is 16-bits and Bits 2-0 MUST be ignored. traverse byte array to length field  and retrieve data length
		 * 11 = The Length field is 24-bits and Bits 2-0 MUST be ignored.traverse byte array to length field and retrieve data length
		 */
		switch (in[0] & 0x18)
		{
		case 0x00:
			// no length field
			dataLength = in[0] & 0x07;
			break;
		case 0x08:
			// length field is 8 bits long
			dataLength = in[initialDataIndex];
			initialDataIndex += 1;
			break;
		case 0x10:
			// length field is 16 bits long
			dataLength = in[initialDataIndex] << 8 + in[initialDataIndex + 1];
			initialDataIndex += 2;
			break;
		case 0x18:
			// length field is 24 bits long
			dataLength = in[initialDataIndex] << 16 + in[initialDataIndex + 1] << 8 + in[initialDataIndex + 2];
			initialDataIndex += 3;
			break;
		}

		System.out.println("type: ");
		switch (type)
		{
		case TLV_OBJECT_INSTANCE:
			System.out.print("TLV_OBJECT_INSTANCE\r\n");
			break;
		case TLV_RESOURCE_INSTANCE:
			System.out.print("TLV_RESOURCE_INSTANCE\r\n");
			break;
		case TLV_MULTIPLE_INSTANCE:
			System.out.print("TLV_RESOURCE_MULTIPLE_INSTANCE\r\n");
			break;
		case TLV_RESSOURCE:
			System.out.print("TLV_RESSOURCE\r\n");
			break;
		default:
			System.out.printf("unknown (%d)\r\n", type);
			break;
		}
		System.out.println("id: "+ identifierValue);
		System.out.println("data ( bytes): "+ dataLength);
		if (type == LWM2MTlvTypes.TLV_OBJECT_INSTANCE || type == LWM2MTlvTypes.TLV_MULTIPLE_INSTANCE)
		{
			decodeTLV(Arrays.copyOfRange(in, initialDataIndex,initialDataIndex+dataLength));
		}

		return initialDataIndex+dataLength;
	}

	public void decodeTLVForGivenObject(LWM2MStandardObjectInterface object)
	{
		this.object = object;
	}
}

