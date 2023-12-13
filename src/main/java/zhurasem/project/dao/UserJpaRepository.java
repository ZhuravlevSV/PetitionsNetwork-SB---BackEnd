package zhurasem.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zhurasem.project.domain.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
}
