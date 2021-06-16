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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class IndexController {
	
	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CollezioneService collezioneService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String getRicerca(@RequestParam(value="cerca", required = true) String cerca, Model model) {
		logger.debug("ricerca");
		
		List<Artista> ricercaArtista = artistaService.artistiPerNome(cerca);
		ricercaArtista.addAll(artistaService.artistiPerCognome(cerca));
		
		model.addAttribute("Artisti", ricercaArtista);
		model.addAttribute("Opere", this.operaService.operePerTitolo(cerca));
		model.addAttribute("Collezione", this.collezioneService.collezionePerNome(cerca));
		model.addAttribute("cerca", cerca);
		
		return "risultatoRicerca";
	}

}
