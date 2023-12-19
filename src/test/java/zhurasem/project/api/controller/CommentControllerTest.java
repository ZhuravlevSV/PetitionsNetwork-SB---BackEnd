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
import zhurasem.project.api.converter.CommentConverter;
import zhurasem.project.api.dto.CommentDto;
import zhurasem.project.business.CommentService;
import zhurasem.project.domain.Comment;
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
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @MockBean
    CommentConverter commentConverter;

    @Test
    public void testCreate() throws Exception {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), author, new ArrayList<>(), new ArrayList<>());

        Comment comment = new Comment(1L, "text", new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), author, petition);

        CommentDto commentDto = new CommentDto(1L, "text", new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), "zhurasem", 1L);

        Mockito.when(commentService.create(comment)).thenReturn(comment);
        Mockito.when(commentConverter.toDto(comment)).thenReturn(commentDto);

        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{" +
                                "\"cid\":1," +
                                "\"text\":\"text\"," +
                                "\"dateFrom\":\"2023-12-16T12:00:00\"," +
                                "\"authorCommentId\":\"zhurasem\"," +
                                "\"petitionCommentId\":1" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadAll() throws Exception {
        Comment comment1 = new Comment(1L, "text 1", new Date(), new User(), new Petition());
        Comment comment2 = new Comment(2L, "text 2", new Date(), new User(), new Petition());
        List<Comment> comments = List.of(comment1, comment2);

        CommentDto commentDto1 = new CommentDto(1L, "text 1", new Date(), "zhurasem", 1L);
        CommentDto commentDto2 = new CommentDto(2L, "text 2", new Date(), "zhurasem", 1L);
        List<CommentDto> commentDtos = List.of(commentDto1, commentDto2);

        Mockito.when(commentService.readAll()).thenReturn(comments);
        Mockito.when(commentConverter.toDtos(comments)).thenReturn(commentDtos);

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].cid", Matchers.is(1)))
                .andExpect(jsonPath("$[1].cid", Matchers.is(2)));
    }

    @Test
    public void testRead() throws Exception {
        Comment comment = new Comment(1L, "text", new Date(), new User(), new Petition());

        CommentDto commentDto = new CommentDto(1L, "text", new Date(), "zhurasem", 1L);

        Mockito.when(commentService.readById(1L)).thenReturn(Optional.of(comment));
        Mockito.when(commentConverter.toDto(comment)).thenReturn(commentDto);

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cid", Matchers.is(1)))
                .andExpect(jsonPath("$.text", Matchers.is("text")))
                .andExpect(jsonPath("$.authorCommentId", Matchers.is("zhurasem")))
                .andExpect(jsonPath("$.petitionCommentId", Matchers.is(1)));
    }

    @Test
    public void testUpdate() throws Exception {
        Comment comment = new Comment(1L, "text", new Date(), new User(), new Petition());

        CommentDto commentDto = new CommentDto(1L, "text", new Date(), "zhurasem", 1L);

        Mockito.when(commentService.readById(1L)).thenReturn(Optional.of(comment));
        Mockito.when(commentService.update(comment)).thenReturn(comment);
        Mockito.when(commentConverter.toDto(comment)).thenReturn(commentDto);

        mockMvc.perform(put("/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{" +
                                "\"cid\":1," +
                                "\"text\":\"text\"," +
                                "\"dateFrom\":\"2023-12-16T12:00:00\"," +
                                "\"authorCommentId\":\"zhurasem\"," +
                                "\"petitionCommentId\":1" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), author, new ArrayList<>(), new ArrayList<>());

        Comment comment = new Comment(1L, "text", new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), author, petition);

        CommentDto commentDto = new CommentDto(1L, "text", new Date(dateFormat.parse("2023-12-16T12:00:00").getTime()), "zhurasem", 1L);

        Mockito.when(commentService.create(comment)).thenReturn(comment);
        Mockito.when(commentConverter.toDto(comment)).thenReturn(commentDto);
        Mockito.when(commentService.readById(1L)).thenReturn(Optional.of(comment));

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"cid\":1," +
                                        "\"text\":\"text\"," +
                                        "\"dateFrom\":\"2023-12-16T12:00:00\"," +
                                        "\"authorCommentId\":\"zhurasem\"," +
                                        "\"petitionCommentId\":1" +
                                        "}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk());
    }
}
