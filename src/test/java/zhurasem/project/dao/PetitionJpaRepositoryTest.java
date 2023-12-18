package zhurasem.project.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PetitionJpaRepositoryTest {

    @Autowired
    PetitionJpaRepository petitionJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    public void createReadUpdateDelete() {

        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        userJpaRepository.save(author);

        // create
        Petition petition1 = new Petition(
                0L,
                "Title 1",
                "text 1",
                1000,
                new Date(),
                author,
                new ArrayList<>(),
                new ArrayList<>()
                );

        Petition petition2 = new Petition(
                0L,
                "Title 2",
                "text 2",
                1000,
                new Date(),
                author,
                new ArrayList<>(),
                new ArrayList<>()
        );

        petitionJpaRepository.save(petition1);
        petitionJpaRepository.save(petition2);

        // read
        List<Petition> petitions = petitionJpaRepository.findAll();
        Optional<Petition> optPetition = petitionJpaRepository.findById(1L);

        Assertions.assertThat(petitions).extracting(Petition::getPid).contains(1L, 2L);
        Assertions.assertThat(petitions).extracting(Petition::getTitle).contains("Title 1", "Title 2");
        Assertions.assertThat(optPetition).isPresent();
        optPetition.ifPresent(petition -> Assertions.assertThat(petition.getPid()).isEqualTo(1L));
        optPetition.ifPresent(petition -> Assertions.assertThat(petition.getTitle()).isEqualTo("Title 1"));
        optPetition.ifPresent(petition -> Assertions.assertThat(petition.getText()).isEqualTo("text 1"));

        // update
        petition1.setTitle("Title updated 1");
        petition1.setText("text updated 1");
        petition2.setTitle("Title updated 2");
        petition2.setText("text updated 2");

        petitionJpaRepository.save(petition1);
        petitionJpaRepository.save(petition2);

        List<Petition> updatedPetitions = petitionJpaRepository.findAll();
        Assertions.assertThat(updatedPetitions).extracting(Petition::getTitle).contains("Title updated 1", "Title updated 2");
        Assertions.assertThat(updatedPetitions).extracting(Petition::getText).contains("text updated 1", "text updated 2");

        // delete
        petitionJpaRepository.deleteById(1L);
        petitionJpaRepository.deleteById(2L);

        List<Petition> remainingPetitions = petitionJpaRepository.findAll();
        Assertions.assertThat(remainingPetitions).isEmpty();
    }
}
