package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;

public interface ArtistaRepository extends CrudRepository<Artista,Long> {

	public Artista findByNome(String nome);
	
	public List<Artista> findAllByNome(String nome);
	
	public List<Artista> findAllByCognome(String cognome);
	
	public List<Artista> findByNomeOrCognome(String nome, String cognome);
	
	public List<Artista> findByNomeAndCognome(String nome, String cognome);
	
	@Query("SELECT a FROM Artista a WHERE nome LIKE %?1% OR cognome LIKE %?1%")
	public List<Artista> findByNomeOrCognomeIsLike(String nome);
	
	public List<Artista> findAll();
	
	public List<Artista> findByOpere(Opera opera);
	
	@Query("UPDATE Artista SET nome = ?1, cognome = ?2, biografia = ?3  WHERE id = ?4")
	@Modifying
	public void saveOrUpdate(String nome, String cognome, String biografia, Long id);
	
}
