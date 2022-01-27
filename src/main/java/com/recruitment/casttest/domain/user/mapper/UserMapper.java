package com.recruitment.casttest.domain.user.mapper;

import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.entity.UserEntity;
import com.recruitment.casttest.domain.user.form.UserCreateForm;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserEntity toEntity(UserDto dto);

    UserDto toDto(UserEntity entity);

    UserDto toDto(UserCreateForm form);

}
