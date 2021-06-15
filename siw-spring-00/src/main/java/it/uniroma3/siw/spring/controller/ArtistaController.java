package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.validator.ArtistaValidator;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private OperaService operaService;

	@Autowired
	private ArtistaValidator artistaValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*Popola la form*/
	@RequestMapping(value="/addArtista", method = RequestMethod.GET)
	public String addArtista(Model model) {
		logger.debug("addArtista");
		model.addAttribute("artista", new Artista());
		return "artistaForm.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un artista dalla pagina dei vari artisti*/
	@RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		Artista artista = this.artistaService.artistaPerId(id);
		model.addAttribute("artista", artista);

		/*popola la lista delle opere di questo autore corrente*/
		model.addAttribute("opere",this.operaService.operePerArtista(artista));
		return "artista.html";
	}

	@RequestMapping(value = "artista/{id}/modificaOpere", method = RequestMethod.GET)
	public String editOpereArtista(@PathVariable("id") Long id, Model model) {
		Artista artista = this.artistaService.artistaPerId(id);
		model.addAttribute("artista",artista);
		model.addAttribute("opereArtista",this.operaService.operePerArtista(artista));
		model.addAttribute("TutteOpere",this.operaService.tutti());
		return "editOpereArtista.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina artisti*/
	@RequestMapping(value = "/artisti", method = RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/artista", method = RequestMethod.POST)
	public String newArtista(@ModelAttribute("artista") Artista artista, 
			Model model, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.artistaService.inserisci(artista);
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
		return "artistaForm.html";
	} 

}
