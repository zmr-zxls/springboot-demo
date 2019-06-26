package com.web.service;

import java.util.Set;

import com.web.model.Card;
import com.web.model.User;
import com.web.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
  @Autowired
  private CardRepository cardRepository;
  /**
   * 保存卡片
   * @param card 卡片
   * @return Card 保存后的卡片
   * @throws Exception 已存在该卡片，抛出异常
   */
  public Card save(Card card) throws Exception {
    // boolean isExist = cardRepository.exsitsByCardNumber(card.getCardNumber());
    int count = cardRepository.countByCardNumber(card.getCardNumber());
    if (count > 0) {
      throw new Exception("已存在该卡片");
    }
    return cardRepository.save(card);
  }
  /**
   * 根据用户id保存卡片，建立一对多的关系
   * @param userid 用户id
   * @param card 卡片
   * @return card
   * @throws Exception 已存在该卡片，抛出异常
   */
  public Card save(String userid, Card card) throws Exception {
    User user = new User();
    user.setId(userid);
    card.setUser(user);
    return save(card);
  }
  /**
   * 根据用户id，查询该用户的所有卡片
   * @param userid 用户id
   * @return Set<Card> 卡片集合
   */
  public Set<Card> getCards(String userid) {
    User user = new User();
    user.setId(userid);
    return cardRepository.findByUser(user);
  }
}