import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class extract_sum_compare extends RecursiveTask<Integer> {
    private static final int threshold = 1;  //阈值
    private String[] list;
    private int start;
    private int end;

    public extract_sum_compare(String[] list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean can_compute = (end - start) < threshold;  //end-start等于0时才为true
        if (can_compute) {
            for (int i = start; i <= end; i++) {  //只循环start到start+1
                try {
                    Integer.parseInt(list[i]);  //字符串转换为整型数字
                } catch (Exception e) {  //非数字不能转换，抛出异常，跳出循环，结束本线程的计算
                    break;
                }
                sum = Integer.parseInt(list[i]);  //数字计入结果中
            }
        } else {
            int middle = (start + end) / 2;  //二分任务，直到每个小任务list长度仅为1

            //创建左右子任务
            extract_sum_compare left = new extract_sum_compare(this.list, start, middle);
            extract_sum_compare right = new extract_sum_compare(this.list, middle + 1, end);

            //开始并行计算，也可写成invokeAll(left, right);
            left.fork();
            right.fork();

            //合并计算结果
            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) {  //并行实现
        String[] list_1 = {"*", "%", "3", "#", "6", "~", "!", "2"};
        String[] list_2 = {"&", "¥", "@", "1", "4", ":", "2", "1"};

        ForkJoinPool fork_join_pool_1 = new ForkJoinPool();  //创建线程池
        extract_sum_compare test1 = new extract_sum_compare(list_1, 0, list_1.length - 1);  //创建任务
        int result1 = fork_join_pool_1.invoke(test1);  //invoke()返回计算结果
        System.out.println(result1);

        ForkJoinPool fork_join_pool_2 = new ForkJoinPool();
        extract_sum_compare test2 = new extract_sum_compare(list_2, 0, list_2.length - 1);
        int result2 = fork_join_pool_2.invoke(test2);
        System.out.println(result2);

        if (result1 > result2) {
            System.out.println("list_1中数字之和 > list_2中数字之和");
        } else if (result1 == result2) {
            System.out.println("list_1中数字之和 == list_2中数字之和");
        } else {
            System.out.println("list_1中数字之和 < list_2中数字之和");
        }
    }

    /*
    public static void main(String[] args) {  //串行实现
        String[] list_1 = {"*", "%", "3", "#", "6", "~", "!", "2"};
        String[] list_2 = {"&", "¥", "@", "1", "4", ":", "2", "1"};

        int sum1 = 0;
        for (int i = 0; i < list_1.length; i++) {
            try {
                Integer.parseInt(list_1[i]);
            } catch (Exception e) {
                continue;
            }
            //System.out.println(Integer.parseInt(list_1[i]));
            sum1 += Integer.parseInt(list_1[i]);
        }
        System.out.println(sum1);

        int sum2 = 0;
        for (int i = 0; i < list_2.length; i++) {
            try {
                Integer.parseInt(list_2[i]);
            } catch (Exception e) {
                continue;
            }
            //System.out.println(Integer.parseInt(list_2[i]));
            sum2 += Integer.parseInt(list_2[i]);
        }
        System.out.println(sum2);

        if (sum1 > sum2) {
            System.out.println("sum1 > sum2");
        } else if (sum1 == sum2) {
            System.out.println("sum1 == sum2");
        } else {
            System.out.println("sum1 < sum2");
        }
    }
    */
}
