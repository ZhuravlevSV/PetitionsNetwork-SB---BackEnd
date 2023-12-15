package zhurasem.project.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zhurasem.project.api.dto.PetitionDto;
import zhurasem.project.business.PetitionService;
import zhurasem.project.domain.Petition;

import java.util.List;

@RestController
public class PetitionController {

    private final PetitionService petitionService;
    private final ModelMapper modelMapper;

    @Autowired
    public PetitionController(PetitionService petitionService, ModelMapper modelMapper) {
        this.petitionService = petitionService;
        this.modelMapper = modelMapper;
    }
}
