package com.hackathon.acs.lwm2m.mediatype.tlv;

public class TLVObject {

	private LWM2MTlvTypes type;
	private int identifier;
	private int length;
	byte[] value;
	public TLVObject(LWM2MTlvTypes type, int identifier, int length,
			byte[] value) {
		super();
		this.type = type;
		this.identifier = identifier;
		this.length = length;
		this.value = value;
	}
	public LWM2MTlvTypes getType() {
		return type;
	}
	public void setType(LWM2MTlvTypes type) {
		this.type = type;
	}
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	
	
}
