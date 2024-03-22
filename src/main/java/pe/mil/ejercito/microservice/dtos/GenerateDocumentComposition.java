package pe.mil.ejercito.microservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.codec.multipart.FilePart;
import pe.mil.ejercito.microservice.components.enums.ExtensionType;
import pe.mil.ejercito.microservice.dtos.response.GenerateDocumentResponse;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * GenerateDocumentComposition
 * <p>
 * GenerateDocumentComposition class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 20/03/2024
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerateDocumentComposition implements Serializable {

    private static final long serialVersionUID = -5611867036302046507L;

    private transient FilePart filePart;
    private String fileExtension;
    private String tempFile;
    private String fileName;
    private ExtensionType extensionType;

    private String division;
    private String type;
    private String contentType;
    private transient Path pathFile;
    private File file;
    private GenerateDocumentResponse documentResponse;
    private boolean combinedRow;
    private boolean combinedCell;
    private int combinedCellNumber;
    private int combinedRowNumber;

}


