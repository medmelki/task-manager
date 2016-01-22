package com.taskmanager.controller;

import com.taskmanager.model.Hach;
import com.taskmanager.model.Node;
import com.taskmanager.service.IHachService;
import com.taskmanager.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/generated/{hach}")
public class GeneratedNodeController {

    @Autowired
    private INodeService nodeService;
    @Autowired
    private IHachService hachService;


    @RequestMapping(value = "/node/", method = RequestMethod.GET)
    public ResponseEntity<List<Node>> listAll(@PathVariable String hach) {
        if (isHachValid(hach)) {
            List<Node> nodes = nodeService.findAll();
            if (nodes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(nodes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/node/", method = RequestMethod.POST)
    public ResponseEntity<Void> addNode(@PathVariable String hach, @RequestBody Node node) {
        if (isHachValid(hach)) {
            nodeService.create(node);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/node/", method = RequestMethod.PUT)
    public ResponseEntity<Node> updateNode(@PathVariable String hach, @RequestBody Node node) {
        if (isHachValid(hach)) {
            nodeService.update(node);
            return new ResponseEntity<>(node, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Node> deleteNode(@PathVariable String hach, @PathVariable String id) {
        if (isHachValid(hach)) {
            Node node = new Node();
            node.setId(Integer.parseInt(id));
            nodeService.delete(node);
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
