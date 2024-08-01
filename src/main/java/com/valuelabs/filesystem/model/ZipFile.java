package com.valuelabs.filesystem.model;

public class ZipFile extends BaseFileSystemModel {
   @Override
   protected BaseFileSystemModel newInstance() {
      return new ZipFile();
   }
}
