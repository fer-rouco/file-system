package com.valuelabs.filesystem.exception;

import java.text.MessageFormat;

public class PathNotFoundException extends Exception {
   public PathNotFoundException(String path) {
      super(MessageFormat.format("Path {0} not found exception.", path));
   }
}
