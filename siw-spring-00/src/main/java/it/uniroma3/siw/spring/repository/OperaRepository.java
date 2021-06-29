package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;

public interface OperaRepository extends CrudRepository<Opera,Long> {

	public List<Opera> findByTitolo (String titolo);
	
	public List<Opera> findByAutore (Artista autore);
	
	public List<Opera> findAll();
	
	public void deleteById(Long id);
	
	public List<Opera> findByOrderByTitolo();
	
	public List<Opera> findByOrderByDataRealizzazione();
}
