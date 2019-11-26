package com.web.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "card")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card {
  @Id
  private String id;
  /**
   * 卡片编号
   */
  private String cardNumber;
  /**
   * 卡片类型
   */
  private String type;
  /**
   * 卡片名（银行提供商名称）
   */
  private String vandor;
  /**
   * 备注
   */
  private String remarks;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 卡片状态
   */
  private int status;
  /**
   * 卡片状态枚举类
   * 0 - 有效
   * -1 - 已挂失
   * 1 - 已失效
   */
  public enum Status {
    NORMAL(0, "有效"),
    LOSE(1, "已挂失"),
    DESTROYED(-1, "已失效");
    int value;
    String message;
    Status(int value, String message) {
      this.value = value;
      this.message = message;
    }
    public int getValue() {
      return value;
    }
    public String getMessage() {
      return message;
    }
    public static Status of(int status) throws Exception {
      if (status == 0) {
        return Status.NORMAL;
      }
      if (status == 1) {
        return Status.LOSE;
      }
      if (status == -1) {
        return Status.DESTROYED;
      }
      throw new Exception("状态无效");
    }
  }

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
    return "Card [cardNumber=" + cardNumber + ", id=" + id + ", type=" + type + ", vandor=" + vandor + ", remarks=" + remarks + "]";
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}