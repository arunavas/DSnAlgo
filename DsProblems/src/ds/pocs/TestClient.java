package com.relcache.core.pocs;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class TestClient {
    public static void main(String[] args) {
        final int TOTAL = 10;
        int c = 1;
        CountDownLatch cdl = new CountDownLatch(c);
        long s = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            new Worker(i, TOTAL, cdl).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - s;
        long rqs = TOTAL * 2 * 10;
        long tps = rqs / time * 1000;
        System.out.println("MAIN performed total " + rqs + " operations in " + time + " millis in " + tps + " tps!");
    }

    static class Worker extends Thread {
        int id;
        int start;
        int end;
        int total;
        CountDownLatch latch;

        public Worker(int id, int chunk, CountDownLatch latch) {
            this.id = id;
            this.total = chunk;
            this.start = id * total;
            this.end = start + total;
            this.latch = latch;
        }
        @Override
        public void run() {
            System.out.println("Worker_" + id + " Started.");
            try {
                Socket socket = new Socket("127.0.0.1", 1990);
                long s = System.currentTimeMillis();
                int fc = 0;
                for (int i = start; i < end; i++) {
                    System.out.println(id + ": Processing " + i + "/" + end + " | " + socket.isConnected() + "," + socket.isClosed() + "," + socket.isBound());
                    if (socket.isClosed()) {
                        socket = new Socket("127.0.0.1", 1990);
                    }
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    String k = "KEY_" + id + "_" + i;
                    String v = "VAL_" + id + "_" + i;
                    dos.writeUTF("P|" + k + "|" + v);
                    dos.flush();
                    dos.writeUTF("G|" + k);
                    dos.flush();
                    String rv = dis.readUTF();
                    if (!rv.equals(v)) {
                        fc++;
                        //System.err.println("Result: " + rv + " <> Type: " + v);
                    }
                    /*dos.writeUTF("D|" + k);
                    dos.flush();
                    dos.writeUTF("G|" + k);
                    dos.flush();
                    rv = dis.readUTF();
                    if (!rv.equals("[NO_DATA]")) {
                        fc++;
                        //System.err.println("Result: " + rv + " should not be present after deletion!");
                    }*/
                    if (i == (end - 1)) {
                        dos.writeUTF("QUIT");
                        dos.flush();
                    }
                    dos.close();
                    dis.close();
                }
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
