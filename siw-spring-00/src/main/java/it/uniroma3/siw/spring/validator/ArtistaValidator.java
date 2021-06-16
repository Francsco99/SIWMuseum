package it.uniroma3.siw.spring.validator;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator {

	@Autowired
	private ArtistaService artistaService;

	private static final Logger logger = LoggerFactory.getLogger(ArtistaValidator.class);

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataNascita","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "luogoNascita","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazionalita","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "biografia","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "immagine","required");

		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.artistaService.alreadyExists((Artista)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
			
			SimpleDateFormat formatter=new SimpleDateFormat("YYYY-MM-DD");  
		    Date dataNascita = new Date();
			try {
				dataNascita = formatter.parse("dataNascita");
			} catch (ParseException e) {
				logger.debug("error parsing dataNascita");
				
			}  
		    
			Date dataMorte = new Date();
			try {
				dataMorte = formatter.parse("dataMorte");
			} catch (ParseException e) {
				logger.debug("error parsing dataMorte");
			} 
			
			if(dataNascita.after(dataMorte)) {
				logger.debug("non può essere nato dopo essere morto");
				errors.reject("assurdoNascita");
			}
			
			else  logger.debug("non ci vedo niente di strano");
		
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.equals(clazz);
	}
}
