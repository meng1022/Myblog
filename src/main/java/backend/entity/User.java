package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Table(name="users")
@Entity
public class User extends AbstractEntity{
    private String email;
    private String password;
    private String name;

    public User(){}

    public User(long id, String email, String password, String name) {
        super.setId(id);
        setEmail(email);
        setPassword(password);
        setName(name);
    }

    @Column(nullable = false,unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getImageUrl(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(this.email.trim().toLowerCase().getBytes(StandardCharsets.UTF_8));
            return "https://www.gravatar.com/avatar/" + String.format("%032x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
