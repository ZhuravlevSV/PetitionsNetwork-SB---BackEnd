package zhurasem.project.api.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zhurasem.project.api.converter.PetitionConverter;
import zhurasem.project.api.dto.PetitionDto;
import zhurasem.project.business.PetitionService;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PetitionController.class)
public class PetitionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PetitionService petitionService;

    @MockBean
    PetitionConverter petitionConverter;

    @Test
    public void testCreate() throws Exception {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), author, new ArrayList<>(), new ArrayList<>());
        PetitionDto petitionDto = new PetitionDto(1L, "Title", "text", 1000, new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), "zhurasem", new ArrayList<>(), new ArrayList<>());

        Mockito.when(petitionService.create(petition)).thenReturn(petition);
        Mockito.when(petitionConverter.toDto(petition)).thenReturn(petitionDto);

        mockMvc.perform(post("/petitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"pid\":1," +
                                        "\"title\":\"Title\"," +
                                        "\"text\":\"text\"," +
                                        "\"goal\":1000," +
                                        "\"dateFrom\":\"2023-12-16T12:00:00\"," +
                                        "\"commentsIds\":[]," +
                                        "\"signedUsersIds\":[]" +
                                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadAll() throws Exception {
        Petition petition1 = new Petition(1L, "Title 1", "text 1", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());
        Petition petition2 = new Petition(2L, "Title 2", "text 2", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());
        List<Petition> petitions = List.of(petition1, petition2);

        PetitionDto petitionDto1 = new PetitionDto(1L, "Title 1", "text 1", 1000, new Date(), "zhurasem", new ArrayList<>(), new ArrayList<>());
        PetitionDto petitionDto2 = new PetitionDto(2L, "Title 2", "text 2", 1000, new Date(), "zhurasem", new ArrayList<>(), new ArrayList<>());
        List<PetitionDto> petitionDtos = List.of(petitionDto1, petitionDto2);

        Mockito.when(petitionService.readAll()).thenReturn(petitions);
        Mockito.when(petitionConverter.toDtos(petitions)).thenReturn(petitionDtos);

        mockMvc.perform(get("/petitions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].pid", Matchers.is(1)))
                .andExpect(jsonPath("$[1].pid", Matchers.is(2)));
    }

    @Test
    public void testRead() throws Exception {
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());

        PetitionDto petitionDto = new PetitionDto(1L, "Title", "text", 1000, new Date(), "zhurasem", new ArrayList<>(), new ArrayList<>());

        Mockito.when(petitionService.readById(1L)).thenReturn(Optional.of(petition));
        Mockito.when(petitionConverter.toDto(petition)).thenReturn(petitionDto);

        mockMvc.perform(get("/petitions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Title")))
                .andExpect(jsonPath("$.text", Matchers.is("text")))
                .andExpect(jsonPath("$.goal", Matchers.is(1000)))
                .andExpect(jsonPath("$.authorPetitionId", Matchers.is("zhurasem")));
    }

    @Test
    public void testUpdate() throws Exception {
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());

        PetitionDto petitionDto = new PetitionDto(1L, "Title", "text", 1000, new Date(), "zhurasem", new ArrayList<>(), new ArrayList<>());

        Mockito.when(petitionService.readById(1L)).thenReturn(Optional.of(petition));
        Mockito.when(petitionService.update(petition)).thenReturn(petition);
        Mockito.when(petitionConverter.toDto(petition)).thenReturn(petitionDto);

        mockMvc.perform(put("/petitions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"pid\":1," +
                                        "\"title\":\"Title\"," +
                                        "\"text\":\"text\"," +
                                        "\"goal\":1000," +
                                        "\"dateFrom\":\"2023-12-16T12:00:00\"," +
                                        "\"commentsIds\":[]," +
                                        "\"signedUsersIds\":[]" +
                                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());

        PetitionDto petitionDto = new PetitionDto(1L, "Title", "text", 1000, new Date(), "zhurasem", new ArrayList<>(), new ArrayList<>());

        Mockito.when(petitionService.create(petition)).thenReturn(petition);
        Mockito.when(petitionConverter.toDto(petition)).thenReturn(petitionDto);
        Mockito.when(petitionService.readById(1L)).thenReturn(Optional.of(petition));

        mockMvc.perform(post("/petitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"pid\":1," +
                                        "\"title\":\"Title\"," +
                                        "\"text\":\"text\"," +
                                        "\"goal\":1000," +
                                        "\"dateFrom\":\"2023-12-16T12:00:00\"," +
                                        "\"commentsIds\":[]," +
                                        "\"signedUsersIds\":[]" +
                                        "}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/petitions/1"))
                .andExpect(status().isOk());
    }
}
