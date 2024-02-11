package com.sgt.notekeeping.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public int addNote(String title,String descrpition,int  CreatedByUser){
        return jdbcTemplate.update("EXEC Note.sp_AddNote  ?,?,?",title,descrpition,CreatedByUser);
    }
}
