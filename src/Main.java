import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static ArrayBlockingQueue<String> Q1 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> Q2 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> Q3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {
        long e = 10_000;
        AtomicReference<String> textA = new AtomicReference<>();
        AtomicReference<String> textB = new AtomicReference<>();
        AtomicReference<String> textC = new AtomicReference<>();

        Thread T1 = new Thread(() -> {
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
        });


        Thread analysisFlow1 = new Thread(() -> {
            String text = null;
            long l = 0;
            String var;
            for (int i = 0; i < e; i++) {

                try {
                    var = Q1.take();

                    long l1 = numberOfLetters(var, 'a');
                    if (l < l1) {
                        text = var;
                        l = l1;

                    }

                } catch (InterruptedException ex) {
                    return;
                }

            }
            textA.set(text);
        });

        Thread analysisFlow2 = new Thread(() -> {
            String text = null;
            long l = 0;
            String var;
            for (int i = 0; i < e; i++) {

                try {
                    var = Q2.take();

                    long l1 = numberOfLetters(var, 'b');
                    if (l < l1) {
                        text = var;
                        l = l1;

                    }

                } catch (InterruptedException ex) {
                    return;
                }

            }
            textB.set(text);
        });

        Thread analysisFlow3 = new Thread(() -> {
            String text = null;
            long l = 0;
            String var;
            for (int i = 0; i < e; i++) {

                try {
                    var = Q3.take();

                    long l1 = numberOfLetters(var, 'c');
                    if (l < l1) {
                        text = var;
                        l = l1;

                    }

                } catch (InterruptedException ex) {
                    return;
                }

            }
            textC.set(text);
        });

        T1.start();
        analysisFlow1.start();
        analysisFlow2.start();
        analysisFlow3.start();

        T1.join();
        analysisFlow1.join();
        analysisFlow2.join();
        analysisFlow3.join();

        System.out.println("Максимальное содержание букв 'a' - " + numberOfLetters(textA.get(), 'a') + " шт.");
        System.out.println("Максимальное содержание букв 'b' - " + numberOfLetters(textA.get(), 'b') + " шт.");
        System.out.println("Максимальное содержание букв 'c' - " + numberOfLetters(textA.get(), 'c') + " шт.");

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static long numberOfLetters(String text, Character a) {
        long l1 = 0;
        for (int j = 0; j < text.length(); j++) {
            if (text.charAt(j) == a) {
                l1++;
            }
        }
        return l1;
    }

}