package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.ProcessResponse;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;
import reactor.core.publisher.Mono;

/**
 * IGenerateDocumentService
 * <p>
 * IGenerateDocumentService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BACSYSTEM APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author Bacsystem
 * @author bacsystem.sac@gmail.com
 * @since 19/03/2024
 */

public interface IGenerateDocumentService {
    Mono<ProcessResponse> doOnFindConfigurationsFiles(GenerateDocumentComposition documentComposition);
}
