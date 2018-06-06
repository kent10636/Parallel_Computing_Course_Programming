def book_sum():
    try:
        f1 = open('./file_1.dat', 'r')
    except IOError:
        print('File open fail!')

    try:
        f2 = open('./file_2.dat', 'r')
    except IOError:
        print('File open fail!')

    line1 = f1.readline()
    line2 = f2.readline()

    word1 = line1.replace(' ', '').split(',')
    word2 = line2.replace(' ', '').split(',')

    sum1 = 0
    for i in word1:
        if i == 'book':
            sum1 = sum1 + 1
    print('file_1.dat中book出现的次数：', sum1)

    sum2 = 0
    for i in word2:
        if i == 'book':
            sum2 = sum2 + 1
    print('file_2.dat中book出现的次数：', sum2)

    print('book出现的总次数：', sum1 + sum2)

    filename = 'python_book_sum_output.txt'
    with open(filename, 'w')as f:
        f.write('file_1.dat中book出现的次数：' + str(sum1) + '\n')
        f.write('file_2.dat中book出现的次数：' + str(sum2) + '\n')
        f.write('book出现的总次数：' + str(sum1 + sum2) + '\n')
        f.close()


if __name__ == '__main__':
    book_sum()
