package com.taskmanager.service;

import com.taskmanager.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IPackageService extends IGenericService<User, Integer> {
}
