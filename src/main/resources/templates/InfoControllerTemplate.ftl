package ${package}.web.controller;

import com.surekam.platform.core.security.comet.CometEngineAdapter;
import ${package}.entity.${entityName};
import ${package}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description: 操作Controller
 * @author ${author}
 * @date ${now}
 */
@Controller
@RequestMapping("${instanceName}/info")
public class ${entityName}InfoController {

    @Autowired
    private ${entityName}Service ${instanceName}Service;


    /**
     * 持久化
     * @param ${instanceName}
     * @return
     */
    @RequestMapping("/save")
    public String save(@ModelAttribute ${entityName} ${instanceName}) {
        if ("".equals(${instanceName}.getId())) {
            ${instanceName}.setId(null);
        }
        ${instanceName}Service.save(${instanceName});
        String sendMsg = "{key:'${entityName}_GROUP',data:{type:1,msg:'刷新'}}";
        CometEngineAdapter.sendToAll(CometEngineAdapter.CHANNEL_ITSM_THIN, sendMsg);
        return "common/success";
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public boolean delete(String id) {
        try {
            ${instanceName}Service.delById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
