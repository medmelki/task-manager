package com.taskmanager.controller;

import com.taskmanager.model.Document;
import com.taskmanager.model.Hach;
import com.taskmanager.model.Picture;
import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IHachService;
import com.taskmanager.service.IPictureService;
import com.taskmanager.service.IUserService;
import com.taskmanager.utils.RolesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
    @Autowired
    private IUserService userService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IHachService hachService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAll() {

        // retrieve the current logged in admin/superadmin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User user = userService.read(auth.getName());
        List<User> users = userService.findAll();
        List<User> usersResult = new ArrayList<>();
        // if the user logged in is a superadmin, return all the users of the app
        if (user.getRole().equals(ROLE_SUPERADMIN)) {
            usersResult = users;
        } else {
            // check if it is an admin then filter the users with the same domain.
            for (User usr : users) {
                if (usr.getManager().getUsername().equals(user.getUsername())) {
                    usersResult.add(usr);
                }
            }
        }
        if (usersResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersResult, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public ResponseEntity<User> getAuthenticatedUser() {

        // retrieve the current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // A security operation to check if the current user is registered in database
        User user = userService.read(auth.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        putManager(user);
        // encode the password of the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        putManager(user);
        // get the current user connected
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            // check if the current user has only a user role
            if (authority.getAuthority().equals("ROLE_USER")) {
                // check if the user is updating its own details, otherwise, return http error
                if (!auth.getName().equals(user.getUsername())) {
                    return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
                }
            }
        }
        User old_user = userService.read(user.getUsername());
        // check if the modification sets a new picture, otherwise, keep the old one
        if (old_user.getPic() != null)
            user.setPic(old_user.getPic());
        // encode the new password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
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

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
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

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/pic/upload/")
    public void uploadProfilePicture(@RequestParam("picture") MultipartFile picture, @RequestParam("username") String username) throws IOException {

        User user = userService.read(username);
        if (!picture.isEmpty()) {
            user.setPic(picture.getBytes());
        }
        userService.update(user);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/pic/{username}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String username) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if (username.equals(name)) {
            User user = userService.read(username);
            if (user.getPic() != null) {
                return new ResponseEntity<>(user.getPic(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/picture/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deletePicture(@PathVariable String id) {

        // TODO : add security aspect to verify it is the picture's owner who is calling the service
        Picture picture = pictureService.read(Integer.parseInt(id));
        pictureService.delete(picture);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/user/document/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteDocument(@PathVariable String id) {

        // TODO : add security aspect to verify it is the picture's owner who is calling the service
        Document document = documentService.read(Integer.parseInt(id));
        documentService.delete(document);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/hash", method = RequestMethod.GET)
    public ResponseEntity<Hach> getHash() {

        Hach hach = new Hach();
        hachService.create(hach);
        return new ResponseEntity<>(hach, HttpStatus.OK);
    }

    @PostConstruct
    public void initData() {
        // init default superadmin
        initAdmin("superadmin", "superadmin", RolesUtils.ROLE_SUPERADMIN);
        // init default admin
        initAdmin("admin", "admin", RolesUtils.ROLE_ADMIN);

    }

    private void initAdmin(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        if (userService.read(username) == null) {
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            user.setManager(user);
            userService.create(user);
        }
    }

    private void putManager(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User admin = userService.read(auth.getName());
        // check if superadmin then put the admin as its own manager
        if (user.getRole().equals(ROLE_SUPERADMIN)) {
            user.setManager(user);
        } else { // the admin logged it is the manager of the user to be created
            user.setManager(admin);
        }
    }

}
