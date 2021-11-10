package ${package}.service.impl;

import com.surekam.platform.core.persistence.Page;
import com.surekam.platform.core.persistence.dao.HibernateBaseDAO;
import ${package}.dao.${entityName}Dao;
import ${package}.entity.${entityName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ${entityName}DaoImpl extends HibernateBaseDAO implements ${entityName}Dao {

    @Autowired
    private ${entityName}Dao ${instanceName}Dao;

    @Override
    public void save(${entityName} ${instanceName}) {
        this.saveObject(${instanceName});
    }

    @Override
    public void delById(String id) {
        this.removeObject(${entityName}.class, id);
    }

    @Override
    public ${entityName} findById(String id) {
        ${entityName}  ${instanceName} = (${entityName}) this.getObject(${entityName}.class, id);
        return ${instanceName};
    }


    @Override
    public Page<${entityName}> getPage(Map<String, String> searchParam, int curPage, int pageNum) {
        StringBuffer hql = new StringBuffer("from ${entityName} where 1=1 ");
        Map<String, Object> paramMap = new HashMap<>();
        if (searchParam.get("title") != null && !"".equals(searchParam.get("title"))) {
            hql.append(" and title like :title \n");
            paramMap.put("title", "%" + searchParam.get("title") + "%");
        }
        hql.append(" order by createTime desc");
        Page<${entityName}> page = this.find(hql.toString(), paramMap, curPage, pageNum);
        return page;
    }

    @Override
    public List<${entityName}> getList(Map<String, String> searchParam) {
         StringBuffer hql = new StringBuffer("from ${entityName} where 1=1 ");
        Map<String, Object> paramMap = new HashMap<>();
        if (searchParam.get("title") != null && !"".equals(searchParam.get("title"))) {
            hql.append(" and title like :title \n");
            paramMap.put("title", "%" + searchParam.get("title") + "%");
        }
        hql.append(" order by createTime desc");
        final List list = this.find(hql.toString(), paramMap);
        return list;
     }

}