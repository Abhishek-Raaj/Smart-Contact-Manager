package com.smartmanager.dao;

public class Msgstr {
   private String msg;
   private String name;
public String getmsg() {
	return msg;
}
public void setmsg(String msg) {
	this.msg = msg;
}
public Msgstr(String msg, String name) {
	super();
	this.msg = msg;
	this.name = name;
}
public String getClassName() {
	return name;
}
public void setClassName(String name) {
	this.name = name;
}
}
