package pe.mil.ejercito.microservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * UserDto
 * <p>
 * UserDto class.
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
public class UserDto implements Serializable {
    private static final long serialVersionUID = -4353098545665927883L;
    Long id;
    String uuId;
    PersonDto person;
    ProfileDto profile;
    UserStatusDto userStatus;
    String username;
    String password;
    Instant createDate;
    Instant updateDate;
}