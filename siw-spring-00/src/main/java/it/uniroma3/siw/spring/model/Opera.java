package it.uniroma3.siw.spring.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Opera {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataRealizzazione;
	
	@Lob
	private String descrizione;
	
	@ManyToOne
	private Collezione collezione;

	@ManyToOne
	private Artista autore;
	
	public Opera() {
		
	}
	
	public Opera(String titolo, Date dataRealizzazione) {
		this.titolo = titolo;
		this.dataRealizzazione = dataRealizzazione;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Date getDataRealizzazione() {
		return dataRealizzazione;
	}

	public void setDataRealizzazione(Date dataRealizzazione) {
		this.dataRealizzazione = dataRealizzazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Collezione getCollezione() {
		return collezione;
	}

	public void setCollezione(Collezione collezione) {
		this.collezione = collezione;
	}

	public Artista getAutore() {
		return autore;
	}

	public void setAutore(Artista autore) {
		this.autore = autore;
	}

	@Override
	public String toString() {
		return "Opera [id=" + id + ", titolo=" + titolo + ", dataRealizzazione=" + dataRealizzazione + ", descrizione="
				+ descrizione + ", collezione=" + collezione + ", autore=" + autore + "]";
	}
}
