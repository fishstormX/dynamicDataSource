package fishmaple.common.web.api;

import fishmaple.common.DAO.Base2Mapper;
import fishmaple.common.DAO.BaseMapper;
import fishmaple.common.config.dynamicDataSource.annotations.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class OpenAPI {
    @Autowired
    BaseMapper baseMapper;
    @Autowired
    Base2Mapper base2Mapper;

    @GetMapping("")
    public String index(){
        return baseMapper.getCount().toString();
    }
    @GetMapping("ds2")
    @TargetDataSource("ds2")
    public String indexx(){
        return base2Mapper.getCount().toString();
    }
}
