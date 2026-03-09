package com.sgt.notekeeping.service;

import com.sgt.notekeeping.config.JwtUtil;
import com.sgt.notekeeping.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;
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
        String userName = (String)body.get("userName");
        String password = (String)body.get("password");
        Map<String, Object> result = userRepository.loginUser(userName,password);
        
        Integer validYn = (Integer) result.get("validYN");
        if (validYn != null && validYn == 1) {
            Integer userId = (Integer) result.get("userID");
            String token = jwtUtil.generateToken(userName, userId);
            result.put("token", token); // Replace DB token with JWT
            result.put("jwtToken", token);
        }
        return result;
    }

    public List<String> getStudentsNames(HttpServletRequest httpServletRequest){
        boolean isValid = isTokenValidate(httpServletRequest);
        System.out.println(isValid);
        return  List.of("Kinjala" , "Jiya" , "Khushi", "Nidhi" , "Varsha" , "Muskan");
    }

    public boolean isTokenValidate(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) return false;

        Map<String, String> cookieMap = getCookiesAsHashMap(cookies);
        try {
            String userId = cookieMap.get("userID");
            if (userId == null) userId = cookieMap.get("CreatedByUser");
            if (userId == null) return false;

            Map<String, Object> result = userRepository.validateToken(Integer.parseInt(userId), cookieMap.get("token"));
            Integer validYn = (Integer) result.get("ValidYN");
            return validYn != null && validYn == 1;
        } catch (Exception e) {
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    private Map<String, String> getCookiesAsHashMap(Cookie[] cookies) {
        Map<String, String> cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie c : cookies) {
                cookieMap.put(c.getName(), c.getValue());
            }
        }
        return cookieMap;
    }

    public ResponseEntity<Map<String, Object>> getUserProfile(Integer userId) {
        try {
            return ResponseEntity.ok(userRepository.getUserProfile(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Map<String, String>> updateUserProfile(Integer userId, Map<String, Object> body) {
        try {
            String email = (String) body.get("email");
            String password = (String) body.get("password");
            int rows = userRepository.updateUserProfile(userId, email, password);
            if (rows > 0) {
                return ResponseEntity.ok(Map.of("status", "Profile updated successfully!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
