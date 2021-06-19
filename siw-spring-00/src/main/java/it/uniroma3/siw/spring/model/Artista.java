package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="artisti")
public class Artista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataMorte;

	private String luogoNascita;
	
	private String luogoMorte;
	
	private String nazionalita;

	private String immagine;
	
	@Lob
	private String biografia;
	
	@OneToMany(mappedBy = "autore", fetch = FetchType.EAGER)
	private List<Opera> opere;
	
	public Artista() {
		this.opere = new ArrayList<>();	
	}
	
	public Artista(String nome, String luogoNascita) {
		this.nome = nome;
		this.luogoNascita = luogoNascita;
		this.opere = new ArrayList<>();	
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public LocalDate getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getLuogoMorte() {
		return luogoMorte;
	}

	public void setLuogoMorte(String luogoMorte) {
		this.luogoMorte = luogoMorte;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Opera> getOpere() {
		return opere;
	}

	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}

	@Override
	public String toString() {
		return "Artista [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita
				+ ", dataMorte=" + dataMorte + ", luogoNascita=" + luogoNascita + ", luogoMorte=" + luogoMorte
				+ ", nazionalita=" + nazionalita + ", opere=" + opere + "]";
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

}