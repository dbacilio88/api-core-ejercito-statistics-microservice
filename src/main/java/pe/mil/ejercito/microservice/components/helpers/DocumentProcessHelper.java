package pe.mil.ejercito.microservice.components.helpers;

/*
 * DocumentProcessHelper
 * <p>
 * DocumentProcessHelper class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 21/03/2024
 */

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;
import reactor.core.publisher.Mono;

@Log4j2
@UtilityClass
public class DocumentProcessHelper {

    public static Mono<GenerateDocumentComposition> doOnGenerateDocumentComposition(GenerateDocumentComposition documentComposition) {
        return doOnSelectTypeDocument(documentComposition)
                .doOnSuccess(success -> log.debug("doOnGenerateDocumentComposition"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<GenerateDocumentComposition> doOnSelectTypeDocument(GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    switch (currentComposition.getExtensionType()) {
                        case EXCEL_XLSX:
                        case EXCEL_XLS:
                            return DocumentProcessExcelHelper.doOnReadDocumentExcel(documentComposition);
                        case TXT:
                            return doOnProcessDocumentTxt(documentComposition);
                        default:
                            return Mono.just(currentComposition);
                    }
                })
                .doOnSuccess(success -> log.debug("doOnSelectTypeDocument"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<GenerateDocumentComposition> doOnProcessDocumentTxt(final GenerateDocumentComposition documentComposition) {
        return Mono.just(documentComposition)
                .flatMap(currentComposition -> {
                    currentComposition.setTempFile("temp.txt");
                    return Mono.just(currentComposition);
                }).doOnSuccess(success -> log.debug("doOnProcessDocumentExcel"))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

}


