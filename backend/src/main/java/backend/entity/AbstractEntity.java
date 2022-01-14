package backend.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@MappedSuperclass
public class AbstractEntity {
    private Long id;
    private Long createTime;
    private Long updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @Column(nullable = false,updatable = false)
    public Long getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Long createTime){
        this.createTime = createTime;
    }

    @Column
    public Long getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Long updateTime){
        this.updateTime = updateTime;
    }

    @Transient // clarify createzonetime is not a key in db
    public String getCreateZoneTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.createTime);
    }

    @Transient // clarify createzonetime is not a key in db
    public String getCreateZoneDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.createTime);
    }

    @Transient // clarify updatezonetime is not a key in db
    public String getUpdateZoneTime(){
        if(this.updateTime!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(this.updateTime);
        }
        return getCreateZoneTime();
    }

    @Transient // clarify createzonetime is not a key in db
    public String getUpdateZoneDate(){
        if(this.updateTime!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(this.updateTime);
        }
        return getCreateZoneDate();
    }

    @PrePersist // set the createTime key before a entry is inserted to db
    public void preInsert(){
        setCreateTime(System.currentTimeMillis());
    }

    @PreUpdate // set the createTime key before a entry is inserted to db
    public void preUpdate(){
        setUpdateTime(System.currentTimeMillis());
    }
}
