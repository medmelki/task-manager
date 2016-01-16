package com.taskmanager.controller;

import com.taskmanager.model.Document;
import com.taskmanager.model.Picture;
import com.taskmanager.model.Role;
import com.taskmanager.model.Node;
import com.taskmanager.service.INodeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class NodeController {

    @Autowired
    private INodeService nodeService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/node/", method = RequestMethod.GET)
    public ResponseEntity<List<Node>> listAll() {

        List<Node> nodes = nodeService.findAll();
        if (nodes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(nodes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/", method = RequestMethod.POST)
    public ResponseEntity<Void> addNode(@RequestBody Node node) {
        nodeService.create(node);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/", method = RequestMethod.PUT)
    public ResponseEntity<Node> updateNode(@RequestBody Node node) {

        nodeService.update(node);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Node> deleteNode(@PathVariable String id) {

        Node node = new Node();
        node.setId(Integer.parseInt(id));
        nodeService.delete(node);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
