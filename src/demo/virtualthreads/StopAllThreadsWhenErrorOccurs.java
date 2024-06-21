package demo.virtualthreads;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class StopAllThreadsWhenErrorOccurs {

        public static void main(String[] args) {

            AtomicBoolean errorOccurred = new AtomicBoolean(false);
            ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();

            try (ExecutorService executor = Executors.newThreadPerTaskExecutor(virtualThreadFactory)) {
                CountDownLatch latch = new CountDownLatch(4);

                for (int i = 0; i < 4; i++) {
                    final int threadNum = i;
                    executor.submit(() -> {
                        try {
                            System.out.println("Thread " + threadNum + " started");
                            if (threadNum == 2) { // Simulate error in the third thread
                                throw new RuntimeException("Simulated error in thread " + threadNum);
                            }
                            Thread.sleep(2000); // Simulate some work
                            System.out.println("Thread " + threadNum + " completed");
                        } catch (Exception e) {
                            System.err.println("Error in thread " + threadNum + ": " + e.getMessage());
                            errorOccurred.set(true);
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                // Wait for all threads to complete or for an error to occur
                try {
                    while (latch.getCount() > 0 && !errorOccurred.get()) {
                        latch.await(100, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (errorOccurred.get()) {
                    System.out.println("An error occurred. Shutting down all threads.");
                    executor.shutdownNow();
                }
            }

            System.out.println("All threads have been stopped.");
        }
}