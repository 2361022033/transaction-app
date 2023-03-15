package Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OneThreadTest  implements Runnable{
    //定义一个多个线程共享的票源
    private int TicketNums = 10;
    Lock lock = new ReentrantLock();
    //创建一个锁对象  保证其唯一性
    Object o = new Object();
    @Override
    public void run() {
        while (true){
            lock.lock();
            //2.在可能出现安全问题的代码前调用lock方法获取lock锁
            if(TicketNums>0) {
                try {
                    Thread.sleep(10);
                    if (TicketNums==5){
                        Thread.currentThread().interrupt();
                        System.out.println("手动中断第五个线程");
                    }else {
                        System.out.println(Thread.currentThread().getName()+"-->正在抢第"+TicketNums+"张票");
                    }
                    TicketNums--;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                return;
            }
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        OneThreadTest ticket = new OneThreadTest();
        new Thread(ticket, "小名").start();
        new Thread(ticket, "老师").start();
        new Thread(ticket, "黄牛党").start();
    }
}


class ThreadHaveResp implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("线程方法开始执行");
        System.out.println(Thread.currentThread().getName());
        return null;
    }

    public static void main(String[] args) throws Exception {
        ThreadHaveResp threadHaveResp = new ThreadHaveResp();
        Thread thread = new Thread();
        Object call = threadHaveResp.call();
    }

}
