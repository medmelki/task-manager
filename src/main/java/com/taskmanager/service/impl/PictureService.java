package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IPictureService;
import org.springframework.stereotype.Repository;

@Repository
public class PictureService extends GenericService<User, Integer> implements IPictureService {

    public PictureService() {
        super(User.class);
    }
}
