package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Curatore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String luogoNascita;
	
	private String email;
	
	private String telefono;
	
	private String matricola;

	@OneToMany(mappedBy = "curatore")
	private List<Collezione> collezioniCurate;
	
	@ManyToOne
	private Museo museoInCuiLavora;
	
	public Curatore() {
		this.collezioniCurate = new ArrayList<>();
	}
	
	public Curatore(String nome, String luogoNascita, String email, String matricola) {
		this.nome = nome;
		this.luogoNascita = luogoNascita;
		this.email = email;
		this.matricola = matricola;
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

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public List<Collezione> getCollezioniCurate() {
		return collezioniCurate;
	}

	public void setCollezioniCurate(List<Collezione> collezioniCurate) {
		this.collezioniCurate = collezioniCurate;
	}

	public Museo getMuseoInCuiLavora() {
		return museoInCuiLavora;
	}

	public void setMuseoInCuiLavora(Museo museoInCuiLavora) {
		this.museoInCuiLavora = museoInCuiLavora;
	}

	@Override
	public String toString() {
		return "Curatore [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", luogoNascita=" + luogoNascita
				+ ", email=" + email + ", telefono=" + telefono + ", matricola=" + matricola + ", collezioniCurate="
				+ collezioniCurate + ", museoInCuiLavora=" + museoInCuiLavora + "]";
	}

}
