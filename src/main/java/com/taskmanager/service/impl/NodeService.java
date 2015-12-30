package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.INodeService;
import org.springframework.stereotype.Repository;

@Repository
public class NodeService extends GenericService<User, Integer> implements INodeService {

    public NodeService() {
        super(User.class);
    }
}
