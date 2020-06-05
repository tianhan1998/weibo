package cn.aynu.java2.weibo.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
