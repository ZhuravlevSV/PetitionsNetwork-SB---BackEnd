package zhurasem.project.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import zhurasem.project.dao.CommentJpaRepository;
import zhurasem.project.domain.Comment;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    UserService userService;

    @Mock
    PetitionService petitionService;

    @Mock
    CommentJpaRepository commentJpaRepository;

    @Test
    public void testCreate() {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), author, new ArrayList<>(), new ArrayList<>());
        Comment comment = new Comment(1L, "text", new Date(), author, petition);

        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(author));
        Mockito.when(petitionService.readById(1L)).thenReturn(Optional.of(petition));

        commentService.create(comment);

        Mockito.verify(commentJpaRepository, Mockito.times(1)).save(comment);
    }

    @Test
    public void testReadAll() {
        Comment comment1 = new Comment(1L, "text 1", new Date(), new User(), new Petition());
        Comment comment2 = new Comment(2L, "text 2", new Date(), new User(), new Petition());
        List<Comment> comments = List.of(comment1, comment2);

        Mockito.when(commentJpaRepository.findAll()).thenReturn(comments);

        List<Comment> commentsReturn = StreamSupport.stream(commentService.readAll().spliterator(), false).toList();
        assertEquals(2, commentsReturn.size());
        Mockito.verify(commentJpaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testRead() {
        Comment comment = new Comment(1L, "text", new Date(), new User(), new Petition());

        Mockito.when(commentJpaRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment commentReturn = commentService.readById(1L).get();

        assertEquals(comment.getId(), commentReturn.getId());
        Mockito.verify(commentJpaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), author, new ArrayList<>(), new ArrayList<>());
        Comment comment = new Comment(1L, "text", new Date(), author, petition);

        Mockito.when(commentJpaRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(author));
        Mockito.when(petitionService.readById(1L)).thenReturn(Optional.of(petition));

        commentService.update(comment);

        Mockito.verify(commentJpaRepository, Mockito.times(1)).save(comment);
    }

    @Test
    public void testDelete() {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), author, new ArrayList<>(), new ArrayList<>());
        Comment comment = new Comment(1L, "text", new Date(), author, petition);

        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(author));
        Mockito.when(petitionService.readById(1L)).thenReturn(Optional.of(petition));

        commentService.create(comment);

        Mockito.verify(commentJpaRepository, Mockito.times(1)).save(comment);

        commentService.deleteById(1L);

        Mockito.verify(commentJpaRepository, Mockito.times(1)).deleteById(1L);
    }
}
