package com.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "card")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card {
  @Id
  private String id;
  private String cardNumber;
  private String type;
  private String vandor;
  @DBRef
  private User user;
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getVandor() {
    return vandor;
  }

  public void setVandor(String vandor) {
    this.vandor = vandor;
  }

  @Override
  public String toString() {
    return "Card [cardNumber=" + cardNumber + ", id=" + id + ", type=" + type + ", vandor=" + vandor + "]";
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}