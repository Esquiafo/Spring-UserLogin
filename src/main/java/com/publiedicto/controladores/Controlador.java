package com.publiedicto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.publiedicto.errores.ErrorServicio;
import com.publiedicto.servicios.UsuarioServicio;

@Controller
public class Controlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	@GetMapping("/login" )
	public String login(@RequestParam(required = false) String error, /* @RequestParam(required = false) String logout,*/
			ModelMap model) {

		if (error != null) {
			model.put("error", "nombre de usuario o clave incorrectos.");
		}
		/*if (logout != null) {
			model.put("logout", "Ha salido correctamente.");
		}*/
		return "login.html";
	}
	
	@GetMapping("/inicio")
	public String Userprofile() {
		return "profile.html";
	}
	
	@GetMapping("/registro")
	public String registro() {
		return "crearUsuario.html";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, @RequestParam String email, @RequestParam String clave,
			@RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) String telefono)
			throws ErrorServicio {

		try {
			usuarioServicio.registrar(nombre, apellido, email, clave, telefono);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("email", email);
			modelo.put("clave1", email);
			return "crearUsuario.html";
		}
		modelo.put("titulo", "Bienvenido a Tinder de Mascotas");
		modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
		return "exito.html";
	}


}