package com.pad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PadFrontApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(PadFrontApplication.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
