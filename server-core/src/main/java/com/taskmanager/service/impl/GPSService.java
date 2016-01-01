package com.taskmanager.service.impl;


import com.taskmanager.model.GPS;
import com.taskmanager.service.IGPSService;
import org.springframework.stereotype.Repository;

@Repository
public class GPSService extends GenericService<GPS, Integer> implements IGPSService {

    public GPSService() {
        super(GPS.class);
    }
}
