import cn.hutool.core.collection.CollUtil;
import org.junit.Test;

import java.util.ArrayList;


//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProviderApplication.class)
public class junfuTest {
    @Test
    public void dateformat() {
//        Date date = new Date();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
//        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//        System.out.println(localDateTime.format(format));
        ArrayList<String> objects = new ArrayList<>();
        ArrayList<String> objects2 = null;
        System.out.println(CollUtil.isEmpty(objects));
        System.out.println(CollUtil.isEmpty(objects2));
    }
}

class Solution {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 1};
        System.out.println(nums[0]);
        int len;
        if ((len = nums.length) == 0 || len == 1) {
            System.out.println(false);
        }
        //保持当前元素为最大, 如果下一个元素比当前元素还要大, 则不需要往前一个一个的比对
        for (int i = 1; i < len; i++) {
            if (nums[i] == nums[i - 1]) {
                System.out.println(true);
            } else if (nums[i] < nums[i - 1]) {
                for (int j = i - 2; j >= 0; j--) {
                    if (nums[i] == nums[j]) {
                        System.out.println(true);
                    }
                }
                nums[i] = nums[i - 1];
                nums[i - 1] = nums[i];
                nums[i] = nums[i - 1];
            }
        }
        System.out.println(false);
    }
}





