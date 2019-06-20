package com.web.controller;

import com.web.model.User;
import com.web.service.UserService;
import com.web.utils.ApiResult;
import com.web.utils.FileUtils;
import com.web.utils.ApiResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Value("${upload.path}")
    private String path;
    /**
     * 用户列表视图
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String getUsers (Model model) {
        // return new ResponseEntity<Object>(userService.getUsers(), HttpStatus.OK);
        ArrayList<User> users = userService.getUserList();
        model.addAttribute("users", users);
        LOG.info("UserController response user/list.tpl");
        return "user/list";
    }
    /**
     * 用户登录
     * @param model
     * @return
     */
    @RequestMapping(value = "/toLogin")
    public String loginView (Model model) {
        return "user/login";
    }
    /**
     * 用户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public User userInfo (@RequestParam(value = "id", required = true) String id) {
        return userService.getUserById(id);
    }
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/users")
    @ResponseBody
    public ArrayList<User> getUseList () {
        return userService.getUserList();
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "delete_user/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> deleteUser (@PathVariable("id") String id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);
    }
    @PostMapping(value = "/regsiter")
    @ResponseBody
    public ApiResult registerUser(@RequestParam("name") String name, @RequestParam("password") String pwd) {
        boolean exist = userService.existEqualName(name);
        if (exist) {
            LOG.info("用户已存在");
            return ApiResult.fail("用户已存在");
        }
        User user = userService.registerUser(name, pwd);
        return ApiResult.success(user);
    }
    /**
     * 用户登录
     * @param name
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiResult login(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUser(name, password);
        if (user == null) {
            LOG.info("登录失败");
            return ApiResult.status(Status.LOGIN_FAILED);
        }
        // 获取session并自动不创建，由客户端返回
        HttpSession session = request.getSession(false);
        if (session == null) {
            // 创建session
            session = request.getSession(true);
            // session.setMaxInactiveInterval(10 * 1000);
            LOG.info("创建Session会话");
        }
        session.setAttribute("USERID", user.getId());
        // Cookie cookie = new Cookie("USER_ID", user.getId() + "");
        // cookie.setMaxAge(3600 * 24);
        // response.addCookie(cookie);
        return ApiResult.success(user);
    }
    /**
     * 返回用户个人中心
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/center/{id}")
    public String user (@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/index";
    }

    /**
     * 接受上传的文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult upload (@RequestParam("file") MultipartFile file) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");
            File localFile = new File(path + File.separator + uuid + FileUtils.getFileExtension(file.getOriginalFilename()));
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(localFile);
            os.write(file.getBytes());
            os.close();
        } catch (IOException e) {
            LOG.error(e.toString());
        }
        LOG.info("上传文件成功");
        return ApiResult.message(0, "上传文件成功");
    }
}
