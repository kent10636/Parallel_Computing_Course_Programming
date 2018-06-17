# 张三、李四两个人，使用同一个账户，张三在柜台取钱，李四在ATM取钱

import threading
import time


class Bank:
    def __init__(self):
        self.money = 5000  # 初始5000元
        self.lock = threading.Lock()  # 创建锁

    def counter_withdraw(self, money1):
        self.lock.acquire()  # 加锁
        self.money = self.money - money1
        print('ZhangSan gets $' + str(money1) + ' from counter, and remains $' + str(self.money) + ' in the account')
        self.lock.release()  # 解锁

    def atm_withdraw(self, money2):
        self.lock.acquire()  # 加锁
        self.money = self.money - money2
        print('LiSi gets $' + str(money2) + ' from ATM, and remains $' + str(self.money) + ' in the account')
        self.lock.release()  # 解锁


class ZhangSan(threading.Thread):
    def __init__(self, same_account):  # 初始化为同一账户
        threading.Thread.__init__(self)
        self.bank = same_account

    def run(self):
        while self.bank.money >= 100:
            self.bank.counter_withdraw(100)  # 柜台取钱100
            time.sleep(0.05)


class LiSi(threading.Thread):
    def __init__(self, same_account):  # 初始化为同一账户
        threading.Thread.__init__(self)
        self.bank = same_account

    def run(self):
        while self.bank.money >= 300:
            self.bank.atm_withdraw(300)  # ATM取钱300
            time.sleep(0.1)


if __name__ == '__main__':
    account = Bank()

    p1 = ZhangSan(account)
    p2 = LiSi(account)
    p1.start()
    p2.start()
