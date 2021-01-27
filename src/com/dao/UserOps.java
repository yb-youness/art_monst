package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.dbutil.Conn;
import com.model.User;

public class UserOps {

	// check Login 
	
	public Boolean checkLogin(User user) {
		Boolean exsite = false;
		String Query ="SELECT count(*) ,pass FROM admin WHERE name=? ";
		 try(Connection con = Conn.getConnection()){
			
			 PreparedStatement stm = con.prepareStatement(Query);
			                   stm.setString(1, user.getName());
			  ResultSet rs = stm.executeQuery();
			          
			     while(rs.next()) {
			    	 if(rs.getInt("count(*)")==1) {
			    		  //Get the Pass as Raw to run the Check
			    		 
			    		  String hashedP = rs.getString("pass");
			               /// Check For PassWord 
			    		  if(BCrypt.checkpw(user.getPass(),hashedP)){
			    			
			    			  exsite=true;
			    		  }
			    		     
			    	 }
			     }
			 
			 
		 } catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		return exsite;
	}
	
public static void main(String[] args) {
	    UserOps ops = new UserOps();
	          User u = new User();
	               u.setName("admin");
	               u.setPass("admin");
	           if( ops.checkLogin(u))
	        	      System.out.println("you are In ");
}
	
}
