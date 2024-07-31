package com.valuelabs.filesystem.util.strategy;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.ZipFile;

import java.util.Map;

public class FileSystemCreateZipFileStrategy extends FileSystemCreateStrategy {

   public FileSystemCreateZipFileStrategy(Map<String, BaseFileSystemModel> inMemoryFileSystem) {
      super(inMemoryFileSystem);
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
      return new ZipFile();
   }
}