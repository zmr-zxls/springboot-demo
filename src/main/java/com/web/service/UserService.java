package com.web.service;

import java.util.ArrayList;

import com.web.model.User;
import com.web.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    public ArrayList<User> getUserList () {
        return (ArrayList<User>) userRepository.findAll();
    }
    public User getUser(String name, String password) {
        // ArrayList<User> lst = userRepository.findByUsernameAndPassword(name, password);
        // return lst.isEmpty() ? null : lst.get(0);
        return userRepository.findOneByUsernameAndPassword(name, password);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
	public User registerUser(String name, String pwd) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
		return this.saveUser(user);
    }
    public boolean existEqualName(String name) {
        return userRepository.findOneByUsername(name) != null;
    }
}
