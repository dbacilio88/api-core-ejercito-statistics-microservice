package pe.mil.ejercito.microservice.controllers.implementations;

import com.bxcode.tools.loader.controllers.base.ReactorControllerBase;
import com.bxcode.tools.loader.dto.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.mil.ejercito.microservice.controllers.contracts.IGenerateDocumentController;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;
import pe.mil.ejercito.microservice.services.contracts.IGenerateDocumentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * GenerateDocumentController
 * <p>
 * GenerateDocumentController class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BACSYSTEM APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author Bacsystem
 * @author bacsystem.sac@gmail.com
 * @since 19/03/2024
 */

@Log4j2
@RestController
@RequestMapping(path = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenerateDocumentController extends ReactorControllerBase implements IGenerateDocumentController {

    private final IGenerateDocumentService generateDocumentService;

    public GenerateDocumentController(final Response response, final IGenerateDocumentService generateDocumentService) {
        super(response, "GenerateDocumentController");
        this.generateDocumentService = generateDocumentService;
    }

    @Override
    @PostMapping(path = "/upload-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<Object>> doOnGenerateFile(@RequestBody Flux<Part> file) {
        return file
                .filter(FilePart.class::isInstance)
                .cast(FilePart.class)
                .next()
                .flatMap(filePart -> this.generateDocumentService.doOnFindConfigurationsFiles(GenerateDocumentComposition.builder()
                        .filePart(filePart)
                        .build()))
                .flatMap(super::response)
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info("MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}


