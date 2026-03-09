package com.sgt.notekeeping.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class NoteRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public int addNote(String title, String descrpition, Integer CreatedByUser, String category, String imageUrl) {
        return jdbcTemplate.update("EXEC Note.sp_AddNote ?,?,?,?,?", title, descrpition, CreatedByUser, category, imageUrl);
    }

    public java.util.List<Map<String, Object>> getAllNotes() {
        return jdbcTemplate.queryForList("EXEC Note.sp_GetALLNote");
    }

    public int deleteNote(Integer noteId) {
        return jdbcTemplate.update("{call Note.sp_DeleteNote(?)}", noteId);
    }

    public int updateNote(Integer noteId, String title, String description, String category, String imageUrl) {
        return jdbcTemplate.update("EXEC Note.sp_UpdateNote ?,?,?,?,?", noteId, title, description, category, imageUrl);
    }
}
