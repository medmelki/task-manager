package com.taskmanager.service;

import com.taskmanager.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends IGenericService<User, String> {
}
