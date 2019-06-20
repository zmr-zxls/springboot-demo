package com.web.repository;

import java.util.ArrayList;

import com.web.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  // @Query("{ 'username': ?0, 'password': ?1}")
  public ArrayList<User> findByUsernameAndPassword(String username, String password);
  public User findOneByUsernameAndPassword(String username, String password);
  public User findOneByUsername(String name);
}