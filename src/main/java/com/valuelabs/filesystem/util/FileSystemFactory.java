package com.valuelabs.filesystem.util;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.*;
import com.valuelabs.filesystem.util.strategy.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileSystemFactory implements AbstractFileSystemFactory {

   private final Map<String, BaseFileSystemModel> inMemoryFileSystem;

   public FileSystemFactory(Map<String, BaseFileSystemModel> inMemoryFileSystem) {
      this.inMemoryFileSystem = inMemoryFileSystem;
   }

   @Override
   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent, boolean skipValidations) throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {

      FileSystemCreateStrategy createStrategy = switch (type) {
         case FileSystemType.DRIVE -> new FileSystemCreateDriveStrategy(inMemoryFileSystem);
         case FileSystemType.FOLDER -> new FileSystemCreateFolderStrategy(inMemoryFileSystem);
         case FileSystemType.TEXT_FILE -> new FileSystemCreateTextFileStrategy(inMemoryFileSystem);
         case FileSystemType.ZIP_FILE -> new FileSystemCreateZipFileStrategy(inMemoryFileSystem);
      };

      BaseFileSystemModel baseFileSystemModel =
            skipValidations ? createStrategy.create(name, pathOfParent) : createStrategy.validateAndCreate(name, pathOfParent);

      baseFileSystemModel.setType(type);
      baseFileSystemModel.setName(name);
      baseFileSystemModel.buildPath(pathOfParent, name);

      return baseFileSystemModel;
   }


   @Override
   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {
      return create(type, name, pathOfParent, false);
   }

}
