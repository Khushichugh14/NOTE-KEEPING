package com.sgt.notekeeping.service;

import com.sgt.notekeeping.config.JwtUtil;
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

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<java.util.List<Map<String, Object>>> getAllNotes() {
        try {
            return ResponseEntity.ok(noteRepository.getAllNotes());
        } catch (Exception e) {
            System.err.println("Error fetching notes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, String>> deleteNote(Integer noteId) {
        try {
            int noOfRows = noteRepository.deleteNote(noteId);
            if (noOfRows > 0) {
                return ResponseEntity.ok(Map.of("status", "Note deleted successfully!"));
            }
        } catch (Exception e) {
            System.err.println("Error deleting note: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "Delete failed: " + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "Note not found!"));
    }

    public ResponseEntity<Map<String, String>> updateNote(Integer noteId, Map<String, Object> body) {
        try {
            String title = (String) body.get("title");
            String descrpition = (String) body.get("descrpition");
            String category = (String) body.get("category");
            if (category == null) category = "Personal";
            String imageUrl = (String) body.get("imageUrl");
            
            int noOfRows = noteRepository.updateNote(noteId, title, descrpition, category, imageUrl);
            if (noOfRows > 0) {
                return ResponseEntity.ok(Map.of("status", "Note updated successfully!"));
            }
        } catch (Exception e) {
            System.err.println("Error updating note: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "Update failed: " + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "Note not found!"));
    }

    public ResponseEntity<Map<String, String>> addNote(Map<String, Object> body, HttpServletRequest httpServletRequest) {
        String title = (String) body.get("title");
        String descrpition = (String) body.get("descrpition");
        String category = (String) body.get("category");
        if (category == null) category = "Personal";

        Integer createdByUser = getUserId(httpServletRequest);

        // Fallback to body if cookie is missing
        if (createdByUser == null && body.get("CreatedByUser") != null) {
            try {
                createdByUser = Integer.parseInt(body.get("CreatedByUser").toString());
                System.out.println("Using CreatedByUser from request body: " + createdByUser);
            } catch (Exception e) {
                System.err.println("Invalid CreatedByUser in body: " + body.get("CreatedByUser"));
            }
        }

        if (createdByUser == null) {
            System.err.println("Note creation failed: No user identity found in cookies or request body.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "User identity missing. Please login again."));
        }

        String imageUrl = (String) body.get("imageUrl");
        System.out.println("Processing note creation for UserID: " + createdByUser);
        System.out.println("Title: " + title + ", Category: " + category + ", imageUrl present: " + (imageUrl != null));
        try {
            int noOfRows = noteRepository.addNote(title, descrpition, createdByUser, category, imageUrl);
            System.out.println("Rows affected: " + noOfRows);
            if (noOfRows > 0) {
                return ResponseEntity.ok(Map.of("status", "New Note Added!!"));
            }
        } catch (Exception e) {
            System.err.println("Database error while adding note: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "Server Error: " + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "Failed to save note in database - 0 rows affected."));
    }

    public Integer getUserId(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) return null;

        Map<String, String> cookieMap = getCookiesAsHashMap(cookies);
        String token = cookieMap.get("token");

        // Try extracting from JWT first
        if (token != null) {
            try {
                if (jwtUtil.validateToken(token)) {
                    return jwtUtil.extractUserId(token);
                }
            } catch (Exception e) {
                System.out.println("Invalid JWT token: " + e.getMessage());
            }
        }

        // Fallback to manual userID cookie
        String userIdStr = cookieMap.get("userID");
        if (userIdStr != null) {
            try {
                return Integer.parseInt(userIdStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid userID in cookie: " + userIdStr);
            }
        }
        return null;
    }

    public Map<String, String> getCookiesAsHashMap(Cookie[] cookies) {
        Map<String, String> cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie c : cookies) {
                cookieMap.put(c.getName(), c.getValue());
            }
        }
        return cookieMap;
    }
}
