package com.blog.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.domain.User;

@Service
public class UserService {
	
	List<User> users;
	
	@Autowired
	IdGenerator idGenerator;
	
	@PostConstruct
	private void init() {
		users = new ArrayList<>();
	}
	
	public List<User> search() {
		return users;
	}
	
	/**
	 * 
	 * @param id
	 * @return - Provides user depending upon id
	 * @author atuln
	 */
	public User getUser(Long id) {
		User user = users.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst().get();
		return user;
	}
	
	
	/**
	 * 
	 * @param newUser
	 * @return - Save the newly created user.
	 * @author atuln
	 */
	public User save(User newUser) {
		User user = new User();
		user.setId(idGenerator.generate());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setUsername(newUser.getUsername());
		users.add(user);
		return user;
	}
	
	/**
	 * 
	 * @param updatedUser
	 * @return - Changing User attributes First name, Last name
	 * and username
	 * @author atuln
	 */
	public User update(User updatedUser) {
		User user = getUser(updatedUser.getId());
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setUsername(updatedUser.getUsername());
		return user;
	}
	
	/**
	 * 
	 * @param id
	 * Delete the existing user by id
	 * @author atuln
	 */
	public void delete(Long id) {
		User user = getUser(id);
		users.remove(user);
	}
	
	
	/**
	 * Delete all the users
	 * @author atuln
	 */
	public void removeall() {
		init();
	}
	
}
