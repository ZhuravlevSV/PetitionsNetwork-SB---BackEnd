package zhurasem.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zhurasem.project.api.converter.PetitionConverter;
import zhurasem.project.api.dto.PetitionDto;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.business.PetitionService;

import java.util.List;

@RestController
public class PetitionController {

    private final PetitionService petitionService;
    private final PetitionConverter petitionConverter;

    @Autowired
    public PetitionController(PetitionService petitionService, PetitionConverter petitionConverter) {
        this.petitionService = petitionService;
        this.petitionConverter = petitionConverter;
    }

    @GetMapping("/petitions")
    List<PetitionDto> getAll() {
        return petitionConverter.toDtos(petitionService.readAll());
    }

    @PostMapping("/petitions")
    PetitionDto create(@RequestBody PetitionDto petitionDto) {
        System.out.println(petitionDto.text);
        try {
            return petitionConverter.toDto(petitionService.create(petitionConverter.toEntity(petitionDto)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Author ID, commentsIDs, signedByIds: not found");
        }
    }
}
