package fishmaple.common;

import fishmaple.common.config.dynamicDataSource.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan(value = "fishmaple.common.DAO")
//@EnableCaching              //开启缓存
//@EnableTransactionManagement    //开启事务
@EnableScheduling   //开启定时器
@Import({DynamicDataSourceRegister.class})
public class MainApplication {

    private static Logger log= LoggerFactory.getLogger(MainApplication.class);
    public static void main(String[] args)
    {
          SpringApplication.run(MainApplication.class, args);

    }
}
