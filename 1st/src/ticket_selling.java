//4个售票窗口同时出售30张电影票

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoxOffice implements Runnable {
    private static int ticket = 30;  //共30张电影票
    private Lock locker = new ReentrantLock();

    public void run() {
        while (true) {
            locker.lock();  //加锁
            if (ticket > 0) {
                try {
                    Thread.sleep(100);  //每次先休眠100ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " sells ticket " + (ticket));
                ticket--;  //减去已卖出的票
            }
            locker.unlock();  //解锁
        }
    }
}

public class ticket_selling {
    public static void main(String[] args) {
        BoxOffice seller = new BoxOffice();
        Thread t1 = new Thread(seller, "Window 1");
        Thread t2 = new Thread(seller, "Window 2");
        Thread t3 = new Thread(seller, "Window 3");
        Thread t4 = new Thread(seller, "Window 4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
