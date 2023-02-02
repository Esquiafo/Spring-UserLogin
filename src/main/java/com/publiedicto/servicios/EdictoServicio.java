package com.publiedicto.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.publiedicto.entidades.Edicto;
import com.publiedicto.entidades.Usuario;
import com.publiedicto.enumerados.TipoDeEdicto;
import com.publiedicto.errores.ErrorServicio;
import com.publiedicto.repositorios.EdictoRepositorio;
import com.publiedicto.repositorios.UsuarioRepositorio;

@Service
public class EdictoServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private EdictoRepositorio edictoRepositorio;

	@Transactional
	public void agregar(String IDusuario, String tipoDeEdicto, String tituloDelEdicto, String campoDeTexto, Integer cantidadDeDias, String observaciones, Date fechaInicio) throws ErrorServicio {

		Usuario usuario = usuarioRepositorio.getById(IDusuario);
		
		validar(tipoDeEdicto, tituloDelEdicto, campoDeTexto, cantidadDeDias, fechaInicio);
		
		Edicto edicto = new Edicto();
		
		edicto.setCliente(usuario);
		edicto.setTipoDeEdicto(TipoDeEdicto.valueOf(tipoDeEdicto));
		edicto.setCantidadDeDias(cantidadDeDias);
		edicto.setTituloDelEdicto(tituloDelEdicto);
		edicto.setFechaInicio(fechaInicio);
		edicto.setCampoDeTexto(campoDeTexto);
		edicto.setCantPalabras(contarPalabras(campoDeTexto));
		edicto.setObservaciones(observaciones);
		edicto.setPrecio(cotizarEdicto(contarPalabras(campoDeTexto), cantidadDeDias));
		
		edictoRepositorio.save(edicto);

	}

	@Transactional
	public void modificar(String usuario, String IDedicto, TipoDeEdicto tipoDeEdicto, String tituloDelEdicto,
			String campoDeTexto) throws ErrorServicio {

		validar1(tipoDeEdicto, tituloDelEdicto, campoDeTexto);

		Optional<Edicto> respuesta = edictoRepositorio.findById(usuario);
		if (respuesta.isPresent()) {
			Edicto edicto = respuesta.get();
			if (edicto.getCliente().getIDusuario().equals(usuario)) {
				edicto.setTipoDeEdicto(tipoDeEdicto);
				edicto.setTituloDelEdicto(tituloDelEdicto);
				edicto.setCampoDeTexto(campoDeTexto);
				edictoRepositorio.save(edicto);

			} else {
				throw new ErrorServicio("Los ID no coinciden");
			}
		}

		else {
			throw new ErrorServicio("No existe un edicto con el ID solicitado");
		}

	}

	@Transactional
	public void AnularEdicto(String IDusuario, String IDedicto) throws ErrorServicio {
		Optional<Edicto> respuesta = edictoRepositorio.findById(IDedicto);
		if (respuesta.isPresent()) {
			Edicto edicto = respuesta.get();
			if (edicto.getCliente().getIDusuario().equals(IDusuario)) {
				edictoRepositorio.delete(edicto);
			}
		} else {
			throw new ErrorServicio("El ID del edicto no se ha encontrado");
		}

	}

	public void validar(String tipoDeEdicto, String tituloDelEdicto, String campoDeTexto, Integer cantidadDeDias, Date fechaInicio) throws ErrorServicio {

		if ((tipoDeEdicto == null)) {
			throw new ErrorServicio("El TIPO DE EDICTO no puede ser nulo");
		}

		if ((tituloDelEdicto == null) || (tituloDelEdicto.isEmpty())) {
			throw new ErrorServicio("El TITULO DEL EDICTO no puede ser nulo/estar vacio");
		}

		if ((campoDeTexto == null)) {
			throw new ErrorServicio("El CAMPO DE TEXTO DEL EDICTO no puede ser nulo");
		}
		
		if (campoDeTexto.isEmpty()) {
			throw new ErrorServicio("El CAMPO DE TEXTO DEL EDICTO no puede estar vacio");
		}
		
		if (cantidadDeDias == null) {
			throw new ErrorServicio("la cantidad de dias no puede ser nula");
		}
		
		if (fechaInicio == null) {
			throw new ErrorServicio("La fecha de inicio no puede ser nula");
		}

	}

	public void validar1(TipoDeEdicto tipoDeEdicto, String tituloDelEdicto, String campoDeTexto) throws ErrorServicio {

		if ((tipoDeEdicto == null)) {
			throw new ErrorServicio("El TIPO DE EDICTO no puede ser nulo");
		}

		if ((tituloDelEdicto == null) || (tituloDelEdicto.isEmpty())) {
			throw new ErrorServicio("El TITULO DEL EDICTO no puede ser nulo/estar vacio");
		}

		if ((campoDeTexto == null) || (campoDeTexto.isEmpty())) {
			throw new ErrorServicio("El CAMPO DE TEXTO DEL EDICTO no puede ser nulo/estar vacio");
		}
	}

	public int contarPalabras(String campoDeTexto) {
		int cantPalabras = 0;

		String edicto = campoDeTexto;
		String[] words = edicto.split("\\s+");

		cantPalabras = words.length;

		return cantPalabras;
	}

	public int cotizarEdicto(int cantPalabras, int cantDias) {
		int precio = 0;
		int fracciones = Math.round(cantPalabras / 16);

		precio = ((fracciones * cantDias) * 16) + 300;

		return precio;
	}

	public void cambiarEstado(String id, String Estado) {

    	Optional<Edicto> respuesta = edictoRepositorio.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Edicto edicto = respuesta.get();
    				
    		edicto.setEstadoDeEdicto(edicto.getEstadoDeEdicto().valueOf(Estado));
    	
    	}
	}

	public List<Edicto> TodosLosEdictos() {

		List<Edicto> Edictos = edictoRepositorio.findAll();

		return Edictos;

	}

}
