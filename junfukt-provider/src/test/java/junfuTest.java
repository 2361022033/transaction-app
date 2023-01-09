import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


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
