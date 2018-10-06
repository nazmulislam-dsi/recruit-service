package org.ni.recruitservice.exception;

import org.ni.recruitservice.utils.Constants;
import org.springframework.http.HttpStatus;


public class BadRequestException extends ApiException{
  private int code;
  public BadRequestException() {
    super(HttpStatus.NO_CONTENT.value(), Constants.BAD_REQUEST);
    this.code = HttpStatus.NO_CONTENT.value();
  }
  public BadRequestException(Exception e) {
    super(HttpStatus.NO_CONTENT.value(),e);
    this.code = code;
  }
}
