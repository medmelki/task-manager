package com.taskmanager.service.impl;


import com.taskmanager.model.Role;
import com.taskmanager.model.User;
import com.taskmanager.service.IRoleService;
import org.springframework.stereotype.Repository;

@Repository
public class RoleService extends GenericService<Role, Integer> implements IRoleService {

    public RoleService() {
        super(Role.class);
    }
}
