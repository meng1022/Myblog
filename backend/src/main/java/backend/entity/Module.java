package backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="modules")
@Entity
public class Module extends AbstractEntity{
    private String modulename;

    @Column(nullable = false)
    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }
}
