package backend.dao;

import backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Component
@Transactional
public class UserDao extends AbstractDao<User>{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

    public User getUserByEmail(String email){
        User user = jdbcTemplate.queryForObject("select * from users where email = ?",rowMapper,email);
        return user;
    }

    public User createUser(String email,String password,String name){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp t = new Timestamp(System.currentTimeMillis());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);

        if(1!=jdbcTemplate.update((conn)->{
            var ps = conn.prepareStatement("insert into users(email,password,name,create) values(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1,email);
            ps.setObject(2,password);
            ps.setObject(3,name);
            ps.setObject(4,time);
            return ps;
        },keyHolder)){
            throw new RuntimeException("Insert Failed");
        }
        User user = new User(keyHolder.getKey().longValue(),email,password,name);
        return user;
    }

}
