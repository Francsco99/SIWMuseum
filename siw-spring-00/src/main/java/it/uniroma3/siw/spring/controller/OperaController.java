package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.validator.OperaValidator;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private OperaValidator operaValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/addOpera", method = RequestMethod.GET)
	public String addOpera(Model model) {
		logger.debug("addOpera");
		model.addAttribute("opera", new Opera());
		return "operaForm.html";
	}

	@RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("opera", this.operaService.operaPerId(id));
		return "opera.html";
	}

	@RequestMapping(value = "/opere", method = RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "opere.html";
	}

	@RequestMapping(value = "/opera", method = RequestMethod.POST)
	public String newArtista(@ModelAttribute("opera") Opera opera, 
			Model model, BindingResult bindingResult) {
		this.operaValidator.validate(opera, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.operaService.inserisci(opera);
			model.addAttribute("opera", this.operaService.tutti());
			return "opere.html";
		}
		return "operaForm.html";
	} 

}
