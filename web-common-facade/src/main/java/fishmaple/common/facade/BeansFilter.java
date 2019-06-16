package fishmaple.common.facade;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeansFilter implements ApplicationContextAware {
    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        printAllBeans(this.applicationContext);
    }

    private void printAllBeans(ApplicationContext applicationContext){
        String[] beans = applicationContext
                .getBeanDefinitionNames();  System.out.println("***********************包检查开始******************************");
        for (String beanName : beans) {
            Class<?> beanType = applicationContext
                    .getType(beanName);

            System.out.println("BeanName:" + beanName+//"     Bean的类型：" + beanType+
                    "     Bean所在的包：" + beanType.getPackage());

        }System.out.println("***********************包检查完毕******************************");

    }
}
