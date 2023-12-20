package zhurasem.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zhurasem.project.domain.Petition;

import java.util.List;

@Repository
public interface PetitionJpaRepository extends JpaRepository<Petition, Long> {
    List<Petition> findAllByAuthorPetition_Username(String username);
}
