package zhurasem.project.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentJpaRepositoryTest {

    @Autowired
    CommentJpaRepository commentJpaRepository;

    @Autowired
    PetitionJpaRepository petitionJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    public void createReadUpdateDelete() {

        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        userJpaRepository.save(author);

        Petition petition = new Petition(
                0L,
                "Title 1",
                "text 1",
                1000,
                new Date(),
                author,
                new ArrayList<>(),
                new ArrayList<>()
        );
        petitionJpaRepository.save(petition);

        // create
        Comment comment1 = new Comment(
                0L,
                "text 1",
                new Date(),
                author,
                petition
        );
        Comment comment2 = new Comment(
                0L,
                "text 2",
                new Date(),
                author,
                petition
        );
        commentJpaRepository.save(comment1);
        commentJpaRepository.save(comment2);

        // read
        List<Comment> comments = commentJpaRepository.findAll();
        Optional<Comment> optComment = commentJpaRepository.findById(1L);

        Assertions.assertThat(comments).extracting(Comment::getCid).contains(1L, 2L);
        Assertions.assertThat(comments).extracting(Comment::getText).contains("text 1", "text 2");
        Assertions.assertThat(optComment).isPresent();
        optComment.ifPresent(comment -> Assertions.assertThat(comment.getCid()).isEqualTo(1L));
        optComment.ifPresent(comment -> Assertions.assertThat(comment.getText()).isEqualTo("text 1"));

        // update
        comment1.setText("text updated 1");
        comment2.setText("text updated 2");

        commentJpaRepository.save(comment1);
        commentJpaRepository.save(comment2);

        List<Comment> updatedComments = commentJpaRepository.findAll();
        Assertions.assertThat(updatedComments).extracting(Comment::getText).contains("text updated 1", "text updated 2");

        // delete
        commentJpaRepository.deleteById(1L);
        commentJpaRepository.deleteById(2L);

        List<Comment> remainingComments = commentJpaRepository.findAll();
        Assertions.assertThat(remainingComments).isEmpty();
    }
}
