package ex01;

public class Egg extends Thread {
    public Egg(int count) {
        super(() -> {
            for (int i = 0; i < count; i++) {
                Program.printEgg();
            }
        });
    }
}
