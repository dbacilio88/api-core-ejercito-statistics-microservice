package pe.mil.ejercito.microservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
/**
 * ProfileOptionModuleDto
 * <p>
 * ProfileOptionModuleDto class.
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
public class ProfileOptionModuleDto implements Serializable {
    private static final long serialVersionUID = 4321433544529760932L;
    Long id;
    String uuId;
    ModuleDto module;
    ProfileDto profile;
    String privileges;
    Instant createDate;
    Instant updateDate;
}