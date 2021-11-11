package ${package}.web.controller;

import com.grid.bootstraptable.BootStrapTablePage;
import com.grid.bootstraptable.BootstrapTableDTO;
import com.surekam.platform.core.persistence.Page;
import com.surekam.platform.core.security.api.SecurityManage;
import ${package}.entity.${entityName};
import ${package}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * @description: 查询Controller
 * @author ${author}
 * @date ${now}
 */
@Controller
@RequestMapping("${instanceName}/data")
public class ${entityName}DataController {

    @Autowired
    private ${entityName}Service ${instanceName}Service;

    /**
     * 获取bootstrap的分页
     *
     * @param tableDTO
     * @return
     * @throws Exception
     */
    @RequestMapping("/page")
    @ResponseBody
    public BootStrapTablePage get${entityName}BootStrapPage(BootstrapTableDTO tableDTO) throws Exception {
        int curPage = tableDTO.getPageNumber();
        int pageNum = tableDTO.getPageSize();
        Map params = tableDTO.getSearchParam();
        params.put("staffId", SecurityManage.getInstance().getCurrentUser().getUserSeq());
        Page<${entityName}> page = ${instanceName}Service.getPage(tableDTO.getSearchParam(), curPage, pageNum);
        return new BootStrapTablePage(page);
    }

}
