package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.*;
import it.uniroma3.siw.spring.model.Opera;

public interface OperaRepository extends CrudRepository<Opera,Long> {

	public List<Opera> findByTitolo (String titolo);
	
	public List<Opera> findAll();
}
