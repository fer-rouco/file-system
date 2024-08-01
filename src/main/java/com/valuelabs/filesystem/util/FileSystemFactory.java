package com.valuelabs.filesystem.util;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.*;
import com.valuelabs.filesystem.util.strategy.*;
import org.springframework.stereotype.Component;

@Component
public class FileSystemFactory implements AbstractFileSystemFactory {

   private final FileSystemUtil fileSystemUtil;

   public FileSystemFactory(FileSystemUtil fileSystemUtil) {
      this.fileSystemUtil = fileSystemUtil;
   }

   @Override
   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent, boolean skipValidations) throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {

      FileSystemCreateStrategy strategy = fileSystemUtil.chooseStrategy(type);

      BaseFileSystemModel baseFileSystemModel =
            skipValidations ? strategy.create(name, pathOfParent) : strategy.validateAndCreate(name, pathOfParent);

      baseFileSystemModel.setType(type);
      baseFileSystemModel.setName(name);
      baseFileSystemModel.buildPath(pathOfParent, name);

      return baseFileSystemModel;
   }


   @Override
   public BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
      return create(type, name, pathOfParent, false);
   }

}
