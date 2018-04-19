package com.common;

import java.text.SimpleDateFormat;


public class Utility {
		
	public static String jsAlertHref(String msg, String url){
		String result="<script>";
		result +="alert('" + msg + "');";
		result += "location.href='" +url+ "';";
		result +="</script>";
		
		return result;		
	}
	
	public static String jsAlertBack(String msg){
		String result="<script>";
		result +="alert('" + msg + "');";
		result += "history.back();";
		result +="</script>";
		
		return result;	
	}
	
	public static String getSysDate(){
		String nowdate="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		nowdate = sdf.format(new java.util.Date());
		return nowdate;		
	}
	
	
}//class








