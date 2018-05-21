import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class java_book_sum extends RecursiveTask<Integer> {
    private static final int threshold = 1;  //阈值
    private String[] list;
    private int start;
    private int end;

    public java_book_sum(String[] list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int count = 0;
        boolean can_compute = (end - start) < threshold;  //end-start等于0时才为true
        if (can_compute) {
            if (list[start].matches("book")) {  //matches()函数匹配book
                count++;
            }
        } else {
            int middle = (start + end) / 2;  //二分任务，直到每个小任务list长度仅为1

            //创建左右子任务
            java_book_sum left = new java_book_sum(this.list, start, middle);
            java_book_sum right = new java_book_sum(this.list, middle + 1, end);

            //开始并行计算，也可写成invokeAll(left, right);
            left.fork();
            right.fork();

            //合并计算结果
            count = left.join() + right.join();
        }
        return count;
    }

    public static void main(String[] args) {
        String[] list1 = null;
        String[] list2 = null;

        try {
            //读入file_1.dat，将其中的内容转换为字符串数组list1（去除空格，以逗号分隔）
            String file_path1 = "D:\\Kent's Workspace\\java\\Parallel_Computing\\2nd\\src\\file_1.dat";
            File file1 = new File(file_path1);
            FileInputStream stream1 = new FileInputStream(file1);
            InputStreamReader reader1 = new InputStreamReader(stream1);
            BufferedReader buffer1 = new BufferedReader(reader1);

            String line1;
            while ((line1 = buffer1.readLine()) != null) {
                list1 = line1.trim().replace(" ", "").split(",");
            }
            //for (int i = 0; i < list1.length; i++) {
            //    System.out.println(list1[i]);  //String[] list1 = {"and","with","we","me","university","with","book","computer","country","book"};（2个book）
            //}

            //读入file_2.dat，将其中的内容转换为字符串数组list2（去除空格，以逗号分隔）
            String file_path2 = "D:\\Kent's Workspace\\java\\Parallel_Computing\\2nd\\src\\file_2.dat";
            File file2 = new File(file_path2);
            FileInputStream stream2 = new FileInputStream(file2);
            InputStreamReader reader2 = new InputStreamReader(stream2);
            BufferedReader buffer2 = new BufferedReader(reader2);

            String line2;
            while ((line2 = buffer2.readLine()) != null) {
                list2 = line2.trim().replace(" ", "").split(",");
            }
            //for (int i = 0; i < list2.length; i++) {
            //    System.out.println(list2[i]);  //String[] list2 = {"bag","boy","book","school","teacher","student","book","book"};（3个book）
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

        ForkJoinPool fork_join_pool_1 = new ForkJoinPool();  //创建线程池
        java_book_sum test1 = new java_book_sum(list1, 0, list1.length - 1);  //创建任务
        int result1 = fork_join_pool_1.invoke(test1);  //invoke()返回计算结果
        System.out.println("book在file_1.dat中的出现次数：" + result1);

        ForkJoinPool fork_join_pool_2 = new ForkJoinPool();
        java_book_sum test2 = new java_book_sum(list2, 0, list2.length - 1);
        int result2 = fork_join_pool_2.invoke(test2);
        System.out.println("book在file_2.dat中的出现次数：" + result2);

        int result = result1 + result2;
        System.out.println("book出现的总次数：" + result);
    }
}
