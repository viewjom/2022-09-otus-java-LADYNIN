package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Go {
    private static final Logger logger = LoggerFactory.getLogger(Go.class);
    private String last = "Thread2";

    private synchronized void action(String message) {
        var count = 0;
        var index = 1;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (last.equals(message)) {
                    this.wait();
                }

                System.out.printf("%s %d\n", Thread.currentThread().getName(), count = count + index);

                if (count == 1) {
                    index = 1;
                } else if (count == 10) {
                    index = -1;
                }

                last = message;
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Go go = new Go();
        new Thread(() -> go.action("Thread1")).start();
        new Thread(() -> go.action("Thread2")).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
