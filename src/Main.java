import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static ArrayBlockingQueue<String> Q1 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> Q2 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> Q3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        long e = 10;

        new Thread(() -> {
            String text;
            for (int i = 0; i < e; i++) {
                text = generateText("abc", 100_000);
                try {
                    Q1.put(text);
                    Q2.put(text);
                    Q3.put(text);
                } catch (InterruptedException x) {
                    return;
                }
            }
        }).start();


        new Thread(() -> {
            String text = null;
            long l = 0;
            String var;
            for (int i = 0; i < e; i++) {

                try {
                    var = Q1.take();

                    long l1 = 0;
                    for (int j = 0; j < var.length(); j++) {
                        if (var.charAt(j) == 'a') {
                            l1++;
                        }
                    }
                    if (l < l1) {
                        text = var;
                        l = l1;

                    }

                } catch (InterruptedException ex) {
                    return;
                }

            }
            //System.out.println("Текст: \""+text+"\" - содержит: "+l+" букв 'а'");
            System.out.println("Максимальное содержание букв 'а' - " + l + " шт.");
        }).start();

        new Thread(() -> {
            String text = null;
            long l = 0;
            String var;
            for (int i = 0; i < e; i++) {

                try {
                    var = Q2.take();

                    long l1 = 0;
                    for (int j = 0; j < var.length(); j++) {
                        if (var.charAt(j) == 'b') {
                            l1++;
                        }
                    }
                    if (l < l1) {
                        text = var;
                        l = l1;

                    }

                } catch (InterruptedException ex) {
                    return;
                }

            }
            //System.out.println("Текст: \""+text+"\" - содержит: "+l+" букв 'b'");
            System.out.println("Максимальное содержание букв 'b' - " + l + " шт.");
        }).start();

        new Thread(() -> {
            String text = null;
            long l = 0;
            String var;
            for (int i = 0; i < e; i++) {

                try {
                    var = Q3.take();

                    long l1 = 0;
                    for (int j = 0; j < var.length(); j++) {
                        if (var.charAt(j) == 'c') {
                            l1++;
                        }
                    }
                    if (l < l1) {
                        text = var;
                        l = l1;

                    }

                } catch (InterruptedException ex) {
                    return;
                }

            }
            //System.out.println("Текст: \""+text+"\" - содержит: "+l+" букв 'c'");
            System.out.println("Максимальное содержание букв 'c' - " + l + " шт.");
        }).start();

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}