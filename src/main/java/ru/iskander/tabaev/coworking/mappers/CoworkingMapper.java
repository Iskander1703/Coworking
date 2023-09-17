package ru.iskander.tabaev.coworking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.CoworkingCreateRequest;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.CoworkingResponse;
import ru.iskander.tabaev.coworking.models.Coworking;

@Mapper
public interface CoworkingMapper {

    CoworkingMapper INSTANCE = Mappers.getMapper(CoworkingMapper.class);
    @Mapping(source = "rooms", target = "rooms")
    CoworkingResponse fromCoworkingToCoworkingResponse(Coworking coworking);

    Coworking fromCoworkingRequest(CoworkingCreateRequest coworkingCreateRequest);

    @Mapping(target = "id", ignore = true)
    void updateCoworkingFromCoworkingCreateRequest(CoworkingCreateRequest request, @MappingTarget Coworking coworking);
}
