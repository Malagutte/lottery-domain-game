package br.com.lottery.domain.game.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityHelper {
    public static ResponseEntity<Object> buildResponseEntity(HttpStatus status, Object body) {
        return ResponseEntity.status(status).body(body);
    }
}
