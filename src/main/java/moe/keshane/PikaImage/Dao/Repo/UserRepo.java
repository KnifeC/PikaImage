package moe.keshane.PikaImage.Dao.Repo;

import moe.keshane.PikaImage.Dao.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,String> {
    User findUserByUsername(String userName);
    User findUserByUserId(String userUuid);
    long countAllByTypeEquals(String type);
}
