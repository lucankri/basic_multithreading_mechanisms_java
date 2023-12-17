package ex01;

public class Program {
    private static final String CODE = "--count=";
    private static boolean eggRun = false;

    public static synchronized void printEgg() {
        if (eggRun) {
            try {
                Program.class.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Egg");
        eggRun = true;
        Program.class.notify();
    }

    public  static synchronized void printHen() {
        if (!eggRun) {
            try {
                Program.class.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Hen");
        eggRun = false;
        Program.class.notify();
    }

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("When starting the program, you need to write one argument: \"--count=VALUE\"");
            System.exit(-1);
        } else {
            int count;
            try {
                count = Integer.parseInt(args[0].substring(CODE.length()));
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                count = 0;
            }
            if (count <= 0) {
                System.out.println("Error: Incorrect input!");
                System.exit(-1);
            }

            Egg egg = new Egg(count);
            Hen hen = new Hen(count);
            egg.start();
            hen.start();
            try {
                egg.join();
                hen.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
