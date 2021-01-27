package com.dao;

import java.util.List;

import com.model.Post;

public interface Ops {
  int Add_Post(Post post); // Add Post 
  List <Post> Get_All_Posts(); // Get All 
  Post Get_one(int id);  // Get one Post 
  Boolean Del_Post(int postid); // Del  one Post
  Boolean Update_Post(Post post);// Update One Post
}
