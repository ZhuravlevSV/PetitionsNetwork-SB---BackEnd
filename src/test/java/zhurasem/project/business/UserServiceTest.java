package zhurasem.project.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import zhurasem.project.dao.UserJpaRepository;
import zhurasem.project.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserJpaRepository userJpaRepository;

    @Test
    public void testCreate() {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        userService.create(user);

        Mockito.verify(userJpaRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testReadAll() {
        User user1 = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        User user2 = new User("putinvla", "putinvla@fit.cvut.cz", "123");
        List<User> users = List.of(user1, user2);

        Mockito.when(userJpaRepository.findAll()).thenReturn(users);

        List<User> usersReturn = StreamSupport.stream(userService.readAll().spliterator(), false).toList();
        assertEquals(2, usersReturn.size());
        Mockito.verify(userJpaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testRead() {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        Mockito.when(userJpaRepository.findById("zhurasem")).thenReturn(Optional.of(user));

        User userReturn = userService.readById("zhurasem").get();

        assertEquals(user.getId(), userReturn.getId());
        Mockito.verify(userJpaRepository, Mockito.times(1)).findById("zhurasem");
    }

    @Test
    public void testUpdate() {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        Mockito.when(userJpaRepository.existsById("zhurasem")).thenReturn(true);

        userService.update(user);

        Mockito.verify(userJpaRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testDelete() {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        userService.create(user);

        Mockito.verify(userJpaRepository, Mockito.times(1)).save(user);

        userService.deleteById("zhurasem");

        Mockito.verify(userJpaRepository, Mockito.times(1)).deleteById("zhurasem");
    }
}
