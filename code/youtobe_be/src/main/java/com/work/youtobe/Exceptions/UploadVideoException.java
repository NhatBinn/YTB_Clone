package com.work.youtobe.Exceptions;

public class UploadVideoException extends RuntimeException {
     public UploadVideoException(String message) {
          super(message);
     }

     public UploadVideoException(String message, Throwable cause) {
          super(message, cause);
     }
}