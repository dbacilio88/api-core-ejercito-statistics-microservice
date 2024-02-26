package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.UserDto;
import pe.mil.ejercito.microservice.repositories.entities.EpUserEntity;

import java.util.List;

/**
 * IUserMapper
 * <p>
 * IUserMapper interface.
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
public interface IUserMapper {

    @Mapping(target = "usUuid", source = "uuId")
    @Mapping(target = "usUsername", source = "username")
    @Mapping(target = "usUserStatus", source = "userStatus")
    @Mapping(target = "usUpdateDate", source = "updateDate")
    @Mapping(target = "usProfile", source = "profile")
    @Mapping(target = "usPerson", source = "person")
    @Mapping(target = "usPassword", source = "password")
    @Mapping(target = "usCreateDate", source = "createDate")
    EpUserEntity mapperToEntity(UserDto source);

    @Mapping(target = "uuId", source = "usUuid")
    @Mapping(target = "username", source = "usUsername")
    @Mapping(target = "userStatus", source = "usUserStatus")
    @Mapping(target = "updateDate", source = "usUpdateDate")
    @Mapping(target = "profile", source = "usProfile")
    @Mapping(target = "person", source = "usPerson")
    @Mapping(target = "password", source = "usPassword")
    @Mapping(target = "createDate", source = "usCreateDate")
    UserDto mapperToDto(EpUserEntity source);

    List<UserDto> mapperToList(List<EpUserEntity> entities);

}


