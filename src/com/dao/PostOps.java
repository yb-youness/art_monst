package com.dao;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import com.dbutil.Conn;
import com.model.Post;
import com.mysql.cj.jdbc.Blob;

public class PostOps implements Ops  {

	 
	 public PostOps() {}
	 
	 
	// Add One Post
	@Override
	public int  Add_Post(Post post) {
	    int status =0;
		String Query = "INSERT INTO POST VALUES (NULL,?,?,?)";
	    try(Connection con = Conn.getConnection()){
	    	PreparedStatement pr = con.prepareStatement(Query);
	    	pr.setString(1, post.getTitle());
	    	pr.setString(2, post.getDesc());
	    	
	    	
	    	// Upload The File To A Directory 
	    	   pr.setBlob(3, post.getFile());
	    	//pr.setString(3, post.getPhoto());
	        status=pr.executeUpdate();
	    	
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return status;
	}
    
	// Get All Posts
	@Override
	public List<Post> Get_All_Posts() {
		String Query ="SELECT * FROM POST";
	  List<Post> Posts = new ArrayList<>();
	  try(Connection con = Conn.getConnection()){
		    PreparedStatement pr = con.prepareStatement(Query);
		    ResultSet rs = pr.executeQuery();
		    while(rs.next()) {
		    	Post post = new Post(); 
		    	post.setId(rs.getInt("Id"));
		    	post.setTitle(rs.getString("Title"));
		    	post.setDesc(rs.getString("Description"));
		    	
		    	// Get File In It Raw Format 
		         Blob fileraw= (Blob) rs.getBlob("Photo");
		         
		         InputStream inputStream = fileraw.getBinaryStream();
		         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		         byte[] buffer = new byte[4096];
		         int bytesRead = -1;
		          
		         while ((bytesRead = inputStream.read(buffer)) != -1) {
		             outputStream.write(buffer, 0, bytesRead);
		         }
		          
		         byte[] imageBytes = outputStream.toByteArray();
		          
		         String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		          
		         inputStream.close();
		         outputStream.close();
		         post.setPhoto(base64Image); // Set The image As String Format 
		                                     // Note add (data:image/jpg;base64,) to The View
		    	Posts.add(post);
		    }
	
	  } catch (SQLException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return Posts;
	}
	
	
	
    // Delete One Post  
	@Override
	public Boolean Del_Post(int postid) {
	    Boolean sucsess = false;
	    String Query ="DELETE FROM POST WHERE Id=?"; 
	    	    try(Connection con = Conn.getConnection()){
	    	   PreparedStatement pr = con.prepareStatement(Query);
	    	   pr.setInt(1, postid);
	    	   sucsess = pr.execute();
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sucsess;
	}
	
	
    // Update One Post 
	@Override
	public Boolean Update_Post(Post post) {
	    String Query ="UPDATE POST SET Title=?, Description=?,Photo=? WHERE Id=?"; 
		boolean sucess = false;
		try(Connection con = Conn.getConnection()){
	    	    
	    	 PreparedStatement pr = con.prepareStatement(Query);
	    	 
	    	 pr.setString(1,post.getTitle());
	    	 pr.setString(2,post.getDesc());
	    	// Upload The File To A Directory 
	    	   pr.setBlob(3, post.getFile());
	    	 pr.setInt(4, post.getId());
	    	 pr.executeUpdate();
	     }catch(SQLException ex) {
	    	 ex.printStackTrace();
	     }
		return sucess;
	}

	@Override
	public Post Get_one(int id) {
      String Query = "SElECT * FROM POST WHERE Id=?";
      Post post = new Post();  
      try(Connection con = Conn.getConnection()){
        	  PreparedStatement pr = con.prepareStatement(Query);
        	  pr.setInt(1, id);
        	  ResultSet rs = pr.executeQuery();
        	   while(rs.next()) {
        		   post.setId(rs.getInt("Id"));
        		   post.setTitle(rs.getString("Title"));
        		   post.setDesc(rs.getString("Description"));
        		   
        		// Get File In It Raw Format 
  		         Blob fileraw= (Blob) rs.getBlob("Photo");
  		         
  		         InputStream inputStream = fileraw.getBinaryStream();
  		         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  		         byte[] buffer = new byte[4096];
  		         int bytesRead = -1;
  		          
  		         while ((bytesRead = inputStream.read(buffer)) != -1) {
  		             outputStream.write(buffer, 0, bytesRead);
  		         }
  		          
  		         byte[] imageBytes = outputStream.toByteArray();
  		          
  		         String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        		   
        		   
        		  post.setPhoto(base64Image); 
        		   
        		   
        	   }  
        	  
        }catch(SQLException | IOException ex) {
        	 ex.printStackTrace();
        }

		return post;
	}
	
	
	
	
	
	public List<Post> Get_All_Posts_pagin(int page,int recordsPerPage) {
		
		String Query ="SELECT * FROM POST LIMIT ? , ?";        //3 Total Rows
	
		
		
	  List<Post> Posts = new ArrayList<>();
	  try(Connection con = Conn.getConnection()){
		    PreparedStatement pr = con.prepareStatement(Query);
		    pr.setInt(1, page); // the page Num 
		    pr.setInt(2, recordsPerPage); // The num of record to display per page 
		    ResultSet rs = pr.executeQuery();
		    
		  
		    
		    while(rs.next()) {
		    	Post post = new Post(); 
		    	post.setId(rs.getInt("Id"));
		    	post.setTitle(rs.getString("Title"));
		    	post.setDesc(rs.getString("Description"));
		    	
		    	// Get File In It Raw Format 
		         Blob fileraw= (Blob) rs.getBlob("Photo");
		         
		         InputStream inputStream = fileraw.getBinaryStream();
		         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		         byte[] buffer = new byte[4096];
		         int bytesRead = -1;
		          
		         while ((bytesRead = inputStream.read(buffer)) != -1) {
		             outputStream.write(buffer, 0, bytesRead);
		         }
		          
		         byte[] imageBytes = outputStream.toByteArray();
		          
		         String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		          
		         inputStream.close();
		         outputStream.close();
		         post.setPhoto(base64Image); // Set The image As String Format 
		                                     // Note add (data:image/jpg;base64,) to The View
		    	Posts.add(post);
		    }
	
	  } catch (SQLException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return Posts;
	}
	
	
	
		  
}

