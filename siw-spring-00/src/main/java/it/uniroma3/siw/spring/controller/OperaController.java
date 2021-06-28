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
import it.uniroma3.siw.spring.validator.OperaValidator;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private OperaValidator operaValidator;

	@Autowired
	private ArtistaService artistaService;

	private Opera operaTemp;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
	public String getOpera(@PathVariable("id") Long id, Model model) {
		Opera opera = this.operaService.operaPerId(id);
		model.addAttribute("opera", opera);
		model.addAttribute("collezione", opera.getCollezione());
		return "opera.html";
	}
	
	
	@RequestMapping(value = "/admin/modificaOpera/{id}", method = RequestMethod.GET)
	public String editOpera(@PathVariable("id") Long id, Model model) {
		Opera opera = this.operaService.operaPerId(id);
		//Artista ascemo = opera.getAutore();
		model.addAttribute("opera", opera);
		//model.addAttribute("idAutore", ascemo.getId());
		return "editOpera.html";
	}
	
	

	@RequestMapping(value = "/opere", method = RequestMethod.GET)
	public String getOpere(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "opere.html";
	}

	@RequestMapping(value="/admin/cancellaOpera", method = RequestMethod.GET)
	public String cancellaOpera(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "cancellaOpere.html";
	}

	@RequestMapping(value="/admin/cancellaOpera/{id}", method = RequestMethod.GET)
	public String ConfermaCancellaOpera(@PathVariable("id") Long id,Model model) {
		logger.debug(id.toString());
		this.operaService.cancellaOpera(id);
		logger.debug("CANCELLATA OPERA CON ID: "+id.toString());
		model.addAttribute("opere", this.operaService.tutti());
		return "opere.html";
	}

	@RequestMapping(value="/admin/addOpera", method = RequestMethod.GET)
	public String addOpera(Model model) {
		logger.debug("addOpera");
		model.addAttribute("opera", new Opera());
		model.addAttribute("artisti",this.artistaService.tutti());
		return "operaForm.html";
	}

	@RequestMapping(value = "/admin/inserisciOpera", method = RequestMethod.POST)
	public String newOpera(@ModelAttribute("opera") Opera opera,
			Model model, BindingResult bindingResult) 
	{
		this.operaValidator.validate(opera, bindingResult);
		if (!bindingResult.hasErrors()) {  
			/*PER INSERIRE IL TITOLO MINUSCOLO NEL DB, al fine di facilitarne la ricerca*/
			opera.setTitolo(opera.getTitolo().toLowerCase()); 
			logger.debug("passo alla conferma");
			operaTemp = opera;
			return "confermaOperaForm.html";
		}
		return "operaForm.html";
	} 

	@RequestMapping(value = "/admin/confermaOpera", method = RequestMethod.POST)
	public String confermaArtista(Model model,
			@RequestParam(value = "action") String comando) {
		model.addAttribute("opera",operaTemp);
		if(comando.equals("confirm")) {
			logger.debug("confermo e salvo dati opera");
			this.operaService.inserisci(operaTemp);
			model.addAttribute("opere", this.operaService.tutti());
			return "opere.html";
		}
		else {
			return "operaForm.html";
		}
	}
}
