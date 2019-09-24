package com.web.repository;

import java.util.Set;

import com.web.model.Card;
import com.web.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {
  @Query(fields = "{user: 0}")
  Set<Card> findByUser(User user);
  // boolean exsitsCardNumber(String cardNumber);
  int countByCardNumber(String cardNumber);
  // boolean exsitsByCardAnUser(Card card, User user);
}