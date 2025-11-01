package com.tomates.naruto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NarutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NarutoApplication.class, args);
        System.out.println("\n Servidor Aplicacion Gestión de Ninjas iniciado en http://localhost:8080/ \n Hecho por el Grupo Tomates\n");
   }
}
