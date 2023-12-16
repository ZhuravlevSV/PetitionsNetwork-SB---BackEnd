package zhurasem.project.api.converter;

import org.springframework.stereotype.Component;
import zhurasem.project.api.dto.CommentDto;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.business.PetitionService;
import zhurasem.project.business.UserService;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CommentConvertor {

    private final UserService userService;
    private final PetitionService petitionService;

    public CommentConvertor(UserService userService, PetitionService petitionService) {
        this.userService = userService;
        this.petitionService = petitionService;
    }

    public Comment toEntity(CommentDto commentDto) throws EntityStateException {

        // author
        Optional<User> optAuthorComment = userService.readById(commentDto.getAuthorCommentId());
        User authorPetition = optAuthorComment.orElseThrow(
                () -> new EntityStateException());

        // petition
        Optional<Petition> optPetitionComment = petitionService.readById(commentDto.getPetitionCommentId());
        Petition petitionComment = optPetitionComment.orElseThrow(
                () -> new EntityStateException());

        return new Comment(
                commentDto.getCid(),
                commentDto.getText(),
                commentDto.getDateFrom(),
                authorPetition,
                petitionComment
        );
    }

    public List<Comment> toEntities(Iterable<CommentDto> commentDtos) {
        List<Comment> entities = new ArrayList<>();
        for(CommentDto commentDto : commentDtos)
            entities.add(toEntity(commentDto));
        return entities;
    }

    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getCid(),
                comment.getText(),
                comment.getDateFrom(),
                comment.getAuthorComment().getId(),
                comment.getPetitionComment().getId()
        );
    }

    public List<CommentDto> toDtos(Iterable<Comment> comments) {
        List<CommentDto> dtos = new ArrayList<>();
        for(Comment comment : comments)
            dtos.add(toDto(comment));
        return dtos;
    }


}
