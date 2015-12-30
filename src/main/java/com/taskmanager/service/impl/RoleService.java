package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IRoleService;
import org.springframework.stereotype.Repository;

@Repository
public class RoleService extends GenericService<User, Integer> implements IRoleService {

    public RoleService() {
        super(User.class);
    }
}
