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

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.validator.OperaValidator;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private OperaValidator operaValidator;

	@Autowired
	private ArtistaService artistaService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/addOpera", method = RequestMethod.GET)
	public String addOpera(Model model) {
		logger.debug("addOpera");
		model.addAttribute("opera", new Opera());
		model.addAttribute("artisti",this.artistaService.tutti());
		return "operaForm.html";
	}

	@RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
	public String getOpera(@PathVariable("id") Long id, Model model) {
		Opera opera = this.operaService.operaPerId(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artista", opera.getAutore());
		return "opera.html";
	}

	@RequestMapping(value = "/opere", method = RequestMethod.GET)
	public String getOpere(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "opere.html";
	}

	@RequestMapping(value = "/opera", method = RequestMethod.POST)
	public String newOpera(@ModelAttribute("opera") Opera opera,
			Model model, BindingResult bindingResult) 
	{
		this.operaValidator.validate(opera, bindingResult);
		if (!bindingResult.hasErrors()) {  
			opera.setTitolo(opera.getTitolo().toLowerCase());    // PER INSERIRE IL TITOLO MINUSCOLO NEL DB, al fine di facilitarne la ricerca 
			this.operaService.inserisci(opera);
			model.addAttribute("opere", this.operaService.tutti());
			return "opere.html";
		}
		return "operaForm.html";
	} 
}
