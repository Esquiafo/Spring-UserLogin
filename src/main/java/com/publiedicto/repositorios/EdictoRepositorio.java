package com.publiedicto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.publiedicto.entidades.Edicto;

@Repository
public interface EdictoRepositorio extends JpaRepository<Edicto, String> {
	
	@Query("SELECT c FROM Edicto c WHERE c.tituloDelEdicto = :tituloDelEdicto ")
	public List<Edicto> buscarPorTitulo(@Param("tituloDelEdicto") String tituloDelEdicto);
	
	@Query("SELECT c FROM Edicto c WHERE c.estadoDeEdicto = :estadoDeEdicto ")
	public List<Edicto> buscarPorEstado(@Param("estadoDeEdicto") String estadoDeEdicto);
	
	@Query("SELECT c FROM Edicto c WHERE c.pagado = :pagado ")
	public List<Edicto> buscarPorPagado(@Param("pagado") String pagado);
	
}
