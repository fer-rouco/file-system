package com.valuelabs.filesystem;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;
import com.valuelabs.filesystem.model.TextFile;
import com.valuelabs.filesystem.util.AbstractFileSystemFactory;
import com.valuelabs.filesystem.util.FileSystemValidationHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FileSystemService {

   private final AbstractFileSystemFactory fileSystemFactory;
   private final FileSystemValidationHelper fileSystemValidationHelper;

   private final Map<String, BaseFileSystemModel> inMemoryFileSystem;

   public FileSystemService(AbstractFileSystemFactory fileSystemFactory, FileSystemValidationHelper fileSystemValidationHelper, Map<String, BaseFileSystemModel> inMemoryFileSystem)
      throws PathAlreadyExistsException, NonATextFileException, PathNotFoundException, IllegalFileSystemOperationException {

      this.fileSystemFactory = fileSystemFactory;
      this.fileSystemValidationHelper = fileSystemValidationHelper;
      this.inMemoryFileSystem = inMemoryFileSystem;

      BaseFileSystemModel rootFileSystemObject = fileSystemFactory.create(FileSystemType.FOLDER, "root", "", true);
      this.inMemoryFileSystem.put(rootFileSystemObject.getPath(), rootFileSystemObject);
   }

   public List<BaseFileSystemModel> get() {
      return inMemoryFileSystem.values().stream().toList();
   }

   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {
      BaseFileSystemModel fileSystemObject = fileSystemFactory.create(type, name, pathOfParent);
      this.inMemoryFileSystem.put(fileSystemObject.getPath(), fileSystemObject);
      return fileSystemObject;
   }

   public BaseFileSystemModel delete(String path) throws PathNotFoundException {
      fileSystemValidationHelper.validatePathNotFound(path);
      return inMemoryFileSystem.remove(path);
   }

   public BaseFileSystemModel move(String sourcePath, String destinationPath)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {
      fileSystemValidationHelper.validatePathNotFound(sourcePath);
      BaseFileSystemModel entityToRemove = inMemoryFileSystem.get(sourcePath);
      BaseFileSystemModel newEntity = fileSystemFactory.create(entityToRemove.getType(), entityToRemove.getName(), destinationPath);
      this.inMemoryFileSystem.put(newEntity.getPath(), newEntity);
      inMemoryFileSystem.remove(sourcePath);
      return newEntity;
   }

   public BaseFileSystemModel writeToFile(String path, String content) throws PathNotFoundException, NonATextFileException {
      fileSystemValidationHelper.validatePathNotFound(path);
      fileSystemValidationHelper.validateNonATextFile(path);
      TextFile textFile = (TextFile) inMemoryFileSystem.get(path);
      textFile.setContent(content);
      return textFile;
   }
}
