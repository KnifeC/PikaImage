package moe.keshane.PikaImage.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Dao.Repo.UserRepo;
import moe.keshane.PikaImage.Exception.DataInputException;
import moe.keshane.PikaImage.Exception.DatabaseException;
import moe.keshane.PikaImage.Exception.UserNotFoundException;
import moe.keshane.PikaImage.Service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User login(String username, String password) {
        String encodePassword = DigestUtils.sha256Hex(password);
        try{
            User user = userRepo.getUserByUsernameAndPassword(username, encodePassword);
            if(user == null){
                throw new UserNotFoundException("用户名或密码错误");
            }
//            user.setPassword("");
            return user;
        }catch(Exception e){
            e.printStackTrace();
            throw new DatabaseException("数据库异常",e);
        }

    }

    @Override
    public User register(String username, String password,String rePassword) {
        if(!password.equals(rePassword)){
//            log.error("{}两次输入密码{}不一致{}",username,password,rePassword);
            throw new DataInputException("两次输入的密码不一致");
        }
        User user = new User();
        user.setUsername(username);
        String encodePassword = DigestUtils.sha256Hex(password);
        user.setPassword(encodePassword);
        if(userRepo.countAllByTypeEquals("admin")==0){
            user.setType("admin");
        }
        try {
            User save = userRepo.save(user);
            return save;
        }catch (Exception se){
            se.printStackTrace();
            throw new DatabaseException("数据库异常",se);
        }
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepo.findUserByUserId(userId);
        return user;
    }
}
