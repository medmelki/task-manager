package com.taskmanager.service.impl;


import com.taskmanager.model.Hach;
import com.taskmanager.service.IHachService;
import org.springframework.stereotype.Repository;

@Repository
public class HachService extends GenericService<Hach, String> implements IHachService {

    public HachService() {
        super(Hach.class);
    }
}
