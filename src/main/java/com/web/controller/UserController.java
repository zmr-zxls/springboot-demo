package com.web.controller;

import com.web.model.User;
import com.web.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {
    private static UserService userService = new UserService();

    @RequestMapping(value = "/list")
    public String getUsers (Model model) {
        // return new ResponseEntity<Object>(userService.getUsers(), HttpStatus.OK);
        ArrayList<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public ResponseEntity userInfo (@RequestParam(value = "id", required = true) int id) {
        return new ResponseEntity(userService.getUserById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "delete_user/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteUser (@PathVariable("id") int id) {
        boolean flag = userService.deleteUser(id);
        String msg = flag ? "删除成功" : "删除失败";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}")
    public String user (@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/index";
    }
}
