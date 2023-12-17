package ex00;

public class Hen extends Thread {
    public Hen(int count) {
        super(() -> {
           for (int i = 0; i < count; i++) {
               System.out.println("Hen");
           }
        });
    }

}
