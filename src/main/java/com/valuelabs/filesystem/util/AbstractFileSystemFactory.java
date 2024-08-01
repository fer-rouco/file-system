package com.valuelabs.filesystem.util;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;

public interface AbstractFileSystemFactory {
   BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException;

   BaseFileSystemModel create(FileSystemType type, String name, String pathOfParent, boolean skipValidations)
         throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException;
}
