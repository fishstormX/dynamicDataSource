package fishmaple.common.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class DefaultPropertiesLoader implements PropertiesLoader,InitializingBean{
    private Environment environment;

    private String prefix;

    @Override
    public void setPrefix(String str){
        this.prefix=str;
    }

    @Override
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    public DefaultPropertiesLoader(String prefix){
        this.prefix=prefix;
    }
    public DefaultPropertiesLoader(Environment env,String prefix){
        this.prefix=prefix;
        this.environment=env;
    }
    public DefaultPropertiesLoader(){
        this.prefix="";
    }


    @Override
    public String getValue(String key) {
        return environment.getProperty(this.prefix+"."+key);
    }



    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
