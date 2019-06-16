package fishmaple.common.facade;

import fishmaple.common.DAO.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DbHold {
    @Autowired
    BaseMapper baseMapper;
    public Integer getRandomOne(){
        System.out.print(baseMapper.getCount());
        return 12;
    }
}
