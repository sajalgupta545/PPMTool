package com.speproject.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

@Service
public class MapValidationErrorService{
    public ResponseEntity<?> MapValidationService(BindingResult result){

        // binding result is added for getting the error free json object
        if(result.hasErrors()){
            HashMap<String,String> errorMap = new HashMap<>();
            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
            }
            return new ResponseEntity<HashMap<String,String>>(errorMap , HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
