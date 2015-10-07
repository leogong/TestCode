package com.leo.test.concurrency;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * User: Leo
 * Date: 13-10-12
 * Time: 下午4:00
 *
 * Poison Pill Demo
 */
public class IndexingService {
    private static final File POISON = new File("");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> queue = new ArrayBlockingQueue<File>(10);


    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    class CrawlerThread extends Thread {
        public void run() {
            try {
                crawl(new File("/home/leo/test"));
            } catch (InterruptedException e) { /* fall through */
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e1) { /* retry */ }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {

        }
    }

    class IndexerThread extends Thread {
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISON)
                        break;
                    else
                        indexFile(file);
                }
            } catch (InterruptedException consumed) {
            }
        }

        private void indexFile(File file) {

        }


    }

}
