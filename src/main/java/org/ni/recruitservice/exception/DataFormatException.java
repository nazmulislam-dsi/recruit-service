package org.ni.recruitservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by nazmul on 9/22/2018.
 */
public @Data
class DataFormatException extends RuntimeException{
    private int code;
    public DataFormatException() {
        super();
        this.code = HttpStatus.UNPROCESSABLE_ENTITY.value();
    }
    public DataFormatException(Exception e) {
        super(e);
        this.code = HttpStatus.UNPROCESSABLE_ENTITY.value();
    }
}
