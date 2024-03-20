package pe.mil.ejercito.microservice.controllers.contracts;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * IGenerateDocumentController
 * <p>
 * IGenerateDocumentController interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BACSYSTEM APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author Bacsystem
 * @author bacsystem.sac@gmail.com
 * @since 19/03/2024
 */
public interface IGenerateDocumentController {

    Mono<ResponseEntity<Object>> doOnGenerateFile(Flux<Part> file);
}
