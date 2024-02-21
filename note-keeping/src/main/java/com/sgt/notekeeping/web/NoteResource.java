package com.sgt.notekeeping.web;

import com.sgt.notekeeping.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

public class NoteResource {

    @Autowired
    NoteService noteService;
    @PostMapping("/note")
    public  ResponseEntity<Map<String, String>>  addNote(@RequestBody Map<String,Object>body , HttpServletRequest httpServletRequest){
        return noteService.addNote(body , httpServletRequest);
    }

}
