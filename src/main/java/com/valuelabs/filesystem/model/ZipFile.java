package com.valuelabs.filesystem.model;

public class ZipFile extends BaseFileSystemModel {
   protected int size() {
      return childrenSumSize() / 2;
   }
}
