package project.main.webstore.domain.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.main.webstore.domain.users.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u")
    Page<User> findUserPage(Pageable pageable);

    Optional<User> findByNickName(String nicName);

    Optional<User> findAllInfoById(Long userId);

    Optional<User> findByEmailAndAndUserNameAndPhone(String email, String userName, String phone);
}
