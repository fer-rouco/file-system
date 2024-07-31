package com.valuelabs.filesystem.util.strategy;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.util.FileSystemValidationHelper;

import java.util.Map;

public abstract class FileSystemCreateStrategy {
   protected final FileSystemValidationHelper fileSystemValidationHelper;
   protected final Map<String, BaseFileSystemModel> inMemoryFileSystem;

   protected FileSystemCreateStrategy(Map<String, BaseFileSystemModel> inMemoryFileSystem) {
      this.inMemoryFileSystem = inMemoryFileSystem;
      this.fileSystemValidationHelper = new FileSystemValidationHelper(inMemoryFileSystem);
   }

   public abstract BaseFileSystemModel create(String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException;

   abstract void validate(String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException;

   public BaseFileSystemModel validateAndCreate(String name, String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {

      this.validate(name, pathOfParent);
      return this.create(name, pathOfParent);
   }

}
