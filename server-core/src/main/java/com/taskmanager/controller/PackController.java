package com.taskmanager.controller;

import com.taskmanager.model.Pack;
import com.taskmanager.service.IPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PackController {

    @Autowired
    private IPackService packService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/", method = RequestMethod.GET)
    public ResponseEntity<List<Pack>> listAll() {

        List<Pack> packs = packService.findAll();
        if (packs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(packs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/", method = RequestMethod.POST)
    public ResponseEntity<Void> addPackage(@RequestBody Pack pack) {
        packService.create(pack);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/", method = RequestMethod.PUT)
    public ResponseEntity<Pack> updatePackage(@RequestBody Pack pack) {

        packService.update(pack);
        return new ResponseEntity<>(pack, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Pack> deletePackage(@PathVariable String id) {

        Pack pack = new Pack();
        pack.setId(Integer.parseInt(id));
        packService.delete(pack);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
