package com.valuelabs.filesystem.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Setter
@Getter
public class TextFile extends BaseFileSystemModel {
   private String content;

   public int size() {
      return !Strings.isEmpty(content) ? content.length() : 0;
   }
}
