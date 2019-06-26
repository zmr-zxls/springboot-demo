package com.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.model.Card;
import com.web.model.User;
import com.web.repository.UserRepository;
import com.web.repository.ext.UserDAO;
import com.web.utils.SimplePage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDAO userDao;
    public User getUserById(String id) {
        Optional<User> op = userRepository.findUserById(id);
        return op.isPresent() ? op.get() : null;
    }
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    public ArrayList<User> getUserList () {
        return (ArrayList<User>) userRepository.findAll();
    }
    public User getUser(String name, String password) {
        // ArrayList<User> lst = userRepository.findByUsernameAndPassword(name, password);
        // return lst.isEmpty() ? null : lst.get(0);
        return userRepository.findOneByUsernameAndPassword(name, password);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
	public User registerUser(String name, String pwd) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
		return this.saveUser(user);
    }
    public boolean existEqualName(String name) {
        return userRepository.findOneByUsername(name) != null;
    }
    public List<Card> getCards(String userid) {
        return userDao.getCardsByUserid(userid);
    }
    /**
     * 分页查询
     * @param userid 用户id
     * @param pageable 可以为null
     * @param sort 可以为null
     * @return
     */
    public SimplePage<Card> getCards(String userid, Pageable pageable, Sort sort) {
        List<Card> cards = userDao.getCardsByUserid(userid, pageable, sort);
        // 统计总数
        long total = userDao.count(userid);
        return new SimplePage<Card>(cards, total, pageable.getPageSize(), pageable.getOffset());
    }
    public SimplePage<Card> getCards(String userid, Pageable pageable) {
        return getCards(userid, pageable, null);
    }
}
