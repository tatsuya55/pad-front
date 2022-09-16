package com.pad;

import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;

public class qqq {
    @Test
    public void test(){
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(path);
    }
}
