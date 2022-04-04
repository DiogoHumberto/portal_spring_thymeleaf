package br.com.portal_spring_thymeleaf.utils.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioJaExisteException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8051368601704623547L;

	public UsuarioJaExisteException(String message) {
        super(message);
    }

    public UsuarioJaExisteException(String field, String value) {
        super(MessageFormat.format("O usuário do campo {0} com valor {1} já existe",
                field, value));
    }

    public UsuarioJaExisteException(String message, Throwable cause) {
        super(message, cause, false, true);
    }
    
}
