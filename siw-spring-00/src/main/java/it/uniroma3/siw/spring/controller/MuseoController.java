package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MuseoController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private CollezioneService collezioneService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

		if(!(opere.size()<4)) {
			for(int i = 0; i<4;i++) {
				int randomIndex = rand.nextInt(opere.size());
				randomList.add(opere.get(randomIndex));
				opere.remove(randomIndex);
			}
		}
		return randomList;
	}

	/*gestisce le immagini random della pagina home*/
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String homePage(Model model) {
		model.addAttribute("opere", this.listaRandom());
		return "index.html";
	}

	/*gestisce la barra di ricerca nella home*/
	@RequestMapping(value="/ricerca", method = RequestMethod.GET)
	public String getRicerca(@RequestParam(value="cerca", required = true) String cerca, 
			Model model) {
		String cercaLower = cerca.toLowerCase();
		List<String> parole = Arrays.asList(cercaLower.split(" "));

		List<Artista> artisti = new ArrayList<>();
		List<Opera> opere = new ArrayList<>();
		List<Collezione> collezioni = new ArrayList<>();

		for(String parola : parole) {

			logger.debug("Sto cercando corrispondenze con: "+ parola);

			for(Artista a : this.artistaService.artistaNomeOCognome(parola)) {
				artisti.add(a);
			}

			for(Opera o : this.operaService.operePerTitolo(parola)) {
				opere.add(o);
			}

			for(Collezione c : this.collezioneService.collezioniPerNome(parola)) {
				collezioni.add(c);
			}

			if(!artisti.isEmpty() || !opere.isEmpty() || !collezioni.isEmpty()) {
				break;
			}
		}
		model.addAttribute("Artisti",artisti);
		model.addAttribute("Opere", opere);
		model.addAttribute("Collezioni", collezioni);
		model.addAttribute("cerca", cerca);

		return "risultatoRicerca";
	}


}
