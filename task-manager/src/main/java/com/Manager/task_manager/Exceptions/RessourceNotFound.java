package com.Manager.task_manager.Exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RessourceNotFound extends RuntimeException{
    public RessourceNotFound (String ressourceName){
        super(String.format("%s not found .",ressourceName));
    }
}
