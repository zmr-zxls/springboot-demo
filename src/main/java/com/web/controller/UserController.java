package com.web.controller;

import com.web.model.User;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${upload.path}")
    private String path;

    @RequestMapping(value = "/list")
    public String getUsers (Model model) {
        // return new ResponseEntity<Object>(userService.getUsers(), HttpStatus.OK);
        ArrayList<User> users = userService.getUsers();
        model.addAttribute("users", users);
        System.out.println("UserController response user/list.tpl");
        return "user/list";
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public ResponseEntity userInfo (@RequestParam(value = "id", required = true) int id) {
        System.out.println("getUserInfo");
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity upload (@RequestParam("file") MultipartFile file) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");
            File localFile = new File(path + File.separator + uuid + this.getFileExtension(file.getOriginalFilename()));
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(localFile);
            os.write(file.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap map = new HashMap();
        map.put("msg", "上传成功");
        map.put("code", 0);
        map.put("succeed", true);
        return new ResponseEntity(map, HttpStatus.OK);
    }
    public String getFileExtension (String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}
