package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.*;
import it.uniroma3.siw.spring.model.Curatore;

public interface CuratoreRepository extends CrudRepository<Curatore, Long> {

	public List<Curatore> findByNome(String nome);
	
	public List<Curatore> findByNomeAndCognome(String nome, String cognome);
	
	public List<Curatore> findAll();
}
