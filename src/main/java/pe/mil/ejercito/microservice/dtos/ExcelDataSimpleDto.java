package pe.mil.ejercito.microservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * ExcelDataSimpleDto
 * <p>
 * ExcelDataSimpleDto class.
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
public class ExcelDataSimpleDto implements Serializable {
    private static final long serialVersionUID = -3233799298685084546L;
    private String name;
    private String charge;
    private String personal;
    private String logistic;
    private String security;
    private String guard;
    private String age;
    private String grade;
    private String instruction;
    private String data;
}