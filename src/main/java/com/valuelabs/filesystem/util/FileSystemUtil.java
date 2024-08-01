package com.valuelabs.filesystem.util;

import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;
import com.valuelabs.filesystem.util.strategy.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
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

   public int calculateSize(String pathOfParent) {
      Map<String, BaseFileSystemModel> subMap = ((TreeMap<String, BaseFileSystemModel>) this.inMemoryFileSystem).subMap(pathOfParent + "/", pathOfParent + "/" + "\uFFFF");
      return subMap
            .values()
            .stream()
            .mapToInt(BaseFileSystemModel::getSize)
            .sum();
   }
}
