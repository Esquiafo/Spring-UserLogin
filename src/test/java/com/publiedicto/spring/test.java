package com.publiedicto.spring;

import java.sql.Date;
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


public class test {

	private UsuarioRepositorio usuarioRepositorio;

	private EdictoRepositorio edictoRepositorio;

	public void agregar(Usuario usuario, String tipoDeEdicto, String tituloDelEdicto, String campoDeTexto,
			Date FechaInicio, Integer cantidadDeDias, String observaciones) throws ErrorServicio {

		validar(tipoDeEdicto, tituloDelEdicto, campoDeTexto);

		Edicto edicto = new Edicto();

		usuario = usuarioRepositorio.buscarPorMail(usuario.getEmail());

		edicto.setCliente(usuario);
		edicto.setTipoDeEdicto(TipoDeEdicto.valueOf(tipoDeEdicto));
		edicto.setCantidadDeDias(cantidadDeDias);
		edicto.setTituloDelEdicto(tituloDelEdicto);
		edicto.setCampoDeTexto(campoDeTexto);
		edicto.setCantPalabras(contarPalabras(campoDeTexto));
		edicto.setObservaciones(observaciones);
		edicto.setPrecio(cotizarEdicto(contarPalabras(campoDeTexto), cantidadDeDias));

		edictoRepositorio.save(edicto);

	}

	public void modificar(String IDusuario, String IDedicto, TipoDeEdicto tipoDeEdicto, String tituloDelEdicto,
			String campoDeTexto) throws ErrorServicio {

		validar1(tipoDeEdicto, tituloDelEdicto, campoDeTexto);

		Optional<Edicto> respuesta = edictoRepositorio.findById(IDusuario);
		if (respuesta.isPresent()) {
			Edicto edicto = respuesta.get();
			if (edicto.getCliente().getIDusuario().equals(IDusuario)) {
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

	public void validar(String tipoDeEdicto, String tituloDelEdicto, String campoDeTexto) throws ErrorServicio {

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

	public void cambiarEstado(Edicto edicto) {

		// edicto.setEstadoDeEdicto();

	}

	public List<Edicto> TodosLosEdictos() {

		List<Edicto> Edictos = edictoRepositorio.findAll();

		return Edictos;

	}

	public void agregar(Usuario user, String tipoDeEdicto, String tituloDelEdicto, String campoDeTexto,
			java.util.Date date, Integer n, String observaciones) {
		// TODO Auto-generated method stub
		
	}
}
