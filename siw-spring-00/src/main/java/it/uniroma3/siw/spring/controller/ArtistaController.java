package it.uniroma3.siw.spring.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	/*variabile temporanea da usare durante la validazione della form*/
	private Artista artistaTemp;

	/*Data di oggi*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataOggi= LocalDate.now();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina artisti*/
	@RequestMapping(value = "/artisti", method = RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti.html";
	}

	/*Popola la form*/
	@RequestMapping(value="/admin/addArtista", method = RequestMethod.GET)
	public String addArtista(Model model) {
		logger.debug("PASSO ALLA FORM addArtista");
		model.addAttribute("artista", new Artista());
		model.addAttribute("dataOggi", dataOggi);
		return "/admin/artistaForm.html";
	}


	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciArtista", method = RequestMethod.POST)
	public String newArtista(@ModelAttribute("artista") Artista artista, 
			Model model, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("PASSO alla conferma");
			artistaTemp = artista;
			return "/admin/confermaArtistaForm.html";
		}
		return "/admin/artistaForm.html";
	} 

	/*conferma l'inserimento dei dati nel db*/
	@RequestMapping(value = "/admin/confermaArtista", method = RequestMethod.POST)
	public String confermaArtista(Model model,
			@RequestParam(value = "action") String comando) {

		model.addAttribute("artista",artistaTemp);

		if(comando.equals("confirm")) {
			/*cambio le stringhe con caratteri tutti minuscoli per facilitare la ricerca*/
			artistaTemp.setNome(artistaTemp.getNome().toLowerCase());					
			artistaTemp.setCognome(artistaTemp.getCognome().toLowerCase());				 
			artistaTemp.setLuogoNascita(artistaTemp.getLuogoNascita().toLowerCase());   
			artistaTemp.setLuogoMorte(artistaTemp.getLuogoMorte().toLowerCase());
			logger.debug("CONFERMO e SALVO dati artista");
			logger.debug("DATA NASCITA: "+ artistaTemp.getDataNascita());
			
			this.artistaService.inserisci(artistaTemp);
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
		else {
			return "/admin/artistaForm.html";
		}
	}

	@RequestMapping(value="/admin/modificaArtista/{id}", method = RequestMethod.GET)
	public String editArtista(Model model, @PathVariable("id")Long id) {
		Artista artista = this.artistaService.artistaPerId(id);
		model.addAttribute("artista", artista);
		return "/admin/editArtista.html";
	}

	@RequestMapping(value="/admin/modificaArtista/{id}",method = RequestMethod.POST)
	public String confermaModifica(@PathVariable("id") Long id,@Validated @ModelAttribute("artista") Artista artista,
			Model model) {
		artistaService.update(artista, id);
		
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti.html";
	} 


}
