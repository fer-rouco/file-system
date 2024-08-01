package com.valuelabs.filesystem.model;

public class Drive extends BaseFileSystemModel {
   @Override
   protected BaseFileSystemModel newInstance() {
      return new Drive();
   }
}
