package com.valuelabs.filesystem.util;

import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;
import com.valuelabs.filesystem.model.TextFile;
import com.valuelabs.filesystem.util.strategy.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Getter
@Component
public class FileSystemUtil {

   private final Map<String, BaseFileSystemModel> inMemoryFileSystem;
   private final FileSystemValidationHelper validatorHelper;

   public FileSystemUtil(Map<String, BaseFileSystemModel> inMemoryFileSystem, FileSystemValidationHelper fileSystemValidationHelper) {
      this.inMemoryFileSystem = inMemoryFileSystem;
      this.validatorHelper = fileSystemValidationHelper;
   }

   public FileSystemCreateStrategy chooseStrategy(FileSystemType type) {
      return switch (type) {
         case FileSystemType.DRIVE -> new FileSystemCreateDriveStrategy(this);
         case FileSystemType.FOLDER -> new FileSystemCreateFolderStrategy(this);
         case FileSystemType.TEXT_FILE -> new FileSystemCreateTextFileStrategy(this);
         case FileSystemType.ZIP_FILE -> new FileSystemCreateZipFileStrategy(this);
      };
   }

   public Map<String, BaseFileSystemModel> getChildren(String pathOfParent) {
      return ((TreeMap<String, BaseFileSystemModel>) inMemoryFileSystem).subMap(pathOfParent + PathFormatter.getDelimiter(), PathFormatter.buildPath(pathOfParent, "\uFFFF"));
   }

   public int calculateSizeOfChildren(String pathOfParent) {
      return getChildren(pathOfParent)
            .values()
            .stream()
            .mapToInt(BaseFileSystemModel::getSize)
            .sum();
   }

   public int calculateSize(String pathOfParent) {
      return switch (getType(pathOfParent)) {
         case FileSystemType.TEXT_FILE -> ((TextFile) inMemoryFileSystem.get(pathOfParent)).size();
         case FileSystemType.ZIP_FILE -> calculateSizeOfChildren(pathOfParent) / 2;
         default -> calculateSizeOfChildren(pathOfParent);
      };
   }

   public void updateSize(BaseFileSystemModel entity) {
      entity.setSize(calculateSize(entity.getPath()));
   }

   public void calculateAndUpdateSize(String... pathOfParent) {
      for (String path : pathOfParent) {
         Optional.ofNullable(inMemoryFileSystem.get(path)).ifPresent(this::updateSize);
         getChildren(path).values().forEach(this::updateSize);
      }
   }

   public FileSystemType getType(String path) {
      return inMemoryFileSystem.get(path).getType();
   }

   public String getTypeAsString(String path) {
      return switch (getType(path)) {
         case DRIVE -> "drive";
         case FOLDER -> "folder";
         case ZIP_FILE -> "zip file";
         case TEXT_FILE -> "text file";
      };
   }

   public void remove(String path) {
      inMemoryFileSystem.remove(path);
   }
}
