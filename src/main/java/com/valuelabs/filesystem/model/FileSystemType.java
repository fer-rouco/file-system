package com.valuelabs.filesystem.model;

public enum FileSystemType {
   DRIVE("drive"),
   FOLDER("folder"),
   TEXT_FILE("text-file"),
   ZIP_FILE("zip-file");

   public final String label;

   FileSystemType(String label) {
      this.label = label;
   }
}
