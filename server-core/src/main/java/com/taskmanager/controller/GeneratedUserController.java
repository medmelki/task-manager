package com.taskmanager.controller;

import com.taskmanager.model.Document;
import com.taskmanager.model.Hach;
import com.taskmanager.model.Picture;
import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.IHachService;
import com.taskmanager.service.IPictureService;
import com.taskmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/generated/{hach}")
public class GeneratedUserController {

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IHachService hachService;


    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAll(@PathVariable String hach) {
        if (isHachValid(hach)) {
            List<User> users = userService.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public ResponseEntity<User> getAuthenticatedUser(@PathVariable String hach) {

        if (isHachValid(hach)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.read(auth.getName());
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@PathVariable String hach, @RequestBody User user) {
        if (isHachValid(hach)) {
            userService.create(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable String hach, @RequestBody User user) {

        if (isHachValid(hach)) {
            User old_user = userService.read(user.getUsername());
            if (old_user.getPic() != null)
                user.setPic(old_user.getPic());
            userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String hach, @PathVariable String username) {

        if (isHachValid(hach)) {
            User user = new User();
            user.setUsername(username);
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/user/documents/upload/")
    public void uploadDocument(@PathVariable String hach, @RequestParam("document") MultipartFile document, @RequestParam("username") String username) throws IOException {

        if (isHachValid(hach)) {
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
    }


    @RequestMapping(value = "/user/pictures/upload/")
    public void uploadPicture(@PathVariable String hach, @RequestParam("picture") MultipartFile picture, @RequestParam("username") String username) throws IOException {

        if (isHachValid(hach)) {
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
    }


    @RequestMapping(value = "/user/pic/upload/")
    public void uploadProfilePicture(@PathVariable String hach, @RequestParam("picture") MultipartFile picture, @RequestParam("username") String username) throws IOException {

        if (isHachValid(hach)) {
            User user = userService.read(username);
            if (!picture.isEmpty()) {
                user.setPic(picture.getBytes());
            }
            userService.update(user);
        }
    }


    @RequestMapping(value = "/user/pic/{username}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String hach, @PathVariable String username) throws IOException {

        if (isHachValid(hach)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            if (username.equals(name)) {
                User user = userService.read(username);
                if (user.getPic() != null) {
                    return new ResponseEntity<>(user.getPic(), HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @RequestMapping(value = "/user/picture/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deletePicture(@PathVariable String hach, @PathVariable String id) {

        if (isHachValid(hach)) {
            // TODO : add security aspect to verify it is the picture's owner who is calling the service
            Picture picture = pictureService.read(Integer.parseInt(id));
            pictureService.delete(picture);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/hash", method = RequestMethod.GET)
    public ResponseEntity<Hach> getHash() {

        Hach hach = new Hach();
        hachService.create(hach);
        return new ResponseEntity<>(hach, HttpStatus.OK);
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
