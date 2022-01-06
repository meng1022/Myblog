package backend.service;

import java.util.List;
import backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserService {
//    @Autowired
//    UserDao userDao;
    @Autowired
    HibernateTemplate hibernateTemplate;

    final Logger logger = LoggerFactory.getLogger(getClass());

//    public User register(String email,String password,String name){
//        logger.info("try to register {}...",email);
//        try{
//            User user = userDao.createUser(email,password,name);
//            return user;
//        }catch (RuntimeException e){
//            logger.info("register failed");
//            throw new RuntimeException("register failed");
//        }
//    }
    public User register(String email,String password,String name){
        logger.info("try to register {}...",email);
        try{
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            hibernateTemplate.save(user);
            return user;
        }catch (RuntimeException e){
            logger.info("register failed");
            throw new RuntimeException("register failed");
        }
    }

    public User getUserById(long id){
        User example = new User();
        example.setId(id);
        List<User> users = hibernateTemplate.findByExample(example);
        if(users.size()!=0)
            return users.get(0);
        return null;
    }
//    public User signin(String email, String password){
//        logger.info("try to sign in by {}...",email);
//        User user = userDao.getUserByEmail(email);
//        if(user.getPassword().equals(password))
//            return user;
//        throw new RuntimeException("login failed.");
//    }

    public User signin(String email, String password){
        logger.info("try to sign in by {}...",email);
        User example = new User();
        example.setEmail(email);
        List<User> users = hibernateTemplate.findByExample(example);
        if(users.size()==0||!users.get(0).getPassword().equals(password))
            return null;
        else
            return users.get(0);
    }

}
