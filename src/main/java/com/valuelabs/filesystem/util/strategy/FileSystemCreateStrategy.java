package com.valuelabs.filesystem.util.strategy;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.util.FileSystemUtil;
import com.valuelabs.filesystem.util.FileSystemValidationHelper;

public abstract class FileSystemCreateStrategy {
   protected final FileSystemUtil fileSystemUtil;
   protected final FileSystemValidationHelper fileSystemValidationHelper;

   protected FileSystemCreateStrategy(FileSystemUtil fileSystemUtil) {
      this.fileSystemUtil = fileSystemUtil;
      this.fileSystemValidationHelper = fileSystemUtil.getValidatorHelper();
   }

   public abstract BaseFileSystemModel create(String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException;

   abstract void validate(String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException;

   public BaseFileSystemModel validateAndCreate(String name, String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {

      this.validate(name, pathOfParent);
      BaseFileSystemModel baseFileSystemModel = this.create(name, pathOfParent);
      baseFileSystemModel.setSize(this.calculateSize(pathOfParent));

      return baseFileSystemModel;
   }

   public int calculateSize(String pathOfParent) {
      return fileSystemUtil.calculateSize(pathOfParent);
   }
}
