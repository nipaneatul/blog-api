package com.blog.api.domain;

import java.util.ArrayList;
import java.util.List;

public class Data {
	
	/**
	 * 
	 * @return - Added test data for user
	 * @author atuln
	 */
	public User addUserData() {
		User user = new User();
		user.setFirstName("Sarah");
		user.setLastName("Palin");
		user.setUsername("s.palin");
		return user;
	}
	
	/**
	 * 
	 * @return - Added test data for post
	 * @author atuln
	 */
	public Post addPostData() {
		List<Comment> comments = new ArrayList<>();
		Post post = new Post();
		post.setContent("content1");
		post.setType("type1");
		post.setUsername("atul");
		
		Comment comment = new Comment();
		comment.setText("commenttext");
		comment.setId(1L);
		comment.setPostId(post.getId());
		
		comments.add(comment);
		
		post.setComments(comments);
		return post;
	}

}
