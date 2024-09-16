package br.com.siteware.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class RestResponseEntityExceptionHandler {
	@ExceptionHandler(APIException.class)
	public ResponseEntity<ErrorApiResponse> handlerGenericException(APIException ex) {
		return ex.buildErrorResponseEntity();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorApiResponse> handlerGenericException(Exception ex) {
		log.error("Exception: ", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorApiResponse.builder().description("INTERNAL SERVER ERROR!")
						.message("POR FAVOR INFORME AO ADMINISTRADOR DO SISTEMA!").build());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorApiResponse> accessDeniedException(AccessDeniedException ex) {
		log.error("Exception: ", ex);
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ErrorApiResponse.builder()
						.message("Acesso negado").build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
