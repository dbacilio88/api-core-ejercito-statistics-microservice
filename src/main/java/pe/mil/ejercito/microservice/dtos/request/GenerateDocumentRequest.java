package pe.mil.ejercito.microservice.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.codec.multipart.FilePart;

import java.io.Serializable;

/**
 * GenerateDocumentRequest
 * <p>
 * GenerateDocumentRequest class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BACSYSTEM APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author Bacsystem
 * @author bacsystem.sac@gmail.com
 * @since 19/03/2024
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerateDocumentRequest implements Serializable {
    private static final long serialVersionUID = -1046349316852724192L;

    private String name;
    private String contentType;
    private transient FilePart file;
}


