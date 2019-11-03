package com.relcache.core.pocs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {

    private static BufferedImage bufferedImage;

    public static void main(String[] args) throws IOException {
        String path = encodeLarge();
        decodeLarge(path);
    }

    private static void decodeLarge(String path) throws IOException {
        File file = new File(path);
        //BufferedImage img = ImageIO.read(file);
        int[] rgbs = deserialize(file).rgbs;

        //int h = img.getHeight(), w = img.getWidth();
        StringBuilder sb = new StringBuilder();
        Color c = null;

        for (int i = 0; i < rgbs.length; i++) {
            //for (int j = 0; j < h; j++) {
                try {
                    c = new Color(rgbs[i]);
                    if (i == 1/* && j == 1*/) {
                        System.out.println("c: " + c.getRGB() + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
                    }
                    sb.append((char)c.getRed()).append((char)c.getGreen()).append((char)c.getBlue());
                } catch (Exception ex) {
                    System.err.println("Ex: " + i);
                }
            //}
        }
        Files.write(Paths.get("/Users/arunavasaha/personal/large_d"), sb.toString().getBytes());
    }

    public static void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

    private static String encodeLarge() throws IOException {
        String content = Files.readAllLines(Paths.get("/Users/arunavasaha/personal/large.txt")).stream().collect(Collectors.joining("\n"));
        /*List<Character> l = new ArrayList<>();
        for (int j = 0; j < content.length(); j++) {
            char c = content.charAt(j);
            int x = c;
            if (x > 255) {
                l.add(c);
            }
        }
        System.out.println(l.size());
        System.exit(0);*/
        int by3 = content.length() / 3;
        if (content.length() % 3 != 0) {
            by3 += 1;
        }
        int[] rgbs = new int[by3];
        int sq = Long.valueOf(Math.round(Math.sqrt(by3))).intValue();
        int h = sq, w = sq;
        if (sq * sq != by3) {
            w += 1;
        }
        System.out.println(Math.pow(sq, 2) + " " + sq + "(" + w + "x" + h + ")");
        System.out.println(content.length() + " " + (content.length() / 3));
        Map<Character, Integer> cMap = new HashMap<>();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        int i = 0;
        int x = 0, y = 0;
        int idx = 0;
        while (i + 3 < content.length()) {
            char rC = content.charAt(i++);
            char gC = content.charAt(i++);
            char bC = content.charAt(i++);
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            try {
                Color c = new Color(r, g, b, 255);
                if (x == 1 && y == 0) {
                    System.out.println("c: " + c.getRGB() + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
                }
                img.setRGB(x++, y, c.getRGB());
                rgbs[idx++] = c.getRGB();
                if (x == w) {
                    x = 0;
                    y++;
                }
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        if (i < content.length()) {
            char rC = content.charAt(i++);
            char gC = i < content.length() ? content.charAt(i++) : 0;
            char bC = i < content.length() ? content.charAt(i) : 0;
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            try {
                Color c = new Color(r, g, b);
                img.setRGB(x, y, c.getRGB());
                rgbs[idx] = c.getRGB();
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println(cMap.size());
        File file = new File("/Users/arunavasaha/personal/large.tgt");
        //test(rgbs, w, h);
        //ImageIO.write(img, "BMP", file);
        serialize(new RGBs(rgbs), file);
        bufferedImage = img;
        return "/Users/arunavasaha/personal/large.tgt";
    }

    private static void serialize(Object obj, File file) throws IOException {
        FileOutputStream fout=new FileOutputStream(file);
        ObjectOutputStream out=new ObjectOutputStream(fout);
        out.writeObject(obj);
        out.flush();
        //closing the stream
        out.close();
    }

    public static RGBs deserialize(File file) {
        RGBs s = null;
        try{
            //Creating stream to read the object
            ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
            s = (RGBs) in.readObject();
            //closing the stream
            in.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void test(int[] rgbs, int width, int height) throws IOException {
        DataBuffer rgbData = new DataBufferInt(rgbs, rgbs.length);
        System.out.println(rgbs.length);
        WritableRaster raster = Raster.createPackedRaster(rgbData, width, height, width,
                new int[]{0xff0000, 0xff00, 0xff},
                null);

        ColorModel colorModel = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);

        BufferedImage img = new BufferedImage(colorModel, raster, false, null);
        File file = new File("/Users/arunavasaha/personal/large.png");
        ImageIO.write(img, "png", file);
    }

    public static void main2(String[] args) throws IOException {
        String content = String.join("\n", Files.readAllLines(Paths.get("/Users/arunavasaha/personal/enwik8")));
        Map<Character, Float> m = new HashMap<>();
        for (int j = 0; j < content.length(); j++) {
            char c = content.charAt(j);
            int x = c;
            if (x > 255) {
                m.put(c, toB255(x));
                System.out.println(c + " " + x + " " + m.get(c) + " " + b255ToChar(m.get(c)));

                if (m.size() == 10) {
                    j = content.length();
                }
            }
        }
        System.out.println(m);
        //System.exit(0);
        long by3 = content.length() / 3;
        if (content.length() % 3 != 0) {
            by3 += 1;
        }
        int sq = Long.valueOf(Math.round(Math.sqrt(by3))).intValue();
        int h = sq, w = sq;
        if (sq * sq != by3) {
            w += 1;
        }
        System.out.println(Math.pow(sq, 2) + " " + sq + "(" + w + "x" + h + ")");
        System.out.println(content.length() + " " + (content.length() / 3));
        Map<Character, Integer> cMap = new HashMap<>();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int i = 0;
        int x = 0, y = 0;
        while (i + 3 < content.length()) {
            char rC = content.charAt(i++);
            char gC = content.charAt(i++);
            char bC = content.charAt(i++);
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            Color c = null;
            try {
                if (r > 255 || g > 255 || b > 255) {
                    c = new Color(toB255(r), toB255(g), toB255(b));
                } else {
                    c = new Color(r, g, b);
                }
                img.setRGB(x++, y, c.getRGB());
                if (x == w) {
                    x = 0;
                    y++;
                }
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        if (i < content.length()) {
            char rC = content.charAt(i++);
            char gC = i < content.length() ? content.charAt(i++) : 0;
            char bC = i < content.length() ? content.charAt(i) : 0;
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            Color c = null;
            try {
                if (r > 255 || g > 255 || b > 255) {
                    c = new Color(toB255(r), toB255(g), toB255(b));
                } else {
                    c = new Color(r, g, b);
                }
                img.setRGB(x++, y, c.getRGB());
                if (x == w) {
                    x = 0;
                    y++;
                }
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println(cMap.size());
        File file = new File("/Users/arunavasaha/personal/enwik8.jpg");
        ImageIO.write(img, "jpg", file);
    }

    private static char b255ToChar(float v) {
        String s = String.valueOf(v);
        int x = Integer.parseInt(new StringBuilder(s.substring(s.indexOf('.') + 1)).reverse().toString());
        System.out.println(s + " " + x);
        return (char)x;
    }

    private static float toB255(int n) {
        return Float.valueOf("0." + (new StringBuilder(String.valueOf(n)).reverse()));
    }

    private static float getB255Number(int n) {
        if (n <= 255) return n;
        int x = n;
        int y = 0;
        boolean flag = true;
        int d = 10;
        while (n / d > 255) {
            d *= 10;
        }
        return Float.valueOf(n / d + "." + n % d);
    }

}
