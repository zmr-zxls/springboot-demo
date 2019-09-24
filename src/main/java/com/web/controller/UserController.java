package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.web.config.GlobalConfig;
import com.web.entry.UserForm;
import com.web.model.User;
import com.web.service.UserService;
import com.web.utils.ApiResult;
import com.web.utils.FileUtils;
import com.web.utils.ApiResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

/**
 * 用户Controller
 * @Validated是参数校验
 */
@Controller
@RequestMapping("/user")
@Validated
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
    public ApiResult userInfo (HttpSession session) {
        String userid = (String) session.getAttribute(GlobalConfig.COOKIE_ID);
        return ApiResult.success(userService.getUserById(userid));
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
    // @PostMapping(value = "/regsiter")
    // @ResponseBody
    // public ApiResult registerUser(@RequestParam("name") @NotEmpty(message = "用户姓名不能为空") String name,
    //     @RequestParam("password") @NotEmpty(message = "用户密码不能为空") String pwd) {
    //     return ApiResult.success(userService.registerUser(name, pwd));
    // }
    /**
     * 用户注册
     * spingboot参数自动绑定
     * <p> @Valid 对绑定参数进行校验
     * @param userForm
     * @return
     */
    @PostMapping(value = "/regsiter")
    @ResponseBody
    public ApiResult registerUser(@Valid UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        return ApiResult.success(userService.registerUser(user));
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
    public ApiResult login(@RequestBody JSONObject json,
        HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUser(json.getString("name"), json.getString("password"));
        if (user == null) {
            LOG.info("登录失败");
            return ApiResult.status(Status.LOGIN_FAILED);
        }
        // 获取session并自动不创建，由客户端返回
        HttpSession session = request.getSession(false);
        if (session == null) {
            // 创建session
            session = request.getSession(true);
            session.setMaxInactiveInterval(24 * 60 * 60);
            LOG.info("创建Session会话");
        }
        session.setAttribute("USERID", user.getId());
        // 存储基本信息到客户端中
        JSONObject object = new JSONObject();
        object.put("id", user.getId());
        object.put("name", user.getUsername());
        try {
            // cookie只能存ascll码，需要将特殊字符进行url编码
            Cookie cookie = new Cookie("USER", URLEncoder.encode(object.toJSONString(), "UTF-8"));
            // 需要设置路径，浏览器才会匹配对应路径发生对应的cookie
            cookie.setPath("/");
            // 禁止js修改cookie
            cookie.setHttpOnly(true);
            /**
             * maxAge 可以为正数，表示此cookie从创建到过期所能存在的时间，以秒为单位，此cookie会存储到客户端电脑，以cookie文件形式保存，不论关闭浏览器或关闭电脑，直到时间到才会过期。
                可以为负数，表示此cookie只是存储在浏览器内存里，只要关闭浏览器，此cookie就会消失。maxAge默认值为-1。
                还可以为0，表示从客户端电脑或浏览器内存中删除此cookie。）
                如果maxAge属性为正数，则表示该Cookie会在maxAge秒之后自动失效。浏览器会将maxAge为正数的Cookie持久化，即写到对应的Cookie文件中。无论客户关闭了浏览器还是电脑，只要还在maxAge秒之前，登录网站时该Cookie仍然有效。
                如果maxAge为负数，则表示该Cookie仅在本浏览器窗口以及本窗口打开的子窗口内有效，关闭窗口后该Cookie即失效。maxAge为负数的Cookie，为临时性Cookie，不会被持久化，不会被写到Cookie文件中。Cookie信息保存在浏览器内存中，因此关闭浏览器该Cookie就消失了。Cookie默认的maxAge值为-1。
                如果maxAge为0，则表示删除该Cookie。Cookie机制没有提供删除Cookie的方法，因此通过设置该Cookie即时失效实现删除Cookie的效果。失效的Cookie会被浏览器从Cookie文件或者内存中删除。
                response对象提供的Cookie操作方法只有一个添加操作add(Cookie cookie)。要想修改Cookie只能使用一个同名的Cookie来覆盖原来的Cookie，达到修改的目的。删除时只需要把maxAge修改为0即可。
                在所遇到的项目中，Action里创建了一个cookie，maxAge为-1，紧接着在另一个方法中要删除cookie，就可以通过创建一个同名同域的cookie，然后将maxAge设置为0，再通过response的addCookie方法对客户端的cookie文件或浏览器内存中的cookie进行删除。
                注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性，例如name、path、domain等，都要与原Cookie完全一样。否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。
                注意二、从客户端读取Cookie时，包括maxAge在内的其他属性都是不可读的，也不会被提交。浏览器提交Cookie时只会提交name与value属性。maxAge属性只被浏览器用来判断Cookie是否过期。
            */
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ApiResult.success(user);
    }
    @PostMapping("/logout")
    @ResponseBody
    public ApiResult logout (HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // request.logout();
            session.setMaxInactiveInterval(0);
        }
        Cookie cookie = new Cookie("USER", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        // try {
        //     response.sendRedirect("/");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        return ApiResult.success("退出成功");
    }
    /**
     * 返回用户个人中心
     * PathVarialbe 参数校验
     * @param id
     * @param model
     * @return
     * 
     */
    @GetMapping(value = "/center/{id:\\d+}")
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
    /**
     * 获取卡片
     * @param id 用户id
     * @param size 分页大小
     * @param index 分页下标
     * @return
     */
    @GetMapping("/cards")
    @ResponseBody
    public ApiResult getCards(@RequestParam("id") String id,
        @RequestParam(value = "size", defaultValue = "15") @DecimalMin(value = "0", message = "值必须大于零") int size,
        @RequestParam(value = "index", defaultValue = "1") @DecimalMin(value = "0", message = "值必须大于零") int index) {
        return ApiResult.success(userService.getCards(id, PageRequest.of(index - 1, size)));
    }
}
