package zhurasem.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurasem.project.dao.CommentJpaRepository;
import zhurasem.project.domain.Comment;

@Service
public class CommentService extends AbstractCrudService<Comment, Long>{

    @Autowired
    public CommentService(CommentJpaRepository commentJpaRepository) {
        super(commentJpaRepository);
    }
}
