package com.valuelabs.filesystem.util;

public class PathFormatter {

   private PathFormatter() {}

   private static final String DELIMITER = "/";
   private static final String ROOT_NAME = "root";

   public static String getDelimiter() {
      return DELIMITER;
   }

   public static String getRootName() {
      return ROOT_NAME;
   }

   public static String getRootPath() {
      return DELIMITER.concat(ROOT_NAME);
   }

   public static String buildPath(String... paths) {
      return
         DELIMITER.concat(
            String.join(DELIMITER, paths)
         ).replace(
            DELIMITER.concat(DELIMITER), DELIMITER
         );
   }

   public static String getParentPath(String path) {
      int indexOfLastPathSeparator = path.lastIndexOf("/");
      return (indexOfLastPathSeparator > 0) ? path.substring(0, indexOfLastPathSeparator) : null;
   }

   public static String removeTrailingSlash(String path) {
      return (path != null) ? path.replaceAll("/$", "") : null;
   }
}
