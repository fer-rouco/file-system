package com.valuelabs.filesystem;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;
import com.valuelabs.filesystem.model.TextFile;
import com.valuelabs.filesystem.util.AbstractFileSystemFactory;
import com.valuelabs.filesystem.util.FileSystemUtil;
import com.valuelabs.filesystem.util.FileSystemValidationHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
public class FileSystemService {

   private final AbstractFileSystemFactory fileSystemFactory;
   private final FileSystemUtil fileSystemUtil;
   private final FileSystemValidationHelper fileSystemValidationHelper;

   private final Map<String, BaseFileSystemModel> inMemoryFileSystem;

   public FileSystemService(AbstractFileSystemFactory fileSystemFactory, FileSystemUtil fileSystemUtil)
      throws PathAlreadyExistsException, PathNotFoundException, IllegalFileSystemOperationException {

      this.fileSystemFactory = fileSystemFactory;
      this.fileSystemUtil = fileSystemUtil;
      this.inMemoryFileSystem = fileSystemUtil.getInMemoryFileSystem();
      this.fileSystemValidationHelper = fileSystemUtil.getValidatorHelper();

      BaseFileSystemModel rootFileSystemObject = fileSystemFactory.create(FileSystemType.FOLDER, "root", "", true);
      this.inMemoryFileSystem.put(rootFileSystemObject.getPath(), rootFileSystemObject);
   }

   public List<BaseFileSystemModel> get() {
      return inMemoryFileSystem.values().stream().toList();
   }

   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
      BaseFileSystemModel fileSystemObject = fileSystemFactory.create(type, name, pathOfParent);
      this.inMemoryFileSystem.put(fileSystemObject.getPath(), fileSystemObject);
      return fileSystemObject;
   }

   public String delete(String path) throws PathNotFoundException {
      fileSystemValidationHelper.validatePathNotFound(path);
      String returnMessage = MessageFormat.format("The {0} {1} was deleted successfully.", fileSystemUtil.getTypeAsString(path), path);
      fileSystemUtil.getChildren(path)
         .keySet()
         .stream()
         .toList()
         .forEach(fileSystemUtil::remove);
      fileSystemUtil.remove(path);
      return returnMessage;
   }

   public BaseFileSystemModel move(String sourcePath, String destinationPath)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
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
      textFile.setSize(textFile.size());
      return textFile;
   }
}
