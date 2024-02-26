package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.ModuleDto;
import pe.mil.ejercito.microservice.repositories.entities.EpModuleEntity;

import java.util.List;

/**
 * IModuleMapper
 * <p>
 * IModuleMapper interface.
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
public interface IModuleMapper {

    @Mapping(target = "moUuid", source = "uuId")
    @Mapping(target = "moUpdateDate", source = "updateDate")
    @Mapping(target = "moPath", source = "path")
    @Mapping(target = "moOrder", source = "order")
    @Mapping(target = "moName", source = "name")
    @Mapping(target = "moModuleStatus", source = "moduleStatus")
    @Mapping(target = "moIsMenu", source = "isMenu")
    @Mapping(target = "moIsComponent", source = "component")
    @Mapping(target = "moIcon", source = "icon")
    @Mapping(target = "moGroup", source = "group")
    @Mapping(target = "moCreateDate", source = "createDate")
    EpModuleEntity mapperToEntity(ModuleDto source);

    @Mapping(target = "uuId", source = "moUuid")
    @Mapping(target = "updateDate", source = "moUpdateDate")
    @Mapping(target = "path", source = "moPath")
    @Mapping(target = "order", source = "moOrder")
    @Mapping(target = "name", source = "moName")
    @Mapping(target = "moduleStatus", source = "moModuleStatus")
    @Mapping(target = "isMenu", source = "moIsMenu")
    @Mapping(target = "icon", source = "moIcon")
    @Mapping(target = "group", source = "moGroup")
    @Mapping(target = "createDate", source = "moCreateDate")
    @Mapping(target = "component", source = "moIsComponent")
    ModuleDto mapperToDto(EpModuleEntity source);

    List<ModuleDto> mapperToList(List<EpModuleEntity> entities);

}


