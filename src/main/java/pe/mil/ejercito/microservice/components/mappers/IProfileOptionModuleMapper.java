package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.ProfileOptionModuleDto;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileOptionModuleEntity;

import java.util.List;

/**
 * IProfileOptionModuleMapper
 * <p>
 * IProfileOptionModuleMapper interface.
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
public interface IProfileOptionModuleMapper {
    @Mapping(target = "posUuid", source = "uuId")
    @Mapping(target = "posUpdateDate", source = "updateDate")
    @Mapping(target = "posProfile", source = "profile")
    @Mapping(target = "posPrivileges", source = "privileges")
    @Mapping(target = "posModule", source = "module")
    @Mapping(target = "posCreateDate", source = "createDate")
    EpProfileOptionModuleEntity mapperToEntity(ProfileOptionModuleDto source);

    @Mapping(target = "uuId", source = "posUuid")
    @Mapping(target = "updateDate", source = "posUpdateDate")
    @Mapping(target = "profile", source = "posProfile")
    @Mapping(target = "privileges", source = "posPrivileges")
    @Mapping(target = "module", source = "posModule")
    @Mapping(target = "createDate", source = "posCreateDate")
    ProfileOptionModuleDto mapperToDto(EpProfileOptionModuleEntity source);

    List<ProfileOptionModuleDto> mapperToList(List<EpProfileOptionModuleEntity> entities);
}


