package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IUserService;
import org.springframework.stereotype.Repository;

@Repository
public class UserService extends GenericService<User, String> implements IUserService {

    public UserService() {
        super(User.class);
    }
}
