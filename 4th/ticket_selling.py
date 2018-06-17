# 4个售票窗口同时出售30张电影票

import threading
import time


class BoxOffice(threading.Thread):
    def __init__(self, num):
        threading.Thread.__init__(self)
        self.setName('Window ' + str(num))  # 售票窗口号

    def run(self):
        global ticket
        global lock

        while ticket > 0:
            lock.acquire()  # 加锁
            print(self.getName() + ' sells ticket ' + str(ticket))
            ticket = ticket - 1  # 票数减1
            lock.release()  # 解锁
            time.sleep(0.1)  # 每次售票完毕后休眠100ms


if __name__ == '__main__':
    ticket = 30  # 共30张电影票

    lock = threading.Lock()  # 创建锁

    threads = []
    for i in range(4):  # 创建4个线程
        threads.append(BoxOffice(i + 1))

    for t in threads:
        t.start()
