package com.valuelabs.filesystem.exception;

import java.text.MessageFormat;

public class NonATextFileException extends Exception {
   public NonATextFileException(String path) {
      super(MessageFormat.format("The path {0} is not a text file exception.", path));
   }
}
