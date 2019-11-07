package moe.keshane.PikaImage.Service.Impl;

import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Dao.Repo.UserRepo;
import moe.keshane.PikaImage.Exception.DataInputException;
import moe.keshane.PikaImage.Exception.DatabaseException;
import moe.keshane.PikaImage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public User register(String username, String password, String rePassword) {
        if(!password.equals(rePassword)){
            throw new DataInputException("两次输入的密码不一致");
        }
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userRepo.save(user);
            return user;
        }catch (Exception se){
            se.printStackTrace();
            throw new DatabaseException("数据库异常",se);
        }
    }
}
