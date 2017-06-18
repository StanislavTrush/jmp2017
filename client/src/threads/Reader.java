package threads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Reader implements Runnable {
    private Path path;
    private CountDownLatch latch;
    private BlockingQueue<String> blockingQueue;

    public Reader(Path path, CountDownLatch latch, BlockingQueue<String> blockingQueue) {
        this.path = path;
        this.latch = latch;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            String content = new String(Files.readAllBytes(path));
            try {
                blockingQueue.put(content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latch.countDown();
    }
}
