package com.sgt.notekeeping.service;

import com.sgt.notekeeping.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;

    public ResponseEntity<String> addNote(Map<String,Object> body){
        String title = (String) body.get("title");
        String descrpition = (String) body.get("descrpition");
        Integer CreatedByUser = (int) body.get("CreatedByUser");


        int noOfRows = noteRepository.addNote(title,descrpition,CreatedByUser);

        if(noOfRows>0){
            return ResponseEntity.ok("New Note Added!!");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Note Add Failed!!");
    }
}
