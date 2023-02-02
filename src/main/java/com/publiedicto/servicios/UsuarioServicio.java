package com.publiedicto.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.publiedicto.entidades.Usuario;
import com.publiedicto.enumerados.Rol;
import com.publiedicto.errores.ErrorServicio;
import com.publiedicto.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void registrar(String nombre, String apellido, String email, String clave, String telefono)
			throws ErrorServicio {

		validar(nombre, apellido, email, clave);

		Usuario usuario = new Usuario();

		usuario.setIDusuario(usuario.getIDusuario());
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(email);
		String encriptada = new BCryptPasswordEncoder().encode(clave);
		usuario.setClave(encriptada);
		usuario.setRol(Rol.CLIENTE);
		usuario.setTelefono(telefono);
		usuarioRepositorio.save(usuario);

	}

	@Transactional
	public void modificar(String IDusuario, String nombre, String apellido, String email, String clave)
			throws ErrorServicio {

		validar(nombre, apellido, email, clave);

		Optional<Usuario> respuesta = usuarioRepositorio.findById(IDusuario);
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setEmail(email);
			String encriptada = new BCryptPasswordEncoder().encode(clave);
			usuario.setClave(encriptada);
			usuarioRepositorio.save(usuario);
		} else {
			throw new ErrorServicio("NO SE ENCONTRÓ EL ID SOLICITADO");
		}
	}

	@Transactional
	public void eliminar(String IDusuario) throws ErrorServicio {

		Optional<Usuario> respuesta = usuarioRepositorio.findById(IDusuario);
		if (respuesta.isPresent()) {

			Usuario usuario = respuesta.get();

			usuarioRepositorio.delete(usuario);

		} else {
			throw new ErrorServicio("NO SE HA ENCONTRADO AL USUARIO SOLICITADO (ELIMINAR)");
		}
	}

	public void validar(String nombre, String apellido, String email, String clave) throws ErrorServicio {
		
		Optional<Usuario> respuesta = Optional.ofNullable(usuarioRepositorio.buscarPorMail(email));

		if ((nombre == null) || (nombre.isEmpty())) {
			throw new ErrorServicio("El NOMBRE del USUARIO no puede ser nulo/estar vacio");
		}

		if ((apellido == null) || (apellido.isEmpty())) {
			throw new ErrorServicio("El APELLIDO del USUARIO no puede ser nulo/estar vacio");
		}

		if ((email == null) || (email.isEmpty())) {
			throw new ErrorServicio("El EMAIL del USUARIO no puede ser nulo/estar vacio");
		}

		if ((clave == null) || (clave.isEmpty())) {
			throw new ErrorServicio("La CLAVE del USUARIO no puede ser nulo/estar vacio");
		}
		
		if (respuesta.isPresent()) {

			throw new ErrorServicio("El EMAIL del USUARIO no puede ser el mismo que el de otro usuario;");

		}


	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepositorio.buscarPorMail(email);

		if (usuario != null) {

			List<GrantedAuthority> permisos = new ArrayList<>();

			GrantedAuthority cliente = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());

			permisos.add(cliente);
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario); // llave + valor

			User user = new User(usuario.getEmail(), usuario.getClave(), permisos);

			return user;

		} else {
			return null;
		}
	}
	
	
    @Transactional
    public void cambiarRol(String id) throws ErrorServicio{
    
    	Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.CLIENTE)) {
    			
    		usuario.setRol(Rol.ADMINISTRADOR);
    		
    		}else if(usuario.getRol().equals(Rol.ADMINISTRADOR)) {
    			usuario.setRol(Rol.CLIENTE);
    		}
    	}
    }
    
    @Transactional
    public Usuario buscarPorId(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            return usuario;
        } else {

            throw new ErrorServicio("No se encontró el usuario solicitado");
        }

    }
	
	/*
	public List<Edicto> TodosLosUsuarios(){
		
		List<Edicto> usuarios = usuarioRepositorio.findAll();
		
		return usuarios;
	}
	*/

}
