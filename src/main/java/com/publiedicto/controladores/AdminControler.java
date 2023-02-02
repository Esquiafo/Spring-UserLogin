package com.publiedicto.controladores;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.publiedicto.entidades.Edicto;
import com.publiedicto.entidades.Usuario;
import com.publiedicto.servicios.EdictoServicio;

@Controller
@RequestMapping("/admin")
public class AdminControler {
	
	
	@GetMapping("/dashboard")
	public String homeAdmin(ModelMap modelo) {
		//List<Edicto> edictos = EdictoServicio.todosLosEdictos();  Prioridad
		//modelo.put("edicto", edictos);
		return "admin.html";
	}
	
	@GetMapping("/cambiarEstado/{id}")
	public String cambiarEstado(ModelMap modelo, @PathVariable String id) {
		try {
			//EdictoServicio.cambiarEstado(id); Prioridad
			modelo.put("exito", "Estado del edicto modificado con Exito!");
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible cambiar el estado");
			return "admin.html";
		}
	}
	
	@GetMapping("/cambiar_rol/{id}")
	public String cambiarRol(ModelMap modelo, @PathVariable String id) {
		try {
			//usuarioServicio.cambiarRol(id);  S2 
			modelo.put("exito", "Rol modificado con Exito!");
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible reasignar el rol");
			return "inicioAdmin";
		}
	}
	
	@PostMapping("/clientes")
	public String verClientes() {
		//List<Usuario> usuarios = usuarioServicio.todosLosUsuarios();  Prioridad
		//modelo.put("usuarios", usuarios);
		return "ListaClientes.html";
	}

}
