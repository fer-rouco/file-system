package com.valuelabs.filesystem.model;

public class Folder extends BaseFileSystemModel {
   protected int size() {
      return childrenSumSize();
   }
}
