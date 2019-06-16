package fishmaple.common.config.dynamicDataSource.annotations;

import java.lang.annotation.*;

/**

 * 在方法上使用，用于指定使用哪个数据源

 * @author Angel(QQ:412887952)

 * @version v.0.1

 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface TargetDataSource {
    String value();

}