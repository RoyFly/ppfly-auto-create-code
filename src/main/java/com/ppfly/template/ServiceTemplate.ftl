package ${package}.service;

import com.surekam.platform.core.persistence.Page;
import java.util.List;
import java.util.Map;
import ${package}.entity.${entityName};

/**
 * @description: 业务类
 * @author ${author}
 * @date ${now}
 */
public interface ${entityName}Service {

    /**
     * @description: 持久化
     * @param ${instanceName}
     */
    void save(${entityName} ${instanceName});

    /**
     * @description: 根据id删除
     * @param id
     */
    void delById(String id) throws Exception;

    /**
     * @description: 通过id查询
     * @param id
     */
    ${entityName} findById(String id);

    /**
     * @description: 分页查询
     * @param searchParam
     */
    Page<${entityName}> getPage(Map<String, String> searchParam, int curPage, int pageNum);

    /**
     * @description: 获取满足条件的集合
     * @param searchParam
     */
    List<${entityName}> getList(Map<String, String> searchParam);

}


