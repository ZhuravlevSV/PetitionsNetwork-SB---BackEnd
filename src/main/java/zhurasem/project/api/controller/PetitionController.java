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

    @PutMapping("/petitions/{id}")
    PetitionDto update(@RequestBody PetitionDto petitionDto, @PathVariable Long id) {
        var petition = petitionService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Petition not found"));
        try {
            petition.setTitle(petitionDto.getTitle());
            petition.setText(petitionDto.getText());
            petition.setGoal(petitionDto.getGoal());
            petition.setDateFrom(petitionDto.getDateFrom());
            petitionService.update(petition);
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Petition ID is not found");
        }
        return petitionConverter.toDto(petition);
    }

    @GetMapping("/petitions/{id}")
    PetitionDto get(@PathVariable Long id) {
        return petitionConverter.toDto(petitionService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Petition not found")));
    }

    @DeleteMapping("/petitions/{id}")
    void delete(@PathVariable Long id) {
        petitionService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Petition not found"));
        petitionService.deleteById(id);
    }
}
