/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author cw
 */
public class Util {

    public static String parseDateTime(Timestamp t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        Date date = new Date(t.getTime());
        return sdf.format(date);
    }
    
    public static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String currentTimestamp = dateFormat.format(new Date());
        return currentTimestamp;
    }
    
    // Generate a salt and hash the password using BCrypt
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(10); // Generate a salt with cost factor 10
        String hashedPassword = BCrypt.hashpw(password, salt);
        return hashedPassword;
    }

    // Verify the password against the hashed password using BCrypt
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    public static void main(String[] args){
        String password = "demo";
        String hashPassword = hashPassword(password);
        hashPassword = "$2a$10$oUNK/tiyMNDbL2IdO/VpUuVC74MfNnByJ/N4sFmsUD4uLes98iwBu";
        System.out.println("hashPwd == " + hashPassword);
        boolean isValid = verifyPassword(password, hashPassword);
        System.out.println(isValid);
    }
}
