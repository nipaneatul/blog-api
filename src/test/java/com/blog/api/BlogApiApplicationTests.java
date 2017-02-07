package com.blog.api;

import static org.testng.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.blog.api.domain.Comment;
import com.blog.api.domain.Data;
import com.blog.api.domain.Post;
import com.blog.api.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



public class BlogApiApplicationTests {

	/**
	 * Test add user functionality
	 * @author atuln
	 */
	@Test(groups={"usergroup"})
	public void test_add_user_api() {
		HttpResponse<JsonNode> jsonResponse;
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		Data data = new Data();
		User user = data.addUserData();
		
		
		
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody.getString("firstName"), user.getFirstName());
			assertEquals(responseBody.getString("lastName"), user.getLastName());
			assertEquals(responseBody.getString("username"), user.getUsername());
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test GetAll user functionality
	 * @author atuln
	 */
	@Test(groups={"usergroup"})
	public void test_get_allusers_api_testing() {
		HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		Data data = new Data();
		User user = data.addUserData();
		
		User user1 = new User();
		user1.setFirstName("atul");
		user1.setLastName("nipane");
		user1.setUsername("atul");
		
		try {
			
			
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body1)
					  .asJson();
			JSONObject responseBody1 = jsonResponse.getBody().getObject();
			
			JsonNode body2 = new JsonNode(mapper.writeValueAsString(user1));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body2)
					  .asJson();
			JSONObject responseBody2 = jsonResponse.getBody().getObject();
			
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			//User[] users = response.getBody();
		    //JSONObject obj = jsonResponse.getBody().getObject();
			JSONArray jsonArray = jsonResponse.getBody().getArray();
			User user2 = new User();
			User user3 = new User();
			User user4 = new User();
			User[] users = new User[500000];
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject row = jsonArray.getJSONObject(i);
			    user2.setFirstName(row.getString("firstName"));
			    user2.setLastName(row.getString("lastName"));
			    user2.setId(row.getLong("id"));
			    user2.setUsername(row.getString("username"));
			    users[i] = user2;
			}
			
			user3 = users[0];
			user4 = users[1];
					
			assertNotNull(user3);
			assertNotNull(user4);
			//assertEquals(responseBody1.getString("firstName"),user3.getFirstName());
			//assertEquals(responseBody1.getString("lastName"),user3.getLastName());
			//assertEquals(responseBody1.getString("username"),user3.getUsername());
			//assertEquals(responseBody2.getString("firstName"),user4.getFirstName());
			//assertEquals(responseBody2.getString("lastName"),user4.getLastName());
			//assertEquals(responseBody2.getString("username"),user4.getUsername());
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Test update user functionality
	 * @author atuln
	 */
	@Test(groups={"usergroup"})
	public void test_update_user_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		Data data = new Data();
		User user = data.addUserData();
try {
			
			
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			Long id = jsonResponse.getBody().getObject().getLong("id");
			
			
			user.setFirstName("atul");
			user.setLastName("nipane");
			user.setUsername("atul");
			user.setId(id);

			JsonNode body1 = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.put("http://localhost:9080/blog/api/users/"+id)
			        .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body1)
					  .asJson();
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/"+id).header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			JSONObject obj = jsonResponse.getBody().getObject();	
			User u = new User();
			u.setFirstName(obj.getString("firstName"));
		    u.setLastName(obj.getString("lastName"));
		    u.setId(obj.getLong("id"));
		    u.setUsername(obj.getString("username"));
			
			assertEquals(u.getFirstName(),user.getFirstName());
			assertEquals(u.getLastName(),user.getLastName());
			assertEquals(u.getUsername(),user.getUsername());
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test delete user functionality
	 * @author atuln
	 */
	@Test(groups={"usergroup"})
	public void test_delete_user_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		Data data = new Data();
		User user = data.addUserData();

		
