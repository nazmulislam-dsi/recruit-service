package org.ni.recruitservice.exception;

import lombok.Data;

public @Data
class ApiException extends RuntimeException {
  private int code;
  public ApiException(int code, String msg) {
    super(msg);
    this.code = code;
  }
  public ApiException(int code, Exception e) {
    super(e);
    this.code = code;
  }
}
