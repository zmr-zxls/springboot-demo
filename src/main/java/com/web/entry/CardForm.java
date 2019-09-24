package com.web.entry;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.web.validator.annotation.CardTypeValidator;

// import org.hibernate.validator.constraints.CreditCardNumber;

public class CardForm implements Serializable{
  private static final long serialVersionUID = 1L;
  @CardTypeValidator(message = "没有该类型的卡片")
  @JsonProperty(value = "type")
  private String cardType;

  // @CreditCardNumber(message = "非法的银行卡号")
  @JsonProperty(value = "number")
  private String cardNumber;
  private String id;
  private String vendor;
  private String remarks;

  public CardForm() {
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
  
}