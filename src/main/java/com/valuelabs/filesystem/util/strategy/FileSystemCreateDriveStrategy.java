package com.valuelabs.filesystem.util.strategy;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.Drive;

import java.util.Map;

public class FileSystemCreateDriveStrategy extends FileSystemCreateStrategy {

   public FileSystemCreateDriveStrategy(Map<String, BaseFileSystemModel> inMemoryFileSystem) {
      super(inMemoryFileSystem);
   }

   @Override
   public void validate(String name, String pathOfParent)
         throws PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {
      fileSystemValidationHelper.validatePathAlreadyExists(name, pathOfParent);
      fileSystemValidationHelper.validateTextFileDoesNotContainAnyOtherEntity(pathOfParent);
      fileSystemValidationHelper.validateDriveIsNotContainedInAnyEntity(pathOfParent);
   }

   @Override
   public BaseFileSystemModel create(String name, String pathOfParent) {
      return new Drive();
   }
}
