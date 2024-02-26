package pe.mil.ejercito.microservice.services.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.mil.ejercito.microservice.components.mappers.IProfileStatusMapper;
import pe.mil.ejercito.microservice.dtos.ProfileStatusDto;
import pe.mil.ejercito.microservice.repositories.contracts.IEpProfileStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IProfileStatusDomainService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProfileStatusDomainService
 * <p>
 * ProfileStatusDomainService class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */

@Log4j2
@Service
public class ProfileStatusDomainService implements IProfileStatusDomainService {

    private final IEpProfileStatusRepository repository;
    private final IProfileStatusMapper profileStatusMapper;

    @Autowired
    public ProfileStatusDomainService(final IEpProfileStatusRepository repository,
                                      final IProfileStatusMapper profileStatusMapper) {
        this.repository = repository;
        this.profileStatusMapper = profileStatusMapper;
    }

    @Override
    public Flux<ProfileStatusDto> getAllEntities() {
        return null;
    }

    @Override
    public Mono<ProfileStatusDto> getByIdEntity(Long id) {
        return Mono.justOrEmpty(this.repository.findById(id))
                .switchIfEmpty(Mono.error(() -> new Exception("error")))
                .flatMap(entity -> get(entity, "success {}", "error {}"));
    }

    @Override
    public Boolean deleteEntity(ProfileStatusDto entity) {
        return null;
    }

    @Override
    public Mono<ProfileStatusDto> getByUuIdEntity(String uuId) {
        return Mono.justOrEmpty(this.repository.findByUuId(uuId))
                .switchIfEmpty(Mono.error(() -> new Exception("error")))
                .flatMap(entity -> get(entity, "success {}", "error {}"));
    }

    @Override
    public Mono<ProfileStatusDto> saveEntity(ProfileStatusDto dto) {
        return null;
    }

    @Override
    public Mono<ProfileStatusDto> updateEntity(ProfileStatusDto dto) {
        return null;
    }


    private Mono<ProfileStatusDto> get(EpProfileStatusEntity entity, String message, String exception) {
        return Mono.just(this.profileStatusMapper.mapperToDto(entity))
                .doOnSuccess(success -> log.info(message, success.toString()))
                .doOnError(throwable -> log.error(exception, throwable.getMessage()))
                .log();
    }
}


