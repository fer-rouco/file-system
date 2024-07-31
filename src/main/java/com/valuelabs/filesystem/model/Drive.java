package com.valuelabs.filesystem.model;

public class Drive extends BaseFileSystemModel {
   protected int size() {
      return childrenSumSize();
   }
}
