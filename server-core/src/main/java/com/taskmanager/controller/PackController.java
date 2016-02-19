package com.taskmanager.controller;

import com.taskmanager.model.Pack;
import com.taskmanager.model.User;
import com.taskmanager.service.IPackService;
import com.taskmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PackController {

    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
    @Autowired
    private IPackService packService;
    @Autowired
    private IUserService userService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/", method = RequestMethod.GET)
    public ResponseEntity<List<Pack>> listAll() {

        // retrieve the current logged in admin/superadmin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User user = userService.read(auth.getName());
        List<Pack> packs;
        // check if superadmin then show all packs
        if (user.getRole().equals(ROLE_SUPERADMIN)) {
            packs = packService.findAll();
        } else {
            // else return only user managed packs
            packs = user.getPacksToManage();
        }
        if (packs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(packs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/", method = RequestMethod.POST)
    public ResponseEntity<Void> addPackage(@RequestBody Pack pack) {
        putManager(pack);
        packService.create(pack);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/pack/", method = RequestMethod.PUT)
    public ResponseEntity<Pack> updatePackage(@RequestBody Pack pack) {
        putManager(pack);
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

    private void putManager(Pack pack) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User admin = userService.read(auth.getName());
        pack.setManager(admin);
    }


}
