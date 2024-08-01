package com.valuelabs.filesystem.util.strategy;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.TextFile;
import com.valuelabs.filesystem.util.FileSystemUtil;

import java.util.Map;

public class FileSystemCreateTextFileStrategy extends FileSystemCreateStrategy {

   public FileSystemCreateTextFileStrategy(FileSystemUtil fileSystemUtil) {
      super(fileSystemUtil);
   }

   @Override
   public void validate(String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {

      fileSystemValidationHelper.validatePathNotFound(pathOfParent);
      fileSystemValidationHelper.validatePathAlreadyExists(name, pathOfParent);
      fileSystemValidationHelper.validateTextFileDoesNotContainAnyOtherEntity(pathOfParent);
   }

   @Override
   public BaseFileSystemModel create(String name, String pathOfParent) {
      TextFile textFile = new TextFile();
      textFile.setSize(textFile.size());
      return textFile;
   }

   @Override
   public int calculateSize(String pathOfParent) {
      return 0;
   }

}