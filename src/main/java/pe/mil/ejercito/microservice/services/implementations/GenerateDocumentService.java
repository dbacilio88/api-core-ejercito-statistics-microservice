package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.validations.ProcessValidationResult;
import com.bxcode.tools.loader.dto.GenericResponse;
import com.bxcode.tools.loader.dto.ProcessResponse;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import pe.mil.ejercito.microservice.components.enums.ExtensionType;
import pe.mil.ejercito.microservice.components.helpers.DocumentProcessHelper;
import pe.mil.ejercito.microservice.components.validations.IParameterRequestValidation;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;
import pe.mil.ejercito.microservice.dtos.response.GenerateDocumentResponse;
import pe.mil.ejercito.microservice.services.contracts.IGenerateDocumentService;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * GenerateDocumentService
 * <p>
 * GenerateDocumentService class.
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
@Service
public class GenerateDocumentService extends ReactorServiceBase implements IGenerateDocumentService {
    protected GenerateDocumentService() {
        super("GenerateDocumentService");
    }

    @Override
    public Mono<ProcessResponse> doOnFindConfigurationsFiles(GenerateDocumentComposition documentComposition) {
        return doOnValidateRequest(documentComposition)
                .flatMap(this::doOnValidateExtension)
                .flatMap(this::doOnGenerateTemporalDocument)
                .flatMap(this::doOnExecuteProcess)
                .flatMap(this::doOnProcessResponse)
                .doOnSuccess(success -> log.debug("MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnValidateRequest(final GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 1 do on validate request");
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    final ProcessValidationResult requestValidation = IParameterRequestValidation.validationExtension().apply(currentComposition);
                    if (ProcessResult.PROCESS_FAILED.equals(requestValidation.getProcessResult())) {
                        return Mono.error(() -> new CommonException("error in request not file content", ResponseEnum.ERROR_INVALID_REQUEST_DATA, requestValidation.getErrors()));
                    }
                    currentComposition.setFileExtension(FilenameUtils.getExtension(currentComposition.getFilePart().filename()));
                    return Mono.just(currentComposition);
                }).doOnSuccess(success -> log.debug("doOnValidateExtension"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnValidateExtension(final GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 2 do on validate extension");
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    Optional<ExtensionType> extensionType = ExtensionType.findByValue(currentComposition.getFileExtension());
                    if (extensionType.isEmpty()) {
                        return Mono.error(() -> new CommonException("error in extension file not found", ResponseEnum.ERROR_INVALID_REQUEST_DATA, null));
                    }
                    currentComposition.setTempFile(extensionType.get().getValue());
                    currentComposition.setExtensionType(extensionType.get());
                    currentComposition.setFileName(currentComposition.getFilePart().filename());
                    return Mono.just(currentComposition);
                }).doOnSuccess(success -> log.debug("doOnValidateExtension"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnGenerateTemporalDocument(final GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 3 do on generate temporal document");
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    Path outputPath = Paths.get(currentComposition.getTempFile());
                    File tempFile = outputPath.toFile();
                    return currentComposition.getFilePart().transferTo(tempFile).thenReturn(tempFile)
                            .flatMap(file -> {
                                currentComposition.setFile(file);
                                currentComposition.setPathFile(outputPath);
                                return Mono.just(currentComposition);
                            });
                })
                .doOnSuccess(success -> log.debug("doOnProcessCreateDocument"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnExecuteProcess(GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 3 do on execute process");
        return Mono.just(documentComposition)
                .flatMap(DocumentProcessHelper::doOnGenerateDocumentComposition)
                .doOnSuccess(success -> log.debug("doOnExecuteProcess"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    public Mono<ProcessResponse> doOnProcessResponse(GenerateDocumentComposition documentComposition) {
        log.debug("STEP # 4 do on process response");
        return Mono.just(documentComposition)
                .flatMap(processResponse -> {
                    final GenericResponse<GenerateDocumentResponse> response = new GenericResponse<>(processResponse.getDocumentResponse());
                    return Mono.just(ProcessResponse.success(response));
                })
                .doOnSuccess(success -> log.debug("doOnProcessResponse"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}