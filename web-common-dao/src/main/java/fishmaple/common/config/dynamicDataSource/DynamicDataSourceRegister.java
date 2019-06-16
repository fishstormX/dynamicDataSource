package fishmaple.common.config.dynamicDataSource;

import fishmaple.common.util.DefaultPropertiesLoader;
import fishmaple.common.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.validation.DataBinder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 动态数据源注册;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */

public class DynamicDataSourceRegister  implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    Logger logger  = LoggerFactory.getLogger(this.getClass());

    //如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
    private ConversionService conversionService = new DefaultConversionService();
    private PropertyValues dataSourcePropertyValues;
    // 默认数据源
    private DataSource defaultDataSource;

    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();


    /**
     * 加载多数据源配置
     */

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("DynamicDataSourceRegister.setEnvironment()");
        initDefaultDataSource(environment);
        initCustomDataSources(environment);

    }



    /**

     * 加载主数据源配置.

     * @param env

     */

    private void initDefaultDataSource(Environment env){
        // 读取主数据源

        PropertiesLoader propertiesLoader = new DefaultPropertiesLoader();
        propertiesLoader.setPrefix("spring.datasource");
        propertiesLoader.setEnvironment(env);

        //Spring旧版本有此包 2.x已移除
        Map<String, Object> dsMap = new HashMap<String, Object>();
        dsMap.put("type", propertiesLoader.getValue("type"));
        dsMap.put("driverClassName", propertiesLoader.getValue("driver-class-name"));
        dsMap.put("url", propertiesLoader.getValue("url"));
        dsMap.put("username", propertiesLoader.getValue("username"));
        dsMap.put("password", propertiesLoader.getValue("password"));
        System.out.println(dsMap);



        //创建数据源;

        defaultDataSource = buildDataSource(dsMap);

        dataBinder(defaultDataSource, env);

    }



    /**

     * 初始化更多数据源

     *

     * @author SHANHY

     * @create 2016年1月24日

     */

    private void initCustomDataSources(Environment env) {

        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源

        PropertiesLoader propertiesLoader = new DefaultPropertiesLoader();
        propertiesLoader.setPrefix("custom.datasource");
        propertiesLoader.setEnvironment(env);
        String dsPrefixs = propertiesLoader.getValue("names");

        for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
            propertiesLoader.setPrefix("custom.datasource."+dsPrefix);
            Map<String, Object> dsMap = new HashMap<String, Object>();
            dsMap.put("type", propertiesLoader.getValue("type"));
            dsMap.put("driverClassName", propertiesLoader.getValue("driver-class-name"));
            dsMap.put("url", propertiesLoader.getValue("url"));
            dsMap.put("username", propertiesLoader.getValue("username"));
            dsMap.put("password", propertiesLoader.getValue("password"));

            DataSource ds = buildDataSource(dsMap);
            customDataSources.put(dsPrefix, ds);
            dataBinder(ds, env);
        }
    }



    /**

     * 创建datasource.

     * @param dsMap

     * @return

     */

    @SuppressWarnings("unchecked")

    public DataSource buildDataSource(Map<String, Object> dsMap) {

        Object type = dsMap.get("type");

        if (type == null){

            type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

        }

        Class<? extends DataSource> dataSourceType;



        try {

            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driverClassName").toString();

            String url = dsMap.get("url").toString();

            String username = dsMap.get("username").toString();

            String password = dsMap.get("password").toString();

            DataSourceBuilder factory =   DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);

            return factory.build();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

        return null;

    }



    /**

     * 为DataSource绑定更多数据

     * @param dataSource

     * @param env

     */

    private void dataBinder(DataSource dataSource, Environment env){

        //RelaxDataBinder 也被废弃了
        DataBinder dataBinder = new DataBinder(dataSource);

        dataBinder.setConversionService(conversionService);

       // dataBinder.setIgnoreNestedProperties(false);//false

        dataBinder.setIgnoreInvalidFields(false);//false

        dataBinder.setIgnoreUnknownFields(true);//true



        if(dataSourcePropertyValues == null){
            /*

            Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");

            Map<String, Object> values = new HashMap<>(rpr);

            // 排除已经设置的属性

            values.remove("type");

            values.remove("driverClassName");

            values.remove("url");

            values.remove("username");

            values.remove("password");

            dataSourcePropertyValues = new MutablePropertyValues(values);*/

        }

        dataBinder.bind(dataSourcePropertyValues);



    }





    @Override

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        System.out.println("DynamicDataSourceRegister.registerBeanDefinitions()");

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        // 将主数据源添加到更多数据源中

        targetDataSources.put("dataSource", defaultDataSource);

        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");

        // 添加更多数据源

        targetDataSources.putAll(customDataSources);

        for (String key : customDataSources.keySet()) {

            DynamicDataSourceContextHolder.dataSourceIds.add(key);

        }



        // 创建DynamicDataSource

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(DynamicDataSource.class);



        beanDefinition.setSynthetic(true);

        MutablePropertyValues mpv = beanDefinition.getPropertyValues();

        //添加属性：AbstractRoutingDataSource.defaultTargetDataSource

        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);

        mpv.addPropertyValue("targetDataSources", targetDataSources);

        registry.registerBeanDefinition("dataSource", beanDefinition);

    }



}