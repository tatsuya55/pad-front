package com.pad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.pad.mapper"})
@SpringBootApplication
public class PadFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(PadFrontApplication.class);
    }
}
