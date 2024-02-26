package pe.mil.ejercito.microservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * ModuleDto
 * <p>
 * ModuleDto class.
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
public class ModuleDto implements Serializable {
    private static final long serialVersionUID = -477612838432715446L;
    Long id;
    String uuId;
    ModuleStatusDto moduleStatus;
    String name;
    Boolean component;
    Boolean isMenu;
    String icon;
    String path;
    Integer order;
    Integer group;
    Instant createDate;
    Instant updateDate;
}