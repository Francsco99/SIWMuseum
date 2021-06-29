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
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.validator.CollezioneValidator;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private CollezioneValidator collezioneValidator;
	
	@Autowired
	private CuratoreService curatoreService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/*Si occupa di gestire la richiesta quando viene selezionata
	 * una collezione dalla pagina delle varie collezioni*/
	@RequestMapping(value="/collezione/{id}",method=RequestMethod.GET)
	public String getCollezione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("collezione", this.collezioneService.collezionePerId(id));
		return "collezione.html";
		
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina collezioni*/
	@RequestMapping(value="/collezioni",method= RequestMethod.GET)
	public String getCollezioni(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "collezioni.html";
	}
	
	/*Popola la form*/
	@RequestMapping(value="/admin/addCollezione", method = RequestMethod.GET)
	public String addCollezione(Model model) {
		logger.debug("addCollezione");
		model.addAttribute("collezione", new Collezione());
		model.addAttribute("curatori",this.curatoreService.tutti());
		return "collezioneForm.html";
		
	}
	
	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciCollezione", method = RequestMethod.POST)
	public String newCollezione(@ModelAttribute("collezione") Collezione collezione,
			Model model, BindingResult bindingResult) {
		this.collezioneValidator.validate(collezione, bindingResult);
		if (!bindingResult.hasErrors()) {
			/*PER INSERIRE IL TITOLO MINUSCOLO NEL DB, al fine di facilitarne la ricerca*/
			collezione.setNome(collezione.getNome().toLowerCase());
			
			this.collezioneService.inserisci(collezione);
			model.addAttribute("collezioni", this.collezioneService.tutti());
			return "collezioni.html";
		}
		return "collezioneForm.html";
	}
	
	@RequestMapping(value="/admin/cancellaCollezione/{id}", method = RequestMethod.GET)
	public String cancellaCollezione(@PathVariable("id") Long id,Model model) {
		logger.debug(id.toString());
		this.collezioneService.cancella(id);
		logger.debug("CANCELLATA COLLEZIONE CON ID: "+id.toString());
		
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "collezioni.html";
	}
}
