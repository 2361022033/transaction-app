import cn.hutool.extra.spring.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = junfuTest.class)
public class junfuTest {
    @Test
    public void dateformat() {
        Date date = new Date();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println(localDateTime.format(format));
    }
}
