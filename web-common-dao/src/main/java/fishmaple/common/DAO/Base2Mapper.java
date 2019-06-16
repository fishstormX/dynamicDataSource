package fishmaple.common.DAO;

import fishmaple.common.config.dynamicDataSource.annotations.TargetDataSource;
import org.springframework.stereotype.Component;

@Component
@TargetDataSource("ds1")
public interface Base2Mapper {

    @TargetDataSource("ds1")
    public Long getCount();
}

