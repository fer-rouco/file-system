package com.valuelabs.filesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valuelabs.filesystem.util.PathFormatter;
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

   protected abstract BaseFileSystemModel newInstance();

   public void buildPath(String basePath, String name) {
      setPath(PathFormatter.buildPath(basePath, Strings.isEmpty(name) ? "" : name));
   }

   @JsonIgnore
   public String getParentPath() {
      return PathFormatter.getParentPath(path);
   }

   @Override
   public String toString() {
      return
         "Type: " + type + " - " +
         "Name: " + name + " - " +
         "Path: " + path + " - " +
         "Size: " + size;
   }

   public BaseFileSystemModel copy() {
      BaseFileSystemModel clone = newInstance();
      clone.setType(type);
      clone.setName(name);
      clone.setPath(path);
      clone.setSize(size);
      return clone;
   }
}
