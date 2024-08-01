package com.valuelabs.filesystem.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public abstract class BaseFileSystemModel {
   private FileSystemType type;
   private String name;
   private String path;
   private int size;

   public void buildPath(String basePath, String name) {
      setPath(basePath + ((!Strings.isEmpty(name)) ? "/" + name : ""));
   }

   @Override
   public String toString() {
      return
         "Type: " + type + " - " +
         "Name: " + name + " - " +
         "Path: " + path + " - " +
         "Size: " + size;
   }
}
