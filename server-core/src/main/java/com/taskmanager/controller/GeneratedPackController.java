package com.taskmanager.controller;

import com.taskmanager.model.Hach;
import com.taskmanager.model.Pack;
import com.taskmanager.service.IHachService;
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
@RequestMapping("/generated/{hach}")
public class GeneratedPackController {

    @Autowired
    private IPackService packService;
    @Autowired
    private IHachService hachService;

    
    @RequestMapping(value = "/pack/", method = RequestMethod.GET)
    public ResponseEntity<List<Pack>> listAll(@PathVariable String hach) {
        if (isHachValid(hach)) {
            List<Pack> packs = packService.findAll();
            if (packs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(packs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    
    @RequestMapping(value = "/pack/", method = RequestMethod.POST)
    public ResponseEntity<Void> addPackage(@PathVariable String hach, @RequestBody Pack pack) {
        if (isHachValid(hach)) {
            packService.create(pack);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    
    @RequestMapping(value = "/pack/", method = RequestMethod.PUT)
    public ResponseEntity<Pack> updatePackage(@PathVariable String hach, @RequestBody Pack pack) {
        if (isHachValid(hach)) {
            packService.update(pack);
            return new ResponseEntity<>(pack, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    
    @RequestMapping(value = "/pack/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Pack> deletePackage(@PathVariable String hach, @PathVariable String id) {
        if (isHachValid(hach)) {
            Pack pack = new Pack();
            pack.setId(Integer.parseInt(id));
            packService.delete(pack);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean isHachValid(String hach) {
        List<Hach> hachs = hachService.findAll();
        // if hash is in database
        for (Hach hachElt : hachs) {
            if (hachElt.getHach().equals(hach)) {
                return true;
            }
        }
        return false;
    }

}
