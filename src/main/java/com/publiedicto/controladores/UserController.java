package com.publiedicto.controladores;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.publiedicto.entidades.Edicto;
import com.publiedicto.entidades.Usuario;
import com.publiedicto.errores.ErrorServicio;
import com.publiedicto.servicios.EdictoServicio;
import com.publiedicto.servicios.UsuarioServicio;

@Controller
@RequestMapping("/inicio")
public class UserController {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@Autowired
	private EdictoServicio edictoServicio;

	@GetMapping("/crearEdicto")
	public String crearEdicto() {
		return "crearEdicto.html";
	}
	
	
	@PostMapping("/publicar")
	public String agregarEdicto(@RequestParam String IDusuario, ModelMap modelo, @RequestParam(required = false) String tipoDeEdicto,
			@RequestParam(required = false) String tituloDelEdicto, @RequestParam(required = false) String CampoDeTexto,
			@RequestParam(required = false) Integer cantidadDeDias, @RequestParam(required = false) String Observaciones,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Date fechaInicio)
			throws ErrorServicio {
		try {
			edictoServicio.agregar(IDusuario, tipoDeEdicto, tituloDelEdicto, CampoDeTexto, cantidadDeDias, Observaciones, fechaInicio);		
		} catch (ErrorServicio e) {
			
			 modelo.put("error", e.getMessage()); 
			 //modelo.put("tipoDeEdicto", tipoDeEdicto);
			 modelo.put("TituloDelEdicto", tituloDelEdicto);
			 modelo.put("CampoDeTexto", CampoDeTexto);
			 
			return "crearEdicto.html";
		}
		modelo.put("titulo", "Bienvenido a Tinder de Mascotas");
		modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
		return "exito.html";
	}

	
	@GetMapping("/misEdictos")
	public String misEdictos(ModelMap modelo) {

		List<Edicto> Edictos = edictoServicio.TodosLosEdictos();

		modelo.put("edictos", Edictos);

		return "misEdictos.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR','ROLE_CLIENTE')")
	@GetMapping("/modificarPerfil")
	public String modificarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {

		Usuario login = (Usuario) session.getAttribute("usuariosession");
		if (login == null || !login.getIDusuario().equals(id)) {
			return "redirect:/inicio";
		}

		try {
			Usuario usuario = usuarioServicio.buscarPorId(id);

			model.addAttribute("perfil", usuario);

		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
		}
		return "modificarPerfil.html";
	}

}
