package zhurasem.project.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import zhurasem.project.api.dto.PetitionDto;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.business.CommentService;
import zhurasem.project.business.UserService;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class PetitionConverter {

    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public PetitionConverter(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    public Petition toEntity(PetitionDto petitionDto) throws EntityStateException {

        // author
        Optional<User> optAuthorPetition = userService.readById(petitionDto.getAuthorPetitionId());
        User authorPetition = optAuthorPetition.orElseThrow(
                () -> new EntityStateException());

        // comments
        Collection<Comment> comments = new ArrayList<>();
        for(Long commentId : petitionDto.getCommentsIds())
            comments.add(commentService.readById(commentId).orElseThrow(
                    () -> new EntityStateException()));

        // signedBy users
        Collection<User> signedBy = new ArrayList<>();
        for(String userId : petitionDto.getSignedUsersIds())
            signedBy.add(userService.readById(userId).orElseThrow(
                    () -> new EntityStateException()));

        return new Petition(
                petitionDto.getPid(),
                petitionDto.getTitle(),
                petitionDto.getText(),
                petitionDto.getGoal(),
                petitionDto.getDateFrom(),
                authorPetition,
                comments,
                signedBy);
    }

    public List<Petition> toEntities(Iterable<PetitionDto> petitionDtos) {
        List<Petition> entitites = new ArrayList<>();
        for(PetitionDto petitionDto : petitionDtos)
            entitites.add(toEntity(petitionDto));
        return entitites;
    }

    public PetitionDto toDto(Petition petition) {
        return new PetitionDto(
                petition.getPid(),
                petition.getTitle(),
                petition.getText(),
                petition.getGoal(),
                petition.getDateFrom(),
                petition.getAuthorPetition().getId(),
                petition.getComments().stream().map(Comment::getCid).toList(),
                petition.getSignedBy().stream().map(User::getUsername).toList()
        );
    }

    public List<PetitionDto> toDtos(Iterable<Petition> petitions) {
        List<PetitionDto> dtos = new ArrayList<>();
        for(Petition petition : petitions)
            dtos.add(toDto(petition));
        return dtos;
    }
}
