package com.valuelabs.filesystem;

import com.valuelabs.filesystem.exception.IllegalFileSystemOperationException;
import com.valuelabs.filesystem.exception.NonATextFileException;
import com.valuelabs.filesystem.exception.PathAlreadyExistsException;
import com.valuelabs.filesystem.exception.PathNotFoundException;
import com.valuelabs.filesystem.model.BaseFileSystemModel;
import com.valuelabs.filesystem.model.FileSystemType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/filesystem")
public class FileSystemController {
   private final FileSystemService fileSystemService;

   public FileSystemController(FileSystemService fileSystemService) {
      this.fileSystemService = fileSystemService;
   }

   @GetMapping
   public List<BaseFileSystemModel> get() {
      return fileSystemService.get();
   }

   @PostMapping
   public BaseFileSystemModel create(@RequestParam FileSystemType type, @RequestParam String name, @RequestParam String pathOfParent)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {
      return fileSystemService.create(type, name, pathOfParent);
   }

   @DeleteMapping
   public BaseFileSystemModel delete(@RequestParam String path) throws PathNotFoundException {
      return fileSystemService.delete(path);
   }

   @PostMapping(path = "/move")
   public BaseFileSystemModel move(@RequestParam String sourcePath, @RequestParam String destinationPath)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException, NonATextFileException {
      return fileSystemService.move(sourcePath, destinationPath);
   }

   @PostMapping(path = "/write-to-file")
   public BaseFileSystemModel writeToFile(@RequestParam String path, @RequestParam String content)
      throws PathNotFoundException, NonATextFileException {
      return fileSystemService.writeToFile(path, content);
   }

   public Map<String, String> handleException(Exception ex) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("message", ex.getMessage());
      return errorResponse;
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(PathNotFoundException.class)
   public Map<String, String> handlePathNotFoundException(Exception ex) {
      return handleException(ex);
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(PathAlreadyExistsException.class)
   public Map<String, String> handlePathAlreadyExistsException(Exception ex) {
      return handleException(ex);
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(IllegalFileSystemOperationException.class)
   public Map<String, String> handleIllegalFileSystemOperationException(Exception ex) {
      return handleException(ex);
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(NonATextFileException.class)
   public Map<String, String> handleNonATextFileException(Exception ex) {
      return handleException(ex);
   }

}
