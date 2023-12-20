package zhurasem.project.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import zhurasem.project.dao.PetitionJpaRepository;
import zhurasem.project.domain.Petition;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@ExtendWith(MockitoExtension.class)
public class PetitionServiceTest {

    @InjectMocks
    PetitionService petitionService;

    @Mock
    UserService userService;

    @Mock
    PetitionJpaRepository petitionJpaRepository;

    @Test
    public void testCreate() {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), author, new ArrayList<>(), new ArrayList<>());

        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(author));

        petitionService.create(petition);

        Mockito.verify(petitionJpaRepository, Mockito.times(1)).save(petition);
    }

    @Test
    public void testReadAll() {
        Petition petition1 = new Petition(1L, "Title 1", "text 1", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());
        Petition petition2 = new Petition(2L, "Title 2", "text 2", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());
        List<Petition> petitions = List.of(petition1, petition2);

        Mockito.when(petitionJpaRepository.findAll()).thenReturn(petitions);

        List<Petition> petitionsReturn = StreamSupport.stream(petitionService.readAll().spliterator(), false).toList();
        assertEquals(2, petitionsReturn.size());
        Mockito.verify(petitionJpaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testRead() {
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), new User(), new ArrayList<>(), new ArrayList<>());

        Mockito.when(petitionJpaRepository.findById(1L)).thenReturn(Optional.of(petition));

        Petition petitionReturn = petitionService.readById(1L).get();

        assertEquals(petition.getId(), petitionReturn.getId());
        Mockito.verify(petitionJpaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), author, new ArrayList<>(), new ArrayList<>());

        Mockito.when(petitionJpaRepository.findById(1L)).thenReturn(Optional.of(petition));
        Mockito.when(petitionJpaRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(author));

        petitionService.update(petition);

        Mockito.verify(petitionJpaRepository, Mockito.times(1)).save(petition);
    }

    @Test
    public void testDelete() {
        User author = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        Petition petition = new Petition(1L, "Title", "text", 1000, new Date(), author, new ArrayList<>(), new ArrayList<>());

        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(author));

        petitionService.create(petition);

        Mockito.verify(petitionJpaRepository, Mockito.times(1)).save(petition);

        Mockito.when(petitionJpaRepository.findById(1L)).thenReturn(Optional.of(petition));

        petitionService.deleteById(1L);

        Mockito.verify(petitionJpaRepository, Mockito.times(1)).deleteById(1L);
    }
}
