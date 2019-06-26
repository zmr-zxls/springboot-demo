package com.web.controller;

import com.web.model.Card;
import com.web.service.CardService;
import com.web.utils.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {
  @Autowired
  private CardService cardService;
  @GetMapping("/list")
  public ApiResult getCards(@RequestParam("id") String userid) {
    return ApiResult.success(cardService.getCards(userid));
  }
  @PostMapping("/save")
  public ApiResult save(@RequestParam("userid") String userid, @RequestParam("cardType") String cardType,
    @RequestParam("vandor") String vandor, @RequestParam("number") String cardNumber) {
    Card card = new Card();
    card.setCardNumber(cardNumber);
    card.setType(cardType);
    card.setVandor(vandor);
    try {
      card = cardService.save(userid, card);
    } catch (Exception e) {
      return ApiResult.fail(e.getMessage());
    }
    return ApiResult.success(card);
  }
}