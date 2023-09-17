package ru.iskander.tabaev.coworking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.RoomCreateRequest;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.RoomResponse;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.exceptions.WebServiceException;
import ru.iskander.tabaev.coworking.mappers.RoomMapper;
import ru.iskander.tabaev.coworking.models.Coworking;
import ru.iskander.tabaev.coworking.models.Room;
import ru.iskander.tabaev.coworking.repositories.CoworkingRepository;
import ru.iskander.tabaev.coworking.repositories.RoomRepository;
import ru.iskander.tabaev.coworking.services.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final CoworkingRepository coworkingRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, CoworkingRepository coworkingRepository) {
        this.roomRepository = roomRepository;
        this.coworkingRepository = coworkingRepository;
    }

    public List<RoomResponse> getRoomsByCoworking(
            Long coworkingId,
            Integer minCapacity,
            LocalDateTime beginDate,
            LocalDateTime endDate) throws WebResourceNotFoundException, WebServiceException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));

        minCapacity = minCapacity == null ? 0 : minCapacity;
        List<Room> rooms;

        if (minCapacity > 20) {
            throw new WebServiceException("Invalid minimum capacity value. The minimum capacity should not be more than 20");
        }

        if (beginDate != null && endDate != null) {
            if (!DateUtils.checkDateInterval(beginDate, endDate)) {
                throw new WebServiceException("Invalid date interval. The interval should have been a multiple of half an hour");
            } else {
                rooms = roomRepository.findAvailableRoomsInCoworking(coworkingId, minCapacity, beginDate, endDate);
            }
        } else {
            rooms = roomRepository.findAllByMaxCapacityGreaterThanEqualAndCoworkingId(minCapacity, coworkingId);
        }
        return rooms
                .stream()
                .map(RoomMapper.INSTANCE::fromRoomToRoomResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse getRoomByCoworkingIdAndRoomId(Long coworkingId, Long roomId) throws WebResourceNotFoundException {
        Room room = roomRepository.findByCoworkingIdAndId(coworkingId, roomId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking or room was not found"));

        return RoomMapper.INSTANCE.fromRoomToRoomResponse(room);

    }


    public RoomResponse createRoom(Long coworkingId, RoomCreateRequest request) throws WebResourceNotFoundException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));

        Room room = RoomMapper.INSTANCE.fromRoomCreateRequestToRoom(request);
        room.setCoworking(coworking);

        coworking.getRooms().add(room);
        room = roomRepository.save(room);

        return RoomMapper.INSTANCE.fromRoomToRoomResponse(room);

    }

    public void deleteRoom(Long coworkingId, Long roomId) throws WebResourceNotFoundException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));

        Room room = roomRepository.findByCoworkingIdAndId(coworkingId, roomId)
                .orElseThrow(() -> new WebResourceNotFoundException("Room was not found"));

        coworking.getRooms().remove(room);
        roomRepository.delete(room);
        coworkingRepository.save(coworking);
    }

    public RoomResponse updateRoom(Long coworkingId, Long roomId, RoomCreateRequest request) throws WebResourceNotFoundException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));

        Room room = roomRepository.findByCoworkingIdAndId(coworkingId, roomId)
                .orElseThrow(() -> new WebResourceNotFoundException("Room was not found"));

        RoomMapper.INSTANCE.updateRoomFromRoomCreateRequest(request, room);
        roomRepository.save(room);
        return RoomMapper.INSTANCE.fromRoomToRoomResponse(roomRepository.save(room));
    }

}
