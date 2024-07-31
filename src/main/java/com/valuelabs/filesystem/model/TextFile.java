package com.valuelabs.filesystem.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TextFile extends BaseFileSystemModel {
   private String content;

   protected int size() {
      return content.length();
   }
}
