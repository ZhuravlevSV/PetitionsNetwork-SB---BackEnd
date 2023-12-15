package zhurasem.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.dao.PetitionJpaRepository;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetitionService extends AbstractCrudService<Petition, Long> {

    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public PetitionService(PetitionJpaRepository petitionJpaRepository, UserService userService, CommentService commentService) {
        super(petitionJpaRepository);
        this.userService = userService;
        this.commentService = commentService;
    }

    @Override
    public Petition create(Petition entity) throws EntityStateException {
        Optional<User> optUser = userService.readById(entity.getAuthorPetition().getUsername());
        if(optUser.isEmpty())
            throw new EntityStateException();
        return super.create(entity);
    }

    @Override
    public Petition update(Petition entity) throws EntityStateException {
        Optional<Petition> optPetition = readById(entity.getId());
        Optional<User> optUser = userService.readById(entity.getAuthorPetition().getUsername());
        if(optPetition.isEmpty() || optUser.isEmpty())
            throw new EntityStateException();
        if(entity.getTitle().isEmpty() || entity.getText().isEmpty())
            throw new EntityStateException();
        return super.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Petition> optPetition = readById(id);

        Petition petition = optPetition.orElseThrow();

        Optional<User> optUser = userService.readById(petition.getAuthorPetition().getUsername());

        User user = optUser.orElseThrow();

        List<Long> commentsIds = petition.getComments()
                .stream()
                .map(Comment::getId)
                .collect(Collectors.toList());

        for(Long commentId : commentsIds)
            commentService.deleteById(commentId);

        /*
        user.getSignedByMe().remove(petition);
        userService.update(user);
        */

        super.deleteById(id);
    }

    public void signPetition(Long pid, String username) throws EntityStateException {
        Optional<Petition> optPetition = readById(pid);
        Optional<User> optUser = userService.readById(username);

        Petition petition = optPetition.orElseThrow();
        User user = optUser.orElseThrow();

        if(petition.getSignedBy().contains(user))
            throw new EntityStateException();

        petition.getSignedBy().add(user);
        user.getSignedByMe().add(petition);

        try {
            update(petition);
            userService.update(user);
        } catch (EntityStateException e) {
            throw new RuntimeException(e);
        }
    }

}
