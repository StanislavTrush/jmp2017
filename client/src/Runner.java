
import threads.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            List<Path> files = Files.list(Paths.get("samples")).collect(Collectors.toList());
            CountDownLatch latch = new CountDownLatch(files.size());
            files.forEach(filePath -> executor.submit(new Reader(filePath, latch, queue)));

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            queue.forEach(Runner::post);
            System.out.println("Completed.");
        }).start();
    }

    private static void post(String json) {
        HttpURLConnection conn = null;
        OutputStream os = null;
        try {
            URL url = new URL("http://localhost:8080/jmp2017/availableActions/post");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain");
            os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            System.out.println("\nOutput from Server ....");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
