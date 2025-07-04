package com.Manager.task_manager.Utils;

public class ValidationsUtils {

    public static String trimToNull(String input){
        if(input == null) return null;
        String trimed = input.trim();
        return trimed.isEmpty() ? null : trimed ;
    }

    public static String requireNonBlank( String input , String fieldName){
        String cleanned = trimToNull(input);
        if (cleanned == null){
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return cleanned;
    }
}
