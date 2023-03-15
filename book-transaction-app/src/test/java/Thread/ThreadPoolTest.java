package Thread;

import com.ProviderApplication;
import com.controller.book.dto.req.BookPageReq;
import com.domain.entity.BookInfo;
import com.domain.mapper.BookInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class ThreadPoolTest{

    @Resource
    private BookInfoMapper bookInfoMapper;
    @Resource
    @Qualifier("onSaleNumServiceExecutor")
    private Executor onSaleNumServiceExecutor;

    @Test
    public void test2() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<BookInfo>> submit = executorService.submit(() -> bookInfoMapper.page(new BookPageReq()));
        System.out.println(new Date());
        // 超时没取到会抛出异常java.util.concurrent.TimeoutException
        // List<BookInfo> out = submit.get(1, TimeUnit.MILLISECONDS);
        List<BookInfo> out = submit.get(3, TimeUnit.SECONDS);
        System.out.println(new Date());
        System.out.println(out);
    }

    @Test
    public void test3() throws Exception {
        CompletableFuture<List<BookInfo>>  future =  CompletableFuture.supplyAsync(()-> {
            System.out.println("当前线程:"+Thread.currentThread().getClass());
            return  bookInfoMapper.page(new BookPageReq());
        },onSaleNumServiceExecutor);
        future.get();
    }


    @Test
    public void test1() throws Exception {
        int corePoolSize = 5;
        int maximumPoolSize = 10;
        long keepAliveTime = 5;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(10);
        // 抛出异常
        RejectedExecutionHandler handler1 = new ThreadPoolExecutor.AbortPolicy();
        // 如果线程池没有关闭,调用当前线程直接运行,如果用此策略,适合并发小且性能要求不够速度快,提交速度不宜过快,不然线程变多,cpu不够用
        RejectedExecutionHandler handler2 = new ThreadPoolExecutor.CallerRunsPolicy();
        // 直接丢弃此次任务
        RejectedExecutionHandler handler3 = new ThreadPoolExecutor.DiscardPolicy();
        // 将等待(阻塞)队列中最老的任务丢弃，压入新的
        RejectedExecutionHandler handler4 = new ThreadPoolExecutor.DiscardOldestPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, handler1);
        for (int i = 0; i < 100; i++) {
            try {
                executor.execute(new Thread(() -> log.info(Thread.currentThread().getName() + " is running")));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        executor.shutdown();
    }

}
