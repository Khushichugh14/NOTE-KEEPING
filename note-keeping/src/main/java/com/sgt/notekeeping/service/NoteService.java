package com.sgt.notekeeping.service;

import com.sgt.notekeeping.repository.NoteRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;

    public  ResponseEntity<Map<String, String>>  addNote(Map<String,Object> body , HttpServletRequest httpServletRequest){
        String title = (String) body.get("title");
        String descrpition = (String) body.get("descrpition");
        Integer CreatedByUser = getUserId(httpServletRequest);

        System.out.println(body);
        int noOfRows = noteRepository.addNote(title,descrpition,CreatedByUser);

        if(noOfRows>0){
            return ResponseEntity.ok(Map.of("status" , "New Note Added!!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status" , "Note Add Failed!!"));
    }

    public int  getUserId(HttpServletRequest httpServletRequest){
        //ertracting Cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        //returning hashmap
        Map<String, String> cookieMap = getCookiesAsHashMap(cookies);
        System.out.println(cookieMap);
        Map<String, Integer> result = new HashMap<>();
        result.put("userID", Integer.parseInt(cookieMap.get("CreatedByUser")));

        //(Integer.parseInt(cookieMap.get("userID")));
        Integer userID = (Integer) result.get("userID");
        return userID;
    }
    public Map<String, String> getCookiesAsHashMap(Cookie[] cookies){
        Map<String,String> cookieMap = new HashMap<>();
        for(Cookie c : cookies)
        {
            cookieMap.put(c.getName() , c.getValue());
            System.out.println(c.getName()+"-"+c.getValue());
        }
        return  cookieMap;
    }
}
