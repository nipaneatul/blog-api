package com.blog.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.domain.Comment;
import com.blog.api.domain.Post;

@Service
public class CommentService {
	
	@Autowired
	PostService postService;
	
	@Autowired
	IdGenerator idGenerator;
	
	/**
	 * 
	 * @param comment
	 * @return the added comment (Function takes comment as input and
	 * add the comment to comment list
	 *  @author atuln
	 */
	public Comment addComment(Comment comment) {
		Long id = idGenerator.generate();
		Post post = postService.getPost(comment.getPostId());
		comment.setId(id);
		post.getComments().add(comment);
		return comment;
	}

}
