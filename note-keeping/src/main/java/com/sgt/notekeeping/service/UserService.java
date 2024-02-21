package com.sgt.notekeeping.service;

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
        return userRepository.loginUser(userName,password);
    }

    public List<String> getStudentsNames(HttpServletRequest httpServletRequest){
        boolean isValid = isTokenValidate(httpServletRequest);
        System.out.println(isValid);
        return  List.of("Kinjala" , "Jiya" , "Khushi", "Nidhi" , "Varsha" , "Muskan");
    }

    public boolean isTokenValidate(HttpServletRequest httpServletRequest){
//        System.out.println(httpServletRequest);
        //ertracting Cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        //returning hashmap
        Map<String, String> cookieMap = getCookiesAsHashMap(cookies);
        Map<String, Object> result = userRepository.validateToken(Integer.parseInt(cookieMap.get("userID")),cookieMap.get("token"));
        Integer validYn = (Integer) result.get("ValidYN");
        return validYn == 1;
    }

    private  Map<String, String> getCookiesAsHashMap(Cookie[] cookies){
        Map<String,String> cookieMap = new HashMap<>();
        for(Cookie c : cookies)
        {
            cookieMap.put(c.getName() , c.getValue());
            System.out.println(c.getName()+"-"+c.getValue());
        }
        return  cookieMap;
    }


}
