package com.publiedicto.entidades;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.publiedicto.enumerados.Rol;

import lombok.Data;

@Data
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String IDusuario;
	private String nombre;
	private String apellido;
	private String email;
	private String clave;
	private String telefono;
	@Enumerated(EnumType.STRING)
	private Rol Rol;
	
}
