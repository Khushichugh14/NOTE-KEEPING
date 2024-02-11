package com.sgt.notekeeping.web;

import com.sgt.notekeeping.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class NoteResource {

    @Autowired
    NoteService noteService;
    @PostMapping("/note")
    public ResponseEntity<String> addNote(@RequestBody Map<String,Object>body){

        return noteService.addNote(body);
    }

}
