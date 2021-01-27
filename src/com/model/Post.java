package com.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.servlet.http.Part;

public class Post {
     private int id;
	 private String title;
	 private String desc;
	 private String Photo;
	 private InputStream file;

	// Constructors 
	 public Post() {}

	 public Post(String title, String desc, String photo) {
		super();
		this.title = title;
		this.desc = desc;
		this.Photo = photo;
		}

	 public Post(int id,String title, String desc, String photo) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.Photo = photo;
		}


	// Getter And Setter 
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getPhoto() {
		return Photo;
	}


	public void setPhoto(String photo) {
		Photo =photo;
	}
	
	
	 public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}


	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", desc=" + desc + ", Photo=" + Photo + "]";
	}
	 
	 
	public HashMap<String, String> validatePost(Part filepart) throws IOException{
		
		  HashMap<String,String> msgs = new  HashMap<String,String> ();
		    if(title.isEmpty()) { // Validate Email
		    	msgs.put("title", "Title Must Not Be Empty !");
		    }if(desc.isEmpty()) { // Validate Desc
		    	msgs.put("desc", "Description Must Not Be Empty !");
		    }
		    
		    InputStream inputStream=null;  // input stream of the upload file
		        
	                if(filepart.getSize()==0) {  
							 msgs.put("photo", "File Is Empty !");
			
	                	// this If The File Is Not empty 
	                }else {

						 if(!(filepart.getContentType().equals("image/jpeg")) && filepart !=null) { // Add Error To The List
							  msgs.put("photo", "Format not Suported only jpeg");
						 }else {
	            	    inputStream = filepart.getInputStream();
	            	      this.setFile(inputStream); // Set The File 
						 }
					
	                }
						
		    
		    
		  return msgs;
	}
	
	
	 
     
}
