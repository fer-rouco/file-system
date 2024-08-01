package com.valuelabs.filesystem.util;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileSystemValidationHelper {
   private final Map<String, BaseFileSystemModel> inMemoryFileSystem;

   public FileSystemValidationHelper(Map<String, BaseFileSystemModel> inMemoryFileSystem) {
      this.inMemoryFileSystem = inMemoryFileSystem;
   }

   public void validatePathNotFound(String path) throws PathNotFoundException {
      if (this.inMemoryFileSystem.get(path) == null) {
         throw new PathNotFoundException(path);
      }
   }

   public void validatePathAlreadyExists(String path) throws PathAlreadyExistsException {
      if (this.inMemoryFileSystem.get(path) != null) {
         throw new PathAlreadyExistsException(path);
      }
   }

   public void validatePathAlreadyExists(String name, String pathOfParent) throws PathAlreadyExistsException {
      this.validatePathAlreadyExists(pathOfParent + "/" + name);
   }

   public void validateTextFileDoesNotContainAnyOtherEntity(String pathOfParent) throws IllegalFileSystemOperationException {
      BaseFileSystemModel fileSystemModel = this.inMemoryFileSystem.get(pathOfParent);
      if (fileSystemModel != null && fileSystemModel.getType() == FileSystemType.TEXT_FILE) {
         throw new IllegalFileSystemOperationException("Text file does not contain any other entity.");
      }
   }

   public void validateDriveIsNotContainedInAnyEntity(String pathOfParent) throws IllegalFileSystemOperationException {
      if (!Strings.isEmpty(pathOfParent)) {
         throw new IllegalFileSystemOperationException("A drive is not contained in any entity.");
      }
   }

   public void validateNonATextFile(String path) throws NonATextFileException {
      BaseFileSystemModel fileSystemModel = this.inMemoryFileSystem.get(path);
      if (fileSystemModel != null && fileSystemModel.getType() != FileSystemType.TEXT_FILE) {
         throw new NonATextFileException(path);
      }
   }
}
