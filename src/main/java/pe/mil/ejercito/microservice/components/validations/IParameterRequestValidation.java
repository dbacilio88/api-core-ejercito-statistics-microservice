package pe.mil.ejercito.microservice.components.validations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.validations.ProcessValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.mil.ejercito.microservice.dtos.GenerateDocumentComposition;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * IParameterRequestValidation
 * <p>
 * IParameterRequestValidation interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 21/03/2024
 */

@FunctionalInterface
public interface IParameterRequestValidation extends Function<GenerateDocumentComposition, ProcessValidationResult> {

    Logger log = LoggerFactory.getLogger(IParameterRequestValidation.class);

    static IParameterRequestValidation validationExtension() {
        return documentComposition -> {
            final ProcessValidationResult result = ProcessValidationResult.builder()
                    .processResult(ProcessResult.PROCESS_SUCCESS)
                    .errors(new ArrayList<>())
                    .build();

            if (documentComposition.getFilePart().filename().isEmpty()) {
                result.getErrors().add("error the filePart is null in request");
            }

            if (!result.getErrors().isEmpty()) {
                result.setProcessResult(ProcessResult.PROCESS_FAILED);
            }

            return result;
        };
    }
}