package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Museo;
import it.uniroma3.siw.spring.repository.MuseoRepository;

@Service
public class MuseoService {

	@Autowired
	private MuseoRepository museoRepository;
	
	@Transactional
	private Museo inserisci(Museo museo) {
		return museoRepository.save(museo);
	}
	
	@Transactional
	private List<Museo> museiPerNome(String nome){
		return museoRepository.findByNome(nome);
	}
	
	@Transactional
	private List<Museo> tutti(){
		return (List<Museo>) museoRepository.findAll();
	}
	
	@Transactional
	public Museo museoPerId(Long id) {
		Optional<Museo> optional = museoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Museo museo) {
		List<Museo> musei = this.museoRepository.findByNome(museo.getNome());
		if (musei.size() > 0)
			return true;
		else 
			return false;
	}
}