package backend.service;

import java.util.List;
import backend.entity.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ModuleService {
    @Autowired
    HibernateTemplate hibernateTemplate;
    final Logger logger = LoggerFactory.getLogger(getClass());

    public Module createmodule(String modulename){
        Module module = new Module();
        module.setModulename(modulename);
        try{
            hibernateTemplate.save(module);
            return module;
        }catch (RuntimeException e){
            logger.info("create module {} failed",modulename);
            throw new RuntimeException("create module failed");
        }
    }

    public List<Module> getmodules(){
        Module module = new Module();
        List<Module> list = hibernateTemplate.findByExample(module);
        return list;
    }

    public Module getModule(long moduleid){
        return hibernateTemplate.get(Module.class,moduleid);
    }
}
