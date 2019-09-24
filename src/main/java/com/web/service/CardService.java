package com.web.service;

import java.util.List;

import com.web.model.Card;
import com.web.model.User;
import com.web.repository.CardRepository;
import com.web.repository.ext.CardDAO;
import com.web.utils.SimplePage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CardService {
  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private CardDAO cardDao;
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
  public SimplePage<Card> getCards(String userid, Pageable pageable, Sort sort, String keywords) {
    // return cardRepository.findByUser(new User(userid));
    List<Card> cards = cardDao.findCardsByUserid(userid, pageable, sort, keywords);
    long total = cardDao.count(userid, keywords);
    return new SimplePage<Card>(cards, total, pageable.getPageSize(), pageable.getPageNumber() + 1);
  }

  public SimplePage<Card> getCards(String userid, Pageable pageable) {
    return getCards(userid, pageable, new Sort(Direction.DESC, "createTime"), null);
  }

  public SimplePage<Card> getCards(String userid, Pageable pageable, String keywords) {
    return getCards(userid, pageable, new Sort(Direction.DESC, "createTime"), keywords);
  }

  public Card getCardById(String id) {
    return cardRepository.findById(id).get();
  }

  public Card getCardById(String id, String userid) {
    return cardDao.getCard(id, userid);
  }

  public Boolean existCardByUserid(String cardId, String userId) {
    return cardDao.existsByCardIdAnUserid(cardId, userId);
  }
  /**
   * 更新card
   * @param card
   * @return
   */
  public Card updateCard(Card card) {
    // 设置用户
    // card.setUser(new User(userid));
    // return cardRepository.save(card);
    cardDao.update(card);
    return getCardById(card.getId());
  }
}