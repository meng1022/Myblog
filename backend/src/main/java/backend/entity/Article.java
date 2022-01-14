package backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="articles")
@Entity
public class Article extends AbstractEntity{
    private String title;
    private Long moduleid;
    private String content;
    private Long userid;

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public Long getModuleid(){
        return moduleid;
    }

    public void setModuleid(Long moduleid){
        this.moduleid = moduleid;
    }

    @Column(nullable = false)
    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    @Column(nullable = false)
    public Long getUserid(){
        return userid;
    }

    public void setUserid(Long userid){
        this.userid = userid;
    }

}
