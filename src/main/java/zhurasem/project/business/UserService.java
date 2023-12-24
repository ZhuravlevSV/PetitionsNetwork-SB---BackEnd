package zhurasem.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurasem.project.dao.UserJpaRepository;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractCrudService<User, String>{

    private final PetitionService petitionService;
    private final CommentService commentService;

    @Autowired
    public UserService(UserJpaRepository userJpaRepository, PetitionService petitionService, CommentService commentService) {
        super(userJpaRepository);
        this.petitionService = petitionService;
        this.commentService = commentService;
    }

    @Override
    public void deleteById(String id) {
        Optional<User> optUser = readById(id);
        User user = optUser.orElseThrow();

        // delete all signs
        user.getSignedByMe().forEach(petition -> petition.getSignedBy().remove(user));


        // delete all petitions
        List<Long> petitionsIds = user.getMyPetitions()
                .stream()
                .map(Petition::getId)
                .collect(Collectors.toList());

        for(Long petitionId : petitionsIds)
            petitionService.deleteById(petitionId);

        // delete all comments
        List<Long> commentsIds = user.getMyComments()
                .stream()
                .map(Comment::getId)
                .collect(Collectors.toList());

        for(Long commentId : commentsIds)
            commentService.deleteById(commentId);

        super.deleteById(id);
    }
}
