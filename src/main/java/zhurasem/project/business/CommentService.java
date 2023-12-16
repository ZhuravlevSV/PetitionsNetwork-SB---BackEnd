package zhurasem.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.dao.CommentJpaRepository;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.Optional;

@Service
public class CommentService extends AbstractCrudService<Comment, Long>{

    private final UserService userService;
    private final PetitionService petitionService;

    @Autowired
    @Lazy
    public CommentService(CommentJpaRepository commentJpaRepository, UserService userService, PetitionService petitionService) {
        super(commentJpaRepository);
        this.userService = userService;
        this.petitionService = petitionService;
    }

    @Override
    public Comment create(Comment entity) throws EntityStateException {
        Optional<User> optUser = userService.readById(entity.getAuthorComment().getUsername());
        Optional<Petition> optPetition = petitionService.readById(entity.getPetitionComment().getPid());
        if(optUser.isEmpty() || optPetition.isEmpty())
            throw new EntityStateException();
        return super.create(entity);
    }

    @Override
    public Comment update(Comment entity) throws EntityStateException {
        Optional<User> optUser = userService.readById(entity.getAuthorComment().getUsername());
        Optional<Petition> optPetition = petitionService.readById(entity.getPetitionComment().getPid());
        if(optUser.isEmpty() || optPetition.isEmpty())
            throw new EntityStateException();
        if(entity.getText().isEmpty())
            throw new EntityStateException();
        return super.update(entity);
    }
}
