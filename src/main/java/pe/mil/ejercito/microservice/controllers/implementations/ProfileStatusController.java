package pe.mil.ejercito.microservice.controllers.implementations;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.mil.ejercito.microservice.controllers.contracts.IProfileStatusController;
import pe.mil.ejercito.microservice.dtos.ProfileStatusDto;
import pe.mil.ejercito.microservice.services.contracts.IProfileStatusDomainService;
import reactor.core.publisher.Mono;

/**
 * ProfileStatusController
 * <p>
 * ProfileStatusController class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */

@Log4j2
@Validated
@RestController
@RequestMapping(params = "/security", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileStatusController implements IProfileStatusController {

    private final IProfileStatusDomainService service;

    public ProfileStatusController(final IProfileStatusDomainService service) {
        this.service = service;
    }

    @Override
    @PostMapping(path = "/datas", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProfileStatusDto>> doOnExecuteProcess(Long id) {

        ThreadContext.put("methods", "doOnExecuteProcess");

        log.info("id entrada {} ", id);
        return this.service.getByIdEntity(id).flatMap(profileStatusDto -> Mono.just(new ResponseEntity<>(profileStatusDto, HttpStatus.OK)));

    }
}


