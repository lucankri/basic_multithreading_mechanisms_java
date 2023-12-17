package ex02;

import java.util.List;

public class CalculateAmount extends Thread {
    private final int begin;
    private final int end;
    private final int threadNumber;
    private static int totalAmount = 0;
    CalculateAmount(int begin, int end, int threadNumber) {
        this.begin = begin;
        this.end = end;
        this.threadNumber = threadNumber;
    }


    public static int getTotalAmount() {
        return totalAmount;
    }

    private static synchronized Integer calculationSum(int begin, int end) {
        List<Integer> array = Program.getListArray();
        Integer sum = 0;
        for (int i = begin; i < end; i++) {
            sum += array.get(i);
        }
        return sum;
    }

    private synchronized void printSum(int begin, int end, int sum) {
        System.out.println("Thread " + threadNumber +
                ": from " + begin + " to " + (end - 1) + " sum is " + sum);
        totalAmount += sum;
    }

    @Override
    public void run() {
        printSum(begin, end, calculationSum(begin, end));
    }
}
