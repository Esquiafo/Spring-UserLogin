package com.publiedicto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.publiedicto.servicios.UsuarioServicio;

@SpringBootApplication
public class PubliEdictoApplication {
	
	 public static void main(String[] args) {
	        SpringApplication.run(PubliEdictoApplication.class, args);
	    }
	
}

