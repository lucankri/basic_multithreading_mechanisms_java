package ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {

    private static final String ONE_ARRAY_SIZE = "--arraySize=";
    private static final String TWO_THREADS_COUNT = "--threadsCount=";

    private static int arraySize = 0;
    private static int threadsCount = 0;

    private static List<Integer> listArray;
    public static void main(String[] args) {
        if (check(args)) {
            Random random = new Random();
            listArray = new ArrayList<>(arraySize);
            for (int i = 0; i < arraySize; i++) {
                listArray.add(random.nextInt((1000 - (-1000)) + 1) + (-1000));
            }
            for (Integer el : listArray) {
                System.out.print(el + " ");
            }
            System.out.println();
            arrayStandardMethodArraySum(listArray);
            List<Thread> threadList = new ArrayList<>(threadsCount);

            int rangeThread = arraySize / threadsCount;
            int begin = 0, end = 0;
            for (int i = 0; i < threadsCount; i++) {
                if (i == threadsCount - 1) {
                    begin = end;
                    end = arraySize;
                    threadList.add(new CalculateAmount(begin, end, (i + 1)));
                } else {
                    begin = i * rangeThread;
                    end = (i + 1) * rangeThread;
                    threadList.add(new CalculateAmount(begin, end, (i + 1)));
                }
            }
            ////////////////////////////////////////////////////////////////
            for (Thread thread : threadList) {
                thread.start();
            }

            for (Thread thread : threadList) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Sum by threads: " + CalculateAmount.getTotalAmount());
        }
    }

    public static List<Integer> getListArray() {
        return listArray;
    }

    public static void arrayStandardMethodArraySum(List<Integer> arrayList) {
        int sum = 0;
        for (Integer el : arrayList) {
            sum += el;
        }
        System.out.println("Sum: " + sum);
    }

    public static boolean check(String[] args) {
        boolean result = true;
        if (args.length != 2) {
            System.out.println("When the program starts, there should be two arguments:\n" +
                    "\"--arraySize=SIZE\" and \"--threadsCount=THREADS_COUNT\"!");
            result = false;
        }
        if (result && !args[0].startsWith(ONE_ARRAY_SIZE)) {
            System.out.println("The first argument should be: \"--arraySize=SIZE\"!");
            result = false;
        }
        if (result && !args[1].startsWith(TWO_THREADS_COUNT)) {
            System.out.println("The second argument should be: \"--threadsCount=THREADS_COUNT\"!");
            result = false;
        }
        if (result) {
            try {
                arraySize = Integer.parseInt(args[0].substring(ONE_ARRAY_SIZE.length()));
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data in the argument \"--arraySize=SIZE\"!");
                result = false;
            }
            try {
                threadsCount = Integer.parseInt(args[1].substring(TWO_THREADS_COUNT.length()));
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data in the argument \"--threadsCount=THREADS_COUNT\"!");
                result = false;
            }
        }

        if (result && (arraySize <= 0 || arraySize > 2_000_000)) {
            System.out.println("Impossible size of array!");
            result = false;
        }
        if (result && (threadsCount > arraySize)) {
            System.out.println("The number of threads must be less than the array!");
            result = false;
        }


        return result;
    }
}
