package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.domain.User;
import com.blog.api.service.UserService;

@RestController
@RequestMapping(path="/users", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 
	 * @return - Gives list of all users
	 * @author atuln
	 */
	@RequestMapping(method=RequestMethod.GET)
	public List<User> list() {
		return userService.search();
	}
	
	
	/**
	 * 
	 * @param id
	 * @return - Gives user on basis of id
	 * @author atuln
	 */
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public User show(@PathVariable Long id) {
		return userService.getUser(id);
	}
	
	
	/**
	 * 
	 * @param updatedUser
	 * @return - Gives updated user
	 * @author atuln
	 */
	@RequestMapping(path="/{id}", method=RequestMethod.PUT)
	public User update(@Validated @RequestBody User updatedUser) {
		return userService.update(updatedUser);
	}
	
	/**
	 * 
	 * @param newUser
	 * @return - Gives user saved in system
	 * @author atuln 
	 */
	@RequestMapping(method=RequestMethod.POST)
	public User save(@Validated @RequestBody User newUser) {
		return userService.save(newUser);
	}
	
	/**
	 * 
	 * @param id
	 * Delete the user on id basis.
	 * @author atuln
	 */
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}
	
	/**
	 * Remove all the users from the system
	 * @author atuln
	 */
	@RequestMapping(path="removeall", method=RequestMethod.GET)
	public void removeall() {
		userService.removeall();;
	}

}
