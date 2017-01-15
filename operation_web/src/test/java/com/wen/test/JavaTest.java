package com.wen.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huwenwen
 * @since 17/1/15
 */
public class JavaTest {
  public static void main(String[] args) {
    Map<String,String> map = new HashMap<>();
    Map<String,String> map2 = new HashMap<>();
    map.put("aa", "1111");
    map.put("bb", "1111");
    map.put("cc", "1111");
    map.put("dd", "1111");
    map2.put("dd", "22222");
    map2.put("ff", "22222");
    map.putAll(map2);
    System.out.println(map.size());
    System.out.println(map.get("dd"));

  }
}
