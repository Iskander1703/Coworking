package ru.iskander.tabaev.coworking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.RoomCreateRequest;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.RoomResponse;
import ru.iskander.tabaev.coworking.models.Room;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);
    @Mapping(source = "capacity", target = "maxCapacity")
    RoomResponse fromRoomToRoomResponse(Room room);

    @Mapping(source = "maxCapacity", target = "capacity")
    Room fromRoomCreateRequestToRoom(RoomCreateRequest room);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "maxCapacity", target = "capacity")
    void updateRoomFromRoomCreateRequest(RoomCreateRequest request, @MappingTarget Room room);
}

