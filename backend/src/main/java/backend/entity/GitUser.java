package backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="gituser")
@Entity
public class GitUser {
    private Long userid;
    private String username;

    public GitUser(){}

    public GitUser(Long userid,String username){
        this.username = username;
        this.userid = userid;
    }

    @Id
    @Column(nullable = false,updatable = false)
    public Long getUserid(){
        return userid;
    }
    public void setUserid(Long userid){
        this.userid = userid;
    }

    @Column(nullable = false)
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

}
