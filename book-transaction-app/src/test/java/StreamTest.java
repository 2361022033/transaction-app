import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        /**
         * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
         * (2) 交易员都在哪些不同的城市工作过？
         * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
         * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
         * (5) 有没有交易员是在米兰工作的？
         * (6) 打印生活在剑桥的交易员的所有交易额。
         * (7) 所有交易中，最高的交易额是多少？
         * (8) 找到交易额最小的交易。
         */
        /**
         * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
         */
        @Test
        public void Test01(){
            transactions.stream().filter((e)->e.getYear()==2011)
                    .sorted(Comparator.comparing(Transaction::getYear)).forEach(System.out::println);
            Optional<Transaction> any = transactions.stream().findAny();
            Optional<Transaction> any2 = transactions.stream().findFirst();

            System.out.println(any);
            System.out.println(any2.isPresent());

        }
        /**
         * (2) 交易员都在哪些不同的城市工作过？
         */
        @Test
        public void Test02(){
            List<String> collect = transactions.stream().map((e) -> e.getTrader().getCity())
                    .distinct().collect(Collectors.toList());
            collect.forEach(System.out::println);
//        List<Trader> traderList = transactions.stream().map((e) -> e.getTrader())
//                .distinct().collect(Collectors.toList());
//        traderList.forEach(System.out::println);
        }

        /**
         * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
         */
        @Test
        public void Test03(){
            transactions.stream().map(e -> e.getTrader())
                    .filter(t -> t.getCity().equals("Cambridge"))
                    .distinct()
                    .sorted(Comparator.comparing(Trader::getName))
                    .forEach(System.out::println);
            System.out.println("====================================");
//        List<Trader> traders = transactions.stream().map((e) -> e.getTrader())
//                .filter(e -> e.getCity().equals("Cambridge"))
//                .distinct()
//                .sorted(Comparator.comparing(Trader::getName))
//                .collect(Collectors.toList());
//        traders.forEach(System.out::println);
            transactions.stream().map(Transaction::getTrader)
                    .filter(e -> e.getCity().equals("Cambridge"))
                    .distinct().sorted(Comparator.comparing(Trader::getName))
                    .forEach(System.out::println);
        }

        /**
         * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
         */
        @Test
        public void Test04(){
//        transactions.stream().map(e->e.getTrader())
//                    .sorted(Comparator.comparing(Trader::getName))
//                    .collect(Collectors
//                    .toCollection(LinkedHashSet::new))
//                    .forEach(System.out::println);
            //     transactions.stream().map(e->e.getTrader())
            //                .distinct()
            //                .sorted(Comparator.comparing(Trader::getName))
            //                .forEach(e-> System.out.println(e.getName()));
            String reduce = transactions.stream()
                    .map(transaction -> transaction.getTrader().getName())
                    .distinct()
                    .sorted()
                    //.reduce((n1, n2) -> n1 + n2);

                    //这里可以给空字符串 "" 作为起始值，就不会返回Optional，也不用使用reduce.get()获取值 字符串相加即拼接
                    //reduce("",(n1, n2) -> n1 + n2);

                    // joining 是拼接字符串最好用的方式 joining("-")可以 将adcd 返回为a-b-c-d
                    .collect(Collectors.joining());

            System.out.println(reduce);
        }

        /**
         * (5) 有没有交易员是在米兰工作的？
         */
        @Test
        public void Test05(){
            transactions.stream().map(Transaction::getTrader)
                    .filter(t -> t.getCity().equals("Milan"))
                    .collect(Collectors.toSet()).forEach(System.out::println);
        }

        /**
         * (6) 打印生活在剑桥的交易员的所有交易额。
         */
        @Test
        public void Test06(){
            transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                    .map(Transaction::getValue)
                    .forEach(System.out::println);

            //.reduce(0, (a, b) -> a + b); 销售总额
            //System.out.println(cambridge);

        }

        /**
         * (7) 所有交易中，最高的交易额是多少？
         */
        @Test
        public void Test07(){
//        Optional<Integer> max = transactions.stream().map(Transaction::getValue)
//                                            .max(Integer::compareTo);
            Optional<Integer> max = transactions.stream().map(Transaction::getValue)
                    //.reduce(Integer::max);
                    .reduce((a, b) -> Integer.max(a, b));
            System.out.println(max.get());

            System.out.println("=================");
            Integer integer = transactions.stream().map(Transaction::getValue)
                    // 先默认（从小到大排序） 再倒序（从大到小） 再找第一个
                    .sorted(Comparator.reverseOrder()).findFirst().get();
            System.out.println(integer);

        }

        /**
         * 找到交易额最小的交易。
         */
        @Test
        public void Test08(){
            Optional<Transaction> min = transactions.stream()
                    .min(Comparator.comparing(Transaction::getValue));
            System.out.println(min.get());
            System.out.println("=================");
            Optional<Transaction> first = transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getValue))
                    .findFirst();
            System.out.println(first.get());
        }
    }




@Data
@AllArgsConstructor
class Trader {
    /**
     * 姓名
     * */
    private final String name ;
    /**
     * 工作地点
     * */
    private final String city;
}

@Data
@AllArgsConstructor
class Transaction {
    /**
     * 交易员
     * */
    private final Trader trader;
    /**
     * 发生交易年限
     * */
    private final int year;
    /**
     * 交易额
     * */
    private final int value;
}


