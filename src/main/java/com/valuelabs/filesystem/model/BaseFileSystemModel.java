package com.valuelabs.filesystem.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public abstract class BaseFileSystemModel {
   private @NonNull FileSystemType type;
   private @NonNull String name;
   private @NonNull String path;

   @Getter
   private final Map<String, BaseFileSystemModel> nodes = new HashMap<>();

   protected abstract int size();

   protected int childrenSumSize() {
      return nodes.values().stream()
            .mapToInt(BaseFileSystemModel::size)
            .sum();
   }

   public void buildPath(String basePath, String name) {
      this.setPath(basePath + ((!Strings.isEmpty(name)) ? "/" + name : ""));
   }

   @Override
   public String toString() {
      return
         "Type: " + type + " - " +
         "Name: " + name + " - " +
         "Path: " + path + " - " +
         "Size: " + size();
   }
}
