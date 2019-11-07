package moe.keshane.PikaImage.Dao.Repo;

import moe.keshane.PikaImage.Dao.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
    User findUserByUsername(String userName);
    User findUserByUserId(String userUuid);
}
