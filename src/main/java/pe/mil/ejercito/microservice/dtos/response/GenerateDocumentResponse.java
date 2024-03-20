package pe.mil.ejercito.microservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.ejercito.microservice.dtos.ExcelDataDto;

import java.io.Serializable;
import java.util.List;

/**
 * GenerateDocumentResponse
 * <p>
 * GenerateDocumentResponse class.
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
public class GenerateDocumentResponse implements Serializable {
    private static final long serialVersionUID = 84440509247931788L;
    private String name;
    private List<ExcelDataDto> excel;
}


