package com.example.config.common;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common utils class as helper/utils box
 */
public class Utils {
    public static boolean isValidJSON(final String json){
        boolean valid = true;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
        } catch(Exception e){
            valid = false;
        }
        return valid;
    }
}
