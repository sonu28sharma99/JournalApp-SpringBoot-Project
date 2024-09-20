package com.sonusharma.journelApp.controller;

import com.sonusharma.journelApp.entity.User;
import com.sonusharma.journelApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }
    
    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAll(){
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getUser(@PathVariable ObjectId myId){
        Optional<User> response = userService.getUserById(myId);
        if(response.isPresent())
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId){
        Optional<User> deleteUser = userService.getUserById(myId);
        if(deleteUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.deleteUserById(myId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserById(@RequestBody User user, @PathVariable String username){
        User userInDb = userService.findByUsername(username);
        if(userInDb!=null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
