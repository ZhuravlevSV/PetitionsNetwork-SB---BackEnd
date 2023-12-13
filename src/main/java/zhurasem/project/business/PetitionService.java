package zhurasem.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurasem.project.dao.PetitionJpaRepository;
import zhurasem.project.domain.Petition;

@Service
public class PetitionService extends AbstractCrudService<Petition, Long> {

    @Autowired
    public PetitionService(PetitionJpaRepository petitionJpaRepository) {
        super(petitionJpaRepository);
    }

}
