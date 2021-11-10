package ${package}.service.impl;

import com.surekam.platform.core.persistence.Page;
import ${package}.dao.${entityName}Dao;
import ${package}.entity.${entityName};
import ${package}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("${instanceName}Service")
@Transactional
public class ${entityName}ServiceImpl implements ${entityName}Service {

    @Autowired
    private ${entityName}Dao ${instanceName}Dao;

    @Override
    public void save(${entityName} ${entityName}) {
        ${instanceName}Dao.save(${entityName});
    }

    @Override
    public void delById(String id) throws Exception {
        ${instanceName}Dao.delById(id);
    }

    @Override
    public ${entityName} findById(String id) {
       return ${instanceName}Dao.findById(id);
    }

    @Override
    public Page<${entityName}> getPage(Map<String, String> searchParam, int curPage, int pageNum) {
        Page<${entityName}> ${instanceName}Page = ${instanceName}Dao.getPage(searchParam, curPage, pageNum);
        return ${instanceName}Page;
    }

    @Override
    public List<${entityName}> getList(Map<String, String> searchParam) {
        return ${instanceName}Dao.getList(searchParam);
    }

}


