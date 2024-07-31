package com.valuelabs.filesystem.exception;

public class IllegalFileSystemOperationException extends Exception {
   public IllegalFileSystemOperationException(String message) {
      super("Illegal file system operation exception. " + message);
   }

   public IllegalFileSystemOperationException() {
      super("Illegal file system operation exception.");
   }
}
