package com.taskmanager.controller;

import com.taskmanager.model.Node;
import com.taskmanager.model.User;
import com.taskmanager.service.INodeService;
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
public class NodeController {

    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
    @Autowired
    private INodeService nodeService;
    @Autowired
    private IUserService userService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/", method = RequestMethod.GET)
    public ResponseEntity<List<Node>> listAll() {

        // retrieve the current logged in admin/superadmin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User user = userService.read(auth.getName());
        List<Node> nodes;
        // check if superadmin then show all nodes
        if (user.getRole().equals(ROLE_SUPERADMIN)) {
            nodes = nodeService.findAll();
        } else {
            // else return only user managed nodes
            nodes = user.getNodesToManage();
        }
        if (nodes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(nodes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/", method = RequestMethod.POST)
    public ResponseEntity<Void> addNode(@RequestBody Node node) {
        putManager(node);
        nodeService.create(node);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/", method = RequestMethod.PUT)
    public ResponseEntity<Node> updateNode(@RequestBody Node node) {
        putManager(node);
        nodeService.update(node);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Node> deleteNode(@PathVariable String id) {

        Node node = new Node();
        node.setId(Integer.parseInt(id));
        nodeService.delete(node);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void putManager(Node node) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User admin = userService.read(auth.getName());
        node.setManager(admin);
    }


}
