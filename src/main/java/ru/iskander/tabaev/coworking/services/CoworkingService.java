package ru.iskander.tabaev.coworking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.CoworkingCreateRequest;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.CoworkingResponse;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.mappers.CoworkingMapper;
import ru.iskander.tabaev.coworking.models.Coworking;
import ru.iskander.tabaev.coworking.repositories.CoworkingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoworkingService {

    @Autowired
    private final CoworkingRepository coworkingRepository;

    @Autowired
    public CoworkingService(CoworkingRepository coworkingRepository) {
        this.coworkingRepository = coworkingRepository;
    }

    public List<CoworkingResponse> getAllCoworkings() {
        List<Coworking> coworkings = coworkingRepository.findAll();
        return coworkings
                .stream()
                .map(coworking -> CoworkingMapper.INSTANCE.fromCoworkingToCoworkingResponse(coworking))
                .collect(Collectors.toList());
    }

    public CoworkingResponse getCoworkingById(Long coworkingId) throws WebResourceNotFoundException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));
        return CoworkingMapper.INSTANCE.fromCoworkingToCoworkingResponse(coworking);
    }

    @Transactional
    public void deleteCoworkingById(Long coworkingId) throws WebResourceNotFoundException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));
        coworkingRepository.delete(coworking);
    }

    public CoworkingResponse createCoworking(CoworkingCreateRequest coworkingCreateRequest) {
        Coworking coworking = coworkingRepository.save(CoworkingMapper.INSTANCE.fromCoworkingRequest(coworkingCreateRequest));
        return CoworkingMapper.INSTANCE.fromCoworkingToCoworkingResponse(coworking);
    }

    public CoworkingResponse updateCoworking(Long coworkingId, CoworkingCreateRequest coworkingCreateRequest) throws WebResourceNotFoundException {
        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));

        CoworkingMapper.INSTANCE.updateCoworkingFromCoworkingCreateRequest(coworkingCreateRequest, coworking);
        coworkingRepository.save(coworking);
        return CoworkingMapper.INSTANCE.fromCoworkingToCoworkingResponse(coworking);
    }
}
