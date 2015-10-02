package com.hackathon.acs.lwm2m.mediatype.tlv;

public class TLVEncoder {

	int createHeader(int[] headerBytes, LWM2MTlvTypes tlvType, int identifier,
			int dataLength) { 

		

		headerBytes = new int[8]; 
//		header = headerBytes[header_size]; 
		
		int header_size = 5; 
		int[] header = new int[header_size]; 
		header[0] = 0;  
		
		switch (tlvType) { 
		case TLV_OBJECT_INSTANCE:
			header[0] = 0x00;
			System.out.println("1");
			headerBytes[7]=0;
			headerBytes[6]=0;

			break; 

		case TLV_RESOURCE_INSTANCE:
			header[0] = 0x40;
			System.out.println("2");
			headerBytes[7]=0;
			headerBytes[6]=1;
			break;
			
		
		case TLV_MULTIPLE_INSTANCE:
			header[0] = 0x80; 
			System.out.println("3");
			headerBytes[7]=1;
			headerBytes[6]=0;
			break; 

		case TLV_RESSOURCE:
			header[0] = 0xC0;
			System.out.println("4");
			headerBytes[7]=1;
			headerBytes[6]=1;
			break;

		default:
			System.out.println("5");
			break; 
 
		} 

			/*
			 *  identifier
			 */

			if (identifier > 0xFF) {
				header[0] = 0x20;
				header[1] = (identifier >> 8) & 0XFF;
				header[2] = identifier & 0XFF; 
				header_size += 1;
				System.out.println("id>0xFF");
				headerBytes[5]=1;

			
			} else {
				header[1] = identifier;
				
				System.out.println("id<0xFF");
				System.out.println(header[1]);
				headerBytes[5]=0;
							
			}

			/*
			 *  data length
			 */


			if (dataLength < 7) {
				header[0] = dataLength;
				System.out.println("data_len<7");
				headerBytes[4]=0;
				headerBytes[3]=0;
			}

			else if (dataLength <= 0xFF) {
				header[0] = 0x08;
				header[header_size] = dataLength;
				header_size += 1;
				System.out.println("data_len<=0xFF");
				headerBytes[4]=0;
				headerBytes[3]=1; 

				
			} else if (dataLength <= 0xFFFF) {
				header[0] = 0x10;
				header[header_size] = (dataLength >> 8) & 0XFF;
				header[header_size + 1] = dataLength & 0XFF;
				header_size += 2;
				System.out.println("data_len<=0xFFFF");
				headerBytes[4]=1;
				headerBytes[3]=0;

				
			} else if (dataLength <= 0xFFFFFF) {
				header[0] = 0x18;
				header[header_size] = (dataLength >> 16) & 0XFF;
				header[header_size + 1] = (dataLength >> 8) & 0XFF;
				header[header_size + 2] = dataLength & 0XFF;
				header_size += 3;
				System.out.println("data_len<=0xFFFFFF"); 
				headerBytes[4]=1;
				headerBytes[3]=1;

			}
		
			
		return (header_size);  
	} 
 
//	public void encodeTLV() {
//			
//		createHeader( 123, Type.TLV_RESOURCE_INSTANCE , 0x01, 5); 
//				  
//	} 
		
	int boolToTlv(boolean value, LWM2MTlvTypes tlvType,  int identifier, int dataLength)
	{
		int intData = value?1:0;
//		createHeader(tlvType, identifier, dataLength);
		return intToTlv( intData, tlvType, identifier, dataLength);  

	} 
		
	
	int intToTlv(int data, LWM2MTlvTypes tlvType,  int identifier, int dataLength)
	{
		int index = 0;
        int b[] = new int[30];
      		
		while (data != 0)
        {
            index++;
            b[index] = data % 2; 
            data = data / 2;
        }  
        
		int newIndex;
        for ( newIndex = index; newIndex > 0; newIndex--)
        {
 //           System.out.print(b[newIndex]);
     
        }
       
        createHeader(b, tlvType, identifier, dataLength); 
//        return opaqueToTlv(b[newIndex], tlvType, identifier, dataLength);
		return ( b[newIndex]);  
        			
	}    
	
	int[] opaqueToTlv(int[] data, LWM2MTlvTypes tlvType, int identifier, int dataLength)
	{   

		createHeader(data, tlvType, identifier, dataLength);
		return ( data); 
	} 
	
	byte[] stringToTlv(String data, LWM2MTlvTypes tlvType,  int identifier, int dataLength)
	{  
		byte[] array = data.getBytes(); 
       System.out.println("byte is" +array); 
       System.out.println("reached 1");
       //createHeader(  array, tlvType, identifier, dataLength); //in string byte[] to int[] conversion
       System.out.println("reached 2");
       return (array); 
	}   
		

	public static void main(String args[])
	{
		TLVEncoder obj = new TLVEncoder();
//		obj.encodeTLV();
				
		System.out.println("\ntest case for integer");
		obj.intToTlv(1242, LWM2MTlvTypes.TLV_OBJECT_INSTANCE, 0xFFFF, 3);
		System.out.println("\ntest case for integer completed...");
		
//		System.out.println("test case for boolean");
//		obj.boolToTlv(false, Type.TLV_RESOURCE_INSTANCE, 0xFF, 4);
//		System.out.println("test case for boolean completed...");
		
//		System.out.println("test case for opaque");
//		obj.opaqueToTlv(0, Type.TLV_RESOURCE_INSTANCE, 0x01, 5);
//		System.out.println("test case for opaque completed...");
			
//		System.out.println("\ntest case for string");
//		obj.stringToTlv("ayushi", Type.TLV_RESOURCE_INSTANCE,  0x01, 5);
//		System.out.println("test case for string completed...");

	}
}

