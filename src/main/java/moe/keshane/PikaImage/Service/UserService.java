package moe.keshane.PikaImage.Service;

import moe.keshane.PikaImage.Dao.Entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User login(String username,String password);
    User register(String username,String password,String rePassword);
}
