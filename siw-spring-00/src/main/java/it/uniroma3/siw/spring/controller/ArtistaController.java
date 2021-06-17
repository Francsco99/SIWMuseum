package it.uniroma3.siw.spring.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.validator.ArtistaValidator;
import net.bytebuddy.asm.Advice.This;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private OperaService operaService;

	@Autowired
	private ArtistaValidator artistaValidator;

	/*variabile temporanea da usare durante la validazione della form*/
	private Artista artistaTemp;

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

	/*Si occupa della richiesta quando viene selezionato
	 * il link per modificare le opere di un artista dentro
	 * la pagina dell'artista*/
	@RequestMapping(value = "artista/{id}/modificaOpere", method = RequestMethod.GET)
	public String editOpereArtista(@PathVariable("id") Long id, Model model) {
		Artista artista = this.artistaService.artistaPerId(id);
		model.addAttribute("artista",artista);
		model.addAttribute("opereArtista",this.operaService.operePerArtista(artista));
		model.addAttribute("TutteOpere",this.operaService.tutti());
		return "editOpereArtista.html";
	}

	@RequestMapping(value="/eliminaOpere",method = RequestMethod.GET)
	public String eliminaOpereArtista( Model model,
			@RequestParam(value="listaOpere")Opera opera,
			@RequestParam(value="action")String comando){
		if(comando.equals("elimina")) {
			logger.debug("lista opere");
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
		else {
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina artisti*/
	@RequestMapping(value = "/artisti", method = RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/inserisciArtista", method = RequestMethod.POST)
	public String newArtista(@ModelAttribute("artista") Artista artista, 
			Model model, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("passo alla conferma");
			artistaTemp = artista;
			return "confermaArtistaForm.html";
		}
		return "artistaForm.html";
	} 

	/*conferma l'inserimento dei dati nel db*/
	@RequestMapping(value = "/confermaArtista", method = RequestMethod.POST)
	public String confermaArtista(Model model,
			@RequestParam(value = "action") String comando) {
		model.addAttribute("artista",artistaTemp);
		if(comando.equals("confirm")) {
			logger.debug("confermo e salvo dati artista");
			this.artistaService.inserisci(artistaTemp);
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
		else {
			return "artistaForm.html";
		}
	}
}
