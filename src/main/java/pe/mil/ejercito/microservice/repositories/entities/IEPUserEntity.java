package pe.mil.ejercito.microservice.repositories.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * IEPUserEntity
 * <p>
 * IEPUserEntity class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 20/02/2024
 */
@Data
@Entity
@Table(name = "EP_USER", indexes = {
        @Index(name = "EP_USER_UN1", columnList = "ES_UUID", unique = true)
})
public class IEPUserEntity {
    @Id
    @Column(name = "ES_ID", nullable = false)
    private Long id;

    @Column(name = "ES_UUID", length = 36, nullable = false)
    private String uuId;
    @Column(name = "UE_NAME", length = 50, nullable = false)
    private String name;
}