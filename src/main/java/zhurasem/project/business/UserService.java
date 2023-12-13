package zhurasem.project.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhurasem.project.dao.UserJpaRepository;
import zhurasem.project.domain.User;

@Service
public class UserService extends AbstractCrudService<User, String>{

    @Autowired
    public UserService(UserJpaRepository userJpaRepository) {
        super(userJpaRepository);
    }

}
