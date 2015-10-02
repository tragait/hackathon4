package com.hackathon.acs.lwm2mconnectorinbound.client.test;

import java.io.IOException;

import com.hackathon.acs.lwm2mconnectorinbound.utils.IOUtil;

public class IOUtilTest {

	public static void main(String[] args) throws IOException {
		byte[] readFile = IOUtil.readFile("C:\\ggtest\\OMA-TS-LightweightM2M-V1_0-20130522-D.docx");
		System.out.println(readFile);
	}
}
