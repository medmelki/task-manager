package com.taskmanager.controller;

import com.taskmanager.model.Document;
import com.taskmanager.model.Picture;
import com.taskmanager.model.Role;
import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IPictureService;
import com.taskmanager.service.IRoleService;
import com.taskmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class UserController {

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IPictureService pictureService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAll() {

        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public ResponseEntity<User> getAuthenticatedUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.read(auth.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/role/", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> listAllRoles() {

        List<Role> roles = roleService.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String username) {

        User user = new User();
        user.setUsername(username);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/documents/upload/")
    public void uploadDocument(@RequestParam("document") MultipartFile document, @RequestParam("username") String username) throws IOException {

        Document doc = new Document();
        User user = new User();
        user.setUsername(username);
        if (!document.isEmpty()) {
            doc.setUser(user);
            doc.setName(document.getOriginalFilename());
            doc.setData(document.getBytes());
        }
        documentService.create(doc);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/pictures/upload/")
    public void uploadPicture(@RequestParam("picture") MultipartFile picture, @RequestParam("username") String username) throws IOException {

        Picture pic = new Picture();
        User user = new User();
        user.setUsername(username);
        if (!picture.isEmpty()) {
            pic.setUser(user);
            pic.setName(picture.getOriginalFilename());
            pic.setData(picture.getBytes());
        }
        pictureService.create(pic);
    }

    //    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/picture/{username}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPicture(@PathVariable String username) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if (username.equals(name)) {
            User user = userService.read(username);
            Picture picture = user.getPictures().get(0);
            if (picture != null) {
                return new ResponseEntity<>(picture.getData(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
