package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.ProfileDto;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileEntity;

import java.util.List;

/**
 * IProfileMapper
 * <p>
 * IProfileMapper interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface IProfileMapper {
    @Mapping(target = "prRoleStatus", source = "roleStatus")
    @Mapping(target = "prName", source = "name")
    EpProfileEntity mapperToEntity(ProfileDto source);

    @Mapping(target = "roleStatus", source = "prRoleStatus")
    @Mapping(target = "name", source = "prName")
    ProfileDto mapperToDto(EpProfileEntity source);

    List<ProfileDto> mapperToList(List<EpProfileEntity> entities);
}


