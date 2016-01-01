package com.taskmanager.service.impl;


import com.taskmanager.model.Package;
import com.taskmanager.service.IPackageService;
import org.springframework.stereotype.Repository;

@Repository
public class PackageService extends GenericService<Package, Integer> implements IPackageService {

    public PackageService() {
        super(Package.class);
    }
}
