package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="collezioni")
public class Collezione {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@Lob
	private String descrizione;
	
	private String immagine;

	@ManyToOne
	private Museo museo;
	
	@ManyToOne
	private Curatore curatore;
	
	@OneToMany(mappedBy = "collezione")
	private List<Opera> opereInCollezione;
	
	public Collezione() {
		this.opereInCollezione = new ArrayList<>();
		
	}
	
	public Collezione(String nome) {
		this.nome = nome;
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Museo getMuseo() {
		return museo;
	}

	public void setMuseo(Museo museo) {
		this.museo = museo;
	}

	public Curatore getCuratore() {
		return curatore;
	}

	public void setCuratore(Curatore curatore) {
		this.curatore = curatore;
	}

	public List<Opera> getOpereInCollezione() {
		return opereInCollezione;
	}

	public void setOpereInCollezione(List<Opera> opereInCollezione) {
		this.opereInCollezione = opereInCollezione;
	}

	@Override
	public String toString() {
		return "Collezione [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", museo=" + museo
				+ ", curatore=" + curatore + ", opereInCollezione=" + opereInCollezione + "]";
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

}
