package ru.iskander.tabaev.coworking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.iskander.tabaev.coworking.controllers.room.dto.RoomCreateRequest;
import ru.iskander.tabaev.coworking.controllers.room.dto.RoomResponse;
import ru.iskander.tabaev.coworking.models.Room;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomResponse fromRoomToRoomResponse(Room room);

    Room fromRoomCreateRequestToRoom(RoomCreateRequest room);

    @Mapping(target = "id", ignore = true)
    void updateRoomFromRoomCreateRequest(RoomCreateRequest request, @MappingTarget Room room);
}

