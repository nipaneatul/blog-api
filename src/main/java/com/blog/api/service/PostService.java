package com.blog.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.domain.Comment;
import com.blog.api.domain.Post;

@Service
public class PostService {
	
	List<Post> posts;
	
	@Autowired
	IdGenerator idGenerator;
	
	@Autowired
	CommentService commentService;
	
	@PostConstruct
	public void init() {
		posts = new ArrayList<>();
	}
	/**
	 * 
	 * @param params 
	 * @return - Returns the List of Post for the specific user
	 * @author atuln
	 */
	public List<Post> search(Map<String, String> params) {
		String username = params.get("username");
		List<Post> result;
		if (username != null) {
			result = getUserPosts(username);
		} else {
			result = posts;
		}
		return result;
	}
	
	/**
	 * 
	 * @param username
	 * @return - Returns the List of Post for the mentioned user
	 * @author atuln
	 */
	public List<Post> getUserPosts(String username) {
		List<Post> userPosts = posts.stream()
				.filter(p -> p.getUsername().equals(username))
				.collect(Collectors.toList());
		return userPosts;
	}
	
	/**
	 * 
	 * @param id
	 * @return - Get the post depending upon its id.
	 * @author atuln
	 */
	public Post getPost(Long id) {
		Post post = posts.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst().get();
		return post;
	}
	
	
	/**
	 * 
	 * @param newPost
	 * @return - Add a new post
	 * @author atuln
	 */
	public Post add(Post newPost) {
		Long postId = idGenerator.generate();
		List<Comment> comments = newPost.getComments();
		
		newPost.setId(postId);
		posts.add(newPost);
		
		if (comments != null) {
			comments.stream()
			.forEach(c -> {
				c.setId(idGenerator.generate());
				c.setPostId(postId);
			});
		}
		return newPost;
	}
	
	
	/**
	 * 
	 * @param updatedPost
	 * @return - Updates the post content value
	 * @author atuln
	 */
	public Post update(Post updatedPost) {
		Post post = getPost(updatedPost.getId());
		post.setContent(updatedPost.getContent());
		return post;
	}
	
	
	/**
	 * 
	 * @param id
	 * Delete the depending upon the id.
	 * @author atuln
	 */
	public void delete(Long id) {
		Post post = getPost(id);
		posts.remove(post);
	}
	
	/**
	 * Delete all the posts
	 * @author atuln
	 */
	public void removeAll() {
		init();
	}
	

}
