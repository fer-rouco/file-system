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
   public BaseFileSystemModel create(
      @RequestParam FileSystemType type,
      @RequestParam String name,
      @RequestParam String pathOfParent
   ) throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
      return fileSystemService.create(type, name, pathOfParent);
   }

   @DeleteMapping
   public String delete(@RequestParam String path) throws PathNotFoundException {
      return fileSystemService.delete(path);
   }

   @DeleteMapping(path = "clean")
   public String cleanAll() {
      return fileSystemService.cleanAll();
   }

   @PostMapping(path = "/move")
   public BaseFileSystemModel move(@RequestParam String sourcePath, @RequestParam String destinationPath)
      throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
      return fileSystemService.move(sourcePath, destinationPath);
   }

   @PostMapping(path = "/write-to-file")
   public BaseFileSystemModel writeToFile(@RequestParam String path, @RequestParam String content)
      throws PathNotFoundException, NonATextFileException {
      return fileSystemService.writeToFile(path, content);
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler({
      PathNotFoundException.class,
      PathAlreadyExistsException.class,
      IllegalFileSystemOperationException.class,
      NonATextFileException.class
   })
   public Map<String, String> handleException(Exception ex) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("message", ex.getMessage());
      return errorResponse;
   }

}
