package com.publiedicto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.publiedicto.entidades.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

	@Query("SELECT c FROM Usuario c WHERE c.email = :email ")
	public Usuario buscarPorMail(@Param("email") String email);
	
	@Query("SELECT c FROM Usuario c WHERE c.IDusuario = :IDusuario ")
	public Usuario buscarPorId(@Param("IDusuario") String IDusuario);

}
