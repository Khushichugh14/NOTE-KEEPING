package com.sgt.notekeeping.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int insertUser(String userName,String email,String password){
        return jdbcTemplate.update("EXEC  Note.sp_AddUser ?,?,?",userName,email,password);
    }

    public Map<String , Object> loginUser(String userName , String password){
        return jdbcTemplate.queryForMap("EXEC  Note.sp_LoginUser ?,?",userName , password);
    }

    public Map<String , Object> validateToken(Integer userID , String token){
        return jdbcTemplate.queryForMap(" EXEC Note.sp_ValidateToken ?,?",userID,token);
    }

}
