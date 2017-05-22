package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	static String[] str={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	
	public static String getMD5Code(String pwd){
		
		String resultStr=null;
		resultStr=new String(pwd);
		
		MessageDigest md;
		
		try {
			md=MessageDigest.getInstance("MD5");
			resultStr=byteToString(md.digest(pwd.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("MD5");
		}
		
		return resultStr;
	}

	private static String byteToString(byte[] digest) {
		// TODO Auto-generated method stub
		StringBuffer stringBuffer=new StringBuffer();
		for(int i = 0;i<digest.length;i++){
			stringBuffer.append(byteToArrayString(digest[i]));
		}
		return stringBuffer.toString();
	}

	private static String byteToArrayString(byte bs) {
		// TODO Auto-generated method stub
		int iret = bs;
		if(iret<0){
			iret+=256;
		}
		
		int iD1=iret/16;
		int iD2=iret%16;
		
		return str[iD1]+str[iD2];
	}
}
