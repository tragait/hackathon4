package com.hackathon.acs.lwm2m.mediatype.json;

public class JsonData {

private String n;
private String sv;
private String v;
private String t;
private String bv;

public String getN() {
return n;
}

public void setN(String n) {
this.n = n;
}

public String getSv() {
return sv;
}

public void setSv(String sv) {
this.sv = sv;
}

public String getV() {
return v;
}

public void setV(String v) {
this.v = v;
}

public String getT() {
return t;
}

public void setT(String t) {
this.t = t;
}

public String getBv() {
return bv;
}

public void setBv(String bv) {
this.bv = bv;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "JsonData [n=" + n + ", sv=" + sv + ", v=" + v + ", t=" + t
			+ ", bv=" + bv + "]";
}


}