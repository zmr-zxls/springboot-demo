package com.web.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;
import com.web.config.GlobalConfig;
import com.web.entry.CardForm;
import com.web.model.Card;
import com.web.service.CardService;
import com.web.utils.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/card")
@Validated
public class CardController {
  @Autowired
  private CardService cardService;

  @GetMapping("/list")
  public ApiResult getCards(
    HttpSession session,
    @RequestParam(value = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
    @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize,
    @RequestParam(value = "status", required = false) Integer status
  ) {
    String userid = (String) session.getAttribute(GlobalConfig.COOKIE_ID);
    return ApiResult.success(cardService.getCards(userid, status, PageRequest.of(pageIndex - 1, pageSize)));
  }

  @GetMapping("/search")
  public ApiResult getCards(
    HttpSession session,
    @RequestParam(value = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
    @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize,
    @RequestParam(value = "keywords", defaultValue = "") String keywords
  ) {
    String userid = (String) session.getAttribute(GlobalConfig.COOKIE_ID);
    return ApiResult.success(cardService.getCards(userid, PageRequest.of(pageIndex - 1, pageSize), keywords));
  }

  /**
   * 创建卡片
   * 
   * @param session
   * @param cardForm
   * @return
   */
  @PostMapping("/save")
  public ApiResult save(HttpSession session, @RequestBody @Valid CardForm cardForm) {
    Card card = new Card();
    card.setCardNumber(cardForm.getCardNumber());
    card.setType(cardForm.getCardType());
    card.setVandor(cardForm.getVendor());
    card.setRemarks(cardForm.getRemarks());
    card.setCreateTime(new Date());
    String userid = (String) session.getAttribute(GlobalConfig.COOKIE_ID);
    try {
      card = cardService.save(userid, card);
    } catch (Exception e) {
      return ApiResult.fail(e.getMessage());
    }
    return ApiResult.success(card);
  }

  /**
   * 获取卡片详情
   * 
   * @param id
   * @return
   */
  @GetMapping("/detail")
  public ApiResult detail(
    String id,
    @SessionAttribute("USERID") String userid
  ) {
    Card card = cardService.getCardById(id, userid);
    if (card == null) {
      return ApiResult.fail("您未有此卡片");
    }
    return ApiResult.success(cardService.getCardById(id, userid));
  }

  /**
   * 修改卡片
   * 
   * @param cardForm
   * @return
   */
  @PutMapping("/update")
  public ApiResult edit(@RequestBody @Valid CardForm cardForm) {
    Card card = new Card();
    card.setId(cardForm.getId());
    card.setCardNumber(cardForm.getCardNumber());
    card.setType(cardForm.getCardType());
    card.setVandor(cardForm.getVendor());
    card.setRemarks(cardForm.getRemarks());
    return ApiResult.success(cardService.updateCard(card), "修改成功");
  }
  /**
   * 修改卡片状态
   * @param json
   * @param userid
   * @return
   */
  @PutMapping("/changeStatus")
  public ApiResult changeStatus(
    @RequestBody JSONObject json,
    @SessionAttribute("USERID") String userid
  ) {
    try {
      Card.Status cs = Card.Status.of(json.getIntValue("status"));
      cardService.updateCardStatus(json.getString("id"), userid, cs.getValue());
      return ApiResult.success(null, "卡片" + cs.getMessage());
    } catch (Exception e) {
      return ApiResult.fail(e.getMessage());
    }
  }
}