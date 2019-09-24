package com.example.demo;

import com.web.Application;
import com.web.model.Card;
import com.web.model.User;
import com.web.service.CardService;
import com.web.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QueryTest {
  @Autowired
  private UserService userService;
  @Autowired
  private CardService cardService;

  // @Test
  // public void findCardByUser() {
  //   Set<Card> cards = cardService.getCards("5d0a1614d1d3f3044c880245");
  //   for (Card card : cards) {
  //     System.out.println(card.toString());
  //   }
  // }

  @Test
  public void saveCardTest() {
    Card card = new Card();
    User user = userService.getUserById("5d0a1614d1d3f3044c880245");
    card.setCardNumber("4343000424242023723");
    card.setType("存储卡");
    card.setVandor("中国工商银行");
    card.setUser(user);
    try {
      card = cardService.save(card);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(card);
  }

  // @Test
  // public void findCardByUserTest() {
  //   Set<Card> cards = cardService.getCards("5d0a1614d1d3f3044c880245");
  //   System.out.println(cards);
  // }
  
  // @Test
  // public void findCardsByUserid() {
  //   List<Card> cards = userService.getCards("5d0a1614d1d3f3044c880245");
  //   System.out.println(cards);
  // }
}