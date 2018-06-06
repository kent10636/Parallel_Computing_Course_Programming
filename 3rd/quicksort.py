def quicksort(list, low, high):
    if low < high:
        middle = partition(list, low, high)
        quicksort(list, low, middle - 1)
        quicksort(list, middle + 1, high)


def partition(list, low, high):
    x = list[high]  # 最后一个数作为pivot
    i = low - 1
    for j in range(low, high):
        if list[j] <= x:
            i = i + 1  # i表示小于pivot的最后一位数
            list[i], list[j] = list[j], list[i]
    list[i + 1], list[high] = list[high], list[i + 1]  # j遍历完整个list后，最后一个数pivot换到小于pivot和大于pivot之间
    return i + 1  # 返回分界点


if __name__ == '__main__':
    list = [3, 7, 12, 5, 3, 10, 11, 9, 4, 2, 4]

    print(list)
    quicksort(list, 0, len(list) - 1)  # low=0,high=10
    print(list)  # list = [2, 3, 3, 4, 4, 5, 7, 9, 10, 11, 12]

    filename = 'quicksort_output.txt'
    try:
        with open(filename, 'w')as f:
            f.write(str(list))
            f.close()
    except IOError:
        print('File open fail!')
