package com.web.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
  private static final String FILE_EXTENSION_REG = "\\.([a-zA-Z_]+)$";
  /**
   * 获取文件的扩展名
   * @param filename
   * @return
   */
  public static String getFileExtension(String filename) {
    Pattern p = Pattern.compile(FILE_EXTENSION_REG);
    Matcher m = p.matcher(filename);
    return m.find() ? m.group(1) : null;
  }
}