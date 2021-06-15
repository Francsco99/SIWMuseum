package it.uniroma3.siw.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MuseoController {
	@RequestMapping(value="/informazioni", method = RequestMethod.GET)
	public String addArtista(Model model) {
		return "informazioni.html";
	}
}
