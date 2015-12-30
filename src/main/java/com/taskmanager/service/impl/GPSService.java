package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IGPSService;
import org.springframework.stereotype.Repository;

@Repository
public class GPSService extends GenericService<User, Integer> implements IGPSService {

    public GPSService() {
        super(User.class);
    }
}
