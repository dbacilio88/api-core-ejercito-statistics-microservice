package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
/**
 * EpProfileEntity
 * <p>
 * EpProfileEntity class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EpProfileEntity")
@Table(name = "EP_PROFILE", indexes = {
        @Index(name = "EP_PROFILE_UN1", columnList = "PR_UUID", unique = true)
})
public class EpProfileEntity {
    @Id
    @Column(name = "PR_ID", nullable = false, precision = 38)
    private Long id;

    @Column(name = "PR_UUID", nullable = false, length = 36)
    private String prUuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PR_ROLE_STATUS", nullable = false)
    @ToString.Exclude
    private EpProfileStatusEntity prRoleStatus;

    @Column(name = "PR_NAME", nullable = false, length = 50)
    private String prName;

}