package com.web.repository.ext;

import java.util.List;
import java.util.regex.Pattern;

import com.mongodb.DBRef;
import com.web.model.Card;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CardDAO {
  @Autowired
  private MongoTemplate mongoTemplate;
  /**
   * 更新
   * @param card
   * @return 更新的条数
   */
  public long update(Card card) {
    Query query = new Query();
    ObjectId id = new ObjectId(card.getId());
    query.addCriteria(Criteria.where("_id").is(id));

    Update update = new Update();
    update.set("type", card.getType());
    update.set("cardNumber", card.getCardNumber());
    update.set("vandor", card.getVandor());
    update.set("remarks", card.getRemarks());

    return mongoTemplate.updateFirst(query, update, Card.class).getModifiedCount();
  }
  /**
   * 根据用户id查询card
   * @param userid
   * @param sort2
   * @param pageable
   * @return
   */
  public List<Card> findCardsByUserid(String userid, Pageable pageable, Sort sort) {
    return findCardsByUserid(userid, pageable, sort, null);
  }
  /**
   * 支持分页查询，排序，模糊查询
   * @param userid
   * @param pageable
   * @param sort
   * @param keywords 模糊查询关键字
   * @return List<Card>卡片列表
   */
  public List<Card> findCardsByUserid(String userid, Pageable pageable, Sort sort, String keywords) {
    Query query = new Query();
    DBRef ref = new DBRef("user", userid);
    query.addCriteria(Criteria.where("user").is(ref)).fields().exclude("user");
    if (sort != null) {
      query.with(sort);
    }
    if (pageable != null) {
      query.with(pageable);
    }
    searchWrapper(query, keywords);
    return mongoTemplate.find(query, Card.class, "card");
  }
  public void searchWrapper(Query query, String keywords) {
    if (keywords != null && !"".equals(keywords)) {
      Pattern pattern = Pattern.compile("^.*?" + keywords + ".*?$", Pattern.CASE_INSENSITIVE);
      Criteria criteria = new Criteria();
      criteria.orOperator(
        Criteria.where("cardNumber").regex(pattern),
        Criteria.where("vandor").regex(pattern),
        Criteria.where("type").regex(pattern),
        Criteria.where("remarks").regex(pattern)
      );
      query.addCriteria(criteria);
    }
  }
  /**
   * 统计用户卡片总数
   * @param userid
   * @return
   */
  public long count(String userid) {
    return count(userid, null);
  }

  /**
   * 统计用户卡片总数
   * @param userid
   * @return
   */
  public long count(String userid, String keywords) {
    Query query = new Query();
    query.addCriteria(Criteria.where("user").is(new DBRef("user", userid)));

    searchWrapper(query, keywords);

    return mongoTemplate.count(query, Card.class, "card");
  }

  public Boolean existsByCardIdAnUserid(String cardId, String userId) {
    return getCard(cardId, userId) == null;
  }

  /**
   * 根据用户id和卡片id，获取卡片
   * @param id
   * @param userid
   * @return
   */
  public Card getCard(String id, String userid) {
    Query query = new Query();
    Criteria c1 = Criteria.where("_id").is(id);
    Criteria c2 = Criteria.where("user").is(new DBRef("user", userid));
    query.addCriteria(c1).addCriteria(c2);
    return mongoTemplate.findOne(query, Card.class, "card");
  }
}