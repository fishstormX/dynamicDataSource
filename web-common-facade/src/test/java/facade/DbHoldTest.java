package facade;

import fishmaple.common.MainApplication;
import fishmaple.common.facade.DbHold;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class DbHoldTest {
    @Autowired
    DbHold dbHold;

    @Test
    public void getRandomOne(){
        dbHold.getRandomOne();
    }
}
