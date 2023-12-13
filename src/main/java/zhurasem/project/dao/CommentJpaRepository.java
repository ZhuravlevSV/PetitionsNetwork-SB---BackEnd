package zhurasem.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zhurasem.project.domain.Comment;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
