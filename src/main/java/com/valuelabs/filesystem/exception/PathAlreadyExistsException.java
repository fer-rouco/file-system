package com.valuelabs.filesystem.exception;

import java.text.MessageFormat;

public class PathAlreadyExistsException extends Exception {
   public PathAlreadyExistsException(String path) {
      super(MessageFormat.format("Path {0} already exists exception.", path));
   }
}
