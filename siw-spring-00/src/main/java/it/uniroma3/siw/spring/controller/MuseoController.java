package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MuseoController {

	@Autowired
	private OperaService operaService;

	/*gestisce la richiesta della pagina informazioni*/
	@RequestMapping(value="/informazioni", method = RequestMethod.GET)
	public String addArtista(Model model) {
		return "informazioni.html";
	}

	/*metodo di supporto per generare una lista di 
	 * quattro opere random*/
	private List<Opera> listaRandom(){
		List<Opera> opere = this.operaService.tutti();
		List<Opera> randomList = new ArrayList<>();
		Random rand = new Random();
		for(int i = 0; i<4;i++) {
			int randomIndex = rand.nextInt(opere.size());
			randomList.add(opere.get(randomIndex));
			opere.remove(randomIndex);
		}
		return randomList;
	}

	/*gestisce la richiesta della pagina home index*/
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String homePage(Model model) {
		model.addAttribute("opere", this.listaRandom());
		return "index.html";
	}

}
