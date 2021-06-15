package it.uniroma3.siw.spring.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;

@Component
public class CollezioneValidator implements Validator {

	@Autowired
	private CollezioneService collezioneService;
	
	private static final Logger logger = LoggerFactory.getLogger(ArtistaValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Collezione.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione","required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "immagine","required");

		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.collezioneService.alreadyExists((Collezione)target)){
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

}
