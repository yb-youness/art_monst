package com.model;

import java.util.HashMap;

public class User {
  
	private String name;
	private String email;
	private String pass;
	                    //You can Add Role If You Want To Setup Comment System 
	
	public User () {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public HashMap<String,String> validateUser(){
		HashMap<String,String>mgs =new HashMap<String,String>();
		
		    if(name.isEmpty()) {
		    	mgs.put("login", "Name or Email  is Required To Login !");
		    }if(pass.isEmpty()) {
		    	mgs.put("pass", "PassWord Is Required !");
		    }
	
		return mgs;
		
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", pass=" + pass + "]";
	}
	
	
	

	
}
