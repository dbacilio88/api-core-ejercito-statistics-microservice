package pe.mil.ejercito.microservice.controllers.contracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pe.mil.ejercito.microservice.dtos.ProfileStatusDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * IProfileStatusController
 * <p>
 * IProfileStatusController interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
public interface IProfileStatusController {

    Mono<ResponseEntity<ProfileStatusDto>> doOnExecuteProcess(@RequestBody @Valid Long id);
}
