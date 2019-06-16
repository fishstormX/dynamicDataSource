package fishmaple.common.util;

import org.springframework.core.env.Environment;

/**
 * 加载.properties文件
 * @author 鱼鱼
 * */
public interface PropertiesLoader {
    String getValue(String key);
    void setPrefix(String prefix);
    void setEnvironment(Environment environment);
}
