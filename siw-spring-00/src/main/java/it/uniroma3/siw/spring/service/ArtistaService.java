package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	public ArtistaRepository artistaRepository;
	
	@Transactional
	public Artista inserisci(Artista artista) {
		return artistaRepository.save(artista);
	}
	

	@Transactional
	public Artista artistaPerNome(String nome){
		return artistaRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Artista> artistiPerNome(String nome){
		return artistaRepository.findAllByNome(nome);
	}
	
	@Transactional
	public List<Artista> artistiPerNomeOCognome(String nome, String cognome){
		return artistaRepository.findByNomeOrCognome(nome,cognome);
	}
	
	@Transactional
	public List<Artista> artistiPerCognome(String cognome){
		return artistaRepository.findAllByCognome(cognome);
	}
	
	@Transactional
	public List<Artista> tutti(){
		return (List<Artista>) artistaRepository.findAll();
	}
	
	@Transactional
	public Artista artistaPerId(Long id) {
		Optional<Artista> optional = artistaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public List<Artista> artistiPerNomeECognome(String nome, String cognome){
		return this.artistaRepository.findByNomeAndCognome(nome, cognome);
	}
	
	@Transactional
	public boolean alreadyExists(Artista artista) {
		List<Artista> artisti = this.artistaRepository.findByNomeAndCognome(artista.getNome(), artista.getCognome());
		if (artisti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public List<Artista> artistaPerOpera(Opera opera) {
		return this.artistaRepository.findByOpere(opera);
	}
	
	@Transactional
	public List<Artista> artistaNomeOCognome(String nome){
		return this.artistaRepository.findByNomeOrCognomeIsLike(nome);
	}
	
	
	@Transactional
	public void update(Artista artista, Long id) {
		this.artistaRepository.saveOrUpdate(artista.getNome(), artista.getCognome(), artista.getBiografia(),id);
	}
}

