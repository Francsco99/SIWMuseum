package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;

import java.util.*;

public interface CollezioneRepository extends CrudRepository<Collezione,Long> {

	public List<Collezione> findByNome(String nome);
	
	public List<Collezione> findAll();
	
	public List<Collezione> findByCuratore(Curatore curatore);

}
