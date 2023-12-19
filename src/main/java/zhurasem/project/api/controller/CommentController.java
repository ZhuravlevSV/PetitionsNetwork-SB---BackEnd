package zhurasem.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zhurasem.project.api.converter.CommentConverter;
import zhurasem.project.api.dto.CommentDto;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.business.CommentService;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @Autowired
    public CommentController(CommentService commentService, CommentConverter commentConverter) {
        this.commentService = commentService;
        this.commentConverter = commentConverter;
    }

    @GetMapping("/comments")
    List<CommentDto> getAll() {
        return commentConverter.toDtos(commentService.readAll());
    }

    @PostMapping("/comments")
    CommentDto create(@RequestBody CommentDto commentDto) {
        try {
            return commentConverter.toDto(commentService.create(commentConverter.toEntity(commentDto)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Author ID, petition ID: not found");
        }
    }

    @PutMapping("/comments/{id}")
    CommentDto update(@RequestBody CommentDto commentDto, @PathVariable Long id) {
        var comment = commentService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        try {
            comment.setText(commentDto.getText());
            comment.setDateFrom(commentDto.getDateFrom());
            commentService.update(comment);
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment ID is not found");
        }
        return commentConverter.toDto(comment);
    }

    @GetMapping("/comments/{id}")
    CommentDto get(@PathVariable Long id) {
        return commentConverter.toDto(commentService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found")));
    }

    @DeleteMapping("/comments/{id}")
    void delete(@PathVariable Long id) {
        commentService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        commentService.deleteById(id);
    }

}
