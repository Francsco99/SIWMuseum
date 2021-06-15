package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="musei")
public class Museo {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String nome;

	@OneToMany(mappedBy = "museo")
	private List<Collezione> collezioni;
	
	@OneToMany(mappedBy = "museoInCuiLavora")
	private List<Curatore> curatoriMuseo;
	
	public Museo() {
		this.collezioni = new ArrayList<>();
		this.curatoriMuseo = new ArrayList<>();
	}
	
	public Museo(String nome) {
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

	public List<Collezione> getCollezioni() {
		return collezioni;
	}

	public void setCollezioni(List<Collezione> collezioni) {
		this.collezioni = collezioni;
	}

	public List<Curatore> getCuratoriMuseo() {
		return curatoriMuseo;
	}

	public void setCuratoriMuseo(List<Curatore> curatoriMuseo) {
		this.curatoriMuseo = curatoriMuseo;
	}

	@Override
	public String toString() {
		return "Museo [id=" + id + ", nome=" + nome + ", collezioni=" + collezioni + ", curatoriMuseo=" + curatoriMuseo
				+ "]";
	}

}