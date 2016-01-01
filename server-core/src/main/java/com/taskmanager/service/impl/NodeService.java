package com.taskmanager.service.impl;


import com.taskmanager.model.Node;
import com.taskmanager.service.INodeService;
import org.springframework.stereotype.Repository;

@Repository
public class NodeService extends GenericService<Node, Integer> implements INodeService {

    public NodeService() {
        super(Node.class);
    }
}
