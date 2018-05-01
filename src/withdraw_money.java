//张三、李四两个人，使用同一个账户，张三在柜台取钱，李四在ATM取钱

class Bank {
    static int money = 10000;

    public synchronized void counter_withdraw(int money1) {
        Bank.money = Bank.money - money1;
        System.out.println("ZhangSan gets $" + money1 + " from counter, and remains $" + Bank.money + " in the account");
    }

    public synchronized void atm_withdraw(int money2) {
        Bank.money = Bank.money - money2;
        System.out.println("LiSi gets $" + money2 + " from ATM, and remains $" + Bank.money + " in the account");
    }
}

class ZhangSan extends Thread {
    Bank bank;

    public ZhangSan(Bank same_account) {
        this.bank = same_account;
    }

    public void run() {
        while (Bank.money >= 100) {
            bank.counter_withdraw(100);
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class LiSi extends Thread {
    Bank bank;

    public LiSi(Bank same_account) {
        this.bank = same_account;
    }

    public void run() {
        while (Bank.money >= 300) {
            bank.atm_withdraw(300);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class withdraw_money {
    public static void main(String[] args) {
        Bank account = new Bank();
        ZhangSan p1 = new ZhangSan(account);
        LiSi p2 = new LiSi(account);
        p1.start();
        p2.start();
    }
}
