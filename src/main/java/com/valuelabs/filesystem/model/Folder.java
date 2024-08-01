package com.valuelabs.filesystem.model;

public class Folder extends BaseFileSystemModel {
   @Override
   protected BaseFileSystemModel newInstance() {
      return new Folder();
   }
}