try {

			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			Long id = jsonResponse.getBody().getObject().getLong("id");
			user.setId(id);
			
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.delete("http://localhost:9080/blog/api/users/" + id)
			        .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body1)
					  .asJson();

			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/"+id).header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
	
			assertEquals(jsonResponse.getStatus(),500);
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Test get a user functionality
	 * @author atuln
	 */
	@Test(groups={"usergroup"})
	public void test_get_oneuser_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		Data data = new Data();
		User user = data.addUserData();

try {

			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			Long id = jsonResponse.getBody().getObject().getLong("id");
			user.setId(id);
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/"+id).header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
				
			User u = new User();
			JSONObject obj = jsonResponse.getBody().getObject();	
			
			u.setFirstName(obj.getString("firstName"));
		    u.setLastName(obj.getString("lastName"));
		    u.setId(obj.getLong("id"));
		    u.setUsername(obj.getString("username"));
			
			assertNotNull(u);
			assertEquals(u.getFirstName(), user.getFirstName());
			assertEquals(u.getLastName(), user.getLastName());
			assertEquals(u.getId(), user.getId());
			
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/users/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	

	
	/**
	 * Test get specific post functionality
	 * @author atuln
	 */
	@Test(groups={"postgroup"})
	public void test_get_specific_post_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		
		Data data = new Data();
		Post post = data.addPostData();

try {

			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			Long id = jsonResponse.getBody().getObject().getLong("id");
			post.setId(id);
			
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/"+id).header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
				
			JSONObject obj = jsonResponse.getBody().getObject();
			Post p = new Post();
			
			
			p.setId(obj.getLong("id"));
			p.setContent(obj.getString("content"));
			p.setType(obj.getString("type"));
			p.setUsername(obj.getString("username"));
			
			assertNotNull(p);
			assertEquals(p.getId(),post.getId());
			assertEquals(p.getContent(),post.getContent());
			assertEquals(p.getType(),post.getType());
			assertEquals(p.getUsername(),post.getUsername());
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test add post functionality
	 * @author atuln
	 */
	@Test(groups={"postgroup"})
	public void test_add_post_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		
		Data data = new Data();
		Post post = data.addPostData();
		
try {

			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			
			JSONObject responseBody = jsonResponse.getBody().getObject();
			
			assertNotNull(jsonResponse.getBody());
			//assertEquals(responseBody,post);
			assertEquals(responseBody.getString("content"),post.getContent());
			assertEquals(responseBody.getString("type"),post.getType());
			assertEquals(responseBody.getString("username"),post.getUsername());
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Test Get All post functionality
	 * @author atuln
	 */
	@Test(groups={"postgroup"})
	public void test_getall_post_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		Data data = new Data();
		Post post = data.addPostData();
		List<Comment> comments1 = new ArrayList<>();
			
		Post post1 = new Post();
		post1.setContent("content2");
		post1.setType("type2");
		post1.setUsername("atul2");
		
		Comment comment1 = new Comment();
		comment1.setText("commenttext1");
		comment1.setId(2L);
		comment1.setPostId(post1.getId());
		comments1.add(comment1);
		post1.setComments(comments1);

try {

	JsonNode body1 = new JsonNode(mapper.writeValueAsString(post));
	jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
			  .header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			  .body(body1)
			  .asJson();
	post.setId(jsonResponse.getBody().getObject().getLong("id"));
	
	JsonNode body2 = new JsonNode(mapper.writeValueAsString(post1));
	jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
			  .header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			  .body(body2)
			  .asJson();
	post1.setId(jsonResponse.getBody().getObject().getLong("id"));
	
	jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts").header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();

	JSONArray jsonArray = jsonResponse.getBody().getArray();
	Post post2 = new Post();
	Post post3 = new Post();
	Post post4 = new Post();
	Post[] posts = new Post[500000];
	for (int i = 0; i < jsonArray.length(); i++) {
	    JSONObject row = jsonArray.getJSONObject(i);
	    post2.setContent(row.getString("content"));
	    post2.setType(row.getString("type"));
	    post2.setId(row.getLong("id"));
	    post2.setUsername(row.getString("username"));
	    posts[i] = post2;
	}
	
	post3 = posts[0];
	post4 = posts[1];
			
	assertNotNull(post3);
	assertNotNull(post4);
	//assertEquals(post,post3);
	//assertEquals(post1,post4);
	jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test update post functionality
	 * @author atuln
	 */
	@Test(groups={"postgroup"})
	public void test_update_post_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		
		Data data = new Data();
		Post post = data.addPostData();

try {

	JsonNode body1 = new JsonNode(mapper.writeValueAsString(post));
	jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
			  .header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			  .body(body1)
			  .asJson();

	
	Long id = jsonResponse.getBody().getObject().getLong("id");
	
	
	post.setContent("content1-changed");
	post.setType("type1-changed");
	post.setUsername("atul-changed");
	post.setId(id);

	JsonNode body2 = new JsonNode(mapper.writeValueAsString(post));
	jsonResponse = Unirest.put("http://localhost:9080/blog/api/posts/"+id)
	        .header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			  .body(body2)
			  .asJson();
	post.setId(jsonResponse.getBody().getObject().getLong("id"));
	
	
	jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/"+id).header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
	JSONObject obj = jsonResponse.getBody().getObject();	
	Post p = new Post();
	
	p.setType(obj.getString("type"));
	p.setContent(obj.getString("content"));
	p.setUsername(obj.getString("username"));
	p.setId(obj.getLong("id"));
	
	assertEquals(p.getId(),post.getId());
	assertEquals(p.getContent(),post.getContent());
	assertNotEquals(p.getType(),post.getType());
	jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
			  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	

	/**
	 * Test delete post functionality
	 * @author atuln
	 */
	@Test(groups={"postgroup"})
	public void test_delete_post_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		
		Data data = new Data();
		Post post = data.addPostData();
		
try {

			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			Long id = jsonResponse.getBody().getObject().getLong("id");
			post.setId(id);
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.delete("http://localhost:9080/blog/api/users/" + id)
			        .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body1)
					  .asJson();
			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/"+id).header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
	
			assertEquals(jsonResponse.getStatus(),200);
						
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test error(400) after deletion
	 * @author atuln 
	 */
	@Test
	public void test_post_api() {
		HttpResponse<JsonNode> jsonResponse;
		List<Comment> comments = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		Data data = new Data();
		User user = data.addUserData();
		
		Post post = new Post();
		post.setContent("test-post-content-1");
		post.setType("test-post-type-1");
		post.setUsername(user.getUsername());
		//post.setUsername(user.getUsername());
		
		Comment comment1 = new Comment();
		Comment comment2 = new Comment();
		
		comment1.setText("comment1");
		comment1.setPostId(post.getId());
		
		comment2.setText("comment2");
		comment2.setPostId(post.getId());
		
		comments.add(comment1);
		comments.add(comment2);
		
		
		
		post.setComments(comments);
		
		
		
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.delete("http://localhost:8080/blog/api/post/"+post.getId())
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			assertEquals(responseBody.get("status"),400);

			
			jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Test to Add comment functionality
	 * @author atuln
	 */
	@Test(groups={"commentgroup"})
	public void test_add_comment_api() {
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		List<Comment> comments = new ArrayList<>();
		
		
		Post post = new Post();
		post.setContent("content1");
		post.setType("type1");
		post.setUsername("atul");
		
		
		
		Comment comment = new Comment();
		comment.setText("commenttext");
		comment.setId(1L);
		

		post.setComments(comments);
		
try {

			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			Long id = jsonResponse.getBody().getObject().getLong("id");
			post.setId(id);
			comment.setPostId(post.getId());
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(comment));
			
			//JsonNode body1 = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:9080/blog/api/posts/" + id+"/comments")
			        .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body1)
					  .asJson();
			
		JSONObject obj = jsonResponse.getBody().getObject();
		
		assertEquals(obj.get("text"),comment.getText());
		jsonResponse = Unirest.get("http://localhost:9080/blog/api/posts/removeall").header("accept", MediaType.APPLICATION_JSON_VALUE)
				  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE).asJson();
						
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
