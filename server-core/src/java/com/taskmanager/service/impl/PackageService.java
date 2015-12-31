package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IPackageService;
import org.springframework.stereotype.Repository;

@Repository
public class PackageService extends GenericService<User, Integer> implements IPackageService {

    public PackageService() {
        super(User.class);
    }
}
