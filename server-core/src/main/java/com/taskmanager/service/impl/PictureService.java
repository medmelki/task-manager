package com.taskmanager.service.impl;


import com.taskmanager.model.Picture;
import com.taskmanager.service.IPictureService;
import org.springframework.stereotype.Repository;

@Repository
public class PictureService extends GenericService<Picture, Integer> implements IPictureService {

    public PictureService() {
        super(Picture.class);
    }
}
