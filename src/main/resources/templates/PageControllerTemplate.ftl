package ${package}.web.controller;

import com.surekam.platform.core.security.api.SecurityManage;
import ${package}.entity.${entityName};
import ${package}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @description: 页面跳转Controller
 * @author ${author}
 * @date ${now}
 */
@Controller
@RequestMapping("${instanceName}/page")
public class ${entityName}PageController {

    @Autowired
    private ${entityName}Service ${instanceName}Service;

    /**
     * 页面跳转-主页面
     */
    @RequestMapping("/index")
    public String gotoIndexPage(Model model) throws Exception {
        String currentUserId = SecurityManage.getInstance().getCurrentUser().getUserSeq();
        model.addAttribute("CURRENT_STAFF_ID", currentUserId);
        return "${instanceName}/index";
    }

    /**
     * 页面跳转-新增页面
     */
    @RequestMapping("/pre-add")
    public String gotoPreAddPage(Model model, String id) throws Exception {
        ${entityName} ${instanceName} = new ${entityName}();
        if (!StringUtils.isEmpty(id)) {
            ${instanceName} = ${instanceName}Service.findById(id);
        }
        model.addAttribute("${instanceName}", ${instanceName});
        return "${instanceName}/preAdd";
    }

}
