package pe.mil.ejercito.microservice.services.contracts;

import pe.mil.ejercito.microservice.dtos.ProfileStatusDto;
import pe.mil.ejercito.microservice.services.base.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * IProfileStatusDomainService
 * <p>
 * IProfileStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
public interface IProfileStatusDomainService extends
        IGetAllDomainEntity<Flux<ProfileStatusDto>>,
        IGetByIdDomainEntity<Mono<ProfileStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<ProfileStatusDto>, String>,
        ISaveDomainEntity<Mono<ProfileStatusDto>, ProfileStatusDto>,
        IUpdateDomainEntity<Mono<ProfileStatusDto>, ProfileStatusDto>,
        IDeleteDomainEntity<ProfileStatusDto> {
}
