package com.web.service;

import com.web.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private static ArrayList<User> users = new ArrayList<User>();
    static {
        for (int i=0; i<10; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername("jobs" + i);
            user.setPassword("2424234" + i);
            user.setPhoneNumber("135678905" + i);
            users.add(user);
        }
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public User getUserById(int id) {
        User user = null;
        for(User temp : users) {
            if (temp.getId() == id) {
                user = temp;
                break;
            }
        }
        return user;
    }
    public boolean deleteUser(int id) {
        return users.remove(this.getUserById(id));
    }
}
