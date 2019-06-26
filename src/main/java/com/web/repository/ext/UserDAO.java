package com.web.repository.ext;

import java.util.List;

import com.mongodb.DBRef;
import com.web.model.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
/**
 * 用户数据层扩展类（无接口）
 * @since 2019-06-26
 * @author zmr-zxls
 * @see UserService
 */
@Component
public class UserDAO {
  @Autowired
  private MongoTemplate mongoTemplate;
  /**
   * 查询用户的所有拥有的卡片
   * @param userid
   * @return
   */
  public List<Card> getCardsByUserid(String userid, Pageable pageable, Sort sort) {
    Query query = new Query();
    DBRef dbRef = new DBRef("user", userid);
    // 通过DBRef关联查询
    Criteria criteria = Criteria.where("user").is(dbRef);
    // 添加条件并去除user字段信息
    query.addCriteria(criteria).fields().exclude("user");
    if (pageable != null) {
      query.with(pageable);
    }
    if (sort != null) {
      query.with(sort);
    }
    // 返回Card类型，来自集合card
    return mongoTemplate.find(query, Card.class, "card");
  }
  public List<Card> getCardsByUserid(String userid) {
    return this.getCardsByUserid(userid, null, null);
  }
  /**
   * 统计用户卡片总数
   * @param userid
   * @return
   */
  public long count(String userid) {
    Query query = new Query();
    query.addCriteria(Criteria.where("user").is(new DBRef("user", userid)));
    return mongoTemplate.count(query, Card.class, "card");
  }
}