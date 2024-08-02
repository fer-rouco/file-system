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
import com.valuelabs.filesystem.util.PathFormatter;
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

      BaseFileSystemModel rootFileSystemObject = fileSystemFactory.create(FileSystemType.FOLDER, PathFormatter.getRootName(), "", true);
      inMemoryFileSystem.put(rootFileSystemObject.getPath(), rootFileSystemObject);
   }

   public List<BaseFileSystemModel> get() {
      return inMemoryFileSystem.values().stream().toList();
   }

   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
      pathOfParent = PathFormatter.removeTrailingSlash(pathOfParent);
      BaseFileSystemModel fileSystemObject = fileSystemFactory.create(type, name, pathOfParent);
      inMemoryFileSystem.put(fileSystemObject.getPath(), fileSystemObject);
      return fileSystemObject;
   }

   public String delete(String path) throws PathNotFoundException {
      path = PathFormatter.removeTrailingSlash(path);
      fileSystemValidationHelper.validatePathNotFound(path);
      String pathOfParent = inMemoryFileSystem.get(path).getParentPath();
      String type = fileSystemUtil.getTypeAsString(path);
      fileSystemUtil.getChildren(path)
         .keySet()
         .stream()
         .toList()
         .forEach(fileSystemUtil::remove);
      fileSystemUtil.remove(path);
      fileSystemUtil.calculateAndUpdateSize(path, pathOfParent);
      return MessageFormat.format("The {0} {1} was deleted successfully.", type, path);
   }

   public BaseFileSystemModel move(String sourcePath, String destinationPath)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
      sourcePath = PathFormatter.removeTrailingSlash(sourcePath);
      destinationPath = PathFormatter.removeTrailingSlash(destinationPath);
      fileSystemValidationHelper.validatePathNotFound(sourcePath);
      fileSystemValidationHelper.validatePathNotFound(destinationPath);
      BaseFileSystemModel entityToRemove = inMemoryFileSystem.get(sourcePath);
      BaseFileSystemModel newEntity = entityToRemove.copy();
      newEntity.setPath(PathFormatter.buildPath(destinationPath, newEntity.getName()));
      inMemoryFileSystem.remove(sourcePath);
      inMemoryFileSystem.put(newEntity.getPath(), newEntity);
      fileSystemUtil.calculateAndUpdateSize(destinationPath);
      for (String child : fileSystemUtil.getChildren(sourcePath).keySet()) {
         this.move(child, newEntity.getPath());
      }
      return newEntity;
   }

   public BaseFileSystemModel writeToFile(String path, String content) throws PathNotFoundException, NonATextFileException {
      path = PathFormatter.removeTrailingSlash(path);
      fileSystemValidationHelper.validatePathNotFound(path);
      fileSystemValidationHelper.validateNonATextFile(path);
      TextFile textFile = (TextFile) inMemoryFileSystem.get(path);
      textFile.setContent(content);
      textFile.setSize(textFile.size());
      fileSystemUtil.calculateAndUpdateSize(textFile.getParentPath());
      return textFile;
   }

   public String cleanAll() {
      final String ROOT_PATH = PathFormatter.getRootPath();
      BaseFileSystemModel rootCopy = inMemoryFileSystem.get(ROOT_PATH).copy();
      inMemoryFileSystem.clear();
      inMemoryFileSystem.put(ROOT_PATH, rootCopy);
      return "Cleaning done successfully.";
   }
}
