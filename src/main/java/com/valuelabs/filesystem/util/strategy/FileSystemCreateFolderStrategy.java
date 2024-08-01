package com.valuelabs.filesystem.util.strategy;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.Folder;
import com.valuelabs.filesystem.util.FileSystemUtil;

import java.util.Map;

public class FileSystemCreateFolderStrategy extends FileSystemCreateStrategy {

   public FileSystemCreateFolderStrategy(FileSystemUtil fileSystemUtil) {
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
      return new Folder();
   }
}
