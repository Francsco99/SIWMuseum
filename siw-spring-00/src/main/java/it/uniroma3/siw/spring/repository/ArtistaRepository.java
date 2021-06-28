package it.uniroma3.siw.spring.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;

public interface ArtistaRepository extends CrudRepository<Artista,Long> {

	public Artista findByNome(String nome);
	
	public List<Artista> findAllByNome(String nome);
	
	public List<Artista> findAllByCognome(String cognome);
	
	public List<Artista> findByNomeOrCognome(String nome, String cognome);
	
	public List<Artista> findByNomeAndCognome(String nome, String cognome);
	
	public List<Artista> findAll();
	
	public List<Artista> findByOpere(Opera opera);
}
