package com.taskmanager.service;

import com.taskmanager.model.Package;
import org.springframework.stereotype.Service;

@Service
public interface IPackageService extends IGenericService<Package, Integer> {
}
