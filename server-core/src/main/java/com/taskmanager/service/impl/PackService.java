package com.taskmanager.service.impl;


import com.taskmanager.model.Pack;
import com.taskmanager.service.IPackService;
import org.springframework.stereotype.Repository;

@Repository
public class PackService extends GenericService<Pack, Integer> implements IPackService {

    public PackService() {
        super(Pack.class);
    }
}
