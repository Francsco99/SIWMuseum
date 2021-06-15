package it.uniroma3.siw.spring.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.service.CuratoreService;

@Component
public class CuratoreValidator implements Validator{

	@Autowired
	private CuratoreService curatoreService;
	
	private static final Logger logger = LoggerFactory.getLogger(CollezioneValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Curatore.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "luogoNascita","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "immagine","required");

		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.curatoreService.alreadyExists((Curatore)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

}
