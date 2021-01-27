package com.controller;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.dao.PostOps;
import com.dao.UserOps;
import com.model.Post;
import com.model.User;


// mail Imports

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


// For Acsepting Form Uplaod Data 

@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PostOps ops;     
 
  
    public Controller() {
        super();
    }
    @Override
    public void init() throws ServletException {
    	 ops = new PostOps();
    
    	super.init();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String  action = request.getServletPath();
		 
		       switch (action) {
			case "/":
			    //To Do Get all posts returns Raw Data 
			
				 /// All_Posts(request, response,"index"); //  All_Posts(request, response,"index");  no pagination old method 
				 
					// Pagination
				All_Posts_paginate(request, response, "index");
				break;
			case "/posts":
			    //To Do Get all posts returns Raw Data 
			
				 /// All_Posts(request, response,"index"); //  All_Posts(request, response,"index");  no pagination old method 
				 
					// Pagination
				All_Posts_paginate(request, response, "index");
				break;	
			case "/setlang":
			        //  Cookies Variables
				  String lang = request.getParameter("lang");
				 Cookie ck=new Cookie("curentlang",URLEncoder.encode(lang,"UTF-8"));
		    	  response.addCookie(ck);
				   response.sendRedirect(request.getHeader("referer")); // request.getHeader("referer")  send To the prev Url 
				break;		
			case "/dashbord":
			    //To Do Get all posts returns Raw Data And display In A Table 
				// Pagination
				if(request.getSession().getAttribute("user")!=null)
				 All_Posts(request, response,"admin");
				else
				 response.sendRedirect(request.getContextPath()+"/login"); 
				break;		
			case "/add":   
				Fowrd(request, response, "add");
				break;		
			case "/addlogic":
				    Add_Logic(request, response); 
				break;
			case "/showPost":
			    //show  one Post 	
				   One_Post(request, response);
				break;	
				
			case "/edit":
			    //show The Edit form 
				bind_Post(request, response);
				break; 
			case "/update":
			      Update_Logic(request, response);
			    // Update Logic
				 int id = Integer.parseInt(request.getParameter("id"));
				break; 	
			case "/remove":
			    // Remove
				 Delte_Post(request, response);
				break;
			case "/about":
				Fowrd(request, response,"about");
				break;
			case "/login":
				Fowrd(request, response,"login");
				break;	
			case "/loginlogic":
			      checkUser(request, response);
				break;	
			case "/loogout":
			      request.getSession().invalidate(); // Remove Session
			      response.sendRedirect(request.getContextPath()+"/");    // Send to the Home Location 
				break;
			case "/contactus":
			        Fowrd(request, response, "contactform");
				break;
				
				
			case "/contactuslogic":
		         // email Stuf Working Just some Tweaks 
			  	sendMailhtml(request, response);
			break;		
			default:
				// 404 page 
				Fowrd(request, response, "404");
				break;
			}
 	   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
	
	//============================================================================================================================
	    // Utils Methods 
	// Foword req ,resp 
	protected void Fowrd(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException  {
		RequestDispatcher rqd = request.getRequestDispatcher("Views/"+path+".jsp");
		                  rqd.forward(request, response);
	}
	
	 // Sucsess Msg 
	private void displayMsg(HttpServletRequest request, HttpServletResponse response,String msgC , String msg) {
		 
      	 HttpSession session = request.getSession(false);
    	 session.setAttribute("errorC", msgC);
    	session.setAttribute("Msg", msg);

	}
	//============================================================================================================================
	                         /////  Post Logic 
	
	// Get All Posts old Method
	private void All_Posts(HttpServletRequest request, HttpServletResponse response,String path ) throws ServletException, IOException  {
		          request.setAttribute("AllPosts",ops.Get_All_Posts());
		            Fowrd(request, response, path);
	}
	
	
	// Get All Posts paginate
	private void All_Posts_paginate(HttpServletRequest request, HttpServletResponse response,String path ) throws ServletException, IOException  {
		 int page=1;
		 
		   int recordsPerPage = 3;  // This must come from web.xml
          if(request.getParameter("page") != null)
              page = Integer.parseInt(request.getParameter("page"));
          int paging =(page-1)*recordsPerPage; // Calculate the limt
            // This To display records
          request.setAttribute("record", ops.Get_All_Posts_pagin(paging,recordsPerPage));
		    /// Calculate the num of page 
          int noOfRecords = ops.Get_All_Posts().size(); // count of records
          int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage); // Calculate the num of page 
          
          
          request.setAttribute("noOfPages", noOfPages);
          request.setAttribute("currentPage", page);
          Fowrd(request, response, path);
	}
	
	//Add Post
	private void Add_Logic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
          Post p = new Post();
         
           p.setTitle(request.getParameter("title"));
           p.setDesc(request.getParameter("body"));
           // get The file from the Request 
           Part filepart = request.getPart("photo");
           
           HashMap<String,String> errorsmsg =p.validatePost(filepart);  
                // Chek If valid  title + body + photo
              if(errorsmsg.isEmpty()) {
            	   ops.Add_Post(p);
            	   // Diplay (Using The Session) Sucsess Msg + fowrd
            	   displayMsg(request,response,"alert-success","Post Add Whith success !");
                   response.sendRedirect(request.getContextPath()+"/dashbord");
              }else{ 
                       // Diplay Error msg Msg + fowrd
           
                    request.setAttribute("Errors", errorsmsg);
                    All_Posts(request, response, "admin");
            	       }
	}
	 
	// Delete Post 
	private void Delte_Post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		 int id = Integer.parseInt(request.getParameter("id"));
		  ops.Del_Post(id);
		  displayMsg(request,response,"alert-info","Post Deleted Whith success !");
		 response.sendRedirect("dashbord");
	}
	//  Bind Post  
	private void bind_Post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		   int id = Integer.parseInt(request.getParameter("id"));
		   request.setAttribute("AllPosts",ops.Get_All_Posts()); // Return All the Posts not A good Solution   !!!
		   request.setAttribute("OldPost",ops.Get_one(id));         // get one Post 
		   Fowrd(request, response, "admin");
	}
	
	
	// Update (Logic) Post
	private void Update_Logic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		int id = Integer.parseInt(request.getParameter("id")); // get The Id  
		
		Post p = new Post();
           p.setId(id);
           p.setTitle(request.getParameter("title"));
           p.setDesc(request.getParameter("body"));
           // get The file from the Request 
           Part filepart = request.getPart("photo"); 
           
           HashMap<String,String> errorsmsg =p.validatePost(filepart);  
           
           if(errorsmsg.isEmpty()) {
        	   ops.Update_Post(p);
        	   displayMsg(request,response,"alert-warning","Post Updated Whith success !");
               response.sendRedirect("dashbord");
           }else {
              // Diplay Error msg Msg + fowrd
              request.setAttribute("Errors",  errorsmsg);
              All_Posts(request, response, "admin"); // Pug to Fixe title
                                                       // Solution => use Session 
           }  
	}
	
	
	
	
	
	 
	// Details On A Post 

	private void One_Post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		 int id = Integer.parseInt(request.getParameter("id"));          
	 	request.setAttribute("Post",ops.Get_one(id));
		           Fowrd(request, response, "details");
  
	}
	

	//============================================================================================================================
    /////  User Logic 
	
	private void checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		UserOps uops = new UserOps();
		     User user = new User();
		          user.setName(request.getParameter("login"));
		          user.setPass(request.getParameter("pass"));
		           
		          if( !user.validateUser().isEmpty()) { // Check if empty + validation
		            	  request.setAttribute("loginerrors",user.validateUser());  
		            	  Fowrd(request, response, "login");
		              }else {// check info + validation 
		            	   
		            	     if(uops.checkLogin(user)) {
		                          response.getWriter().println("You Are  in"); 
		                             // Remove prev session
		                              request.getSession().invalidate();
		                              HttpSession session =request.getSession(true);
		                                          session.setAttribute("user", user.getName());
		                                          response.sendRedirect("dashbord");
		                          
				            	    // Set The Session
				            	    
		            	     }else {
		            	    	  request.setAttribute("ErrorCrid","Invalid Cridentiale!");  
				            	  Fowrd(request, response, "login");
		            	     }
		            	   
		              }
		            
		          
	}
	
	
		
		
	///Sending Email logic
	
	
	private void sendMailhtml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		  // Note This Send Only text Not Formated Html 
		
			// Activate less Secure App To Send Email Using SMTP (Gmail Server ) =>       https://myaccount.google.com/security
			 String username = "xxxxx@gmail.com"; // Change this
	         String password = "xxxxxx";          // Change This

	        // Get data Send 
	        
	        String from= request.getParameter("from");
	        String object = request.getParameter("obj");
	        String content = request.getParameter("cont");
	        
	        
	        System.out.println(from);
	        
	        Properties prop = new Properties();
	        prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        try {
	        	

	            Message message = new MimeMessage(session);
	            
	        
	            
	            message.setFrom(new InternetAddress(from)); // Sender this wont work  hardcode the email in the content
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse("ecomercnet@gmail.com") // Reciver 
	            );
	            message.setSubject(object); // Subject
	            message.setContent("<h1>"+content+"</h1>","text/html");  // Content
	            
	        

	            Transport.send(message);

	              // Redirect or Fowrd 
	                 // If redriect Use Session or msgMethod
	              response.sendRedirect(request.getContextPath()+"/contactus?suc=true");
	               

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
    }

}
