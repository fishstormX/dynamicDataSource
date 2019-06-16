package fishmaple.common.web;

import fishmaple.common.DAO.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class BaseController {
    @GetMapping("index")
    public String index(){
        return "index";
    }

}
