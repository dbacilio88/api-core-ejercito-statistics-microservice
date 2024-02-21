package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.IEPUserEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * IEPUserRepository
 * <p>
 * IEPUserRepository interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 21/02/2024
 */

@Repository
public interface IEPUserRepository extends JpaRepository<IEPUserEntity, Long> {

    Optional<IEPUserEntity> findByUuId(UUID uuId);

}