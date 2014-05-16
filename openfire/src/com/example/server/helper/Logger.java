package com.example.server.helper;


public class Logger {
	private static final String TAG="Test";
	public static void println(Object object,Object args){
		if (object==null) {
			object="nothing";
		}
		else if (object instanceof String) {
			object=object.toString();
		}
		else {
			object=object.getClass().getSimpleName();
		}
		if (args==null) {
			args="nothing";
		}
		System.out.println(TAG+"【"+object.toString()+"】【"+args.toString()+"】");
	}
	
	public static void println(Object classes,String method,Object args){
		if (classes==null) {
			classes="nothing";
		}
		else if (classes instanceof String) {
			classes=classes.toString();
		}
		else {
			classes=classes.getClass().getSimpleName();
		}
		if (method==null) {
			method="nothing";
		}
		if (args==null) {
			args="nothing";
		}
		System.out.println(TAG+"【"+classes.toString()+"】【"+method+"】【"+args.toString()+"】");
	}
}
