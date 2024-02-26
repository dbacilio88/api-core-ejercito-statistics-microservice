package pe.mil.ejercito.microservice.dtos;

import lombok.*;

import java.io.Serializable;

/**
 * ModuleStatusDto
 * <p>
 * ModuleStatusDto class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleStatusDto implements Serializable {
    private static final long serialVersionUID = -5992603418667191923L;
    private Long id;
    private String uuId;
    private String code;
    private String name;
    private String description;
}