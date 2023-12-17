package ex03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {
    private static final String THREADS_COUNT = "--threadsCount=";
    private static int threadsCount = 0;

    public static void main(String[] args) {
        if (check(args)) {
            List<String> fileUrls = readUrlsFromFile("src/ex03/files_urls.txt");
            List<Thread> threads = new ArrayList<>();

            for (int i = 0; i < threadsCount; i++) {
                threads.add(new DownloadThread(fileUrls, i + 1));
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<String> readUrlsFromFile(String filePath) {
        List<String> fileUrls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileUrls.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return fileUrls;
    }

    public static boolean check(String[] args) {
        boolean result = true;
        if (args.length != 1) {
            System.out.println("When the program starts, there should be two arguments:\n" +
                    "\"--threadsCount=THREADS_COUNT\"!");
            result = false;
        }
        if (result && !args[0].startsWith(THREADS_COUNT)) {
            System.out.println("The second argument should be: \"--threadsCount=THREADS_COUNT\"!");
            result = false;
        }
        if (result) {
            try {
                threadsCount = Integer.parseInt(args[0].substring(THREADS_COUNT.length()));
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data in the argument \"--threadsCount=THREADS_COUNT\"!");
                result = false;
            }
            if (threadsCount < 2) {
                System.out.println("The number of threads must be more than one!");
                result = false;
            }
        }
        return result;
    }
}
