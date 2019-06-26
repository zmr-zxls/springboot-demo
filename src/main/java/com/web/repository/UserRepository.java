package com.web.repository;

import java.util.ArrayList;
import java.util.Optional;

import com.web.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  // @Query("{ 'username': ?0, 'password': ?1}")
  public ArrayList<User> findByUsernameAndPassword(String username, String password);
  public User findOneByUsernameAndPassword(String username, String password);
  public User findOneByUsername(String name);
  @Query(value = "{'_id': ?0}", fields = "{username: 1, email: 1}")
  public Optional<User> findUserById(String id);
}