package com.sgt.notekeeping.service;

import com.sgt.notekeeping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<Map<String, String>> insertUser(Map<String,Object> body){
        String userName = (String) body.get("userName");
        String email = (String) body.get("email");
        String password = (String) body.get("password");

        int noOfRows = userRepository.insertUser(userName,email,password);

        if(noOfRows>0){
            return ResponseEntity.ok(Map.of("status" , "successfully inserted user"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status" , "user insertion failed"));
    }
    public Map<String ,Object> loginUser(Map<String,Object> body){
        String email = (String)body.get("email");
        String password = (String)body.get("password");
        return userRepository.loginUser(email,password);
    }

    public ResponseEntity<String> updateUser(Map<String,Object> body,Integer userid){
        int userId = (int) body.get("userId");
        String userName = (String) body.get("userName");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        int noOfRows = userRepository.updateUser(userId,userName,email,password);

        if(noOfRows>0){
            return ResponseEntity.ok("successfully updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("update failed");
    }

}
