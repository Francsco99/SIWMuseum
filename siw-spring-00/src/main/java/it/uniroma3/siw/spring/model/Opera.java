package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="opere")
public class Opera {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataRealizzazione;
	
	@Lob
	private String descrizione;
	
	@Lob
	private String immagine;
	
	@ManyToOne
	@JoinColumn(name="collezione_id")
	private Collezione collezione;

	@ManyToOne(fetch = FetchType.EAGER)
	private Artista autore;
	
	public Opera() {
		
	}
	
	public Opera(String titolo, LocalDate dataRealizzazione) {
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

	public LocalDate getDataRealizzazione() {
		return dataRealizzazione;
	}

	public void setDataRealizzazione(LocalDate dataRealizzazione) {
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

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
}
