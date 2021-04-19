package br.com.lottery.domain.game.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseEntiityHelper {

    private ResponseEntiityHelper(){
        throw  new RuntimeException("illegal access");
    }

    public static ResponseEntity<Object> buildResponseEntity(HttpStatus status, Optional<?> body){
        if(!body.isPresent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(status).body(body.get());
    }
}
