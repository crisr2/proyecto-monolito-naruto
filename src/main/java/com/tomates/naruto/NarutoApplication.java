package com.tomates.naruto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NarutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NarutoApplication.class, args);
        System.out.println("Servidor Aplicación Ninjas iniciada en http://localhost:8080/");
   }
}
