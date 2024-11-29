package br.com.elotech.libraryapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * Classe responsável por tratar exceções lançadas pela aplicação e retornar respostas tratadas.
 * Utiliza a anotação {@link ControllerAdvice} para aplicar os tratamentos de forma
 * global a todos os controllers.
 *
 * <p>
 * Intercepta exceções e retorna uma resposta contendo:
 * - Timestamp
 * - Código HTTP
 * - Mensagem de erro
 * - Mensagem detalhada do erro
 * - URI do recurso que gerou o erro
 * </p>
 *
 * Exceções tratadas:
 * <ul>
 *   <li>{@link ObjectNotfoundException}: Quando um objeto não é encontrado.</li>
 *   <li>{@link DataIntegrityException}: Quando ocorre problema na integridade de dados.</li>
 * </ul>
 *
 * @author João Ayezzer
 *
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ObjectNotfoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotfoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Data integrity", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
