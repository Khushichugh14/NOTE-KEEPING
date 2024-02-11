package com.sgt.notekeeping.web;
import com.sgt.notekeeping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserResource {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> insertUser(@RequestBody Map<String,Object> body){
        return userService.insertUser(body);
    }

    @PostMapping("/login")
    public Map<String,Object> loginUser(@RequestBody Map<String , Object> body){
        return userService.loginUser(body);
    }

    @PutMapping("/user/{userid}")
    public ResponseEntity<String> updateUser(@RequestBody Map<String,Object>body,@PathVariable Integer userid){
        return userService.updateUser(body,userid);
    }
}
