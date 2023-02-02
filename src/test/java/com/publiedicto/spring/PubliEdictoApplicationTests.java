package com.publiedicto.spring;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.publiedicto.entidades.Usuario;

@SpringBootTest
class PubliEdictoApplicationTests {
	
	//EdictoRepositorio edictoRepo=  new EdictoRepositorio();

	@Test
	void contextLoads() {
		
		test t = new test();
		
		Integer n = 2;
		
		Usuario user = new Usuario();
				
		Date date = new Date();
				
		t.agregar(user, "JUDICIAL", "Edicto", "casdfasdfasdfasdfasasddfgdfgf", date, n, "sajdfasdf");
		
		n+=1;
		
		t.agregar(user, "JUDICIAL", "Edicto2", "dfgddddfgdfgdgdgdg", date, n, "sajdfasdf");
		
		n+=1;
		
		t.agregar(user, "JUDICIAL", "Edicto3", "casdfasdfadfgdfgdfsdfasdfasasdf", date, n, "sajdfasdf");
		
		n+=3;
		
		t.agregar(user, "JUDICIAL", "Edict4", "casdfasdfasdddddddddddddddddddddddddddffffffffffdfasdfasasdf", date, n, "sajdfasdf");
		
		System.out.println(t.TodosLosEdictos().toString());
		
		
	}

}
