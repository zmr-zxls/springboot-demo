package com.web.controller;

import javax.servlet.http.HttpSession;

import com.web.config.GlobalConfig;
import com.web.model.Card;
import com.web.service.CardService;
import com.web.utils.ApiResult;
import com.web.validator.annotation.CardTypeValidator;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@Validated
public class CardController {
  @Autowired
  private CardService cardService;
  @GetMapping("/list")
  public ApiResult getCards(HttpSession session) {
    String userid = (String) session.getAttribute(GlobalConfig.COOKIE_ID);
    return ApiResult.success(cardService.getCards(userid));
  }
  @PostMapping("/save")
  public ApiResult save(HttpSession session,
    @RequestParam("cardType")  @CardTypeValidator(message = "没有该类型的卡片") String cardType,
    @RequestParam("vandor") String vandor,
    @RequestParam("cardNumber") @CreditCardNumber(message = "非法的银行卡号") String cardNumber) {
    Card card = new Card();
    card.setCardNumber(cardNumber);
    card.setType(cardType);
    card.setVandor(vandor);
    String userid = (String)session.getAttribute(GlobalConfig.COOKIE_ID);
    try {
      card = cardService.save(userid, card);
    } catch (Exception e) {
      return ApiResult.fail(e.getMessage());
    }
    return ApiResult.success(card);
  }
}