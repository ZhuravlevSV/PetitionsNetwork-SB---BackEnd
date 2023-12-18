package zhurasem.project.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zhurasem.project.domain.User;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserJpaRepositoryTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    public void createReadUpdateDelete() {

        // create
        User user1 = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        User user2 = new User("putinvla", "putinvla@fit.cvut.cz", "228");

        userJpaRepository.save(user1);
        userJpaRepository.save(user2);

        // read
        List<User> users = userJpaRepository.findAll();
        Optional<User> optUser = userJpaRepository.findById("zhurasem");

        Assertions.assertThat(users).extracting(User::getUsername).contains("zhurasem", "putinvla");
        Assertions.assertThat(optUser).isPresent();
        optUser.ifPresent(user -> Assertions.assertThat(user.getUsername()).isEqualTo("zhurasem"));

        // update
        user1.setEmail("zhurasem@cvut.cz");
        user1.setPassword("321");
        user2.setEmail("putinvla@cvut.cz");
        user2.setPassword("111");

        userJpaRepository.save(user1);
        userJpaRepository.save(user2);

        List<User> updatedUsers = userJpaRepository.findAll();
        Assertions.assertThat(updatedUsers).extracting(User::getEmail).contains("zhurasem@cvut.cz", "putinvla@cvut.cz");
        Assertions.assertThat(updatedUsers).extracting(User::getPassword).contains("321", "111");

        // delete
        userJpaRepository.deleteById("zhurasem");
        userJpaRepository.deleteById("putinvla");

        List<User> remainingUsers = userJpaRepository.findAll();
        Assertions.assertThat(remainingUsers).isEmpty();
    }
}
