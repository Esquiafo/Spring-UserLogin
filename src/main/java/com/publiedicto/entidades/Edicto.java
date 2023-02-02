package com.publiedicto.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.publiedicto.enumerados.EstadoDeEdicto;
import com.publiedicto.enumerados.TipoDeEdicto;

import lombok.Data;

@Data
@Entity
public class Edicto {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String IDedicto ;
	@Enumerated(EnumType.STRING)
	private EstadoDeEdicto estadoDeEdicto;
	@Enumerated(EnumType.STRING)
	private TipoDeEdicto tipoDeEdicto;
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	private Integer CantidadDeDias;
	private String tituloDelEdicto;
	private String campoDeTexto;
	private Integer cantPalabras; 
	private Integer precio;
	private String observaciones; 
	private Boolean pagado = false;
	
	@ManyToOne
	private Usuario cliente; 
	

}
